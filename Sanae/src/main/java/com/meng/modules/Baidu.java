package com.meng.modules;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meng.tools.FileTool;
import com.meng.tools.Hash;
import com.meng.tools.JsonHelper;
import com.meng.tools.Network;
import com.meng.tools.SJFRandom;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Baidu {
    private static AppId appid = JsonHelper.fromJson(FileTool.readString(new File("C://Program Files/sjf2.json")), AppId.class);

    public static String generalTranslate(String cmd) {  
        long salt = SJFRandom.nextInRange(1000000000, 9999999999L);
        String sign = encode(appid.id + cmd + salt + appid.sign);
        System.out.println("appid md5:" + encode(sign));
        System.out.println(Hash.toHexString(sign.getBytes(StandardCharsets.UTF_8)));
        System.out.println(Hash.getMd5Instance().calculate(sign));
        String result = Network.httpPost(
            "https://fanyi-api.baidu.com/api/trans/vip/translate",
            null,
            null,
            "q", cmd,
            "from", "auto",
            "to", "zh",
            "appid", appid.id,
            "salt", salt,
            "sign", sign);
        JsonObject jobj = new JsonParser().parse(result).getAsJsonObject(); 
        return jobj.get("trans_result").getAsJsonArray().get(0).getAsJsonObject().get("dst").getAsString();
    }

    private static String encode(String s) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
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

    public static class AppId {
        public String id;
        public String sign;
    }
}
