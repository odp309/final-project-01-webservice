package com.bni.finalproject01webservice.configuration.exceptions;

public class WalletException extends RuntimeException {

    public WalletException(String message) {
        super(message);
    }

    public WalletException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
