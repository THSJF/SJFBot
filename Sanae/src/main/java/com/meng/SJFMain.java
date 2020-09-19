package com.meng;

import com.whitemagic2014.bot.SJF;
import com.whitemagic2014.events.GroupEvents;
import com.whitemagic2014.events.MessageEvents;
import com.whitemagic2014.events.RecallEvent;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import com.whitemagic2014.bot.BotWrapper;

public class SJFMain {
    public static void main(String... args) {
        
        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo("deviceInfo.json");
        final Bot bot = BotFactoryJvm.newBot(2528419891L, "sword_a", config);
        Autoreply atr = new Autoreply(bot);
        atr.CQ=new CoolQ(bot);
        atr.startup();
        BotWrapper wrapper=new BotWrapper(bot,atr,atr.CQ);
        // for (ListenerHost event : events) {
        Events.registerEvents(bot, new GroupEvents(wrapper));
        Events.registerEvents(bot, new MessageEvents(wrapper));
        Events.registerEvents(bot, new RecallEvent(wrapper));
        Events.registerEvents(bot,atr.CQ);

        // }

        bot.login();


        new Thread(new Runnable(){

                @Override
                public void run() {
                    bot.join(); 
                }
            }).start();
        
        
      //  new SJF(atr);      
    }
}
