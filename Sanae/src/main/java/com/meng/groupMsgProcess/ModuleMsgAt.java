package com.meng.groupMsgProcess;
import com.meng.*;
import com.meng.tools.*;

public class ModuleMsgAt extends BaseModule {

	@Override
	public BaseModule load() {
		enable = true;
		return this;
	}

	@Override
	protected boolean processMsg(long fromGroup, long fromQQ, String msg, int msgId) {
		if (Tools.CQ.isAtme(msg)) {
			Autoreply.sendMessage(fromGroup, 0, msg.replace("[CQ:at,qq=" + Autoreply.CQ.getLoginQQ() + "]", "[CQ:at,qq=" + fromQQ + "]"));
			return true;
		}
		return false;
	}
}
