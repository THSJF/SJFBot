package com.meng.config.qq;
import java.util.HashMap;

public class MultiConfigHolder {
    private HashMap<Long,ConfigHolder> hm = new HashMap<>();
    public ConfigHolder getConfig(long botQq) {
        return hm.get(botQq);
    }
}
