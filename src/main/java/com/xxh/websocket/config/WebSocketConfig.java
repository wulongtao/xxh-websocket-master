package com.xxh.websocket.config;

import com.xxh.websocket.handler.BaseWebSocketHandler;
import com.xxh.websocket.properties.WebSocketProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * Created by wulongtao on 2017/6/29.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
    @Autowired
    private WebSocketProperties properties;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        logger.info("websocket path="+properties.getPath());
        logger.info("websocket InputBufferSize="+properties.getInputBufferSize());
        logger.info("websocket IdleTimeout="+properties.getIdleTimeout());
        logger.info("websocket MaxBinaryMessageBufferSize="+properties.getMaxBinaryMessageBufferSize());
        logger.info("websocket MaxTextMessageBufferSize="+properties.getMaxTextMessageBufferSize());
        webSocketHandlerRegistry.addHandler(handler(), properties.getPath()).setHandshakeHandler(handshakeHandler()).setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler handler() {
        return new BaseWebSocketHandler();
    }

    @Bean
    public DefaultHandshakeHandler handshakeHandler() {
        return new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy());
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxSessionIdleTimeout(800000);
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        container.setMaxSessionIdleTimeout(300000);
        return container;
    }
}
