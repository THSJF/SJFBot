package com.meng.SJFInterfaces;

import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;

/**
 * @author: 司徒灵羽
 **/

public interface IGroupEvent {
	public boolean onGroupFileUpload(int sendTime, long fromGroup, long fromQQ, String file);
	public boolean onGroupAdminChange(int subtype, int sendTime, long fromGroup, long beingOperateQQ);
	public boolean onGroupMemberDecrease(MemberLeaveEvent event);
	public boolean onGroupMemberIncrease(MemberJoinEvent event);
	public boolean onRequestAddGroup(int subtype, int sendTime, final long fromGroup, final long fromQQ, String msg, final String responseFlag);
}
