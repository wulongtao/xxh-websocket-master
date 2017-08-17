package com.xxh.websocket.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客服实体类
 * Created by wulongtao on 2017/7/20.
 */
public class User {
    private String id;
    private String loginName;
    private int onlineStatus;
    private String pwd;
    private Date gmtModified;
    private Date gmtCreate;

    private List<String> lstAid = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLstAid(List<String> lstAid) {
        this.lstAid = lstAid;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", onlineStatus=" + onlineStatus +
                ", pwd='" + pwd + '\'' +
                ", gmtModified=" + gmtModified +
                ", gmtCreate=" + gmtCreate +
                ", lstAid=" + lstAid +
                '}';
    }
}
