package com.xxh.websocket.handler.messaging;

import com.google.gson.Gson;
import org.springframework.web.socket.TextMessage;

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
}
