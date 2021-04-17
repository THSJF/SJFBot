package com.meng.modules.qq.handler.group;

import net.mamoe.mirai.event.events.MessageRecallEvent;

public interface IGroupRecall {
    public boolean onGroupRecall(MessageRecallEvent.GroupRecall event);
}
