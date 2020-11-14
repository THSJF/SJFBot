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

    public ModuleRepeater(BotWrapperEntity bw) {
        super(bw);
    }

	@Override
	public ModuleRepeater load() {
		return this;
	}

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long groupId = gme.getGroup().getId();
        Repeater rp = repeaters.get(groupId);
        if (!entity.configManager.isFunctionEnbled(groupId, this)) {
            return false; 
        }
		if (rp == null) {
			repeaters.put(groupId, rp = new Repeater());
		}
        long qqId = gme.getSender().getId();
        return rp.check(groupId, qqId, gme.getMessage());
    }

	private class Repeater {
		private MessageChain lastMsgRecieved = EmptyMessageChain.INSTANCE;
		private boolean lastStatus = false;

		private boolean check(long groupId, long qqId, MessageChain msg) {
            boolean b = false; 
            b = checkRepeatStatu(groupId, qqId, msg);
            lastMsgRecieved = msg;
			return b;
		}

		// 复读状态
		private boolean checkRepeatStatu(long groupId, long qqId, MessageChain msg) {
			boolean b = false;
			if (!lastStatus && entity.messageEquals(lastMsgRecieved, msg)) {
				b = repeatStart(groupId, qqId, msg);
			}
			if (lastStatus && entity.messageEquals(lastMsgRecieved, msg)) {
				b = repeatRunning(groupId, qqId, msg);
			}
			if (lastStatus && !entity.messageEquals(lastMsgRecieved, msg)) {
				b = repeatEnd(groupId, qqId, msg);
			}
			lastStatus = entity.messageEquals(lastMsgRecieved, msg);
			return b;
		}

		private boolean repeatEnd(long groupId, long qqId, MessageChain msg) {
			return false;
		}

		private boolean repeatRunning(long groupId, long qqId, MessageChain msg) {
			return false;
		}

		private boolean repeatStart(long groupId,  long qqId,  MessageChain msg) {
			entity.sjfTx.sendGroupMessage(groupId, msg);
			return true;
		}
	}

    @Override
    public String getModuleName() {
        return "repeater";
    }
}

