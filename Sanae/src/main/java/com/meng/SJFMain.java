package com.meng;

import com.meng.adapter.BotWrapperEntity;
import com.meng.adapter.SJFRX;
import com.meng.adapter.SJFTX;
import com.meng.config.ConfigManager;
import com.meng.modules.ModuleManager;
import com.meng.tools.SJFExecutors;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;

public class SJFMain {

    public static void main(String... args) {

        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo("deviceInfo.json");
        final Bot bot = BotFactoryJvm.newBot(2856986197L, "sword_a", config);

        SJFTX tx = new SJFTX(bot);
        ModuleManager moduleManager = new ModuleManager();
        SJFRX rx = new SJFRX(moduleManager);
        Events.registerEvents(bot, rx);
        ConfigManager configManager = new ConfigManager();
        BotWrapperEntity entity = new BotWrapperEntity(bot, tx, rx, moduleManager, configManager);
        moduleManager.setBotWrapperEntity(entity);
        configManager.setBotWrapperEntity(entity);
        moduleManager.loadModules(configManager);
        moduleManager.load();
        tx.entity = rx.entity = moduleManager.entity = entity;
        MessagePool.start();
        bot.login();
        SJFExecutors.execute(new Runnable(){

                @Override
                public void run() {
                    bot.join();
                }
            });
    }
}

