package com.nexussphere.common.exceptions;

import lombok.Getter;

@Getter
public class AccountNotEnabledException extends RuntimeException {
    private final String userId;
    public AccountNotEnabledException(String userId,String message) {
        super(message);
        this.userId = userId;
    }

}
