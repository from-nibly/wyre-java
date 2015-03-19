package com.fromnibly.api.use;

import com.fromnibly.exceptions.TypeException;

/**
 * Created by thato_000 on 3/12/2015.
 */
public interface TypeHandler<T> {

    Class<? extends T> get(String message) throws TypeException;

}
