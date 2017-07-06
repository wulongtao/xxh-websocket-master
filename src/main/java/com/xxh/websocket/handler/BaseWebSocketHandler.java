package com.xxh.websocket.handler;

import com.xxh.websocket.handler.data.ChatData;
import com.xxh.websocket.handler.messaging.Message;
import com.xxh.websocket.handler.messaging.MessageFactory;
import com.xxh.websocket.handler.session.ChatSession;
import com.xxh.websocket.handler.data.ChatConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;

/**
 * Created by wulongtao on 2017/6/29.
 */
public class BaseWebSocketHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(BaseWebSocketHandler.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ChatData chatData;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("成功建立连接");
        String msg = messageSource.getMessage("welcome", null, LocaleContextHolder.getLocale());
        logger.info("msg=" + msg);
        //获取绑定的ID
        String clientId = getClientId(session);
        if (clientId != null) {
            ChatSession chatSession = new ChatSession(session);
            chatData.putClient(clientId, chatSession);

        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("handle text message");
        logger.debug("消息内容：" + message.getPayload());
    }

    /**
     * 发送消息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String clientId, Message message) {
        boolean ret = true;
        if (chatData.getClient(clientId) == null) return false;

        ChatSession chatSession = chatData.getClient(clientId);

        try {
            ret = chatSession.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            chatData.putErrorMessasge(message);
        }

        return ret;

    }

    /**
     * 发送消息给当前用户
     * @param message
     * @return
     */
    protected boolean sendMessageToCurrentUser(Message message) {
        String clientId = message.getUserId();
        return sendMessageToUser(clientId, message);
    }

    public boolean sendMessageToAllUsers(Message message) {
        boolean isSuccess = true;
        Set<String> sClientId = chatData.clientIdSet();
        for (String clientId : sClientId) {
            ChatSession chatSession = chatData.getClient(clientId);
            try {
                isSuccess = isSuccess == false ? false : chatSession.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("连接关闭，status：" + status);
        //删除用户会话
        chatData.removeClient(getClientId(session));
    }

    /**
     * 获取该会话绑定的ID
     * @param session
     * @return
     */
    protected String getClientId(WebSocketSession session) {
        if (!session.getAttributes().containsKey(ChatConstants.CLIENT_ID)) {
            return null;
        }
        String clientId = (String) session.getAttributes().get(ChatConstants.CLIENT_ID);
        return clientId;
    }

    /**
     * 为会话绑定ID
     * @param session
     * @param clientId
     * @return
     */
    protected boolean bindClientId(WebSocketSession session, String clientId) {
        String oldClientId = getClientId(session);
        try {
            if (oldClientId == null) {
                session.sendMessage(MessageFactory.buildMessage(ChatConstants.RESULT_BIND_USER_FAILED, "绑定用户失败"));
            }

            session.getAttributes().put(ChatConstants.CLIENT_ID, clientId);
            chatData.removeClient(oldClientId);
            chatData.putClient(clientId, new ChatSession(session));

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 非WebSocket端会话接入
     * @param id
     * @param session
     */
    public void addChatSession(String id, ChatSession session) {
        chatData.putClient(id, session);
    }

    /**
     * 非WebSocket获取会话
     * @param id
     * @return
     */
    public ChatSession getChatSession(String id) {
        return chatData.getClient(id);
    }


}
