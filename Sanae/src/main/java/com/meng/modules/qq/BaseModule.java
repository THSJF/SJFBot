package com.meng.modules.qq;

import com.meng.config.DataPersistenter;
import com.meng.config.SanaeData;
import com.meng.modules.qq.SBot;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.SJFRandom;
import java.lang.reflect.Field;
import java.util.ArrayList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
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
            return field.getAnnotation(SanaeData.class).value();
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return "";
        }
    }

    public int[] sendMessage(GroupMessageEvent event, Message msg) {
        return sendMessage(event.getGroup(), msg);
    }

    public int[] sendMessage(GroupMessageEvent event, String msg) {
        return sendMessage(event.getGroup(), msg);
    }

    public int[] sendGroupMessage(long fromGroup, Message msg) {
        return entity.sendGroupMessage(fromGroup, msg);
    }

    public int[] sendGroupMessage(long fromGroup, String msg) {
        return entity.sendGroupMessage(fromGroup, new PlainText(msg));
    }

    public int[] sendGroupMessage(long fromGroup, String[] msg) {
        return entity.sendGroupMessage(fromGroup, SJFRandom.randomSelect(msg));
    }

    public int[] sendGroupMessage(long fromGroup, ArrayList<String> msg) {
        return entity.sendGroupMessage(fromGroup, msg.toArray(new String[0]));
    }

    public int[] sendMessage(Group group, String msg) {
        return entity.sendGroupMessage(group.getId(), msg);
    }

    public int[] sendMessage(Group group, Message msg) {
        return entity.sendGroupMessage(group.getId(), msg);
    }

    public int[] sendMessage(Group group, String[] msg) {
        return entity.sendGroupMessage(group.getId(), SJFRandom.randomSelect(msg));
    }

    public int[] sendMessage(Group group, ArrayList<String> msg) {
        return entity.sendGroupMessage(group.getId(), msg.toArray(new String[0]));
    } 

    public int[] sendQuote(GroupMessageEvent gme, String msg) {
        return entity.sendGroupMessage(gme.getGroup().getId(), new QuoteReply(gme.getSource()).plus(msg));
    }

    public int[] sendQuote(GroupMessageEvent gme, Message msg) {
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
        return load();
    }
}
