package com.meng.modules;

import com.meng.SBot;
import com.meng.handler.MessageManager;
import com.meng.handler.group.IGroupMessageEvent;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.PlainText;

public class Derecall extends BaseModule implements IGroupMessageEvent {

    public Derecall(SBot bot){
        super(bot);
    }
    
    @Override
    public Derecall load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return getClass().toString();
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        return false;
    }

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        return false;
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        entity.sendGroupMessage(event.getGroup().getId(), new PlainText(String.valueOf(event.getOperator().getId())).plus("撤回了:"));
        entity.sendGroupMessage(event.getGroup().getId(), MessageManager.get(event).getMessage());
        return false;
    }

}
