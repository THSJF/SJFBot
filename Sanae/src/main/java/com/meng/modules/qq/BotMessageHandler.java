package com.meng.modules.qq;

import com.meng.bot.Functions;
import com.meng.config.ConfigManager;
import com.meng.modules.qq.handler.MessageManager;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;
import net.mamoe.mirai.event.events.BotMuteEvent;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.event.events.NudgeEvent;
import org.jetbrains.annotations.NotNull;

public class BotMessageHandler extends SimpleListenerHost {

    private SBot sb;
    private ModuleManager moduleManager;

    public BotMessageHandler(SBot sb) {
        this.sb = sb;
        moduleManager = sb.moduleManager;
    }

    //群消息
    @NotNull
    @EventHandler()
    public ListeningStatus onReceive(final GroupMessageEvent event) {
        if (event.getSender().getId() == 3045126546L) {//过于吵闹的刷屏机器
            return ListeningStatus.LISTENING;
        }
        MessageManager.put(event);
        moduleManager.onGroupMessage(event);
        return ListeningStatus.LISTENING;
    }
    //好友消息
    @NotNull
    @EventHandler()
    public ListeningStatus onReceive(FriendMessageEvent event) {
        if (!ConfigManager.getInstance().isFunctionEnabled(event.getSender().getId(), Functions.FriendMessageEvent)) {
            return ListeningStatus.LISTENING; 
        }
        moduleManager.onFriendMessage(event);
        return ListeningStatus.LISTENING;
    }

    //群撤回: GroupRecall
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(MessageRecallEvent.GroupRecall event) {
        if (!ConfigManager.getInstance().isFunctionEnabled(event.getGroup().getId(), Functions.MessageRecallEvent_GroupRecall)) {
            return ListeningStatus.LISTENING; 
        }
        moduleManager.onGroupRecall(event);
        return ListeningStatus.LISTENING;
    }
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(BotLeaveEvent event) {
        if (!ConfigManager.getInstance().isFunctionEnabled(event.getGroup().getId(), Functions.BotLeaveEvent)) {
            return ListeningStatus.LISTENING; 
        }
        return ListeningStatus.LISTENING;
    }
    //机器人被禁言: BotMuteEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(BotMuteEvent event) {
        if (!ConfigManager.getInstance().isFunctionEnabled(event.getGroup().getId(), Functions.BotMuteEvent)) {
            return ListeningStatus.LISTENING; 
        }
        long id = event.getGroup().getId();
        long id2 = event.getOperator().getId();
//        ConfigManager.getInstance().addBlack(id, id2);
//        event.getGroup().quit();
        sb.sendGroupMessage(SBot.yysGroup, String.format("在群%d中被%d禁言", id, id2));
        return ListeningStatus.LISTENING;
    }
    //成员已经加入群: MemberJoinEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(MemberJoinEvent event) {
        if (!ConfigManager.getInstance().isFunctionEnabled(event.getGroup().getId(), Functions.MemberJoinEvent)) {
            return ListeningStatus.LISTENING; 
        }
        moduleManager.onMemberJoin(event);
        return ListeningStatus.LISTENING;
    }
    //成员已经离开群: MemberLeaveEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(MemberLeaveEvent event) {
        if (!ConfigManager.getInstance().isFunctionEnabled(event.getGroup().getId(), Functions.MemberLeaveEvent)) {
            return ListeningStatus.LISTENING; 
        }
        moduleManager.onMemberLeave(event);
        return ListeningStatus.LISTENING;
    }
    //机器人被邀请加入群: BotInvitedJoinGroupRequestEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(BotInvitedJoinGroupRequestEvent event) {
        if (ConfigManager.getInstance().isBlackQQ(event.getInvitorId()) || ConfigManager.getInstance().isBlackGroup(event.getGroupId())) {
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

    @NotNull
    @EventHandler
    public ListeningStatus onReceive(NudgeEvent event) {
        sb.moduleManager.onNudge(event);
        return ListeningStatus.LISTENING;
    }
}
