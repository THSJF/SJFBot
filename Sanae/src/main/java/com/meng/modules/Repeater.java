package com.meng.modules;

import com.meng.Functions;
import com.meng.SBot;
import com.meng.handler.group.IGroupMessageEvent;
import java.util.HashMap;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.EmptyMessageChain;
import net.mamoe.mirai.message.data.MessageChain;

/**
 * @Description: 复读机
 * @author: 司徒灵羽
 **/
public class Repeater extends BaseModule implements IGroupMessageEvent {

	private HashMap<Long, SubRepeater> repeaters = new HashMap<>();

    public Repeater(SBot bw) {
        super(bw);
    }

	@Override
	public Repeater load() {
		return this;
	}

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long groupId = gme.getGroup().getId();
        SubRepeater rp = repeaters.get(groupId);
        if (!entity.configManager.isFunctionEnabled(gme.getGroup(), Functions.Repeater)) {
            return false;
        }
		if (rp == null) {
			repeaters.put(groupId, rp = new SubRepeater());
		}
        long qqId = gme.getSender().getId();
        return rp.check(groupId, qqId, gme.getMessage());
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        return false;
    }

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        return false;
    }

	private class SubRepeater {
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
			if (!lastStatus && SBot.messageEquals(lastMsgRecieved, msg)) {
				b = repeatStart(groupId, qqId, msg);
			}
			if (lastStatus && SBot.messageEquals(lastMsgRecieved, msg)) {
				b = repeatRunning(groupId, qqId, msg);
			}
			if (lastStatus && !SBot.messageEquals(lastMsgRecieved, msg)) {
				b = repeatEnd(groupId, qqId, msg);
			}
			lastStatus = SBot.messageEquals(lastMsgRecieved, msg);
			return b;
		}

		private boolean repeatEnd(long groupId, long qqId, MessageChain msg) {
			return false;
		}

		private boolean repeatRunning(long groupId, long qqId, MessageChain msg) {
			return false;
		}

		private boolean repeatStart(long groupId,  long qqId,  MessageChain msg) {
			entity.sendGroupMessage(groupId, msg);
			return true;
		}
	}

    @Override
    public String getModuleName() {
        return "repeater";
    }
}

