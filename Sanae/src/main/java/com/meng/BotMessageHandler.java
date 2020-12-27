package com.meng;

import com.meng.config.ConfigManager;
import com.meng.handler.MessageManager;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileTool;
import com.meng.tools.FileTypeUtil;
import com.meng.tools.Hash;
import java.io.File;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
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
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Message;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

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
    public ListeningStatus onReceive(final GroupMessageEvent event) {
        MessageManager.put(event);
        event.getMessage().forEachContent(new Function1<Message,Unit>(){

                @Override
                public Unit invoke(Message p1) {
                    if (p1 instanceof FlashImage) {
                        FlashImage fi = (FlashImage)p1;
                        String url = sb.queryImageUrl(fi.getImage());
                        try {
                            byte[] fileBytes = Jsoup.connect(url).ignoreContentType(true).method(Connection.Method.GET).execute().bodyAsBytes();
                            File folder = new File(SBot.appDirectory + "/image/flashImage/");
                            if (!folder.exists()) {
                                folder.mkdirs();
                            }
                            File file = new File(folder.getAbsolutePath() + "/" + Hash.getMd5Instance().calculate(fileBytes) + "." + FileTypeUtil.getFileType(fileBytes));
                            FileTool.saveFile(file, fileBytes);
                        } catch (Exception e) {
                            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                        }
                    }
                    return null;
                }
            });
        moduleManager.onGroupMessage(event);
        return ListeningStatus.LISTENING;
    }
    //好友消息
    @NotNull
    @EventHandler()
    public ListeningStatus onReceive(FriendMessageEvent event) {
        if (!configManager.isFunctionEnabled(event.getSender().getId(), Functions.FriendMessageEvent)) {
            return ListeningStatus.LISTENING; 
        }
        moduleManager.onFriendMessage(event);
        return ListeningStatus.LISTENING;
    }

    //群撤回: GroupRecall
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(MessageRecallEvent.GroupRecall event) {
        if (!configManager.isFunctionEnabled(event.getGroup().getId(), Functions.MessageRecallEvent_GroupRecall)) {
            return ListeningStatus.LISTENING; 
        }
        moduleManager.onGroupMessageRecall(event);
        return ListeningStatus.LISTENING;
    }
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(BotLeaveEvent event) {
        if (!configManager.isFunctionEnabled(event.getGroup().getId(), Functions.BotLeaveEvent)) {
            return ListeningStatus.LISTENING; 
        }
        return ListeningStatus.LISTENING;
    }
    //机器人被禁言: BotMuteEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(BotMuteEvent event) {
        if (!configManager.isFunctionEnabled(event.getGroup().getId(), Functions.BotMuteEvent)) {
            return ListeningStatus.LISTENING; 
        }
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
        if (!configManager.isFunctionEnabled(event.getGroup().getId(), Functions.MemberJoinEvent)) {
            return ListeningStatus.LISTENING; 
        }
        moduleManager.onMemberJoin(event);
        return ListeningStatus.LISTENING;
    }
    //成员已经离开群: MemberLeaveEvent
    @NotNull
    @EventHandler
    public ListeningStatus onReceive(MemberLeaveEvent event) {
        if (!configManager.isFunctionEnabled(event.getGroup().getId(), Functions.MemberLeaveEvent)) {
            return ListeningStatus.LISTENING; 
        }
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
