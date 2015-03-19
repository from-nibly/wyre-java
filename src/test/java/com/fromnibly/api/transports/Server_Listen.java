package com.fromnibly.api.transports;

import com.fromnibly.api.Joiner;
import com.fromnibly.api.messages.Message;
import org.junit.Test;

/**
 * Created by thato_000 on 3/13/2015.
 */
public class Server_Listen {

    @Test
    public void listenStartsAWebsocketServer() throws Exception {

        Server<Message> server = new Server<>();
        Joiner joiner = server.listen(8000);
        joiner.join();

    }

}
