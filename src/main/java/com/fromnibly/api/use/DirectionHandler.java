package com.fromnibly.api.use;

import com.fromnibly.exceptions.DirectionException;
import com.fromnibly.exceptions.KeyException;

/**
 * Created by thato_000 on 3/11/2015.
 */
public interface DirectionHandler<T> {

    Direction get(T message) throws DirectionException;

    void set(T message, Direction direction) throws DirectionException;
}
