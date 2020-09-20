package com.meng.SJFInterfaces;

public interface IGroupEvent {
	public boolean onGroupFileUpload(int sendTime, long fromGroup, long fromQQ, String file);
	public boolean onGroupAdminChange(int subtype, int sendTime, long fromGroup, long beingOperateQQ);
	public boolean onGroupMemberDecrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ);
	public boolean onGroupMemberIncrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ);
	public boolean onRequestAddGroup(int subtype, int sendTime, final long fromGroup, final long fromQQ, String msg, final String responseFlag);
}
