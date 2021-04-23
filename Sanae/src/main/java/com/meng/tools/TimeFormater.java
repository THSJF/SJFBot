package com.meng.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 司徒灵羽
 */

public class TimeFormater {

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

    public static String cal(long miSec) {
        long second=miSec / 1000;
        long h = 0;
        long min = 0;
        long temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp > 60) {
                min = temp / 60;
            }
        } else {
            min = second / 60;
        }
        if (h == 0) {
            return min + "分";
        } else {
            return h + "时" + min + "分";
        }
    }

	public static long getTomorrow() {
		Calendar nextDay = Calendar.getInstance();
		nextDay.set(Calendar.HOUR_OF_DAY, 0);
		nextDay.set(Calendar.MINUTE, 0);
		nextDay.set(Calendar.SECOND, 0);
		nextDay.add(Calendar.DAY_OF_MONTH, 1);
		return nextDay.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
	}
}
