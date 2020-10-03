package com.meng.tools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SJFExecutors {

	/**
	 * @author 司徒灵羽
	 */

	private static ScheduledThreadPoolExecutor scheduledExecutorService = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(32);
	private static ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

	public static void executeAfterTime(Runnable runnable, long delay, TimeUnit timeUnit) {
		scheduledExecutorService.schedule(runnable, delay, timeUnit);
	}

	public static void executeWithFixedDelay(Runnable runnable, long wait, long delay, TimeUnit time) {
		scheduledExecutorService.scheduleWithFixedDelay(runnable, wait, delay, time);
	}

	public static void executeAtFixedRate(Runnable runnable, long wait, long rate, TimeUnit time) {
		scheduledExecutorService.scheduleAtFixedRate(runnable, wait, rate, time);
	}

	public static void execute(Runnable runnable) {
		threadPool.execute(runnable);
	}

	public static int getActiveCount() {
		return scheduledExecutorService.getActiveCount() + threadPool.getActiveCount();
	}

	public static int getPoolSize() {
		return scheduledExecutorService.getPoolSize() + threadPool.getPoolSize();
	}

	public static int getLargestPoolSize() {
		return scheduledExecutorService.getLargestPoolSize() + threadPool.getLargestPoolSize();
	}

	public static long getCompletedTaskCount() {
		return scheduledExecutorService.getCompletedTaskCount() + threadPool.getCompletedTaskCount();
	}

	public static long getTaskCount() {
		return scheduledExecutorService.getTaskCount() + threadPool.getTaskCount();
	}

	public static void shutdownNow() {
		scheduledExecutorService.shutdownNow();
		threadPool.shutdownNow();
	}
}
