package com.meng;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import net.mamoe.mirai.event.events.GroupEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;

public class EventReceiver extends SimpleListenerHost {
    private BotWrapper wrapper;

    public EventReceiver(BotWrapper bw) {
        wrapper = bw;
    }

    @NotNull
    @EventHandler(priority = Listener.EventPriority.NORMAL)
    public ListeningStatus onMemberJoinEvent(@NotNull MemberJoinEvent event) {
        event.getGroup().sendMessage("欢迎新人" + event.getMember().getNameCard());
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }

    @NotNull
    @EventHandler(priority = Listener.EventPriority.NORMAL)
    public ListeningStatus onMemberLeaveEvent(@NotNull MemberLeaveEvent.Kick event) {
        event.getGroup().sendMessage(new PlainText(event.getMember().getNameCard() + "(" + event.getMember().getId() + ")被踹了一脚"));
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }

    @NotNull
    @EventHandler(priority = Listener.EventPriority.NORMAL)
    public ListeningStatus onMemberLeaveEvent(@NotNull MemberLeaveEvent.Quit event) {
        event.getGroup().sendMessage(new PlainText(event.getMember().getNameCard() + "(" + event.getMember().getId() + ")跑莉"));
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }
    
    @NotNull
    @EventHandler(priority = Listener.EventPriority.NORMAL)
    public ListeningStatus onMessage(@NotNull GroupMessageEvent event) throws Exception {
        Group g = event.getSubject();
        Member m = event.getSender();
        if (m.getId() == 2856986197L && event.getMessage().contentToString().equals("SJF")) {
            g.sendMessage("我在。");
        }
        wrapper.getAutoreply().groupMsg(0, event.getSource().getId(), g.getId(), m.getId(), "", event.getMessage().contentToString(), 0);
        return ListeningStatus.LISTENING;
    }

    @NotNull
    @EventHandler
    public ListeningStatus onMessage(@NotNull FriendMessageEvent event) {
        wrapper.getAutoreply().privateMsg(0, event.getSource().getId(), event.getSender().getId(), event.getMessage().contentToString(), 0);
        return ListeningStatus.LISTENING;
    }

    @NotNull
    @EventHandler
    public ListeningStatus onGroupRecall(@NotNull MessageRecallEvent.GroupRecall event) {
        String result = event.getOperator().getNameCard();
        System.out.println(result + " 撤回了" + event.getMessageId());
        event.getGroup().sendMessage(result + " 撤回了" + event.getMessageId());
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }

    @NotNull
    @EventHandler
    public ListeningStatus onFriendRecall(@NotNull MessageRecallEvent.FriendRecall event) {
        String result = event.getOperator() + "";
        System.out.println(result + " 撤回了" + event.getMessageId());
        event.getBot().getFriend(event.getOperator()).sendMessage("你撤回了 " + event.getMessageId());
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }
}
