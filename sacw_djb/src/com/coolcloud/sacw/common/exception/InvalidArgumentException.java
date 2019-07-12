package com.coolcloud.sacw.common.exception;

public class InvalidArgumentException extends RuntimeException {

    private static final long serialVersionUID = 8004981781290260786L;

    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(String s) {
        super(s);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentException(Throwable cause) {
        super(cause);
    }

}
