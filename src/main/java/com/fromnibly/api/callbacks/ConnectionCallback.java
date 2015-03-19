package com.fromnibly.api.callbacks;

import com.fromnibly.api.connection.Connection;

/**
 * Created by thato_000 on 3/13/2015.
 */
public interface ConnectionCallback<T> {

    void onConnection(Connection<T> connection);
}
