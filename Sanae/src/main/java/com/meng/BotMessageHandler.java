package com.meng;

import com.meng.config.ConfigManager;
import com.meng.handler.MessageManager;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;
import net.mamoe.mirai.event.events.BotMuteEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;

public class BotMessageHandler extends SimpleListenerHost {

    private SBot sb;
    private ModuleManager moduleManager;
    private ConfigManager configManager;
    public BotMessageHandler(SBot sb) {
        this.sb = sb;
        moduleManager = sb.moduleManager;
        configManager = sb.configManager;
    }

    //群消息
    @NotNull
    @EventHandler()
    public ListeningStatus onReceive(GroupMessageEvent event) {
        MessageManager.put(event);
        moduleManager.onGroupMessage(event);
        return ListeningStatus.LISTENING;
    }
    //好友消息
    @NotNull
    @EventHandler()
    public ListeningStatus onReceive(FriendMessageEvent event) {
        moduleManager.onFriendMessage(event);
        return ListeningStatus.LISTENING;
    }


    //群撤回: GroupRecall
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(MessageRecallEvent.GroupRecall event) {
        moduleManager.onGroupMessageRecall(event);
        return ListeningStatus.LISTENING;
    }
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(BotLeaveEvent event) {
        return ListeningStatus.LISTENING;
    }
    //机器人被禁言: BotMuteEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(BotMuteEvent event) {
        long id = event.getGroup().getId();
        long id2 = event.getOperator().getId();
        configManager.addBlack(id, id2);
        event.getGroup().quit();
        sb.sendGroupMessage(SBot.yysGroup, String.format("在群%d中被%d禁言", id, id2));
        return ListeningStatus.LISTENING;
    }
    //成员已经加入群: MemberJoinEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(MemberJoinEvent event) {
        moduleManager.onMemberJoin(event);
        return ListeningStatus.LISTENING;
    }
    //成员已经离开群: MemberLeaveEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(MemberLeaveEvent event) {
        moduleManager.onMemberLeave(event);
        return ListeningStatus.LISTENING;
    }
    //机器人被邀请加入群: BotInvitedJoinGroupRequestEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(BotInvitedJoinGroupRequestEvent event) {
        if (configManager.isBlackQQ(event.getInvitorId()) || configManager.isBlackGroup(event.getGroupId())) {
            event.ignore();
        } else {
            event.accept();    
        }
        return ListeningStatus.LISTENING;
    }
    //一个账号请求添加机器人为好友: NewFriendRequestEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(NewFriendRequestEvent event) {
        event.accept();
        return ListeningStatus.LISTENING;
    }
}
