package com.coolcloud.sacw.common.exception;

public class OperationFailedException extends RuntimeException {

    private static final long serialVersionUID = 4659061679251852621L;

    public OperationFailedException() {
        super();
    }

    public OperationFailedException(String message) {
        super(message);
    }

    public OperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationFailedException(Throwable cause) {
        super(cause);
    }

}
