package com.fromnibly.api;

/**
 * Created by jordandavidson on 3/20/15.
 */
public class ErrorOrMessage<T> {

    private final T message;
    private final Throwable exception;

    public ErrorOrMessage(T message) {
        this.message = message;
        this.exception = null;
    }

    public ErrorOrMessage(Throwable exception) {
        this.message = null;
        this.exception = exception;
    }
}
