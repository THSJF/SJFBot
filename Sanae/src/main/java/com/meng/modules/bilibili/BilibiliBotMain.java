package com.meng.modules.bilibili;

import com.meng.tools.Network;
import java.util.HashMap;
import java.util.Map;
import com.meng.tools.SJFExecutors;
import java.util.concurrent.TimeUnit;
import com.meng.modules.bilibili.live.LiveListener;

public class BilibiliBotMain {
    
    public static String REFERER = "Referer";
    
    public static Map<String, String> liveHead = new HashMap<String,String>(){
        {
            put("Host", "api.live.bilibili.com");
            put("Accept", "application/json, text/javascript, */*; q=0.01");
            put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            put("Connection", "keep-alive");
            put("Origin", "https://live.bilibili.com");
        }
    };
    public static Map<String, String> mainHead = new HashMap<String,String>(){
        {
            put("Host", "api.bilibili.com");
            put("Accept", "application/json, text/javascript, */*; q=0.01");
            put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            put("Connection", "keep-alive");
            put("Origin", "https://www.bilibili.com");
        }
    };
    
    public static String getCsrf(String cookie) {
        return Network.cookieToMap(cookie).get("bili_jct");
    }
    
    public static void main(String... args) {
        SJFExecutors.executeWithFixedDelay(new LiveListener(),0,30,TimeUnit.SECONDS);
    }
}
