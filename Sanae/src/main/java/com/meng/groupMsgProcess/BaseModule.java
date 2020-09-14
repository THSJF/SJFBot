package com.meng.groupMsgProcess;

public abstract class BaseModule {

	public boolean enable = false;
	private String moduleName = null;

	public final boolean onMsg(long fromGroup, long fromQQ, String msg, int msgId) {
		if (!enable) {
			return false;
		}
		return processMsg(fromGroup, fromQQ, msg, msgId);
	}
	public final String getModuleName() {
		if (moduleName == null) {
			moduleName = getClass().getName();
		}
		return moduleName;
	}
	public abstract BaseModule load();
	protected abstract boolean processMsg(long fromGroup, long fromQQ, String msg, int msgId);
}
