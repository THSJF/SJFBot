package com.meng.modules.touhou;

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
    
}
