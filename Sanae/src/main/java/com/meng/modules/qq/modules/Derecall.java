package com.meng.modules.qq.modules;

import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.MessageManager;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.qq.handler.group.IGroupRecallEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.data.PlainText;

public class Derecall extends BaseModule implements IGroupMessageEvent,IGroupRecallEvent {

    public Derecall(SBot bot) {
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
    public boolean onGroupRecall(MessageRecallEvent.GroupRecall event) {
        sendGroupMessage(event.getGroup().getId(), new PlainText(String.valueOf(event.getOperator().getId())).plus("撤回了:"));
        sendGroupMessage(event.getGroup().getId(), MessageManager.get(event).getMessage());
        return false;
    }

}
