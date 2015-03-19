package com.fromnibly.api.callbacks.defaults;

import com.fromnibly.api.callbacks.MessageCallback;
import com.fromnibly.api.context.MessageContext;
import com.fromnibly.api.messages.Message;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by thato_000 on 3/13/2015.
 */
@Slf4j
public class MessageCallbackDefault implements MessageCallback<Message> {
    @Override
    public void call(MessageContext<Message> context) {
        log.info("message received {}", context.getMessage());
    }
}
