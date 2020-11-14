package com.meng.modules;

import com.meng.SJFInterfaces.IGroupEvent;
import com.meng.adapter.BotWrapperEntity;
import com.meng.tools.ExceptionCatcher;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import com.meng.SJFInterfaces.BaseModule;

public class MemberChange extends BaseModule implements IGroupEvent {

    @Override
    public BaseModule load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "welcome";
    }

    public MemberChange(BotWrapperEntity bwe) {
       super(bwe);
    }

    @Override
    public boolean onGroupFileUpload(int sendTime, long fromGroup, long fromQQ, String file) {
        return false;
    }

    @Override
    public boolean onGroupAdminChange(int subtype, int sendTime, long fromGroup, long beingOperateQQ) {
        return false;
    }

    @Override
    public boolean onGroupMemberDecrease(MemberLeaveEvent event) {
        return false;
    }

    @Override
    public boolean onGroupMemberIncrease(MemberJoinEvent event) {
        try {
            if (event instanceof MemberJoinEvent.Invite) {
                MemberJoinEvent.Invite mji = (MemberJoinEvent.Invite)event;
                event.getGroup().sendMessage(String.format("invite,member:%s,arg:%s", mji.getMember(), mji.component1())); 
            } else if (event instanceof MemberJoinEvent.Active) {
                MemberJoinEvent.Active mji = (MemberJoinEvent.Active)event;
                event.getGroup().sendMessage(String.format("active,member:%s,arg:%s", mji.getMember(), mji.component1()));
            }
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        long groupId = event.getGroup().getId();
        String welc = entity.configManager.getWelcome(groupId);
        entity.sjfTx.sendGroupMessage(groupId, welc == null ?"欢迎新人": welc);
        return false;
    }

    @Override
    public boolean onRequestAddGroup(int subtype, int sendTime, long fromGroup, long fromQQ, String msg, String responseFlag) {
        return false;
    }

}
