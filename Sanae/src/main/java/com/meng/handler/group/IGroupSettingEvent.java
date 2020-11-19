package com.meng.handler.group;

import net.mamoe.mirai.event.events.GroupAllowAnonymousChatEvent;
import net.mamoe.mirai.event.events.GroupAllowConfessTalkEvent;
import net.mamoe.mirai.event.events.GroupAllowMemberInviteEvent;
import net.mamoe.mirai.event.events.GroupEntranceAnnouncementChangeEvent;
import net.mamoe.mirai.event.events.GroupMuteAllEvent;
import net.mamoe.mirai.event.events.GroupNameChangeEvent;
import net.mamoe.mirai.event.events.GroupSettingChangeEvent;

/**
 * @author: 司徒灵羽
 **/
public interface IGroupSettingEvent {      
    public boolean onGroupSettingChange(GroupSettingChangeEvent event);
    public boolean onGroupNameChange(GroupNameChangeEvent event);
    public boolean onGroupEntranceAnnouncementChange(GroupEntranceAnnouncementChangeEvent event);
    public boolean onGroupMuteAll(GroupMuteAllEvent event);
    public boolean onGroupAllowAnonymousChange(GroupAllowAnonymousChatEvent event);
    public boolean onGroupAllowConfessTalkChange(GroupAllowConfessTalkEvent event);
    public boolean onAllowInviteChange(GroupAllowMemberInviteEvent event);
}
