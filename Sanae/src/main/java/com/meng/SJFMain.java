package com.meng;

import com.whitemagic2014.bot.MagicBotR;
import java.util.List;
import net.mamoe.mirai.event.ListenerHost;

public class SJFMain {
    
    public List<ListenerHost> events;
    private String lognet;

    public SJFMain(){
        MagicBotR bot = new MagicBotR(2528419891L, "sword_arr", "deviceInfo.json", events, lognet);
        
    }
    public static void main(String... args) {
new SJFMain();
        
    }
}
