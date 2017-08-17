package com.xxh.websocket.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;

/**
 * Created by wulongtao on 2017/8/17.
 */
@Repository
public class CustomerDO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveCustomer(String id, String lastServiceId, Date gmtCreate, Date gmtModified) {
        jdbcTemplate.update("INSERT  INTO `gchat_costomer`() VALUES ");
    }

}
