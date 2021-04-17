package com.meng.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormater {

	/**
	 * @author 司徒灵羽
	 */

	public static String getTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String getTime(long timeStamp) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeStamp));
	}

	public static String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public static String getDate(long timeStamp) {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(timeStamp));
	}

	public static String formatDate(int y, int m, int d) {
		return getDate(new Date(y, m, d).getTime());
	}

	public static long getNextDay() {
		Calendar nextDay = Calendar.getInstance();
		nextDay.set(Calendar.HOUR_OF_DAY, 0);
		nextDay.set(Calendar.MINUTE, 0);
		nextDay.set(Calendar.SECOND, 0);
		nextDay.add(Calendar.DAY_OF_MONTH, 1);
		return nextDay.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
	}
}
