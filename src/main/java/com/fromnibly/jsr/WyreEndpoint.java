package com.fromnibly.jsr;

import com.fromnibly.api.Defaults;
import com.fromnibly.api.HandlerManager;
import com.fromnibly.api.callbacks.CallbackManager;
import com.fromnibly.api.connection.Connection;
import com.fromnibly.api.context.ErrorContext;
import com.fromnibly.api.context.MessageContext;
import com.fromnibly.api.context.RequestContext;
import com.fromnibly.api.use.Direction;
import com.google.common.collect.Maps;
import org.eclipse.jetty.server.Authentication;
import org.jdeferred.Deferred;
import org.jdeferred.impl.DeferredObject;

import javax.websocket.*;
import java.util.Map;
import java.util.UUID;

/**
 * Created by thato_000 on 3/11/2015.
 */

public class WyreEndpoint<T> extends Endpoint {

    private final Map<String, Connection<T>> connectionMap = Maps.newConcurrentMap();
    private final Map<String, Deferred<MessageContext<? extends T>, ErrorContext<? extends T>, MessageContext<? extends T>>> requestMap = Maps.newConcurrentMap();
    private final HandlerManager<T> handlers;
    private final Defaults defaults;
    private final CallbackManager<T> callbackManager;


    public WyreEndpoint(Defaults defaults,
                        CallbackManager<T> callbackManager,
                        HandlerManager<T> handlerManager
    ) {
        this.handlers = handlerManager;
        this.defaults = defaults;
        this.callbackManager = callbackManager;
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

        String id = UUID.randomUUID().toString();
        final Connection<T> connection = new Connection<T>(id, session, requestMap, defaults, handlers);
        connectionMap.put(id, connection);

        //call onConnection
        callbackManager.getConnectionCallback().onConnection(connection);

        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String s) {
                handleMessage(s, true, connection);
            }
        });
        session.addMessageHandler(new MessageHandler.Whole<byte[]>() {
            @Override
            public void onMessage(byte[] bytes) {
                handleMessage(bytes, true, connection);
            }
        });
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
    }

    @Override
    public void onError(Session session, Throwable thr) {
        super.onError(session, thr);
    }

    private void handleMessage(byte[] bytes, boolean last, Connection<T> connection) {
        handleMessage(new String(bytes), last, connection);
    }

    private void handleMessage(String s, boolean last, Connection<T> connection) {
        try {
            Class<? extends T> type = handlers.getTypeHandler().get(s);
            T message = handlers.getCodecHandler().decode(s, type);

            Direction direction = handlers.getDirectionHandler().get(message);
            String key = handlers.getKeyHandler().get(message);
            Deferred<MessageContext<? extends T>, ErrorContext<? extends T>, MessageContext<? extends T>> callback = requestMap.get(key);

            RequestContext<T> requestContext;
            MessageContext<T> messageContext;
            ErrorContext<T> errorContext;

            switch (direction) {
                case REQUEST:
                    requestContext = new RequestContext<T>(message, type, connection, handlers, key, defaults);
                    callbackManager.request(type, requestContext);
                    break;
                case MESSAGE:
                    messageContext = new MessageContext<T>(message, type, connection);
                    callbackManager.message(type, messageContext);
                    break;
                case REPLY:
                    messageContext = new MessageContext<T>(message, type, connection);
                    callback.resolve(messageContext);
                    break;
                case PARTIAL:
                    messageContext = new MessageContext<T>(message, type, connection);
                    callback.notify(messageContext);
                    break;
                case ERROR:
                    errorContext = new ErrorContext<T>(message, type, connection, null);
                    callback.reject(errorContext);
                    break;
            }


        } catch (Exception e) {
            //call the on error!
        }
    }
}
