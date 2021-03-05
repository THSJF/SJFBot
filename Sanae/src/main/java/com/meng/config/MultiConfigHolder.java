package com.meng.config;
import java.util.HashMap;
import com.meng.config.javabeans.ConfigHolder;

public class MultiConfigHolder {
    private HashMap<Long,ConfigHolder> hm = new HashMap<>();
    public ConfigHolder getConfig(long botQq) {
        return hm.get(botQq);
    }
}
