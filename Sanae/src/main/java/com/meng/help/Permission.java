package com.meng.help;

public enum Permission {
    Normal(0),
    Admin(1),
    Master(2),
    BotMaster(3);

    private Permission(int i){
        pms = i;  
    }

    private int pms;
}
