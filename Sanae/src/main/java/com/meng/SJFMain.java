package com.meng;

import com.meng.adapter.*;
import com.meng.config.*;
import com.meng.modules.*;
import com.meng.tools.*;
import java.io.*;
import java.util.concurrent.*;
import net.mamoe.mirai.*;
import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.event.*;
import net.mamoe.mirai.utils.*;

/**
 * @author: 司徒灵羽
 **/

public class SJFMain {

    public static void main(String... args) {

        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo("C://Program Files/bot_data/deviceInfo.json");
        AccountInfo info = GSON.fromJson(FileTool.readString(new File("C://Program Files/sjf.json")), AccountInfo.class);
        final Bot bot = BotFactoryJvm.newBot(info.account, info.password, config);
        SJFTX tx = new SJFTX(bot);
        ModuleManager moduleManager = new ModuleManager();
        SJFRX rx = new SJFRX(moduleManager);
        Events.registerEvents(bot, rx);
        ConfigManager configManager = new ConfigManager().init();
        final BotWrapperEntity entity = new BotWrapperEntity(bot, tx, rx, moduleManager, configManager);
        ExceptionCatcher.getInstance(entity).init();
        moduleManager.setBotWrapperEntity(entity);
        configManager.setBotWrapperEntity(entity);
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
                            entity.sjfTx.sendGroupMessage(BotWrapperEntity.yysGroup, "退出群" + group.getId());
                        }
                    }
                }
            }, 1, TimeUnit.MINUTES); 
        moduleManager.loadModules(bot);
    }
    
    public static class AccountInfo {
        public long account;
        public String password;
    }
}

