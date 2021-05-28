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
import com.meng.modules.qq.ILoad;
import com.meng.modules.qq.BaseModule;
import com.meng.tools.FileWatcher;

public class Baidu implements ILoad {

    private static Baidu instance;
    private File cfg = new File("C://Program Files/sjf2.json");
    private AppId appid = JsonHelper.fromJson(FileTool.readString(cfg), AppId.class);

    public static Baidu getInstance() {
        if (instance == null) {
            instance = new Baidu();
        }
        return instance;
    }

    private Baidu(){
        FileWatcher.getInstance().addOnFileChangeListener(cfg, new Runnable(){

                @Override
                public void run() {
                    reload();
                }
            });
    }
    
    @Override
    public BaseModule load() {
        return null;
    }

    @Override
    public BaseModule reload() {
        appid = JsonHelper.fromJson(FileTool.readString(new File("C://Program Files/sjf2.json")), AppId.class);
        return null;
    }

    public String generalTranslate(String cmd) {  
        long salt = SJFRandom.nextInRange(1000000000, 9999999999L);
        String sign = Hash.getMd5Instance().calculate(appid.id + cmd + salt + appid.sign);
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

    public static class AppId {
        public String id;
        public String sign;
    }
}
