package com.meng.SJFInterfaces;

public interface IGroupMessage {
	public boolean onGroupMessage(long fromGroup, long fromQQ, String msg, int msgId);
}
