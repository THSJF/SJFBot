package com.meng.modules.qq.modules;

import com.meng.Functions;
import com.meng.config.ConfigManager;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.MessageManager;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.Tools;
import java.io.File;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;

public class Copper extends BaseModule implements IGroupMessageEvent {

    private File[] imageFolder = new File(SBot.appDirectory + "/image/r15").listFiles();

    public Copper(SBot bot) {
        super(bot);
    }

    @Override
    public Copper load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return getClass().toString();
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        Group group = event.getGroup();
        if (!ConfigManager.getInstance().isFunctionEnabled(group.getId(), Functions.Copper)) {
            return false; 
        }
        if (event.getMessage().contentToString().equals("copper")) {
            Image uploadImage = entity.toImage(Tools.ArrayTool.rfa(imageFolder), event.getGroup());
            int[] id = sendGroupMessage(group.getId(), ConfigManager.getInstance().isFunctionEnabled(group.getId(), Functions.CopperFlash) ?FlashImage.from(uploadImage): uploadImage);
            MessageManager.autoRecall(entity, id);
        }
        return false;
    }
}
