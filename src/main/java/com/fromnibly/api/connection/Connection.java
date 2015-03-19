package com.fromnibly.api.connection;

import com.fromnibly.api.Defaults;
import com.fromnibly.api.HandlerManager;
import com.fromnibly.api.WyreCallback;
import com.fromnibly.api.use.Direction;
import com.fromnibly.util.CallbackWrapper;
import com.fromnibly.util.PromiseFactory;
import lombok.Getter;
import org.jdeferred.Deferred;
import org.jdeferred.Promise;

import javax.websocket.CloseReason;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by thato_000 on 3/12/2015.
 */
public class Connection<T> {

    @Getter
    private final String id;
    private final Session session;
    private final RemoteEndpoint.Basic remote;
    private final Map<String, Promise<T, Throwable, T>> requestMap;
    private final Defaults defaults;
    private final HandlerManager<T> handlers;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Connection(String id,
                      Session session,
                      Map<String, Promise<T, Throwable, T>> requestMap,
                      Defaults defaults,
                      HandlerManager<T> handlers
    ) {
        this.id = id;
        this.session = session;
        this.remote = session.getBasicRemote();
        this.requestMap = requestMap;
        this.defaults = defaults;
        this.handlers = handlers;
    }


    public Promise<T, Throwable, T> send(T message) {
        return send(message, (Deferred<T, Throwable, T>) null, defaults.isBinary());
    }

    public Promise<T, Throwable, T> send(T message, boolean binary) {
        return send(message, (Deferred<T, Throwable, T>) null, binary);
    }

    public Promise<T, Throwable, T> send(T message, WyreCallback<T> callback) {
        return send(message, CallbackWrapper.wrap(callback), defaults.isBinary());
    }

    public Promise<T, Throwable, T> send(T message, WyreCallback<T> callback, boolean binary) {
        return send(message, CallbackWrapper.wrap(callback), binary);
    }

    public Promise<T, Throwable, T> send(T message, Deferred<T, Throwable, T> opt) {
        return send(message, opt, defaults.isBinary());
    }

    public Promise<T, Throwable, T> send(T message, Deferred<T, Throwable, T> opt, final boolean binary) {
        final Deferred<T, Throwable, T> deferred = PromiseFactory.create(opt);
        try {
            final String payload = handlers.getCodecHandler().encode(message);
            final Direction direction = handlers.getDirectionHandler().get(message);
            final String key = handlers.getKeyHandler().get(message);

            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (binary) {
                            remote.sendBinary(ByteBuffer.wrap(payload.getBytes()));
                        } else {
                            remote.sendText(payload);
                        }
                        //if its a request we need to resolve it when we get the reply.
                        if (direction == Direction.REQUEST) {
                            requestMap.put(key, deferred);
                        } else { //if it's a regular message we need to resolve it now that it has been sent.
                            deferred.resolve(null);
                        }
                    } catch (Exception e) { //if there was a problem we need to reject it
                        deferred.reject(e);
                    }
                }
            });

        } catch (Exception e) {
            deferred.reject(e);
        }
        return deferred.promise();
    }

    public Promise<T, Throwable, T> request(T message) {
        return request(message, (Deferred<T, Throwable, T>) null, defaults.isBinary());
    }

    public Promise<T, Throwable, T> request(T message, boolean binary) {
        return request(message, (Deferred<T, Throwable, T>) null, binary);
    }

    public Promise<T, Throwable, T> request(T message, WyreCallback<T> callback) {
        return request(message, CallbackWrapper.wrap(callback), defaults.isBinary());
    }

    public Promise<T, Throwable, T> request(T message, WyreCallback<T> callback, boolean binary) {
        return request(message, CallbackWrapper.wrap(callback), binary);
    }

    public Promise<T, Throwable, T> request(T message, Deferred<T, Throwable, T> opt) {
        return request(message, opt, defaults.isBinary());
    }

    public Promise<T, Throwable, T> request(T message, Deferred<T, Throwable, T> opt, boolean binary) {
        Deferred<T, Throwable, T> deferred = PromiseFactory.create(opt);
        try {
            handlers.getDirectionHandler().set(message, Direction.REQUEST);
            send(message, deferred, binary);
        } catch (Exception e) {
            deferred.reject(e);
        }
        return deferred;
    }

    public void close(CloseReason reason) throws IOException {
        session.close(reason);
    }

    public void close() throws IOException {
        session.close();
    }


}
