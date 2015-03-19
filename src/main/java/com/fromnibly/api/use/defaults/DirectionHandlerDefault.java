package com.fromnibly.api.use.defaults;

import com.fromnibly.api.messages.Message;
import com.fromnibly.api.use.Direction;
import com.fromnibly.api.use.DirectionHandler;
import com.fromnibly.exceptions.DirectionException;

import java.lang.reflect.Field;

/**
 * Created by thato_000 on 3/11/2015.
 */
public class DirectionHandlerDefault implements DirectionHandler<Message> {

    @Override
    public Direction get(Message message) throws DirectionException {
        try {
            Field keyField = message.getClass().getField("direction");
            String value = keyField.get(message).toString();
            Direction direction = Direction.valueOf(value.toUpperCase());
            return direction;
        } catch (Exception e) {
            throw new DirectionException("There was a problem getting the direction", e);
        }
    }

    @Override
    public void set(Message message, Direction direction) throws DirectionException {
        try {
            Field keyField = message.getClass().getField("direction");
            keyField.set(message, direction.toString());
        } catch (Exception e) {
            throw new DirectionException("There was a problem setting the direction", e);
        }
    }
}
