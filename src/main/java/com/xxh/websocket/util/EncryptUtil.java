package com.xxh.websocket.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wulongtao on 2017/7/20.
 */
public class EncryptUtil {

    public static String encodeMd5WithSalt(String content, String salt) {
        MessageDigest md = null;
        String encodeStr = "";

        try {
            md = MessageDigest.getInstance("MD5");
            md.update((salt.trim()+content).getBytes());
            byte[] b = md.digest();
            Base64 base64Encoder = new Base64();
            encodeStr = new String(base64Encoder.encode(b));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            encodeStr = content;
        } finally {
            return encodeStr;
        }
    }
}
