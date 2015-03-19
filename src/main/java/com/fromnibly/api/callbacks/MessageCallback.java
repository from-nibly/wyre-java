package com.fromnibly.api.callbacks;

import com.fromnibly.api.context.MessageContext;

/**
 * Created by thato_000 on 3/13/2015.
 */
public interface MessageCallback<T> {
    void call(MessageContext<T> context);
}
