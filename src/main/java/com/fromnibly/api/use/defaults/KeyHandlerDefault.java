package com.fromnibly.api.use.defaults;

import com.fromnibly.api.messages.Message;
import com.fromnibly.api.use.KeyHandler;
import com.fromnibly.exceptions.KeyException;

import java.lang.reflect.Field;

/**
 * Created by thato_000 on 3/11/2015.
 */
public class KeyHandlerDefault implements KeyHandler<Message> {

    @Override
    public String get(Message message) throws KeyException {
        try {
            Field keyField = message.getClass().getField("key");
            return (String) keyField.get(message);
        } catch (Exception e) {
            throw new KeyException("There was a problem getting the key", e);
        }
    }

    @Override
    public void set(Message message, String key) throws KeyException {
        try {
            Field keyField = message.getClass().getField("key");
            keyField.set(message, key);
        } catch (Exception e) {
            throw new KeyException("There was a problem setting the key", e);
        }
    }
}
