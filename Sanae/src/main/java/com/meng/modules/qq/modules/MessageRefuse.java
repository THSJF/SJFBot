package com.meng.modules.qq.modules;

import com.meng.config.ConfigManager;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.SJFExecutors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.EmptyMessageChain;
import net.mamoe.mirai.message.data.MessageChain;

/**
 * @author: 司徒灵羽
 **/
public class MessageRefuse extends BaseModule implements IGroupMessageEvent {

	public ConcurrentHashMap<Long,FireWallBean> msgMap = new ConcurrentHashMap<>();

    public MessageRefuse(SBot bw) {
        super(bw);
    }

	@Override
	public MessageRefuse load() {
		SJFExecutors.executeAtFixedRate(new Runnable(){

				@Override
				public void run() {
					for (FireWallBean mb : msgMap.values()) {
						mb.lastSeconedMsgs = 0;
					}
				}
			}, 1, 1, TimeUnit.SECONDS);
		return this;
	}

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
		long qqId = gme.getSender().getId();
        long groupId = gme.getGroup().getId();
        ConfigManager ci = ConfigManager.getInstance();
        if (ci.isBlackQQ(qqId)) {

        }
        if (ci.isBlockQQ(qqId) || ci.isBlockWord(gme.getMessage().contentToString())) {
            return true;
        }
		FireWallBean mtmb = msgMap.get(qqId);
		if (mtmb == null) {
			mtmb = new FireWallBean();
			msgMap.put(qqId, mtmb);
		}
		//发言间隔过短
		if (System.currentTimeMillis() - mtmb.lastSpeakTimeStamp < 500) {
			++mtmb.timeSubLowTimes;
		} else {
			mtmb.timeSubLowTimes = 0;
		}
		if (mtmb.timeSubLowTimes > 5) {
			if (!mtmb.tiped) {
				mtmb.tiped = true;
				sendGroupMessage(groupId,  "你说话真快");
			}
			return true;
		}
		//重复次数过多
		mtmb.lastSpeakTimeStamp = System.currentTimeMillis();
		if (SBot.messageEquals(mtmb.lastMsg, gme.getMessage())) {
			++mtmb.repeatTime;
		} else {
			mtmb.repeatTime = 0;
		}
		if (mtmb.repeatTime > 5) {
			if (!mtmb.tiped) {
				mtmb.tiped = true;
				sendGroupMessage(groupId,  "怎么又是这句话");
			}
			mtmb.lastMsg = gme.getMessage();
			return true;
		}
		mtmb.lastMsg = gme.getMessage();
		//一秒内消息过多
		++mtmb.lastSeconedMsgs;
		if (mtmb.lastSeconedMsgs > 4) {
			if (!mtmb.tiped) {
				mtmb.tiped = true;
				sendGroupMessage(groupId,  "你真稳");
			}
			return true;
		}
		mtmb.tiped = false;
		return false;
	}
    
    @Override
    public String getModuleName() {
        return "msgrefuse";
    }

	private class FireWallBean {
		public long qq;//qq
		public long lastSpeakTimeStamp;//最后一次发言时间
		public long timeSubLowTimes;//最后两次发言时间差过短次数
		public int repeatTime;//同一句话重复次数
		public int lastSeconedMsgs;//一秒内消息数量
		public MessageChain lastMsg = EmptyMessageChain.INSTANCE;//最后一句话
		public boolean tiped = false;//刷屏提示
	}

}

