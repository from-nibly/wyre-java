package com.fromnibly.api.use.defaults;

import com.fromnibly.api.messages.Message;
import com.fromnibly.api.use.CodecHandler;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 * Created by thato_000 on 3/11/2015.
 */
public class CodecHandlerDefault implements CodecHandler<Message> {

    JsonParser parser = new JsonParser();
    Gson gson = new Gson();

    @Override
    public String encode(Message object) {
        return gson.toJson(object);
    }

    @Override
    public Message decode(String message, Class<? extends Message> clazz) {
        return gson.fromJson(message, clazz);
    }

}
