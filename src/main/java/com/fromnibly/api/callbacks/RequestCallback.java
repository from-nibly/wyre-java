package com.fromnibly.api.callbacks;

import com.fromnibly.api.context.RequestContext;

/**
 * Created by thato_000 on 3/13/2015.
 */
public interface RequestCallback<T> {
    void call(RequestContext<T> context);
}
