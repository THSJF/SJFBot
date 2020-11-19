package com.meng.modules;

import com.meng.SBot;
import com.meng.handler.group.IGroupMemberEvent;
import net.mamoe.mirai.event.events.MemberSpecialTitleChangeEvent;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;
import net.mamoe.mirai.event.events.MemberMuteEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MemberUnmuteEvent;

/**
 * @author: 司徒灵羽
 **/
public class MemberChange extends BaseModule implements IGroupMemberEvent {

    @Override
    public boolean onMemberJoinRequest(MemberJoinRequestEvent event) {
        return false;
    }

    @Override
    public boolean onMemberJoin(MemberJoinEvent event) {
        long groupId = event.getGroup().getId();
        String welc = entity.configManager.getWelcome(groupId);
        entity.sendGroupMessage(groupId, welc == null ?"欢迎新人": welc);
        return false;
    }

    @Override
    public boolean onMemberLeave(MemberLeaveEvent event) {
        return false;
    }

    @Override
    public boolean onInviteBot(BotInvitedJoinGroupRequestEvent event) {
        return false;
    }

    @Override
    public boolean onCardChange(MemberCardChangeEvent event) {
        return false;
    }

    @Override
    public boolean onTitleChange(MemberSpecialTitleChangeEvent event) {
        return false;
    }

    @Override
    public boolean onPermissionChange(MemberPermissionChangeEvent event) {
        return false;
    }

    @Override
    public boolean onMemberMute(MemberMuteEvent event) {
        return false;
    }

    @Override
    public boolean onMemberUnmute(MemberUnmuteEvent event) {
        return false;
    }

    @Override
    public BaseModule load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "memmberevent";
    }

    public MemberChange(SBot bwe) {
        super(bwe);
    }


}
