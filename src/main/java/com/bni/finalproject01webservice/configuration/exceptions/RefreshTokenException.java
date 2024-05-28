package com.bni.finalproject01webservice.configuration.exceptions;

public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException(String message) {
        super(message);
    }

    public RefreshTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
