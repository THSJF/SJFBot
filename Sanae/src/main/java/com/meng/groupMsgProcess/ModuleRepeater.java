package com.meng.groupMsgProcess;

import com.meng.*;
import com.meng.config.*;
import java.util.*;

public class ModuleRepeater extends BaseModule {
	
	private HashMap<Long, Repeater> repeaters = new HashMap<>();

	@Override
	public BaseModule load() {
		enable = true;
		return this;
	} 

	@Override
	protected boolean processMsg(long fromGroup, long fromQQ, String msg, int msgId) {
		Repeater rp=repeaters.get(fromGroup);
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
			ConfigManager.instence.send(SanaeDataPack.encode(SanaeDataPack.opIncRepeatBreak).write(group).write(qq));
			return false;
		}

		private boolean repeatRunning(long group, long qq, String msg) {
			ConfigManager.instence.send(SanaeDataPack.encode(SanaeDataPack.opIncRepeat).write(group).write(qq));
			return false;
		}

		private boolean repeatStart(long group,  long qq,  String msg) {
			ConfigManager.instence.send(SanaeDataPack.encode(SanaeDataPack.opIncRepeatStart).write(qq));
			Autoreply.sendMessage(group, 0, msg);
			return true;
		}
	}
}
