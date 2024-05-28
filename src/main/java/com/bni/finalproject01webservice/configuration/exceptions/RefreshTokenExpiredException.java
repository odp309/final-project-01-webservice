package com.bni.finalproject01webservice.configuration.exceptions;

public class RefreshTokenExpiredException extends RuntimeException {

    public RefreshTokenExpiredException(String message) {
        super(message);
    }

    public RefreshTokenExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
