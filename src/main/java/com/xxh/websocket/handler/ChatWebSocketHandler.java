package com.xxh.websocket.handler;

import com.xxh.websocket.core.Seat;
import com.xxh.websocket.entity.User;
import com.xxh.websocket.handler.data.ChatConstants;
import com.xxh.websocket.handler.messaging.Message;
import com.xxh.websocket.handler.messaging.MessageFactory;
import com.xxh.websocket.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * Created by wulongtao on 2017/7/7.
 *
 */
public class ChatWebSocketHandler extends BaseWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    @Autowired
    private UserService userService;

    @Override
    public void onMessage(Message message, String clientId) {
        Integer type = message.getType();
        String userId = message.getUserId();

        //判断type是否为空
        if (type == null || type == 0) {
            chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_NEET_CLIENT_ID_ERROR,
                    "websocket.message.chat.empty.message.type.error", ChatConstants.TYPE_SERVER_NOTICE, userId, type));
            return;
        }

        if (type != ChatConstants.TYPE_LOGIN && type != ChatConstants.TYPE_CUS_LOGIN && type != ChatConstants.TYPE_SUPERVISOR_LOGIN) {
            //用户不在线
            if (!chatData.isClientOnline(userId)) {
                chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_CLIENT_NEED_LOGIN_FAILED,
                        "websocket.message.chat.user.need.login.fail", ChatConstants.TYPE_SERVER_NOTICE, userId, type));
                return;
            }
            //message中的用户ID和session中的用户ID不相等
            if (!Objects.equals(clientId, userId)) {
                chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_CLIENT_ID_MATCH_FAILED,
                        "websocket.message.chat.client.id.not.match.fail", ChatConstants.TYPE_SERVER_NOTICE, userId, type));
                return;
            }
        } else {
            if (chatData.isClientOnline(userId)) {
                chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_HAS_LOGINED_FAILED,
                        "websocket.message.chat.has.logined", ChatConstants.TYPE_SERVER_NOTICE, userId, type));
                return;
            }
            bindClientId(userId, clientId);
        }

        switch (type) {
            //客服登陆
            case ChatConstants.TYPE_LOGIN:
                handleLogin(message);
                break;
        }

    }

    public void handleLogin(Message message) {
        logger.info("handle login");

        String userId = message.getUserId();
        Message msgResponse = new Message(ChatConstants.RESULT_OK,
                "websocket.message.chat.login.success", ChatConstants.TYPE_SERVER_NOTICE, userId);
        msgResponse.setMessageType(message.getType());

        User user = userService.userLogin(userId, message.getLoginName(), message.getPwd());
        if (user == null) {
            chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_LOGIN_FAILED,
                    "websocket.message.chat.login.failed", ChatConstants.TYPE_SERVER_NOTICE, userId, message.getType()));
            return;
        }

        //登陆成功加入坐席队列
        Seat seat = null;
        for (String aid : user.getLstAid()) {
//            if (aid == null)
        }

        chatData.sendMessageToCurrentUser(msgResponse);
    }
}
