package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import java.util.HashMap;
import net.mamoe.mirai.message.GroupMessageEvent;

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
        long fromQQ = gme.getSender().getId();
        long fromGroup = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
		Repeater rp = repeaters.get(fromGroup);
		if (rp == null) {
			rp = new Repeater(fromGroup);
			repeaters.put(fromGroup, rp);
		}
        return rp.check(fromGroup, fromQQ, msg);
    }

	private class Repeater {
		private String lastMessageRecieved = "";
		private boolean lastStatus = false;
		private long groupNumber = 0;

		public Repeater(long groupNumber) {
			this.groupNumber = groupNumber;
		}

		public boolean check(long fromGroup, long fromQQ, String msg) {
			boolean b = false; 
			b = checkRepeatStatu(fromGroup, fromQQ, msg);
			lastMessageRecieved = msg;
			return b;
		}

		// 复读状态
		private boolean checkRepeatStatu(long group, long qq, String msg) {
			boolean b = false;
			if (!lastStatus && lastMessageRecieved.equals(msg)) {
				b = repeatStart(group, qq, msg);
			}
			if (lastStatus && lastMessageRecieved.equals(msg)) {
				b = repeatRunning(group, qq, msg);
			}
			if (lastStatus && !lastMessageRecieved.equals(msg)) {
				b = repeatEnd(group, qq, msg);
			}
			lastStatus = lastMessageRecieved.equals(msg);
			return b;
		}

		private boolean repeatEnd(long group, long qq, String msg) {
			return false;
		}

		private boolean repeatRunning(long group, long qq, String msg) {
			return false;
		}

		private boolean repeatStart(long group,  long qq,  String msg) {
			entity.sjfTx.sendGroupMessage(group, msg);
			return true;
		}
	}
}

