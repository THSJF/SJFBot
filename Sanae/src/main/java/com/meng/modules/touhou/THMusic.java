package com.meng.modules.touhou;

public class THMusic {
    public String name;
    public THGameData game;

    public THMusic(String name, THGameData game) {
        this.name = name;
        this.game = game;
    }

    public String getNameCN() {
        String[] parts = name.split(" ~ ");
        if (parts.length < 2) {
            char c = parts[0].charAt(0);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                return null;
            } else {
                return name;
            }
        }
        char c = parts[0].charAt(0);
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
            return parts[1];
        } else if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')) {
            return parts[0];
        }
        return null;
    }

    public String getNameEng() {
        String[] parts = name.split(" ~ ");
        if (parts.length < 2) {
            char c = parts[0].charAt(0);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                return name;
            } else {
                return null;
            }
        }
        char c = parts[0].charAt(0);
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
            return parts[0];
        } else if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')) {
            return parts[1];
        }
        return null;
    }
}
