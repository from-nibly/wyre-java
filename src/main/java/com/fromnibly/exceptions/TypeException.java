package com.fromnibly.exceptions;

/**
 * Created by thato_000 on 3/13/2015.
 */
public class TypeException extends WyreException {

    private final String payload;

    public TypeException(String message, String payload) {
        super(message);
        this.payload = payload;
    }

    public TypeException(String message, String payload, Throwable cause) {
        super(message, cause);
        this.payload = payload;
    }
}
