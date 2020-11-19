package com.meng.handler.group;

import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;
import net.mamoe.mirai.event.events.BotMuteEvent;
import net.mamoe.mirai.event.events.BotUnmuteEvent;

/**
 * @author: 司徒灵羽
 **/
public interface IGroupBotEvent {
    public boolean onBotLeave(BotLeaveEvent event);
    public boolean onBotPermissionChange(BotGroupPermissionChangeEvent event);
    public boolean onBotMute(BotMuteEvent event); 
    public boolean onBotUnmute(BotUnmuteEvent event);
    public boolean onBotJoinGroup(BotJoinGroupEvent event);
}
