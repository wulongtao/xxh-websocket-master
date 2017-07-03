package com.xxh.websocket.handler.session;

import com.xxh.websocket.handler.messaging.Message;
import com.xxh.websocket.handler.messaging.MessageFactory;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by wulongtao on 2017/7/3.
 * 即时通讯会话通用层
 */
public class ChatSession {
    public static final int TYPE_WEBSOCKET = 1;
    public static final int TYPE_HTTP = 2;

    private int type;
    private Object session;

    public ChatSession() {
    }

    public ChatSession(Object session) {
        this.session = session;
        if (this.session instanceof WebSocketSession) {
            this.type = TYPE_WEBSOCKET;
        } else if (this.session instanceof HttpSocketSession) {
            this.type = TYPE_HTTP;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getSession() {
        return session;
    }

    public void setSession(Object session) {
        this.session = session;
    }

    public boolean sendMessage(Message message) throws Exception {
        if (this.type == TYPE_WEBSOCKET) {
            WebSocketSession session = (WebSocketSession) getSession();
            if (!session.isOpen()) return false;
            session.sendMessage(MessageFactory.buildMessage(message));
        } else if (this.type == TYPE_HTTP) {
            return ((HttpSocketSession)getSession()).sendMessage(message);
        }
        return true;
    }
}
