package com.meng;

import com.meng.modules.ModuleManager;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;

public class SJFMain {
    public static void main(String... args) {

        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo("deviceInfo.json");
        final Bot bot = BotFactoryJvm.newBot(2528419891L, "sword_a", config);
        Autoreply atr = new Autoreply(bot);
              Events.registerEvents(bot, atr.CQ);
        bot.login();
        new Thread(new Runnable(){

                @Override
                public void run() {
                    bot.join(); 
                }
            }).start();
    }
}
