package com.fromnibly.exceptions;

/**
 * Created by thato_000 on 3/11/2015.
 */
public class KeyException extends WyreException {

    public KeyException(String message) {
        super(message);
    }

    public KeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
