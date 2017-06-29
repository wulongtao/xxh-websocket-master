package com.xxh.websocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wulongtao on 2017/6/28.
 */
@RestController
public class DemoController {
    Logger logger = LoggerFactory.getLogger(DemoController.class);
    @Value("${spring.websocket.path1}")
    private String path;

    @RequestMapping("/demo")
    public Map<String, Object> demo() {
        logger.debug("aaaa");
        logger.info("bbbb");
        Map<String, Object> map = new HashMap<>();
        map.put("result", 0);
        map.put("message", "success");
        return map;
    }

    @RequestMapping("/testProp")
    public String testProp() {
        return path;
    }
}
