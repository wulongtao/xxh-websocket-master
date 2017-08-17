package com.xxh.websocket.handler.data;

import com.xxh.websocket.core.SeatCircularBlockingQueue;
import com.xxh.websocket.entity.User;
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
    private Map<String, ChatSession> mClientSession = new HashMap<>();
    //错误消息队列
    private Queue<Message> qErrMsg = new LinkedBlockingQueue<>();
    //在线用户集合
    private Map<String, User> mOnlineClient = new HashMap<>();
    //在线状态集合
//    private Map<String, >
    //坐席集合
    private static Map<String, Map<Integer, SeatCircularBlockingQueue>> mAbilitySeat = new HashMap<>();

    /**
     * 根据能力组和状态获取坐席队列
     * @param aid
     * @param onlineStatus
     * @return
     */
    public SeatCircularBlockingQueue getSeatQueue(String aid, int onlineStatus) {
        Map<Integer, SeatCircularBlockingQueue> mSeat = getSeatMapByAid(aid);

        if (mSeat == null) {
            return null;
        }

        if (!mSeat.containsKey(onlineStatus)) {
            mSeat.put(onlineStatus, new SeatCircularBlockingQueue());
        }

        return mSeat.get(onlineStatus);
    }

    /**
     * 根据能力组ID获取坐席队列集合
     * @param aid
     * @return
     */
    public Map<Integer, SeatCircularBlockingQueue> getSeatMapByAid(String aid) {
        if (Objects.isNull(aid)) {
            return null;
        }

        if (!mAbilitySeat.containsKey(aid)) {
            mAbilitySeat.put(aid, new TreeMap<>());
        }

        return mAbilitySeat.get(aid);
    }

    /**
     * 添加用户会话
     * @param clientId
     * @param session
     */
    public void putChatSession(String clientId, ChatSession session) {
        this.mClientSession.put(clientId, session);
    }

    /**
     * 获取用户会话
     * @param clientId
     * @return
     */
    public ChatSession getChatSession(String clientId) {
        return this.mClientSession.get(clientId);
    }

    /**
     * 删除用户会话
     * @param clientId
     */
    public void removeChatSession(String clientId) {
        if (!this.mClientSession.containsKey(clientId)) {
            this.mClientSession.remove(clientId);
        }
    }

    /**
     * 判断用户
     * @param clientId
     * @return
     */
    public boolean containChatSession(String clientId) {
        return this.mClientSession.containsKey(clientId);
    }

    /**
     * 错误信息加入队列
     * @param message
     */
    public void putErrorMessasge(Message message) {
        this.qErrMsg.offer(message);
    }

    /**
     * 错误信息出队
     * @return
     */
    public Message pollErrorMessage() {
        return this.qErrMsg.poll();
    }

    public boolean isClientOnline(String clientId) {
        return mOnlineClient.containsKey(clientId);
    }

    public void addOnlineClient(User client) {
        mOnlineClient.put(client.getId(), client);
    }

    public User getOnlineClient(String clientId) {
        return mOnlineClient.get(clientId);
    }

    /**
     * 用户ID集合
     * @return
     */
    public Set<String> clientIdSet() {
        return this.mClientSession.keySet();
    }

    /**
     * 发送消息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String clientId, Message message) {
        boolean ret = true;
        if (this.getChatSession(clientId) == null) return false;

        ChatSession chatSession = this.getChatSession(clientId);

        try {
            ret = chatSession.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            this.putErrorMessasge(message);
        }

        return ret;

    }

    /**
     * 发送消息给当前用户
     * @param message
     * @return
     */
    public boolean sendMessageToCurrentUser(Message message) {
        String clientId = message.getUserId();
        return sendMessageToUser(clientId, message);
    }

    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(Message message) {
        boolean isSuccess = true;
        Set<String> sClientId = this.clientIdSet();
        for (String clientId : sClientId) {
            ChatSession chatSession = this.getChatSession(clientId);
            try {
                isSuccess = isSuccess == false ? false : chatSession.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }


}
