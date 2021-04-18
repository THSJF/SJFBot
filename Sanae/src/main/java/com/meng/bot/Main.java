package com.meng.bot;

import com.meng.modules.qq.BotMessageHandler;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.MessageManager;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.GSON;
import com.meng.tools.SJFExecutors;
import java.io.File;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.BotConfiguration;

public class Main {

    public static File lastImageFile;

    public static void main(String... args) {
        FileFormat.init();
        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo("C://Program Files/sanae_data/deviceInfo.json");

        AccountInfo info = GSON.fromJson(FileTool.readString(new File("C://Program Files/sjf.json")), AccountInfo.class);
        final SBot sb = new SBot(BotFactory.INSTANCE.newBot(info.account, info.password, config));
        ExceptionCatcher.getInstance().init();
        sb.init();
        BotMessageHandler bmh = new BotMessageHandler(sb);
        // GlobalEventChannel.INSTANCE.registerListenerHost(bmh);
        sb.getEventChannel().registerListenerHost(bmh);
//        EventChannel channel = GlobalEventChannel.INSTANCE.filter(new Function1<Event, Boolean>(){
//
//                @Override
//                public Boolean invoke(Event p1) {
//                    return p1 instanceof BotEvent && ((BotEvent) p1).getBot().getId() == 2528419891L;
//                }
//            });

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
                        if (group.getBotAsMember().getMuteTimeRemaining() > 0) {
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
