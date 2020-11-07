package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.annotation.SanaeData;
import com.meng.config.DataPersistenter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import net.mamoe.mirai.message.GroupMessageEvent;

public class MAimMessage extends BaseGroupModule {

    /**
     * @Description: 定向消息
     * @author: 司徒灵羽
     **/
    
    @SanaeData("AimMessage.json")
    private AimMessageHolder holder = new AimMessageHolder();

    public MAimMessage(BotWrapperEntity bw) {
        super(bw);
    }

    @Override
    public String getModuleName() {
        return "定向消息";
    }

    @Override
    public MAimMessage load() {
        DataPersistenter.read(this);
        return this;
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        long qqId = gme.getSender().getId();
        long groupId = gme.getGroup().getId();
        if (holder.delayList.size() != 0) {
            Iterator<MessageWait> iter = holder.delayList.iterator();
            while (iter.hasNext()) {
                MessageWait mw = iter.next();
                if (mw.qq == qqId) {
                    if (mw.group == -1) {
                        entity.sjfTx.sendGroupMessage(groupId, mw.content);
                        iter.remove();
                    } else if (mw.group == groupId) {
                        entity.sjfTx.sendGroupMessage(groupId, mw.content);
                        iter.remove();
                    }
                }
            }
        }
        MessageWait mw = holder.delayMap.remove(qqId);
        if (mw != null) {
            if (mw.qq == qqId) {
                if (mw.group == -1) {
                    entity.sjfTx.sendGroupMessage(groupId, mw.content);
                } else if (mw.group == groupId) {
                    entity.sjfTx.sendGroupMessage(groupId, mw.content);
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
