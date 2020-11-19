package com.meng.handler.group;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;
import net.mamoe.mirai.event.events.MemberSpecialTitleChangeEvent;
import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;
import net.mamoe.mirai.event.events.MemberMuteEvent;
import net.mamoe.mirai.event.events.MemberUnmuteEvent;

/**
 * @author: 司徒灵羽
 **/
public interface IGroupMemberEvent {
    public boolean onMemberJoinRequest(MemberJoinRequestEvent event);
    public boolean onMemberJoin(MemberJoinEvent event);
    public boolean onMemberLeave(MemberLeaveEvent event);
    public boolean onInviteBot(BotInvitedJoinGroupRequestEvent event);

    public boolean onCardChange(MemberCardChangeEvent event);
    public boolean onTitleChange(MemberSpecialTitleChangeEvent event);

    public boolean onPermissionChange(MemberPermissionChangeEvent event);

    public boolean onMemberMute(MemberMuteEvent event);
    public boolean onMemberUnmute(MemberUnmuteEvent event);
}
