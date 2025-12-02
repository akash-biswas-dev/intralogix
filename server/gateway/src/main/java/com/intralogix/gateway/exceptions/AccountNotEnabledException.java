package com.intralogix.gateway.exceptions;

public class AccountNotEnabledException extends RuntimeException {
    public AccountNotEnabledException(String message) {
        super(message);
    }
}
