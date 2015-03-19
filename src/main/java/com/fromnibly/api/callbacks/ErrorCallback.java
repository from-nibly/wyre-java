package com.fromnibly.api.callbacks;

/**
 * Created by thato_000 on 3/13/2015.
 */
public interface ErrorCallback<T> {
    void call(Throwable e);
}
