package com.meng.modules;

import com.google.gson.reflect.TypeToken;
import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.SJFInterfaces.IPersistentData;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.DataPersistenter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import net.mamoe.mirai.message.GroupMessageEvent;
import com.meng.SjfPersistentData;

public class MAimMessage extends BaseGroupModule implements IPersistentData {

    /**
     * @Description: 定向消息
     * @author: 司徒灵羽
     **/

    @Override
    public String getModuleName() {
        return "定向消息";
    }

    @SjfPersistentData("AimMessage.json")
    private AimMessageHolder holder = new AimMessageHolder();

    public MAimMessage(BotWrapperEntity bw) {
        super(bw);
    }

    @Override
    public BotWrapperEntity getWrapper() {
        return entity;
    }

    @Override
    public String getPersistentName() {
        return "AimMessage.json";
    }

    @Override
    public Type getDataType() {
        return new TypeToken<AimMessageHolder>(){}.getType();
    }

    @Override
    public Object getDataBean() {
        return holder;
    }

    @Override
    public void setDataBean(Object o) {
        holder = (AimMessageHolder) o;
    }

    @Override
    public MAimMessage load() {
        DataPersistenter.read(this);
        return this;
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        long fromQQ = gme.getSender().getId();
        long fromGroup = gme.getGroup().getId();
        if (holder.delayList.size() != 0) {
            Iterator<MessageWait> iter = holder.delayList.iterator();
            while (iter.hasNext()) {
                MessageWait mw = iter.next();
                if (mw.qq == fromQQ) {
                    if (mw.group == -1) {
                        entity.sjfTx.sendGroupMessage(fromGroup, mw.content);
                        iter.remove();
                    } else if (mw.group == fromGroup) {
                        entity.sjfTx.sendGroupMessage(fromGroup, mw.content);
                        iter.remove();
                    }
                }
            }
        }
        MessageWait mw = holder.delayMap.remove(fromQQ);
        if (mw != null) {
            if (mw.qq == fromQQ) {
                if (mw.group == -1) {
                    entity.sjfTx.sendGroupMessage(fromGroup, mw.content);
                } else if (mw.group == fromGroup) {
                    entity.sjfTx.sendGroupMessage(fromGroup, mw.content);
                }
            }
        }
        save();
        return false;
    }

    public void addTip(long InGroup, long toQQ, String msg) {
        holder.delayList.add(new MessageWait(InGroup, toQQ, msg));
        save();
    }

    public void addTip(long toQQ, String msg) {
        addTip(-1, toQQ, msg);
    }


    public void addTipSingleton(long InGroup, long toQQ, String msg) {
        holder.delayMap.put(toQQ, new MessageWait(InGroup, toQQ, msg));
        save();
    }

    public void addTipSingleton(long toQQ, String msg) {
        addTipSingleton(-1, toQQ, msg);
    }  

    public void save() {
        DataPersistenter.save(this);
    }

    private class AimMessageHolder {
        public ArrayList<MessageWait> delayList = new ArrayList<>();
        public ConcurrentHashMap<Long,MessageWait> delayMap = new ConcurrentHashMap<>();
    }

    private class MessageWait {
        public long group = 0;
        public long qq = 0;
        public String content = "";
        public MessageWait(long inGroup, long toQQ, String msg) {
            group = inGroup;
            qq = toQQ;
            content = msg;
        }
	}
}
