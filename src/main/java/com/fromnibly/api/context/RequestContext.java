package com.fromnibly.api.context;

import com.fromnibly.api.Defaults;
import com.fromnibly.api.HandlerManager;
import com.fromnibly.api.WyreCallback;
import com.fromnibly.api.connection.Connection;
import com.fromnibly.api.use.Direction;
import com.fromnibly.api.use.DirectionHandler;
import com.fromnibly.api.use.KeyHandler;
import com.fromnibly.util.CallbackWrapper;
import com.fromnibly.util.PromiseFactory;
import org.jdeferred.Deferred;
import org.jdeferred.Promise;

/**
 * Created by thato_000 on 3/13/2015.
 */
public class RequestContext<T> extends MessageContext<T> {

    private final DirectionHandler<T> directionHandler;
    private final KeyHandler<T> keyHandler;
    private final String key;
    private final Defaults defaults;

    public RequestContext(T message,
                          Class<? extends T> type,
                          Connection<T> connection,
                          HandlerManager<T> handlers,
                          String key,
                          Defaults defaults
    ) {
        super(message, type, connection);
        this.directionHandler = handlers.getDirectionHandler();
        this.keyHandler = handlers.getKeyHandler();
        this.key = key;
        this.defaults = defaults;
    }

    public Promise<T, Throwable, T> reply(T message) {
        return reply(message, (Deferred<T, Throwable, T>) null, defaults.isBinary());
    }

    public Promise<T, Throwable, T> reply(T message, boolean binary) {
        return reply(message, (Deferred<T, Throwable, T>) null, binary);
    }

    public Promise<T, Throwable, T> reply(T message, WyreCallback<T> callback) {
        return reply(message, CallbackWrapper.wrap(callback), defaults.isBinary());
    }

    public Promise<T, Throwable, T> reply(T message, WyreCallback<T> callback, boolean binary) {
        return reply(message, CallbackWrapper.wrap(callback), binary);
    }

    public Promise<T, Throwable, T> reply(T message, Deferred<T, Throwable, T> opt) {
        return reply(message, opt, defaults.isBinary());
    }

    public Promise<T, Throwable, T> reply(T message, Deferred<T, Throwable, T> opt, final boolean binary) {
        opt = setProperties(message, Direction.REPLY, opt);
        if (!opt.isRejected()) {
            this.getConnection().send(message, opt, binary);
        }
        return opt.promise();
    }

    public Promise<T, Throwable, T> error(T message) {
        return error(message, (Deferred<T, Throwable, T>) null, defaults.isBinary());
    }

    public Promise<T, Throwable, T> error(T message, boolean binary) {
        return error(message, (Deferred<T, Throwable, T>) null, binary);
    }

    public Promise<T, Throwable, T> error(T message, WyreCallback<T> callback) {
        return error(message, CallbackWrapper.wrap(callback), defaults.isBinary());
    }

    public Promise<T, Throwable, T> error(T message, WyreCallback<T> callback, boolean binary) {
        return error(message, CallbackWrapper.wrap(callback), binary);
    }

    public Promise<T, Throwable, T> error(T message, Deferred<T, Throwable, T> opt) {
        return error(message, opt, defaults.isBinary());
    }

    public Promise<T, Throwable, T> error(T message, Deferred<T, Throwable, T> opt, final boolean binary) {
        opt = setProperties(message, Direction.ERROR, opt);
        if (!opt.isRejected()) {
            this.getConnection().send(message, opt, binary);
        }
        return opt.promise();
    }

    public Promise<T, Throwable, T> partial(T message) {
        return partial(message, (Deferred<T, Throwable, T>) null, defaults.isBinary());
    }

    public Promise<T, Throwable, T> partial(T message, boolean binary) {
        return partial(message, (Deferred<T, Throwable, T>) null, binary);
    }

    public Promise<T, Throwable, T> partial(T message, WyreCallback<T> callback) {
        return partial(message, CallbackWrapper.wrap(callback), defaults.isBinary());
    }

    public Promise<T, Throwable, T> partial(T message, WyreCallback<T> callback, boolean binary) {
        return partial(message, CallbackWrapper.wrap(callback), binary);
    }

    public Promise<T, Throwable, T> partial(T message, Deferred<T, Throwable, T> opt) {
        return partial(message, opt, defaults.isBinary());
    }

    public Promise<T, Throwable, T> partial(T message, Deferred<T, Throwable, T> opt, final boolean binary) {
        opt = setProperties(message, Direction.PARTIAL, opt);
        if (!opt.isRejected()) {
            this.getConnection().send(message, opt, binary);
        }
        return opt.promise();
    }

    private Deferred<T, Throwable, T> setProperties(T message, Direction direction, Deferred<T, Throwable, T> opt) {
        Deferred<T, Throwable, T> deferred = PromiseFactory.create(opt);
        try {
            keyHandler.set(message, key);
            directionHandler.set(message, direction);
        } catch (Exception e) {
            deferred.reject(e);
        }

        return deferred;
    }
}
