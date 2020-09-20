package com.meng.tools;

import com.meng.BotWrapper;
import java.util.concurrent.TimeUnit;

public class MessageDeleter {

	public static void autoDelete(final BotWrapper bw, final int msgId, int second) {
		SJFExecutors.executeAfterTime(new Runnable(){

				@Override
				public void run() {
					bw.getCQ().deleteMsg(msgId);
				}
			}, second, TimeUnit.SECONDS);
	}

	public static void autoDelete(final BotWrapper bw, final int msgId) {
		SJFExecutors.executeAfterTime(new Runnable(){

				@Override
				public void run() {
                    bw.getCQ().deleteMsg(msgId);
				}
			}, 60, TimeUnit.SECONDS);
	}
}
