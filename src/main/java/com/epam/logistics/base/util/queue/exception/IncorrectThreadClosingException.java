package com.epam.logistics.base.util.queue.exception;

public class IncorrectThreadClosingException extends Exception {

    public IncorrectThreadClosingException() {
        super("Thread closed incorrect!");
    }
}
