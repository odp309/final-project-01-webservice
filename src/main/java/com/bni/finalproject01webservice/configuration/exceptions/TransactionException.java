package com.bni.finalproject01webservice.configuration.exceptions;

public class TransactionException extends RuntimeException {

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
