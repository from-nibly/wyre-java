package com.fromnibly.exceptions;

/**
 * Created by thato_000 on 3/12/2015.
 */
public class WyreException extends Exception {

    public WyreException(String message) {
        super(message);
    }

    public WyreException(String message, Throwable cause) {
        super(message, cause);
    }
}
