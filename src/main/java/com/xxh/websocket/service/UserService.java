package com.xxh.websocket.service;

import com.xxh.websocket.entity.User;
/**
 * Created by wulongtao on 2017/7/20.
 */
public interface UserService {

    User userLogin(String userId, String loginName, String pwd);


}
