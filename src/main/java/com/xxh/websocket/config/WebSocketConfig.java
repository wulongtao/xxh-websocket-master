package com.xxh.websocket.config;

import com.xxh.websocket.handler.BaseWebSocketHandler;
import com.xxh.websocket.interceptor.WebSocketHandshsakeInterceptor;
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

import java.util.concurrent.TimeUnit;

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
        logger.info("websocket properties="+properties.toString());
        webSocketHandlerRegistry.addHandler(handler(), properties.getPath()).setHandshakeHandler(handshakeHandler())
                .addInterceptors(new WebSocketHandshsakeInterceptor())
                .setAllowedOrigins("*");
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
        logger.info("加载配置");
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        /**
         * 最低不能低于10秒，低于10秒还是10秒才断开连接
         * 这里设置3分钟超时
         */
        container.setMaxSessionIdleTimeout(TimeUnit.MINUTES.toMillis(3));
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
}
