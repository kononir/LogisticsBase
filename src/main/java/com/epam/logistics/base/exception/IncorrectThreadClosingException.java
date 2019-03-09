package com.epam.logistics.base.exception;

public class IncorrectThreadClosingException extends Exception {

    public IncorrectThreadClosingException() {
        super("Thread closed incorrect!");
    }
}
