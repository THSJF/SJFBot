package com.meng.modules.qq.handler.friend;

import net.mamoe.mirai.event.events.FriendMessageEvent;

/**
 * @author: 司徒灵羽
 **/
public interface IFriendMessageEvent {
    public boolean onFriendMessage(FriendMessageEvent event);
}
