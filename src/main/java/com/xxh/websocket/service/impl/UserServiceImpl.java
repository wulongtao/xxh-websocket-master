package com.xxh.websocket.service.impl;

import com.xxh.websocket.dao.UserDO;
import com.xxh.websocket.entity.User;
import com.xxh.websocket.entity.UserAbility;
import com.xxh.websocket.service.UserService;
import com.xxh.websocket.util.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by wulongtao on 2017/7/20.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDO userDO;

    @Override
    public User userLogin(String userId, String loginName, String pwd) {
        User user = userDO.getUserById(userId);
        if (!Objects.equals(user.getPwd(), EncryptUtil.encodeMd5WithSalt(pwd, loginName))) {
            logger.debug("pwd="+user.getPwd()+",encrypt="+EncryptUtil.encodeMd5WithSalt(pwd, loginName));
            return null;
        }

        List<UserAbility> lstUserAbility = userDO.listUserAbility(userId);
        for (UserAbility userAbility : lstUserAbility) {
            String aid = userAbility.getAid();
            if (aid == null || Objects.equals("", aid.trim())) {
                continue;
            }
            user.addAid(userAbility.getAid());
        }

        return user;
    }


}
