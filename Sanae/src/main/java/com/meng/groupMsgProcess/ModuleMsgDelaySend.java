package com.meng.groupMsgProcess;

import com.meng.*;
import com.meng.config.*;
import java.util.*;

public class ModuleMsgDelaySend extends BaseModule {

	private boolean noTip=true;

	@Override
	public BaseModule load() {
		enable = true;
		return this;
	}

	@Override
	protected boolean processMsg(long fromGroup, long fromQQ, String msg, int msgId) {
		if (noTip) {
			return false;
		}
		Iterator<MessageWait> iter=ConfigManager.instence.SanaeConfig.delayMsg.iterator();
		while (iter.hasNext()) {
			MessageWait mw=iter.next();
			if (mw.q == fromQQ) {
				if (mw.g == -1) {
					Autoreply.sendMessage(fromGroup, 0, mw.c);
					iter.remove();
				} else if (mw.g == fromGroup) {
					Autoreply.sendMessage(fromGroup, 0, mw.c);
					iter.remove();
				}
			}
		}
		if (ConfigManager.instence.SanaeConfig.delayMsg.size() == 0) {
			noTip = true;
		}
		ConfigManager.instence.saveSanaeConfig();
		return false;
	}

	public void addTip(long InGroup, long toQQ, String msg) {
		ConfigManager.instence.SanaeConfig.delayMsg.add(new MessageWait(InGroup, toQQ, msg));
		noTip = false;
		ConfigManager.instence.saveSanaeConfig();
	}

	public void addTip(long toQQ, String msg) {
		ConfigManager.instence.SanaeConfig.delayMsg.add(new MessageWait(-1, toQQ, msg));
		noTip = false;
		ConfigManager.instence.saveSanaeConfig();
	}

	public class MessageWait {
		public long g=0;
		public long q=0;
		public String c="";
		public MessageWait(long inGroup, long toQQ, String msg) {
			g = inGroup;
			q = toQQ;
			c = msg;
		}
	}
}
