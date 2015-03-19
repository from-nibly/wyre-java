package com.fromnibly.api.callbacks;

import com.fromnibly.api.callbacks.defaults.ConnectionCallbackDefault;
import com.fromnibly.api.callbacks.defaults.ErrorCallbackDefault;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by thato_000 on 3/13/2015.
 */
@Getter
public class CallbackManager<T> {
    @Setter
    private ConnectionCallback<T> connectionCallback = new ConnectionCallbackDefault();
    @Setter
    private ErrorCallback<T> errorCallback = new ErrorCallbackDefault();
    private final Map<Class<? extends T>, MessageCallback<? extends T>> messageCallbacks = Maps.newConcurrentMap();
    private final Map<Class<? extends T>, RequestCallback<? extends T>> requestCallbacks = Maps.newConcurrentMap();

}
