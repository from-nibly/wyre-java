package com.fromnibly.api.messages;

import com.fromnibly.api.use.Direction;

/**
 * Created by thato_000 on 3/13/2015.
 */
public class RequestMessage extends TwoWayMessage {

    public RequestMessage(String key) {
        super(key, Direction.REQUEST);
    }
}
