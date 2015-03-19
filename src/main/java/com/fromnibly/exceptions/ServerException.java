package com.fromnibly.exceptions;

/**
 * Created by thato_000 on 3/13/2015.
 */
public class ServerException extends Exception {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
