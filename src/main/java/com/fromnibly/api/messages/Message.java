package com.fromnibly.api.messages;

import com.fromnibly.api.use.Direction;

/**
 * Created by thato_000 on 3/13/2015.
 */
public abstract class Message {
    private final Class type;
    private final Direction direction;

    public Message(Direction direction) {
        this.type = this.getClass();
        this.direction = direction;
    }
}
