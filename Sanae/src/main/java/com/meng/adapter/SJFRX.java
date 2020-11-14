package com.meng.adapter;

import com.meng.MessagePool;
import com.meng.modules.ModuleManager;
import com.meng.modules.Recall;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BeforeImageUploadEvent;
import net.mamoe.mirai.event.events.BotAvatarChangedEvent;
import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;
import net.mamoe.mirai.event.events.BotMuteEvent;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.event.events.BotReloginEvent;
import net.mamoe.mirai.event.events.BotUnmuteEvent;
import net.mamoe.mirai.event.events.FriendAddEvent;
import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;
import net.mamoe.mirai.event.events.FriendDeleteEvent;
import net.mamoe.mirai.event.events.FriendMessagePostSendEvent;
import net.mamoe.mirai.event.events.FriendMessagePreSendEvent;
import net.mamoe.mirai.event.events.FriendRemarkChangeEvent;
import net.mamoe.mirai.event.events.GroupAllowAnonymousChatEvent;
import net.mamoe.mirai.event.events.GroupAllowConfessTalkEvent;
import net.mamoe.mirai.event.events.GroupAllowMemberInviteEvent;
import net.mamoe.mirai.event.events.GroupEntranceAnnouncementChangeEvent;
import net.mamoe.mirai.event.events.GroupMessagePostSendEvent;
import net.mamoe.mirai.event.events.GroupMessagePreSendEvent;
import net.mamoe.mirai.event.events.GroupMuteAllEvent;
import net.mamoe.mirai.event.events.GroupNameChangeEvent;
import net.mamoe.mirai.event.events.GroupSettingChangeEvent;
import net.mamoe.mirai.event.events.ImageUploadEvent;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MemberMuteEvent;
import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;
import net.mamoe.mirai.event.events.MemberSpecialTitleChangeEvent;
import net.mamoe.mirai.event.events.MemberUnmuteEvent;
import net.mamoe.mirai.event.events.MessagePostSendEvent;
import net.mamoe.mirai.event.events.MessagePreSendEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.event.events.MessageSendEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.event.events.TempMessagePostSendEvent;
import net.mamoe.mirai.event.events.TempMessagePreSendEvent;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.TempMessageEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @Description: bot接收事件
 * @author: 司徒灵羽
 **/

public class SJFRX extends SimpleListenerHost {

    public final ModuleManager moduleManager;
    public BotWrapperEntity entity;

    public SJFRX(ModuleManager mm) {
        moduleManager = mm;
    }

