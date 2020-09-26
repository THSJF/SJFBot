package com.meng.tools;

import com.meng.adapter.BotWrapperEntity;
import java.util.concurrent.TimeUnit;

public class MessageDeleter {

	public static void autoDelete(final BotWrapperEntity bw, final int msgId, int second) {
		SJFExecutors.executeAfterTime(new Runnable(){

				@Override
				public void run() {
					bw.deleteMsg(msgId);
				}
			}, second, TimeUnit.SECONDS);
	}

	public static void autoDelete(final BotWrapperEntity bw, final int msgId) {
		SJFExecutors.executeAfterTime(new Runnable(){

				@Override
				public void run() {
                    bw.deleteMsg(msgId);
				}
			}, 60, TimeUnit.SECONDS);
	}
}
