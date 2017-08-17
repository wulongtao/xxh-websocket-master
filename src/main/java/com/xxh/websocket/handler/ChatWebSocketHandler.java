package com.xxh.websocket.handler;

import com.xxh.websocket.core.Seat;
import com.xxh.websocket.core.SeatCircularBlockingQueue;
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

    @Autowired
    private MessageHandler handler;

    @Override
    public void onMessage(Message message, String clientId) {
        Integer type = message.getType();
        String userId = message.getUserId();

        if (!handler.handleCheckMessage(message, clientId)) {
            return;
        }


        switch (type) {
            case ChatConstants.TYPE_CUSTOMER_SERVICE_LOGIN: //客服登陆
                handler.handleCustomerServiceLogin(message);
                bindClientId(userId, clientId);
                break;

            case ChatConstants.TYPE_CUSTOMER_SERVICE_LOGOUT: //客服退出登陆
                if (closeClientSession(message.getUserId())) {

                }
                break;

            case ChatConstants.TYPE_CUSTOMER_LOGIN: //用户登陆

                bindClientId(userId, clientId);
                break;
        }
    }

}
