package com.meng.modules.touhou;

public class THMusic {
    public String name;
    public THGameData game;

    public THMusic(String name, THGameData game) {
        this.name = name;
        this.game = game;
    }

    public String getNameCN() {
        int index = name.indexOf("~");
        if (index == -1) {
            char c = name.charAt(0);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                return null;
            } else {
                return name;
            }
        } else {
            return name.substring(index - 1); 
        }
    }

    public String getNameEng() {
        int index = name.indexOf("~");
        if (index == -1) {
            char c = name.charAt(0);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                return name;
            } else {
                return null;
            }
        } else {
            return name.substring(index - 1, name.length()); 
        }
    }
}
