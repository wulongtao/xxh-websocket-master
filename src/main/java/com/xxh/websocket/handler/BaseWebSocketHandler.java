package com.xxh.websocket.handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xxh.websocket.handler.data.ChatData;
import com.xxh.websocket.handler.messaging.Message;
import com.xxh.websocket.handler.messaging.MessageFactory;
import com.xxh.websocket.handler.session.ChatSession;
import com.xxh.websocket.handler.data.ChatConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * Created by wulongtao on 2017/6/29.
 * WebSocket连接、消息处理基类，
 */
public abstract class BaseWebSocketHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(BaseWebSocketHandler.class);

    @Autowired
    protected ChatData chatData;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("成功建立连接");
        //获取绑定的ID
        String clientId = getClientId(session);
        if (clientId != null) {
            ChatSession chatSession = new ChatSession(session);
            chatData.putChatSession(clientId, chatSession);
            chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_WEBSOCKET_CONNECT_SUCCESS,
                    "websocket.message.base.connect.success", ChatConstants.TYPE_SERVER_NOTICE, clientId));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.debug("消息内容：" + message.getPayload());

        if (getClientId(session) == null) {
            chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_BIND_CLIENT_ID_ERROR,
                    "websocket.message.base.bind.client.id.error", ChatConstants.TYPE_SERVER_NOTICE));
            return;
        }

        try {
            Message msg = new Gson().fromJson(message.getPayload(), Message.class);
            //如果没有UserId，直接设置一个
            if (msg.getUserId() == null) {
                chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_NEET_CLIENT_ID_ERROR,
                        "websocket.message.base.need.client.id.error", ChatConstants.TYPE_SERVER_NOTICE, getClientId(session)));
                return;
            }

            onMessage(msg, getClientId(session));
        } catch (JsonSyntaxException e) {
            chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_NEET_JSON_DATA_ERROR,
                    "websocket.message.base.need.json.data.error", ChatConstants.TYPE_SERVER_NOTICE, getClientId(session)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void onMessage(Message message, String clientId);

    /**
     * 关闭用户会话
     * @param clientId
     */
    public boolean closeClientSession(String clientId) {
        ChatSession chatSession = chatData.getChatSession(clientId);
        if (chatSession.getType() != ChatSession.TYPE_WEBSOCKET) {
            chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_LOGOUT_FAILED,
                    "websocket.message.chat.logout.failed", ChatConstants.TYPE_SERVER_NOTICE, clientId));
            return false;
        }
        try {
            ((WebSocketSession)chatSession.getSession()).close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_LOGOUT_FAILED,
                    "websocket.message.chat.logout.failed", ChatConstants.TYPE_SERVER_NOTICE, clientId));
            return false;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("连接关闭，status：" + status);
        String clientId = getClientId(session);
        //删除用户会话
        chatData.removeChatSession(clientId);
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
    private boolean bindClientId(WebSocketSession session, String clientId) {
        String oldClientId = getClientId(session);
        try {
            if (oldClientId == null) {
                session.sendMessage(MessageFactory.buildMessage(ChatConstants.RESULT_BIND_USER_FAILED_ERROR, "绑定用户失败"));
            }

            session.getAttributes().put(ChatConstants.CLIENT_ID, clientId);
            chatData.removeChatSession(oldClientId);
            chatData.putChatSession(clientId, new ChatSession(session));

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 绑定用户ID
     * @param clientId
     * @param oldClientId
     * @return
     */
    protected boolean bindClientId(String clientId, String oldClientId) {
        WebSocketSession session = (WebSocketSession) chatData.getChatSession(oldClientId).getSession();
        return bindClientId(session, clientId);
    }


}
