package com.meng.modules.qq;

import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.MessageManager;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileTool;
import com.meng.tools.JsonHelper;
import com.meng.tools.SJFExecutors;
import java.io.File;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.BotConfiguration;

public class QqBotMain {
    private static QqBotMain instance;
    public SBot sbot;
    
    public static QqBotMain getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new QqBotMain();
    }

    public void init() {
        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo("C://Program Files/sanae_data/deviceInfo.json");

        AccountInfo info = JsonHelper.fromJson(FileTool.readString(new File("C://Program Files/sjf.json")), AccountInfo.class);
        sbot = new SBot(BotFactory.INSTANCE.newBot(info.account, info.password, config));
        ExceptionCatcher.getInstance().init();
        sbot.init();
        BotMessageHandler bmh = new BotMessageHandler(sbot);
        sbot.getEventChannel().registerListenerHost(bmh);
        MessageManager.init();
        sbot.login();
        SJFExecutors.execute(new Runnable(){

                @Override
                public void run() {
                    sbot.join();
                }
            });
        SJFExecutors.executeAfterTime(new Runnable(){

                @Override
                public void run() {
                    for (Group group:sbot.getGroups()) {
                        if (group.getBotAsMember().getMuteTimeRemaining() > 0) {
                            if (group.getId() == SBot.yysGroup) {
                                continue;
                            }
                            group.quit();
                            sbot.sendGroupMessage(SBot.yysGroup, "退出群" + group.getId());
                        }
                    }
                }
            }, 1, TimeUnit.MINUTES);  
    }

    public static class AccountInfo {
        public long account;
        public String password;
    }
}
