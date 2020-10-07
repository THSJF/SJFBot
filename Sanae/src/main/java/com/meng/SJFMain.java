package com.meng;

import com.meng.adapter.BotWrapperEntity;
import com.meng.adapter.SJFRX;
import com.meng.adapter.SJFTX;
import com.meng.config.ConfigManager;
import com.meng.modules.ModuleManager;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.SJFExecutors;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import java.util.concurrent.TimeUnit;
import com.meng.config.AccountInfo;
import com.meng.tools.GSON;
import com.meng.tools.FileTool;
import java.io.File;

/**
 * @author: 司徒灵羽
 **/

public class SJFMain {

    public static void main(String... args) {

        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo("deviceInfo.json");
        System.out.println(GSON.toJson(config));
        BotConfiguration bc2= new BotConfiguration();
        bc2.randomDeviceInfo();
        System.out.println(bc2);
        AccountInfo info = GSON.fromJson(FileTool.readString(new File("C://Program Files/sjf.json")), AccountInfo.class);
        final Bot bot = BotFactoryJvm.newBot(info.account, info.password, config);
        SJFTX tx = new SJFTX(bot);
        ModuleManager moduleManager = new ModuleManager();
        SJFRX rx = new SJFRX(moduleManager);
        Events.registerEvents(bot, rx);
        ConfigManager configManager = new ConfigManager();
        configManager.init();
        final BotWrapperEntity entity = new BotWrapperEntity(bot, tx, rx, moduleManager, configManager);
        ExceptionCatcher.getInstance(entity).init();
        moduleManager.setBotWrapperEntity(entity);
        configManager.setBotWrapperEntity(entity);
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
                }
            });
        SJFExecutors.executeAfterTime(new Runnable(){

                @Override
                public void run() {
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
            }, 1, TimeUnit.MINUTES); 
        moduleManager.loadModules(bot);
    }
}

