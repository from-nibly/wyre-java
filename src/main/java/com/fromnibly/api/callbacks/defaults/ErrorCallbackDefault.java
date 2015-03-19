package com.fromnibly.api.callbacks.defaults;

import com.fromnibly.api.callbacks.ErrorCallback;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by thato_000 on 3/13/2015.
 */
@Slf4j
public class ErrorCallbackDefault<T> implements ErrorCallback<T> {
    @Override
    public void call(Throwable e) {
        log.error("There was an error!", e);
    }
}
