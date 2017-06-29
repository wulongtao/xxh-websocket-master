package com.xxh.websocket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wulongtao on 2017/6/28.
 */
@RestController
public class DemoController {

    @RequestMapping("/demo")
    public Map<String, Object> demo() {
        Map<String, Object> map = new HashMap<>();
        map.put("result", 0);
        map.put("message", "success");
        return map;
    }
}
