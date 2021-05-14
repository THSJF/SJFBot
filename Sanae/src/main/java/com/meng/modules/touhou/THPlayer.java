package com.meng.modules.touhou;
import com.meng.tools.SJFRandom;

public class THPlayer {
    public String name;
    public String[] type;

    public THPlayer(String name) {
        this.name = name;
    }

    public THPlayer(String name, String... type) {
        this.name = name;
        this.type = type;
    }

    public String randomType() {
        if (type == null) {
            return name;
        }
        return name + "-" + SJFRandom.randomSelect(type);
    }
}
