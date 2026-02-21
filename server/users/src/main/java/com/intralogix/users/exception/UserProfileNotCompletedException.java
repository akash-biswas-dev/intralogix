package com.intralogix.users.exception;

public class UserProfileNotCompletedException extends RuntimeException {
    public UserProfileNotCompletedException(String message) {
        super(message);
    }
}
