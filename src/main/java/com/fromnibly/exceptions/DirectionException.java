package com.fromnibly.exceptions;

/**
 * Created by thato_000 on 3/11/2015.
 */
public class DirectionException extends WyreException {

    public DirectionException(String message) {
        super(message);
    }

    public DirectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
