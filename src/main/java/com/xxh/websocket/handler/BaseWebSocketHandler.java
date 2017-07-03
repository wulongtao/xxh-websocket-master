package com.xxh.websocket.handler;

import com.xxh.websocket.config.WebSocketConfig;
import com.xxh.websocket.handler.messaging.MessageFactory;
import com.xxh.websocket.handler.session.ChatSession;
import com.xxh.websocket.util.ChatConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wulongtao on 2017/6/29.
 */
public class BaseWebSocketHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(BaseWebSocketHandler.class);

    //保存用户会话
    private static final Map<String, ChatSession> users;

    static {
        users = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        WebSocketConfig.startTime = System.currentTimeMillis();
        logger.info("成功建立连接");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("handle text message");
        logger.debug("消息内容：" + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("连接关闭" + (System.currentTimeMillis()-WebSocketConfig.startTime));
    }

    protected String getClientId(WebSocketSession session) {
        if (!session.getAttributes().containsKey(ChatConstants.CLIENT_ID)) {
            return null;
        }
        String clientId = (String) session.getAttributes().get(ChatConstants.CLIENT_ID);
        return clientId;
    }

    protected boolean bindClientId(WebSocketSession session, String clientId) {
        String oldClientId = getClientId(session);
        try {
            if (oldClientId == null) {
                session.sendMessage(MessageFactory.buildMessage(ChatConstants.RESULT_BIND_USER_FAILED, "绑定用户失败"));
            }

            session.getAttributes().put(ChatConstants.CLIENT_ID, clientId);
            users.remove(oldClientId);
            users.put(clientId, new ChatSession(session));

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}
