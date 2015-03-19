package com.fromnibly.api.use;

import com.fromnibly.exceptions.KeyException;

import java.lang.reflect.Field;

/**
 * Created by thato_000 on 3/11/2015.
 */
public interface KeyHandler<T> {

    String get(T message) throws KeyException;

    void set(T message, String key) throws KeyException;
}
