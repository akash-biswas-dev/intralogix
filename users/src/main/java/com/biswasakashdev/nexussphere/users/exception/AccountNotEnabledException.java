package com.biswasakashdev.nexussphere.users.exception;

import lombok.Getter;

@Getter
public class AccountNotEnabledException extends RuntimeException {
    private final String userId;
    public AccountNotEnabledException(String userId,String message) {
        super(message);
        this.userId = userId;
    }

}
