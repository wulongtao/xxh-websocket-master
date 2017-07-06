package com.xxh.websocket.handler.data;

import com.xxh.websocket.handler.messaging.Message;
import com.xxh.websocket.handler.session.ChatSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wulongtao on 2017/7/5.
 * 通讯中所用到的一些共享数据
 */
@Component
public class ChatData {
    private Logger logger = LoggerFactory.getLogger(ChatData.class);

    //用户会话
    private Map<String, ChatSession> mClient = new HashMap<>();
    //错误消息队列
    private Queue<Message> qErrMsg = new LinkedBlockingQueue<>();

    public void putClient(String ClientId, ChatSession session) {
        this.mClient.put(ClientId, session);
    }

    public ChatSession getClient(String ClientId) {
        return this.mClient.get(ClientId);
    }

    public void removeClient(String ClientId) {
        if (!mClient.containsKey(ClientId)) {
            mClient.remove(ClientId);
        }
    }

    public void putErrorMessasge(Message message) {
        this.qErrMsg.offer(message);
    }

    public Message pollErrorMessage() {
        return this.qErrMsg.poll();
    }

    public Set<String> clientIdSet() {
        return this.mClient.keySet();
    }


}
