package com.xxh.websocket.interceptor;

import com.xxh.websocket.handler.data.ChatConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.UUID;

/**
 * Created by wulongtao on 2017/7/3.
 */
public class WebSocketHandshsakeInterceptor implements HandshakeInterceptor {
    private Logger logger = LoggerFactory.getLogger(WebSocketHandshsakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            String clientId = UUID.randomUUID().toString().toUpperCase();
            map.put(ChatConstants.CLIENT_ID, clientId);
            logger.info("绑定ID：" + clientId);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
