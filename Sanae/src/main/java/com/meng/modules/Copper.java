package com.meng.modules;

import com.meng.Functions;
import com.meng.SBot;
import com.meng.handler.MessageManager;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.tools.Tools;
import java.io.File;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
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
        if (!entity.configManager.isFunctionEnabled(group.getId(), Functions.Copper)) {
            return false; 
        }
        if (event.getMessage().contentToString().equals("copper")) {
            Image uploadImage = group.uploadImage(Tools.ArrayTool.rfa(imageFolder));
            int id = entity.sendGroupMessage(group.getId(), entity.configManager.isFunctionEnabled(group.getId(), Functions.CopperFlash) ?FlashImage.from(uploadImage): uploadImage);
            MessageManager.autoRecall(entity, id);
        }
        return false;
    }

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        return false;
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        return false;
    }
}
