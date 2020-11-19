package com.meng.handler.group;

import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @author: 司徒灵羽
 **/
public interface IGroupMessageEvent {
    public boolean onGroupMessage(GroupMessageEvent event);
    public boolean onGroupMemberNudge(MemberNudgedEvent event);
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event);
}
