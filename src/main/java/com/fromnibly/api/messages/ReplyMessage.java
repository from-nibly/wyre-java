package com.fromnibly.api.messages;

import com.fromnibly.api.use.Direction;

/**
 * Created by thato_000 on 3/13/2015.
 */
public class ReplyMessage extends TwoWayMessage {

    public ReplyMessage(String key) {
        super(key, Direction.REPLY);
    }
}
