package com.xxh.websocket.dao;

import com.xxh.websocket.entity.User;
import com.xxh.websocket.entity.UserAbility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wulongtao on 2017/7/20.
 */
@Repository
public class UserDO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserById(String userId) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM `gchat_user` WHERE `user_id` = ?", new Object[]{userId}, new BeanPropertyRowMapper<>(User.class));
        return user;
    }

    public List<UserAbility> listUserAbility(String userId) {
        List<UserAbility> lstUserAbility = jdbcTemplate.query("SELECT * FROM `gchat_user_ability` WHERE `userId` = ?", new Object[]{userId}, new BeanPropertyRowMapper<>(UserAbility.class));
        return lstUserAbility;
    }
}
