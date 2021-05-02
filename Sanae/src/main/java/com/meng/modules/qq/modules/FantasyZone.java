package com.meng.modules.qq.modules;

import com.meng.bot.Functions;
import com.meng.config.ConfigManager;
import com.meng.modules.fantasyZone.FantasyZoneApi;
import com.meng.modules.fantasyZone.javabean.Fantasy;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.MessageManager;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
import com.meng.tools.SJFExecutors;
import com.meng.tools.Tools;
import java.io.File;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class FantasyZone extends BaseModule implements IGroupMessageEvent {

    private File[] imageFolder = new File(SBot.appDirectory + "/image/r15").listFiles();

    public FantasyZone(SBot sbot) {
        super(sbot);
    }
    @Override
    public FantasyZone load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean onGroupMessage(final GroupMessageEvent event) {
        if (!ConfigManager.getInstance().isFunctionEnabled(event.getGroup(), Functions.FantasyZone)) {
            return false;
        }
        if (event.getMessage().contentToString().equals("copper")) {
            Image uploadImage = entity.toImage(Tools.ArrayTool.rfa(imageFolder), event.getGroup());
            int[] id = sendMessage(event.getGroup(), uploadImage);
            MessageManager.autoRecall(entity, id);
        } else if (event.getMessage().contentToString().equals("fantasy")) {
            SJFExecutors.execute(new Runnable(){

                    @Override
                    public void run() {
                        Fantasy fts = FantasyZoneApi.getPicture();
                        File imageFile = new File(SBot.appDirectory + "/fantasy/" + fts.id + ".jpg");
                        FileTool.saveFile(imageFile, Network.httpGetRaw(fts.url));
                        Image img = entity.toImage(imageFile, event.getGroup());
                        
                        MessageChainBuilder builder = new MessageChainBuilder();
                        builder.add("pixiv id:");
                        builder.add(String.valueOf(fts.id));
                        builder.add("\n");
                        builder.add(img);
                        sendMessage(event, builder.asMessageChain()); 
                    }
                }); 
        }
        return false;
    }
}
