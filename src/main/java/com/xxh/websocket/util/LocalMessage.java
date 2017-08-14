package com.xxh.websocket.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by wulongtao on 2017/7/7.
 */
@Component
public class LocalMessage {
    @Autowired
    private MessageSource messageSource;


    public String getMessage(String key) {
        String message = null;
        try {
            message = messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {}
        return message;
    }
}
