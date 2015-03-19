package com.fromnibly.api.callbacks.defaults;

import com.fromnibly.api.callbacks.ConnectionCallback;
import com.fromnibly.api.connection.Connection;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by thato_000 on 3/13/2015.
 */
@Slf4j
public class ConnectionCallbackDefault<T> implements ConnectionCallback<T> {
    @Override
    public void onConnection(Connection<T> connection) {
        log.info("new connection received {}", connection.getId());
    }
}
