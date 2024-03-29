package com.meng.modules.qq.handler.friend;

import net.mamoe.mirai.event.events.FriendAddEvent;
import net.mamoe.mirai.event.events.FriendDeleteEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;

/**
 * @author: 司徒灵羽
 **/
public interface IFriendChangeEvent {
    public boolean onFriendAdd(FriendAddEvent event);
    public boolean onRequestAddFriend(NewFriendRequestEvent event);
    public boolean onFriendDelete(FriendDeleteEvent event);
}
