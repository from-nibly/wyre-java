package com.fromnibly.api.messages;

import com.fromnibly.api.use.Direction;

/**
 * Created by thato_000 on 3/13/2015.
 */
public abstract class TwoWayMessage extends Message {
    private final String key;

    public TwoWayMessage(String key, Direction direction) {
        super(direction);
        this.key = key;
    }
}
