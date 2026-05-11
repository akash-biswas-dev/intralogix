package com.biswasakashdev.nexussphere.gateway.exceptions;


import lombok.Getter;

@Getter
public class ProfileNotCompletedException extends RuntimeException {

    private final String userId;

    public ProfileNotCompletedException(String userId) {
        super();
        this.userId = userId;
    }
}
