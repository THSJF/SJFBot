package com.meng.modules.qq.modules;

import com.meng.bot.Functions;
import com.meng.config.CommandDescribe;
import com.meng.config.ConfigManager;
import com.meng.help.HelpGenerator;
import com.meng.help.Permission;
import com.meng.modules.FantasyZoneApi;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.MessageManager;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
import com.meng.tools.SJFExecutors;
import com.meng.tools.SJFPathTool;
import com.meng.tools.SJFRandom;
import java.io.File;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class FantasyZone extends BaseModule implements IGroupMessageEvent {

    public FantasyZone(SBot sbot) {
        super(sbot);
    }

    @Override
    public String getModuleName() {
        return "冻鳗图片";
    }

    @Override
    public BaseModule load() {
        HelpGenerator.Item mainMenu = HelpGenerator.getInstance().newItem(Permission.Normal, getModuleName());
        mainMenu.arg(".fantasy").arg("从FantasyZone获取冻鳗图");
        mainMenu.arg(".copper").arg("自身存储的冻鳗图");
        return super.load();
    }

    @Override
    @CommandDescribe(cmd = "copper/fantasy", note = "二刺螈图片")
    public boolean onGroupMessage(final GroupMessageEvent event) {
        if (!ConfigManager.getInstance().isFunctionEnabled(event.getGroup(), Functions.FantasyZone)) {
            return false;
        }
        if (event.getMessage().contentToString().equals("copper")) {
            Image uploadImage = entity.toImage(SJFRandom.randomSelect(new File(SJFPathTool.getR15Path()).listFiles()), event.getGroup());
            int[] id = sendMessage(event.getGroup(), uploadImage).getIds();
            MessageManager.autoRecall(entity, id);
            return true;
        } 
        if (event.getMessage().contentToString().equals("fantasy")) {
            SJFExecutors.execute(new Runnable(){

                    @Override
                    public void run() {
                        FantasyZoneApi.Fantasy fts = FantasyZoneApi.getPicture();
                        File imageFile = SJFPathTool.getFantasyPath(fts.id + ".jpg");
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
            return true;
        }
        return false;
    }
}
