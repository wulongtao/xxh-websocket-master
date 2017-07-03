package com.xxh.websocket.handler.session;

import com.xxh.websocket.handler.messaging.Message;

/**
 * Created by wulongtao on 2017/7/3.
 */
public interface HttpSocketSession {
    boolean sendMessage(Message message);
}
