package com.meng.modules.qq.modules;

import com.meng.config.SanaeData;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.FileTool;
import com.meng.tools.FileWatcher;
import com.meng.tools.JsonHelper;
import java.io.File;
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
    private final File cfg;
    public AimMessage(SBot bw) {
        super(bw);
        cfg = new File("C://Program Files/sanae_data/persistent/" + getSanaeValue("holder"));
        FileWatcher.getInstance().addOnFileChangeListener(cfg, new Runnable(){

                @Override
                public void run() {
                    reload();
                }
            });
    }

    @Override
    public AimMessage reload() {
        String json = FileTool.readString(cfg);
        if (json != null && !json.equals("null")) {  
            holder = JsonHelper.fromJson(json, AimMessageHolder.class);
        }
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
        public final long group;
        public final long qq ;
        public final String content;
        public MessageWait(long inGroup, long toQQ, String msg) {
            group = inGroup;
            qq = toQQ;
            content = msg;
        }
	}
}
