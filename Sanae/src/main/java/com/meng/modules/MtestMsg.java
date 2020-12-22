package com.meng.modules;

import com.meng.SBot;
import com.meng.handler.group.IGroupMessageEvent;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.contact.Group;

/**
 * @author: 司徒灵羽
 **/
public class MtestMsg extends BaseModule implements IGroupMessageEvent {

    @Override
    public String getModuleName() {
        return "测试模块";
    }

    public MtestMsg(SBot bw) {
        super(bw);
    }
    
    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        if (gme.getMessage().contentToString().equals("ggroup")) {
            for(Group g:entity.getGroups()){
                System.out.println(g);
            }
        }
        return false;
    }

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        return false;
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        return false;
    }

    @Override
    public BaseModule load() {
        return this;
    }

}
