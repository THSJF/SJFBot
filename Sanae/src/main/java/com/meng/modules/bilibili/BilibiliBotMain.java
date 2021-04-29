package com.meng.modules.bilibili;

import com.meng.tools.Network;
import java.util.HashMap;
import java.util.Map;
import com.meng.tools.SJFExecutors;
import java.util.concurrent.TimeUnit;
import com.meng.modules.bilibili.live.LiveListener;
import com.meng.config.Person;
import com.meng.config.qq.GroupConfig;
import com.meng.config.ConfigManager;
import com.meng.Functions;
import com.meng.modules.qq.QqBotMain;

public class BilibiliBotMain {

    public static String REFERER = "Referer";
    private static BilibiliBotMain instance;

    public LiveListener liveListener;

    public static BilibiliBotMain getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new BilibiliBotMain();
    }
    
    public static Map<String, String> liveHead = new HashMap<String,String>(){{
            put("Host", "api.live.bilibili.com");
            put("Accept", "application/json, text/javascript, */*; q=0.01");
            put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            put("Connection", "keep-alive");
            put("Origin", "https://live.bilibili.com");
        }};
    public static Map<String, String> mainHead = new HashMap<String,String>(){{
            put("Host", "api.bilibili.com");
            put("Accept", "application/json, text/javascript, */*; q=0.01");
            put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            put("Connection", "keep-alive");
            put("Origin", "https://www.bilibili.com");
        }};

    public static String getCsrf(String cookie) {
        return Network.cookieToMap(cookie).get("bili_jct");
    }

    public void init() {
        liveListener = LiveListener.getInstance();
        SJFExecutors.executeWithFixedDelay(liveListener, 0, 30, TimeUnit.SECONDS);
        liveListener.addListener(new LiveListener.OnStatusChangeListener(){

                @Override
                public void onStart(Person person) {
                    for (Map.Entry<Long,GroupConfig> entry : ConfigManager.getInstance().getConfigHolder().groupCfgs.entrySet()) {
                        if (entry.getValue().enabled.contains(Functions.BilibiliTip)) {
                            QqBotMain.getInstance().sbot.sendGroupMessage(entry.getKey(), String.format("%s开始直播，链接%s", person.name, person.liveUrl));
                        }
                    }
                }

                @Override
                public void onStop(Person person) {
                    for (Map.Entry<Long,GroupConfig> entry : ConfigManager.getInstance().getConfigHolder().groupCfgs.entrySet()) {
                        if (entry.getValue().enabled.contains(Functions.BilibiliTip)) {
                            QqBotMain.getInstance().sbot.sendGroupMessage(entry.getKey(), String.format("%s开始直播结束", person.name));
                        }
                    }
                }
            });
    }
}
