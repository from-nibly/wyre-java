package com.fromnibly.api.context;

import com.fromnibly.api.connection.Connection;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by thato_000 on 3/13/2015.
 */
@Getter
@AllArgsConstructor
public class MessageContext<T> {

    private final T message;
    private final Class<? extends T> type;
    private final Connection<T> connection;
}
