package com.biswasakashdev.nexussphere.common.exceptions;

public class GRPCClientException extends RuntimeException{
    public GRPCClientException(String message) {
        super(message);
    }

    public GRPCClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
