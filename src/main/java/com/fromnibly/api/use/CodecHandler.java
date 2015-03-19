package com.fromnibly.api.use;

/**
 * Created by thato_000 on 3/11/2015.
 */
public interface CodecHandler<T> {

    String encode(T object);

    T decode(String payload, Class<? extends T> clazz);
}
