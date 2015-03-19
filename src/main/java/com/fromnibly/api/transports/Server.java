package com.fromnibly.api.transports;

import com.fromnibly.api.Defaults;
import com.fromnibly.api.HandlerManager;
import com.fromnibly.api.Joiner;
import com.fromnibly.api.callbacks.CallbackManager;
import com.fromnibly.api.callbacks.ErrorCallback;
import com.fromnibly.api.callbacks.MessageCallback;
import com.fromnibly.api.callbacks.RequestCallback;
import com.fromnibly.api.use.CodecHandler;
import com.fromnibly.api.use.DirectionHandler;
import com.fromnibly.api.use.KeyHandler;
import com.fromnibly.api.use.TypeHandler;
import com.fromnibly.exceptions.ServerException;
import com.fromnibly.jetty.WyreEndpointConfig;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.Extension;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;

/**
 * Created by thato_000 on 3/11/2015.
 */
@Slf4j
public class Server<T> {


    private final HandlerManager<T> handlers = new HandlerManager<T>();
    private final CallbackManager<T> callbackManager = new CallbackManager<T>();

    public Server() {

    }

    public Joiner listen(int port) throws ServerException {
        return listen(port, "/", new Defaults(), Lists.newArrayList(), Lists.newArrayList());
    }

    public Joiner listen(int port, String path) throws ServerException {
        return listen(port, path, new Defaults(), Lists.newArrayList(), Lists.newArrayList());
    }

    public Joiner listen(int port, String path, Defaults defaults) throws ServerException {
        return listen(port, path, defaults, Lists.newArrayList(), Lists.newArrayList());
    }

    public Joiner listen(int port, String path, Defaults defaults, List<String> subProtocols, List<Extension> extensions) throws ServerException {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);
        try {
            ServerContainer wsContainer = WebSocketServerContainerInitializer.configureContext(context);
            ServerEndpointConfig config = new WyreEndpointConfig(path, subProtocols, extensions, defaults, callbackManager, handlers);
            wsContainer.addEndpoint(config);
            server.start();

            //TODO deal with this
        } catch (Exception e) {
            throw new ServerException("there was a problem starting the server", e);
        }

        return new Joiner() {
            @Override
            public void join() throws InterruptedException {
                server.join();
            }
        };
    }

    public void use(CodecHandler<T> codec) {
        handlers.setCodecHandler(codec);
    }

    public void use(KeyHandler<T> key) {
        handlers.setKeyHandler(key);
    }

    public void use(DirectionHandler<T> direction) {
        handlers.setDirectionHandler(direction);
    }

    public void use(TypeHandler<T> type) {
        handlers.setTypeHandler(type);
    }

    public void onMessage(Class<? extends T> clazz, MessageCallback<? extends T> callback) {
        callbackManager.getMessageCallbacks().put(clazz, callback);
    }

    public void onRequest(Class<? extends T> clazz, RequestCallback<? extends T> callback) {
        callbackManager.getRequestCallbacks().put(clazz, callback);
    }

    public void onError(ErrorCallback<T> callback) {
        callbackManager.setErrorCallback(callback);
    }

}
