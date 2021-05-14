package com.meng.modules.touhou;

import java.util.function.Consumer;

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

    public String getCharaNick() {
        String fullName = null;
        final StringBuilder sb = new StringBuilder();
        sb.append(fullName).append("有以下称号:\n");
        final String fn = fullName;
        THGameDataManager.forEachCharacter(new Consumer<THCharacter>(){

                @Override
                public void accept(THCharacter thc) {
                    if (thc.name.equals(fn)) {
                        if (thc.nick.equals("该角色信息未填坑")) {
                            return;
                        }
                        sb.append(thc.nick).append("(").append(thc.game).append(")\n"); 
                    }
                }
            });
        if (sb.toString().equals(fullName + "有以下称号:\n")) {
            return "该角色信息未填坑";
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
