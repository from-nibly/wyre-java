package com.fromnibly.jetty;

import com.fromnibly.api.callbacks.CallbackManager;
import com.fromnibly.api.Defaults;
import com.fromnibly.api.HandlerManager;
import com.fromnibly.jsr.WyreEndpoint;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Extension;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

/**
 * Created by thato_000 on 3/11/2015.
 */
public class WyreEndpointConfig<T> implements ServerEndpointConfig {

    private final String path;
    private final List<String> subProtocols;
    private final List<Extension> extensions;
    private final Defaults defaults;
    private final CallbackManager<T> callbackManager;
    private final HandlerManager<T> handlers;

    public WyreEndpointConfig(String path,
                              List<String> subProtocols,
                              List<Extension> extensions,
                              Defaults defaults,
                              CallbackManager<T> callbackManager,
                              HandlerManager<T> handlers
    ) {
        this.path = path;
        this.subProtocols = subProtocols;
        this.extensions = extensions;
        this.defaults = defaults;
        this.callbackManager = callbackManager;
        this.handlers = handlers;
    }

    @Override
    public Class<?> getEndpointClass() {
        return WyreEndpoint.class;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public List<String> getSubprotocols() {
        return subProtocols;
    }

    @Override
    public List<Extension> getExtensions() {
        return extensions;
    }

    @Override
    public Configurator getConfigurator() {
        return new Configurator() {
            @Override
            public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
                return (T) new WyreEndpoint(defaults, callbackManager, handlers);
            }
        };
    }

    @Override
    public List<Class<? extends Encoder>> getEncoders() {
        return Lists.newArrayList();
    }

    @Override
    public List<Class<? extends Decoder>> getDecoders() {
        return Lists.newArrayList();
    }

    @Override
    public Map<String, Object> getUserProperties() {
        return Maps.newHashMap();
    }
}
