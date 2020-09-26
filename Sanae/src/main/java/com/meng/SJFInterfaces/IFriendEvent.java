package com.meng.SJFInterfaces;

/**
 * @author: 司徒灵羽
 **/

public interface IFriendEvent {
	public boolean onFriendAdd(int sendTime, long fromQQ);
	public boolean onRequestAddFriend(int sendTime, long fromQQ, String msg, String responseFlag);
}
