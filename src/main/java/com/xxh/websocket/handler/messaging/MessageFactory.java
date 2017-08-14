package com.xxh.websocket.handler.messaging;

import com.google.gson.Gson;
import com.xxh.websocket.util.LocalMessage;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;

/**
 * Created by wulongtao on 2017/7/3.
 */
public class MessageFactory {

    public static TextMessage buildMessage(int result, String message) {
        Message msg = new Message(result, message);
        return new TextMessage(new Gson().toJson(msg));
    }

    public static TextMessage buildMessage(Message message) {
        return new TextMessage(new Gson().toJson(message));
    }

    public static Message buildMessage(int result, String message, int type, String userId) {
        return new Message(result, message, type, userId);
    }

    public static Message buildMessage(int result, String message, int type, String userId, int messageType) {
        Message msg = new Message(result, message, type, userId);
        msg.setMessageType(messageType);
        return msg;
    }

    public static Message buildMessage(int result, String message, int type) {
        return new Message(result, message, type);
    }

}
