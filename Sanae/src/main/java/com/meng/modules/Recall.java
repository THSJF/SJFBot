package com.meng.modules;

import com.meng.MessagePool;
import com.meng.SJFInterfaces.BaseModule;
import com.meng.adapter.BotWrapperEntity;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.data.PlainText;

public class Recall extends BaseModule {

    public Recall(BotWrapperEntity bwe) {
        super(bwe);
    }

    public boolean onReceive(MessageRecallEvent.GroupRecall event) {
        entity.sjfTx.sendGroupMessage(event.getGroup().getId(), new PlainText(event.getOperator().getId() + "撤回了").plus(MessagePool.get(event.getMessageId()).getMessage()));
        return true;
    }

    @Override
    public Recall load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "recall";
    }

}
