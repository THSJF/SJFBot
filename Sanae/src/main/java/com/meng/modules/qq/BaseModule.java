package com.meng.modules.qq;

import com.meng.annotation.BotData;
import com.meng.config.DataPersistenter;
import com.meng.modules.qq.SBot;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.SJFRandom;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.QuoteReply;

/**
 * @author: 司徒灵羽
 **/
public abstract class BaseModule implements ILoad {

    public SBot entity;

    public BaseModule(SBot bw) {
        entity = bw;
    }

    public final void save() {
        DataPersistenter.save(this);  
    }

    protected final String getSanaeValue(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            return field.getAnnotation(BotData.class).value();
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return "";
        }
    }

    public MessageSource sendMessage(GroupMessageEvent event, Message msg) {
        return sendMessage(event.getGroup(), msg);
    }

    public MessageSource sendMessage(GroupMessageEvent event, String msg) {
        return sendMessage(event.getGroup(), msg);
    }

    public MessageSource sendGroupMessage(long fromGroup, Message msg) {
        return entity.sendGroupMessage(fromGroup, msg);
    }

    public MessageSource sendGroupMessage(long fromGroup, String msg) {
        return entity.sendGroupMessage(fromGroup, new PlainText(msg));
    }

    public MessageSource sendGroupMessage(long fromGroup, String[] msg) {
        return entity.sendGroupMessage(fromGroup, SJFRandom.randomSelect(msg));
    }

    public MessageSource sendGroupMessage(long fromGroup, ArrayList<String> msg) {
        return entity.sendGroupMessage(fromGroup, msg.toArray(new String[0]));
    }

    public MessageSource sendMessage(Group group, String msg) {
        return entity.sendGroupMessage(group.getId(), msg);
    }

    public MessageSource sendMessage(Group group, Message msg) {
        return entity.sendGroupMessage(group.getId(), msg);
    }

    public MessageSource sendMessage(Group group, String[] msg) {
        return entity.sendGroupMessage(group.getId(), SJFRandom.randomSelect(msg));
    }

    public MessageSource sendMessage(Group group, List<String> msg) {
        return entity.sendGroupMessage(group.getId(), msg.toArray(new String[0]));
    } 

    public MessageSource sendQuote(GroupMessageEvent gme, String msg) {
        return entity.sendGroupMessage(gme.getGroup().getId(), new QuoteReply(gme.getSource()).plus(msg));
    }

    public MessageSource sendQuote(GroupMessageEvent gme, Message msg) {
        return entity.sendGroupMessage(gme.getGroup().getId(), new QuoteReply(gme.getSource()).plus(msg));
    }

    public BaseModule load() {
        DataPersistenter.read(this);
        return this;
    }

    public String getModuleName() {
        return getClass().getSimpleName();
    }

    @Override
    public BaseModule reload() {
        System.out.println(getModuleName() + " reload...");
        return load();
    }
}
