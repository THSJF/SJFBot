package com.meng.modules.qq.modules;

import com.meng.config.DataPersistenter;
import com.meng.config.SanaeData;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;

/**
 * @author: 司徒灵羽
 **/
public class AimMessage extends BaseModule implements IGroupMessageEvent {

    @SanaeData("AimMessage.json")
    private AimMessageHolder holder = new AimMessageHolder();

    public AimMessage(SBot bw) {
        super(bw);
    }

    @Override
    public AimMessage load() {
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
                        sendGroupMessage(groupId, MiraiCode.deserializeMiraiCode(mw.content));
                        iter.remove();
                    } else if (mw.group == groupId) {
                        sendGroupMessage(groupId, MiraiCode.deserializeMiraiCode(mw.content));
                        iter.remove();
                    }
                }
            }
        }
        MessageWait mw = holder.delayMap.remove(qqId);
        if (mw != null) {
            if (mw.qq == qqId) {
                if (mw.group == -1) {
                    sendGroupMessage(groupId, MiraiCode.deserializeMiraiCode(mw.content));
                } else if (mw.group == groupId) {
                    sendGroupMessage(groupId, MiraiCode.deserializeMiraiCode(mw.content));
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
