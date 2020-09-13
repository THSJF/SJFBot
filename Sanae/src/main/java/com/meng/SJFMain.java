package com.meng;

import java.util.List;
import net.mamoe.mirai.event.ListenerHost;
import com.meng.bot.SJFBot;
import java.util.ArrayList;
import com.meng.events.MessageEvents;

public class SJFMain {
    
    public List<ListenerHost> events;
    private String lognet;

    public SJFMain(){
        events = new ArrayList<>();
        events.add(new MessageEvents());
        SJFBot bot = new SJFBot(2528419891L, "sword", "deviceInfo.json", events, lognet);
        
    }
    public static void main(String... args) {
        new SJFMain(); 
    }
}
