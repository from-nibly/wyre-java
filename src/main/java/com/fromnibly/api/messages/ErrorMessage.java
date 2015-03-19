package com.fromnibly.api.messages;

import com.fromnibly.api.use.Direction;

/**
 * Created by thato_000 on 3/13/2015.
 */
public class ErrorMessage extends TwoWayMessage {
    public ErrorMessage(String key) {
        super(key, Direction.ERROR);
    }
}
