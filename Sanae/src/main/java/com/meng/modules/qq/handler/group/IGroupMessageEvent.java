package com.meng.modules.qq.handler.group;

import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @author: 司徒灵羽
 **/
public interface IGroupMessageEvent {
    public boolean onGroupMessage(GroupMessageEvent event);
}
