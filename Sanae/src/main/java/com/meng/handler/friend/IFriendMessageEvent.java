package com.meng.handler.friend;

import net.mamoe.mirai.message.FriendMessageEvent;

public interface IFriendMessageEvent {
    public boolean onFriendMessage(FriendMessageEvent event);
}
