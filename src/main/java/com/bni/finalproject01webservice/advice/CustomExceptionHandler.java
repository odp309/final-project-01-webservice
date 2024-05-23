package com.bni.finalproject01webservice.advice;

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
