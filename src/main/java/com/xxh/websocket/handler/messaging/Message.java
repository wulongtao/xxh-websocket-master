package com.xxh.websocket.handler.messaging;

/**
 * Created by wulongtao on 2017/7/3.
 */
public class Message {
    //返回码
    private Integer result;
    //返回信息
    private String message;
    //消息类型
    private Integer type;
    //用户ID
    private String userId;

    public Message(Integer result, String message) {
        this.result = result;
        this.message = message;
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
        this.message = message;
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
}
