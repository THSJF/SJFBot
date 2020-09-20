package com.meng;

import net.mamoe.mirai.Bot;

public class BotWrapper {
    private Bot bot;
    private Autoreply autoreply;
    private CoolQ CQ;
    private CQCode CC;

    public BotWrapper(Bot b, Autoreply a, CoolQ c, CQCode cc) {
        bot = b;
        autoreply = a;
        CQ = c;
        CC = cc;
    }

    public CoolQ getCQ() {
        return CQ;
    }

    public Autoreply getAutoreply() {
        return autoreply;
    }

    public Bot getBot() {
        return bot;
    }
    
    public CQCode getCC(){
        return CC;
    }
    
    public void execute(Runnable r){
        autoreply.threadPool.execute(r);
    }
}
