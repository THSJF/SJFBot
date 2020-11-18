package com.meng.handler.friend;

import net.mamoe.mirai.event.events.FriendAddEvent;
import net.mamoe.mirai.event.events.FriendDeleteEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;

public interface IFriendEvent extends IFriendMessageEvent,IFriendChangeEvent {
    
}
