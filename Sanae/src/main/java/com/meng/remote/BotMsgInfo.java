package com.meng.remote;

public class BotMsgInfo {
	public int sendTo=0;
	public int recFrom=0;
	public int msgPerSec=0;
	public int msgCmdPerSec=0;
	public int msgSendPerSec=0;

	public void reset() {
		sendTo = 0;
		recFrom = 0;
		msgPerSec = 0;
		msgCmdPerSec = 0;
		msgSendPerSec = 0;
	}
}
