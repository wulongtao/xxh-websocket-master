package com.xxh.websocket.entity;

/**
 * 用户实体类
 * Created by wulongtao on 2017/8/17.
 */
public class Customer {
    private String id;
    private String lastServiceUserId;
    private String gmtCreate;
    private String gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastServiceUserId() {
        return lastServiceUserId;
    }

    public void setLastServiceUserId(String lastServiceUserId) {
        this.lastServiceUserId = lastServiceUserId;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
}
