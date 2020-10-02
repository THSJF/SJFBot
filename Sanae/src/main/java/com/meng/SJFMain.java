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
import com.meng.sjfmd.libs.ExceptionCatcher;
import net.mamoe.mirai.contact.Group;

/**
 * @author: 司徒灵羽
 **/

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
        final BotWrapperEntity entity = new BotWrapperEntity(bot, tx, rx, moduleManager, configManager);
        ExceptionCatcher.getInstance(entity).init();
        moduleManager.setBotWrapperEntity(entity);
        configManager.setBotWrapperEntity(entity);
        configManager.init();
        moduleManager.loadModules(configManager);
        moduleManager.loadModules(entity);
        moduleManager.load();
        tx.entity = rx.entity = moduleManager.entity = entity;
        MessagePool.start();
        bot.login();
        SJFExecutors.execute(new Runnable(){

                @Override
                public void run() {
                    bot.join();
                    for (Group group:bot.getGroups()) {
                        if (group.getBotMuteRemaining() > 0) {
                            if (group.getId() == entity.yysGroup) {
                                continue;
                            }
                            group.quit();
                            bot.getGroup(entity.yysGroup).sendMessage("退出群" + group.getId());
                        }
                    }
                }
            });
        moduleManager.loadModules(bot);
    }
}

