package com.xxh.websocket.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wulongtao on 2017/7/20.
 */
public class User {
    private String userId;
    private String userName;
    private String pwd;

    private List<String> lstAid = new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void addAid(String aid) {
        this.lstAid.add(aid);
    }

    public List<String> getLstAid() {
        return lstAid;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
