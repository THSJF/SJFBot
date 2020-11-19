package com.meng.handler.friend;

import net.mamoe.mirai.message.FriendMessageEvent;

/**
 * @author: 司徒灵羽
 **/
public interface IFriendMessageEvent {
    public boolean onFriendMessage(FriendMessageEvent event);
}
