package com.meng.modules;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meng.tools.Network;
import com.meng.tools.SJFRandom;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.meng.tools.Hash;

public class Baidu {
    public static String generalTranslate(String cmd) {
        long salt = SJFRandom.nextInRange(1000000000, 9999999999L);
        String sign = "20200513000452672" + cmd + salt + encode("45Khk0SiGZNUIwJMj6wn");
        System.out.println("45Khk0SiGZNUIwJMj6wn md5:" + encode("45Khk0SiGZNUIwJMj6wn").equals(Hash.toHexString("45Khk0SiGZNUIwJMj6wn".getBytes(StandardCharsets.UTF_8))));
        String result = Network.httpPost(
            "https://fanyi-api.baidu.com/api/trans/vip/translate",
            null,
            null,
            "q", cmd,
            "from", "auto",
            "to", "zh",
            "appid", "20200513000452672",
            "salt", salt,
            "sign", sign);
        JsonObject jobj = new JsonParser().parse(result).getAsJsonObject(); 
        return jobj.get("trans_result").getAsJsonArray().get(0).getAsJsonObject().get("dst").getAsString();
    }

    private static String encode(String s) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            //对字符串加密，返回字节数组
            instance.update(s.getBytes(StandardCharsets.UTF_8));
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                int i = b & 0xFF;
                String hexString = Integer.toHexString(i);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
