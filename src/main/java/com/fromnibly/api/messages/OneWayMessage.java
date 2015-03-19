package com.fromnibly.api.messages;

import com.fromnibly.api.use.Direction;

/**
 * Created by thato_000 on 3/13/2015.
 */
public abstract class OneWayMessage extends Message {

    public OneWayMessage() {
        super(Direction.MESSAGE);
    }
}
