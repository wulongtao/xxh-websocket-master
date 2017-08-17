package com.xxh.websocket.handler.messaging;

import com.google.gson.annotations.Expose;
import com.xxh.websocket.util.LocalMessage;
import com.xxh.websocket.util.SpringKit;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by wulongtao on 2017/7/3.
 * 国际化直接在这个类中封装了
 */
public class Message {
    //返回码
    private Integer result;
    //返回信息
    private String message;
    //消息类型
    private Integer type;
    //type的源类型
    private Integer messageType;
    //用户ID
    private String userId;
    //登陆账号
    private String loginName;
    //密码
    private String pwd;
    //在线状态
    private Integer onlineStatus;
    //能力ID
    private String ability;
    //时间
    private long addTime;
    //时间字符串
    private String addTimeStr;

    public Message() {
        Clock clock = Clock.systemUTC();
        this.addTime = clock.millis();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.addTimeStr = pattern.format(LocalDateTime.now(clock));
    }

    public Message(Integer result, String message) {
        this();
        this.result = result;
        setMessage(message);
    }

    public Message(Integer result, String message, int type) {
        this(result, message);
        this.type = type;
    }

    public Message(Integer result, String message, Integer type, String userId) {
        this(result, message);
        this.type = type;
        this.userId = userId;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {

        LocalMessage localMessage = (LocalMessage) SpringKit.getBean("localMessage");
        if (localMessage == null) {
            this.message = message;
            return;
        }
        if (localMessage.getMessage(message) != null) {
            this.message = localMessage.getMessage(message);
        } else {
            this.message = message;
        }
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getAddTimeStr() {
        return addTimeStr;
    }

    public void setAddTimeStr(String addTimeStr) {
        this.addTimeStr = addTimeStr;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }
}
