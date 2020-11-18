package com.meng.tools;

import com.meng.SBot;
import java.util.concurrent.TimeUnit;

public class MessageDeleter {

	public static void autoDelete(final SBot bw, final int msgId, int second) {
		SJFExecutors.executeAfterTime(new Runnable(){

				@Override
				public void run() {
					bw.recall(msgId);
				}
			}, second, TimeUnit.SECONDS);
	}

	public static void autoDelete(final SBot bw, final int msgId) {
		SJFExecutors.executeAfterTime(new Runnable(){

				@Override
				public void run() {
                    bw.recall(msgId);
				}
			}, 60, TimeUnit.SECONDS);
	}
}
