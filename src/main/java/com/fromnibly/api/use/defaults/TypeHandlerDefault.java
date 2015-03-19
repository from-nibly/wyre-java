package com.fromnibly.api.use.defaults;

import com.fromnibly.api.messages.Message;
import com.fromnibly.api.use.TypeHandler;
import com.fromnibly.exceptions.TypeException;
import com.google.gson.JsonParser;

import java.util.List;

/**
 * Created by thato_000 on 3/12/2015.
 */
public class TypeHandlerDefault implements TypeHandler<Message> {

    private final JsonParser parser = new JsonParser();
    private final List<String> packages;

    public TypeHandlerDefault(List<String> packages) {
        this.packages = packages;
    }

    @Override
    public Class<? extends Message> get(String payload) throws TypeException {
        String type = parser.parse(payload).getAsJsonObject().get("type").getAsString();
        Class<? extends Message> rtn = null;
        for (String pkg : packages) {
            try {
                if (!pkg.endsWith(".")) {
                    pkg += ".";
                }
                Class<?> clazz = Class.forName(pkg + type);
                rtn = (Class<? extends Message>) clazz;
                break;
            } catch (ClassNotFoundException e) {

            }
        }
        if (rtn == null) {
            rtn = Message.class;
        }
        return rtn;
    }
}
