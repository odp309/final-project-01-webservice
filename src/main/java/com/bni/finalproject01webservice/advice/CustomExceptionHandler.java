package com.bni.finalproject01webservice.advice;

import com.bni.finalproject01webservice.configuration.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAllExceptions(Exception ex, WebRequest request) {
        ProblemDetail errorDetail = null;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Authentication Failure");
        } else if (ex instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Not Authorized");
        } else if (ex instanceof RefreshTokenExpiredException) {
            status = HttpStatus.UNAUTHORIZED;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Refresh token expired");
        } else if (ex instanceof RefreshTokenException) {
            status = HttpStatus.FORBIDDEN;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Refresh token invalid");
        } else if (ex instanceof UserException && ex.getMessage().equals("Email already exist!")) {
            status = HttpStatus.CONFLICT;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Email already registered");
        } else if (ex instanceof UserException && ex.getMessage().equals("Employee is not active!")) {
            status = HttpStatus.FORBIDDEN;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "This account is not active");
        } else if (ex instanceof UserException && ex.getMessage().equals("User not found!")) {
            status = HttpStatus.NOT_FOUND;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "User not found");
        } else if (ex instanceof UserException && ex.getMessage().equals("Invalid pin!")) {
            status = HttpStatus.UNAUTHORIZED;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Pin is invalid");
        } else if (ex instanceof WalletException && ex.getMessage().equals("Wallet already exist!")) {
            status = HttpStatus.CONFLICT;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "This type of wallet already exists");
        } else if (ex instanceof WalletException && ex.getMessage().equals("Wallet not found!")) {
            status = HttpStatus.NOT_FOUND;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Wallet not found");
        } else if (ex instanceof TransactionException && ex.getMessage().equals("Transfers to the same account are not allowed!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Self-transfers are not permitted");
        } else if (ex instanceof TransactionException && ex.getMessage().equals("Balance insufficient!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Insufficient funds");
        } else if (ex instanceof TransactionException && ex.getMessage().equals("Wallet balance insufficient!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Insufficient wallet funds");
        } else if (ex instanceof TransactionException && ex.getMessage().equals("Amount is less than the minimum buy!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Buy amount is less than the minimum buy");
        } else if (ex instanceof TransactionException && ex.getMessage().equals("Amount is less than the minimum transfer!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Transfer amount is less than the minimum transfer");
        } else if (ex instanceof TransactionException && ex.getMessage().equals("Amount is less than the minimum deposit!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Deposit amount is less than the minimum deposit");
        } else if (ex instanceof TransactionException && ex.getMessage().equals("Amount is less than the minimum sell!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Sell amount is less than the minimum sell");
        } else if (ex instanceof TransactionException && ex.getMessage().equals("Amount is less than the minimum withdrawal!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Withdrawal amount is less than the minimum withdrawal");
        } else if (ex instanceof WithdrawalException && ex.getMessage().equals("Workdays must be greater than zero!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Workdays cannot be zero");
        } else if (ex instanceof WithdrawalException && ex.getMessage().equals("Workdays cannot be larger than 5!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Workdays maximum is five");
        } else if (ex instanceof WithdrawalException && ex.getMessage().equals("The selected date is a weekend!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Cannot select weekend date");
        } else if (ex instanceof WithdrawalException && ex.getMessage().equals("The selected date is a holiday!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Cannot select holiday date");
        } else if (ex instanceof WithdrawalException && ex.getMessage().equals("User is in cooldown!")) {
            status = HttpStatus.FORBIDDEN;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "User in cooldown");
        } else if (ex instanceof WithdrawalException && ex.getMessage().equals("User already had ongoing withdrawal!")) {
            status = HttpStatus.FORBIDDEN;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Withdrawal can only be one at a time");
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            status = HttpStatus.BAD_REQUEST;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), "Invalid method argument: " + ex.getMessage());
        } else {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), "An unexpected error occurred");
            errorDetail.setProperty("error_message", ex.getMessage());
        }

        return new ResponseEntity<>(errorDetail, status);
    }
}
