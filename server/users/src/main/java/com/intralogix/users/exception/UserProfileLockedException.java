package com.intralogix.users.exception;

public class UserProfileLockedException extends RuntimeException {
    public UserProfileLockedException(String message) {
        super(message);
    }
}