    //群消息
    @NotNull
    @EventHandler()
    public ListeningStatus onReceive(@NotNull GroupMessageEvent event) {
        MessagePool.put(event);
        moduleManager.onGroupMessage(event);
        return ListeningStatus.LISTENING;
    }
    //好友消息
    @NotNull
    @EventHandler()
    public ListeningStatus onReceive(@NotNull FriendMessageEvent event) {
        moduleManager.onFriendMessage(event);
        return ListeningStatus.LISTENING;
    }
    //临时消息
    @NotNull
    @EventHandler()
    public ListeningStatus onReceive(@NotNull TempMessageEvent event) {
        // moduleManager.onFriendMessage(event);
        return ListeningStatus.LISTENING;
    }
    //Bot 登录完成: BotOnlineEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotOnlineEvent event) {
        return ListeningStatus.LISTENING;
    }
    //Bot 离线: BotOfflineEvent
    //主动: Active
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotOfflineEvent.Active event) {
        return ListeningStatus.LISTENING;
    }
    //被挤下线: Force
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotOfflineEvent.Force event) {
        return ListeningStatus.LISTENING;
    }
    //被服务器断开或因网络问题而掉线: Dropped
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotOfflineEvent.Dropped event) {
        return ListeningStatus.LISTENING;
    }
    //服务器主动要求更换另一个服务器: RequireReconnect
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotOfflineEvent.RequireReconnect event) {
        return ListeningStatus.LISTENING;
    }
    //Bot 重新登录: BotReloginEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotReloginEvent event) {
        return ListeningStatus.LISTENING;
    }
    //Bot 头像改变: BotAvatarChangedEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotAvatarChangedEvent event) {
        return ListeningStatus.LISTENING;
    }
    // 主动发送消息: MessageSendEvent
    //群消息: GroupMessageSendEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MessageSendEvent.GroupMessageSendEvent event) {
        return ListeningStatus.LISTENING;
    }
    //好友消息: FriendMessageSendEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MessageSendEvent.FriendMessageSendEvent event) {
        return ListeningStatus.LISTENING;
    }
    //主动发送消息前: MessagePreSendEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MessagePreSendEvent event) {
        return ListeningStatus.LISTENING;
    }
    //群消息: GroupMessagePreSendEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull GroupMessagePreSendEvent event) {
        return ListeningStatus.LISTENING;
    }
    //好友消息: FriendMessagePreSendEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull FriendMessagePreSendEvent event) {
        return ListeningStatus.LISTENING;
    }
    //群临时会话消息: TempMessagePreSendEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull TempMessagePreSendEvent event) {
        return ListeningStatus.LISTENING;
    }
    //主动发送消息后: MessagePostSendEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MessagePostSendEvent event) {
        return ListeningStatus.LISTENING;
    }
    //群消息: GroupMessagePostSendEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull GroupMessagePostSendEvent event) {
        return ListeningStatus.LISTENING;
    }
    //好友消息: FriendMessagePostSendEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull FriendMessagePostSendEvent event) {
        return ListeningStatus.LISTENING;
    }
    //群临时会话消息: TempMessagePostSendEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull TempMessagePostSendEvent event) {
        return ListeningStatus.LISTENING;
    }
    //消息撤回: MessageRecallEvent
    //好友撤回: FriendRecall
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MessageRecallEvent.FriendRecall event) {
        return ListeningStatus.LISTENING;
    }
    //群撤回: GroupRecall
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MessageRecallEvent.GroupRecall event) {
        if (!entity.configManager.isFunctionEnbled(event.getGroup().getId(), Recall.class)) {
            return ListeningStatus.LISTENING;
        }
        entity.moduleManager.getModule(Recall.class).onReceive(event);
        return ListeningStatus.LISTENING;
    }
    //群撤回: TempRecall
