package com.xxh.websocket.handler;

import com.xxh.websocket.core.Seat;
import com.xxh.websocket.core.SeatCircularBlockingQueue;
import com.xxh.websocket.entity.User;
import com.xxh.websocket.handler.data.ChatConstants;
import com.xxh.websocket.handler.data.ChatData;
import com.xxh.websocket.handler.messaging.Message;
import com.xxh.websocket.handler.messaging.MessageFactory;
import com.xxh.websocket.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * 通用的消息处理类
 * Created by wulongtao on 2017/8/17.
 */
public class MessageHandler {
    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Autowired
    protected ChatData chatData;

    @Autowired
    private UserService userService;

    /**
     * 客服登陆
     * @param message
     */
    public void handleCustomerServiceLogin(Message message) {
        logger.info("handle login");

        String userId = message.getUserId();
        int type = message.getType();

        if (Objects.isNull(userId)) {
            chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_USER_ID_NULL,
                    "websocket.message.chat.user.id.null", ChatConstants.TYPE_SERVER_NOTICE, userId, type));
            return;
        }

        if (chatData.isClientOnline(userId)) {
            chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_HAS_LOGINED_FAILED,
                    "websocket.message.chat.has.logined", ChatConstants.TYPE_SERVER_NOTICE, userId, type));
            return;
        }


        User user = userService.userLogin(userId, message.getLoginName(), message.getPwd());
        if (user == null) {
            chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_LOGIN_FAILED,
                    "websocket.message.chat.login.failed", ChatConstants.TYPE_SERVER_NOTICE, userId, message.getType()));
            return;
        }

        //客服加入对应能力组的坐席队列中
        Seat seat = null;
        for (String aid : user.getLstAid()) {
            SeatCircularBlockingQueue queue = chatData.getSeatQueue(aid, user.getOnlineStatus());
            logger.debug("用户[" + userId + "]加入能力组队列：aid=[" + aid + "]，onlineStatus=[" + user.getOnlineStatus() + "]");
        }

        //加入在线集合
        chatData.addOnlineClient(user);

        Message msgResponse = new Message(ChatConstants.RESULT_OK,
                "websocket.message.chat.login.success", ChatConstants.TYPE_SERVER_NOTICE, userId);
        msgResponse.setMessageType(message.getType());
        msgResponse.setOnlineStatus(user.getOnlineStatus());

        chatData.sendMessageToCurrentUser(msgResponse);
    }

    public void handleCustomerLogi(Message message) {
        String userId = message.getUserId();
        int type = message.getType();
        String ability = message.getAbility();

        if (Objects.nonNull(userId)) {
            if (chatData.isClientOnline(userId)) {
                chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_HAS_LOGINED_FAILED,
                        "websocket.message.chat.has.logined", ChatConstants.TYPE_SERVER_NOTICE, userId, type));
                return;
            }
        } else {

        }
    }

    /**
     * 通用验证方法，进入onMessage时需要调用此方法做校验
     * @param message
     * @param clientId
     * @return
     */
    public boolean handleCheckMessage(Message message, String clientId) {
        Integer type = message.getType();
        String userId = message.getUserId();

        //判断type是否为空
        if (type == null || type == 0) {
            chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_NEET_CLIENT_ID_ERROR,
                    "websocket.message.chat.empty.message.type.error", ChatConstants.TYPE_SERVER_NOTICE, userId, type));
            return false;
        }

        if (type != ChatConstants.TYPE_CUSTOMER_SERVICE_LOGIN && type != ChatConstants.TYPE_CUSTOMER_LOGIN) {
            if (Objects.isNull(userId)) {
                chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_USER_ID_NULL,
                        "websocket.message.chat.user.id.null", ChatConstants.TYPE_SERVER_NOTICE, userId, type));
                return false;
            }
            //用户不在线
            if (!chatData.isClientOnline(userId)) {
                chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_CLIENT_NEED_LOGIN_FAILED,
                        "websocket.message.chat.user.need.login.fail", ChatConstants.TYPE_SERVER_NOTICE, userId, type));
                return false;
            }
            //message中的用户ID和session中的用户ID不相等
            if (Objects.nonNull(clientId) && !Objects.equals(clientId, userId)) {
                chatData.sendMessageToCurrentUser(MessageFactory.buildMessage(ChatConstants.RESULT_CLIENT_ID_MATCH_FAILED,
                        "websocket.message.chat.client.id.not.match.fail", ChatConstants.TYPE_SERVER_NOTICE, userId, type));
                return false;
            }
        }

        return true;
    }
}
