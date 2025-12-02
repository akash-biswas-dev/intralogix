package com.intralogix.users.exception;


public class DatabaseOperationException extends RuntimeException{

    public DatabaseOperationException(String message) {
        super(message);
    }
}
