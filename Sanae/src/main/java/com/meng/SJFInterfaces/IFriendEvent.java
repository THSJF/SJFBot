package com.meng.SJFInterfaces;

public interface IFriendEvent {
	public boolean onFriendAdd(int sendTime, long fromQQ);
	public boolean onRequestAddFriend(int sendTime, long fromQQ, String msg, String responseFlag);
}
