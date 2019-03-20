package com.epam.logistics.base.exception;

public class IncorrectThreadClosingException extends Exception {

    private static final String message = "Thread closed incorrect!";

    public IncorrectThreadClosingException() {
        super(message);
    }

    public IncorrectThreadClosingException(Throwable cause) {
        super(message, cause);
    }
}
