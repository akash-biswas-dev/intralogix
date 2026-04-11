package com.nexussphere.workspace.exceptions;

public class DatasourceOperationFailedException extends RuntimeException {
    public DatasourceOperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatasourceOperationFailedException(String message) {
        super(message);
    }
}
