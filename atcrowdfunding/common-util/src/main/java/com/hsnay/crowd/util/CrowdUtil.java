package com.hsnay.crowd.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrowdUtil {
    /**
     * 字符串md5加密
     *
     * @param source 传入的明文字符串
     * @return 加密结果
     */
    public static String md5(String source) {
        if (source == null || source.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        String algorithm = "md5";
        try {
            byte[] input = source.getBytes(StandardCharsets.UTF_8);
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] output = messageDigest.digest(input);
            int signum = 1;
            int radix = 16;
            BigInteger bigInteger = new BigInteger(signum, output);
            String encoded = bigInteger.toString(radix);
            return encoded;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 判断当前请求是不是Ajax请求
     *
     * @param request
     * @return
     */
    //Accept: application/json  X-Requested-With: XMLHttpRequest
    public static boolean judgeRequestType(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String requested = request.getHeader("X-Requested-With");
        return (accept != null && accept.contains("application/json"))
                ||
                (requested != null && requested.equals("XMLHttpRequest"));
    }
}
