package com.meng;

import net.mamoe.mirai.Bot;

public class BotWrapper {
    private Bot bot;
    private Autoreply autoreply;
    private CoolQ CQ;

    public BotWrapper(Bot b, Autoreply a, CoolQ c) {
        bot = b;
        autoreply = a;
        CQ = c;
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
    
    public void execute(Runnable r){
        autoreply.threadPool.execute(r);
    }
}
