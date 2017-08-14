package com.xxh.websocket.handler.data;

/**
 * Created by wulongtao on 2017/7/7.
 */
public class User {
    private String userId;
    private String nick;

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
