package com.meng;

import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileTool;
import com.meng.tools.GSON;
import com.meng.tools.SJFExecutors;
import java.io.File;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import com.meng.handler.MessageManager;
import com.meng.tools.FileFormat;

/**
 * @author: 司徒灵羽
 **/
public class SJFMain {

    public static File lastImageFile;
    
    public static void main(String... args) {
        FileFormat.init();
        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo("C://Program Files/sanae_data/deviceInfo.json");
        AccountInfo info = GSON.fromJson(FileTool.readString(new File("C://Program Files/sjf.json")), AccountInfo.class);
        final SBot sb = new SBot(BotFactoryJvm.newBot(info.account, info.password, config), config);
        ExceptionCatcher.getInstance(sb).init();
        sb.init();
        BotMessageHandler bmh = new BotMessageHandler(sb);
        Events.registerEvents(sb, bmh);
        MessageManager.init();
        sb.login();
        SJFExecutors.execute(new Runnable(){

                @Override
                public void run() {
                    sb.join();
                }
            });
        SJFExecutors.executeAfterTime(new Runnable(){

                @Override
                public void run() {
                    for (Group group:sb.getGroups()) {
                        if (group.getBotMuteRemaining() > 0) {
                            if (group.getId() == SBot.yysGroup) {
                                continue;
                            }
                            group.quit();
                            sb.sendGroupMessage(SBot.yysGroup, "退出群" + group.getId());
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

