package com.xxh.websocket.core;

import java.util.*;

/**
 * Created by wulongtao on 2017/7/6.
 * 坐席：记录该坐席的客服用户ID，客服正在服务的用户ID集合，
 */
public class Seat {
    //坐席用户ID
    private String userId;
    //咨询用户集合
    private Set<String> lstClient = new LinkedHashSet<>();

    public Seat() {

    }

    public Seat(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSeatServiceCount() {
        return this.lstClient.size();
    }

    public void addClient(String clientId) {
        this.lstClient.add(clientId);
    }

    public boolean removeClient(String clientId) {
        return this.lstClient.remove(clientId);
    }

    public Set<String> getAllClient() {
        return this.lstClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;

        Seat seat = (Seat) o;

        if (!Objects.equals(getUserId(), seat.getUserId())) return false;
        return Objects.equals(lstClient, seat.lstClient);
    }

    @Override
    public int hashCode() {
        int result = getUserId().hashCode();
        result = 31 * result + lstClient.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "userId='" + userId + '\'' +
                ", lstClientId=" + lstClient +
                '}';
    }
}
