package com.bni.finalproject01webservice.configuration.exceptions;

public class WithdrawalException extends RuntimeException {

    public WithdrawalException(String message) {
        super(message);
    }

    public WithdrawalException(String msg, Throwable cause) {
        super(msg, cause);
    }
}