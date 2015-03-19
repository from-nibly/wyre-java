package com.fromnibly.api.messages;

import com.fromnibly.api.use.Direction;

/**
 * Created by thato_000 on 3/13/2015.
 */
public class PartialMessage extends TwoWayMessage {
    public PartialMessage(String key) {
        super(key, Direction.PARTIAL);
    }
}
