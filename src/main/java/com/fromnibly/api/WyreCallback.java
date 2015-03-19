package com.fromnibly.api;

/**
 * Created by thato_000 on 3/13/2015.
 */
public interface WyreCallback<T> {

    void callback(T data, Throwable error, T partial);
}
