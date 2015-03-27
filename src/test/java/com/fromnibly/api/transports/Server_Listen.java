package com.fromnibly.api.transports;

import com.fromnibly.api.Joiner;
import com.fromnibly.api.callbacks.MessageCallback;
import com.fromnibly.api.context.MessageContext;
import com.fromnibly.api.messages.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by thato_000 on 3/13/2015.
 */
@Slf4j
public class Server_Listen {

    @Test
    public void listenStartsAWebsocketServer() throws Exception {

        Server<Message> server = new Server<Message>();

        server.onMessage(Message.class, new MessageCallback<Message>() {
            @Override
            public void call(MessageContext<Message> context) {
                log.debug("Testing", context.getMessage());
            }
        });

        Joiner joiner = server.listen(8000);
        joiner.join();

    }

}