//    @NotNull
//    @EventHandler
//    public ListeningStatus onReceive(@NotNull MessageRecallEvent.TempRecall event) {
//        return ListeningStatus.LISTENING;
//    }
    //图片上传前: BeforeImageUploadEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BeforeImageUploadEvent event) {
        return ListeningStatus.LISTENING;
    }
    //图片上传完成: ImageUploadEvent
    //图片上传成功: Succeed
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull ImageUploadEvent.Succeed event) {
        return ListeningStatus.LISTENING;
    }
    //图片上传失败: Failed
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull ImageUploadEvent.Failed event) {
        return ListeningStatus.LISTENING;
    }
    //机器人被踢出群或在其他客户端主动退出一个群: BotLeaveEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotLeaveEvent event) {
        return ListeningStatus.LISTENING;
    }
    //机器人主动退出一个群: Active
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotLeaveEvent.Active event) {
        return ListeningStatus.LISTENING;
    }
    //机器人被管理员或群主踢出群: Kick
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotLeaveEvent.Kick event) {
        return ListeningStatus.LISTENING;
    }
    //机器人在群里的权限被改变: BotGroupPermissionChangeEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotGroupPermissionChangeEvent event) {
        return ListeningStatus.LISTENING;
    }
    //机器人被禁言: BotMuteEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotMuteEvent event) {
        long id = event.getGroup().getId();
        long id2 = event.getOperator().getId();
        entity.configManager.addBlack(id, id2);
        event.getGroup().quit();
        entity.sjfTx.sendGroupMessage(entity.yysGroup, String.format("在群%d中被%d禁言", id, id2));
        return ListeningStatus.LISTENING;
    }
    //机器人被取消禁言: BotUnmuteEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotUnmuteEvent event) {
        return ListeningStatus.LISTENING;
    }
    //机器人成功加入了一个新群: BotJoinGroupEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotJoinGroupEvent event) {
        return ListeningStatus.LISTENING;
    }
    //群设置改变: GroupSettingChangeEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull GroupSettingChangeEvent event) {
        return ListeningStatus.LISTENING;
    }
    //群名改变: GroupNameChangeEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull GroupNameChangeEvent event) {
        return ListeningStatus.LISTENING;
    }
    //入群公告改变: GroupEntranceAnnouncementChangeEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull GroupEntranceAnnouncementChangeEvent event) {
        return ListeningStatus.LISTENING;
    }
    //全员禁言状态改变: GroupMuteAllEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull GroupMuteAllEvent event) {
        return ListeningStatus.LISTENING;
    }
    //匿名聊天状态改变: GroupAllowAnonymousChatEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull GroupAllowAnonymousChatEvent event) {
        return ListeningStatus.LISTENING;
    }
    //坦白说状态改变: GroupAllowConfessTalkEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull GroupAllowConfessTalkEvent event) {
        return ListeningStatus.LISTENING;
    }
    //允许群员邀请好友加群状态改变: GroupAllowMemberInviteEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull GroupAllowMemberInviteEvent event) {
        return ListeningStatus.LISTENING;
    }
    //成员已经加入群: MemberJoinEvent
    //成员被邀请加入群: Invite
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MemberJoinEvent event) {
        entity.moduleManager.onGroupMemberIncrease(event);
        return ListeningStatus.LISTENING;
    }
    //成员主动加入群: Active
    //  @NotNull
    // @EventHandler
    //  public ListeningStatus onReceive(@NotNull MemberJoinEvent.Active event) {
    //      entity.moduleManager.onGroupMemberIncrease(event);
    //      return ListeningStatus.LISTENING;
    //   }
    //成员已经离开群: MemberLeaveEvent
    //成员被踢出群: Kick
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MemberLeaveEvent event) {
        entity.moduleManager.onGroupMemberDecrease(event);
        return ListeningStatus.LISTENING;
    }
 /*   //成员主动离开群: Quit
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MemberLeaveEvent.Quit event) {
        return ListeningStatus.LISTENING;
    }*/
    //一个账号请求加入群: MemberJoinRequestEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MemberJoinRequestEvent event) {
        return ListeningStatus.LISTENING;
    }
    //机器人被邀请加入群: BotInvitedJoinGroupRequestEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull BotInvitedJoinGroupRequestEvent event) {
        if (entity.configManager.isBlackQQ(event.getInvitorId()) || entity.configManager.isBlackGroup(event.getGroupId())) {
            event.ignore();
        } else {
            event.accept();    
        }
        return ListeningStatus.LISTENING;
    }
    //成员群名片改动: MemberCardChangeEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MemberCardChangeEvent event) {
        return ListeningStatus.LISTENING;
    }
    //成员群头衔改动: MemberSpecialTitleChangeEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MemberSpecialTitleChangeEvent event) {
        return ListeningStatus.LISTENING;
    }
    //成员权限改变: MemberPermissionChangeEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MemberPermissionChangeEvent event) {
        return ListeningStatus.LISTENING;
    }
    //群成员被禁言: MemberMuteEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MemberMuteEvent event) {
        return ListeningStatus.LISTENING;
    }
    //群成员被取消禁言: MemberUnmuteEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull MemberUnmuteEvent event) {
        return ListeningStatus.LISTENING;
    }
    //好友昵称改变: FriendRemarkChangeEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull FriendRemarkChangeEvent event) {
        return ListeningStatus.LISTENING;
    }
    //成功添加了一个新好友: FriendAddEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull FriendAddEvent event) {
        return ListeningStatus.LISTENING;
    }
    //好友已被删除: FriendDeleteEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull FriendDeleteEvent event) {
        return ListeningStatus.LISTENING;
    }
    //一个账号请求添加机器人为好友: NewFriendRequestEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull NewFriendRequestEvent event) {
        event.accept();
        return ListeningStatus.LISTENING;
    }
    //好友头像改变: FriendAvatarChangedEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(@NotNull FriendAvatarChangedEvent event) {
        return ListeningStatus.LISTENING;
    }
}
