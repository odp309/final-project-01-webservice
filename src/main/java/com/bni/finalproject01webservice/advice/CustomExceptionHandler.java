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
        } else if (ex instanceof TransactionException && ex.getMessage().equals("Balance insufficient!")) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Insufficient funds");
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
