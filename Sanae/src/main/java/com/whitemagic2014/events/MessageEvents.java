package com.whitemagic2014.events;

import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.mamoe.mirai.contact.Member;

/**
 * @Description: 非指令消息的消息事件
 * @author: magic chen
 * @date: 2020/9/8 11:40
 **/
public class MessageEvents extends SimpleListenerHost {

    private static Logger logger = LoggerFactory.getLogger(MessageEvents.class);

    @NotNull
    @EventHandler(priority = Listener.EventPriority.NORMAL)
    public ListeningStatus onMessage(@NotNull GroupMessageEvent event) throws Exception {
        Group g = event.getSubject();
        Member m = event.getSender();
        if (m.getId() == 2856986197L && event.getMessage().contentToString().equals("SJF")) {
            g.sendMessage("我在。");
        }
        return ListeningStatus.LISTENING;
    }
}
