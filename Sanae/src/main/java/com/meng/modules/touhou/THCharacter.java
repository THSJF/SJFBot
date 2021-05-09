package com.meng.modules.touhou;

public class THCharacter {
    public String name;
    public THGameData game;
    public String nick = "该角色信息未填坑";

    public THCharacter(String name, THGameData game) {
        this.name = name;
        this.game = game;
    }

    public THCharacter(String name, THGameData game, String nick) {
        this.name = name;
        this.game = game;
        this.nick = nick;
    }
}
