package com.fromnibly.api.callbacks;

import com.fromnibly.api.callbacks.defaults.ConnectionCallbackDefault;
import com.fromnibly.api.callbacks.defaults.ErrorCallbackDefault;
import com.fromnibly.api.context.MessageContext;
import com.fromnibly.api.context.RequestContext;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by thato_000 on 3/13/2015.
 */
public class CallbackManager<T> {
    private final Map<Class<? extends T>, MessageCallback<? extends T>> messageCallbacks = Maps.newConcurrentMap();
    private final Map<Class<? extends T>, RequestCallback<? extends T>> requestCallbacks = Maps.newConcurrentMap();
    @Setter
    @Getter
    private ConnectionCallback<T> connectionCallback = new ConnectionCallbackDefault();
    @Setter
    @Getter
    private ErrorCallback<T> errorCallback = new ErrorCallbackDefault();

    public void onRequest(Class<? extends T> clazz, RequestCallback<? extends T> requestCallback) {
        requestCallbacks.put(clazz, requestCallback);
    }

    public void onMessage(Class<? extends T> clazz, MessageCallback<? extends T> messageCallback) {
        messageCallbacks.put(clazz, messageCallback);
    }

    public void request(Class<? extends T> clazz, RequestContext<? extends T> requestContext) {
        while (clazz != Object.class) {
            for (Map.Entry<Class<? extends T>, RequestCallback<? extends T>> pair : requestCallbacks.entrySet()) {
                if(pair.getKey() == clazz) {
                    pair.getValue().call(requestContext);
                }
            }
        }
    }

    public void message(Class<? extends T> clazz, MessageContext<? extends T> messageContext) {

    }
}
