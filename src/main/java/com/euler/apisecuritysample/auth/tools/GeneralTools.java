package com.euler.apisecuritysample.auth.tools;


import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * Author: xjf
 * Version: 1.0.0
 * Description:
 * CreateDateTime: 2023-12-12
 */

public class GeneralTools {

    private static final String BASE62_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generateAppId(){
        long num = IdUtils.nextId();
        StringBuilder sb = new StringBuilder();
        do {
            int remainder = (int) (num % 62);
            sb.insert(0, BASE62_CHARACTERS.charAt(remainder));
            num /= 62;
        } while (num != 0);
        return sb.toString();
    }

    public static String generateAppSecret(String appId){
        return DigestUtils.md5DigestAsHex(appId.getBytes(StandardCharsets.UTF_8)).toUpperCase();
    }

}
