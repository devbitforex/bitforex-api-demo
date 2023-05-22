package com.bitforex.example.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;
import java.util.TreeMap;

public class SignatureUtil {

    public static String toQueryString(String uri, TreeMap<String, Object> paramsMap, String accessKey, String secretKey) {
        paramsMap.put("accessKey", accessKey);
        paramsMap.put("nonce", System.currentTimeMillis());
        String toSignString = uri + "?";
        String queryString = "";
        for (String param : paramsMap.keySet()) {
            String value = paramsMap.get(param).toString();
            queryString += param + "=" + value + "&";
        }
        if (queryString.endsWith("&")) {
            queryString = queryString.substring(0, queryString.length() - 1);
        }
        String signData = EncoderBySHA256(secretKey, toSignString + queryString);
        queryString = queryString + "&signData=" +  signData;
        return queryString;
    }
    public static TreeMap<String, Object> toQueryMap(String uri, TreeMap<String, Object> paramsMap, String accessKey, String secretKey) {
        paramsMap.put("accessKey", accessKey);
        paramsMap.put("nonce", System.currentTimeMillis());
        String toSignString = uri + "?";
        String queryString = "";
        for (String param : paramsMap.keySet()) {
            String value = paramsMap.get(param).toString();
            queryString += param + "=" + value + "&";
        }
        if (queryString.endsWith("&")) {
            queryString = queryString.substring(0, queryString.length() - 1);
        }
        String signData = EncoderBySHA256(secretKey, toSignString + queryString);
        paramsMap.put("signData", signData);
        return paramsMap;
    }
    public static String EncoderBySHA256(String secretKey, String content) {

        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return bytes2Hex(sha256_HMAC.doFinal(content.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("SHA256 ecode Exception");
        }
    }

    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
