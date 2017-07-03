package com.xxh.websocket.handler.messaging;

/**
 * Created by wulongtao on 2017/7/3.
 */
public class Message {
    private Integer result;
    private String message;
    private Integer type;

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
}
