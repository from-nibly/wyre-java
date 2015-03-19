package com.fromnibly.jsr;

import com.fromnibly.api.callbacks.CallbackManager;
import com.fromnibly.api.Defaults;
import com.fromnibly.api.HandlerManager;
import com.fromnibly.api.connection.Connection;
import com.fromnibly.api.context.MessageContext;
import com.fromnibly.api.context.RequestContext;
import com.fromnibly.api.use.Direction;
import com.google.common.collect.Maps;
import org.jdeferred.Promise;

import javax.websocket.*;
import java.util.Map;
import java.util.UUID;

/**
 * Created by thato_000 on 3/11/2015.
 */

public class WyreEndpoint<T> extends Endpoint {

    private final Map<String, Connection<T>> connectionMap = Maps.newConcurrentMap();
    private final Map<String, Promise<T, Throwable, T>> requestMap = Maps.newConcurrentMap();
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
//        session.addMessageHandler(new MessageHandler.Partial<String>() {
//            @Override
//            public void onMessage(String s, boolean b) {
//                handleMessage(s, b, connection);
//            }
//        });
//        session.addMessageHandler(new MessageHandler.Partial<byte[]>() {
//            @Override
//            public void onMessage(byte[] bytes, boolean b) {
//                handleMessage(bytes, b, connection);
//            }
//        });
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

            MessageContext<T> context;

            if (direction == Direction.REQUEST) {
                context = new RequestContext<T>(message, type, connection, handlers, key, defaults);
            } else {
                context = new MessageContext<T>(message, type, connection);
            }

            //call the appropriate 'on' method

        } catch (Exception e) {
            //call the on error!
        }
    }
}
