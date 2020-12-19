package com.meng.modules;

import com.meng.Functions;
import com.meng.SBot;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import java.io.File;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class Copper extends BaseModule implements IGroupMessageEvent {
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
        if (!entity.configManager.isFunctionEnabled(event.getGroup().getId(), Functions.Copper)) {
            return false; 
        }
        if (event.getMessage().contentToString().equals("copper")) {
            MessageChainBuilder mcb = new MessageChainBuilder();
            try {
                mcb.add(event.getGroup().uploadImage(new File(SBot.appDirectory + "/image/r15")));
            } catch (Exception e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
            entity.sendGroupMessage(event.getGroup().getId(), mcb.asMessageChain());
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
