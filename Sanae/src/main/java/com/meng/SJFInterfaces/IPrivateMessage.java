package com.meng.SJFInterfaces;

import java.io.*;

public interface IPrivateMessage {
	public boolean onPrivateMsg(long fromQQ, String msg, int msgId);
}
