package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import java.util.HashMap;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.EmptyMessageChain;
import net.mamoe.mirai.message.data.MessageChain;

/**
 * @Description: 复读机
 * @author: 司徒灵羽
 **/

public class ModuleRepeater extends BaseGroupModule {

	private HashMap<Long, Repeater> repeaters = new HashMap<>();
    private MessageChain emptyMessageChain = EmptyMessageChain.INSTANCE;
    public ModuleRepeater(BotWrapperEntity bw) {
        super(bw);
    }

	@Override
	public ModuleRepeater load() {
		return this;
	}

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long fromQQ = gme.getSender().getId();
        long fromGroup = gme.getGroup().getId();
        Repeater rp = repeaters.get(fromGroup);
		if (rp == null) {
			repeaters.put(fromGroup, rp = new Repeater());
		}
        return rp.check(fromGroup, fromQQ, gme.getMessage());
    }

	private class Repeater {
		private MessageChain lastMsgRecieved = emptyMessageChain;
		private boolean lastStatus = false;

		public boolean check(long fromGroup, long fromQQ, MessageChain msg) {
			boolean b = false; 
			b = checkRepeatStatu(fromGroup, fromQQ, msg);
			lastMsgRecieved = msg;
            //msg.first(Image.Key).toString()
			return b;
		}

		// 复读状态
		private boolean checkRepeatStatu(long group, long qq, MessageChain msg) {
			boolean b = false;
			if (!lastStatus && entity.messageEquals(lastMsgRecieved, msg)) {
				b = repeatStart(group, qq, msg);
			}
			if (lastStatus && entity.messageEquals(lastMsgRecieved, msg)) {
				b = repeatRunning(group, qq, msg);
			}
			if (lastStatus && !entity.messageEquals(lastMsgRecieved, msg)) {
				b = repeatEnd(group, qq, msg);
			}
			lastStatus = entity.messageEquals(lastMsgRecieved, msg);
			return b;
		}

		private boolean repeatEnd(long group, long qq, MessageChain msg) {
			return false;
		}

		private boolean repeatRunning(long group, long qq, MessageChain msg) {
			return false;
		}

		private boolean repeatStart(long group,  long qq,  MessageChain msg) {
			entity.sjfTx.sendGroupMessage(group, msg);
			return true;
		}
	}
}

