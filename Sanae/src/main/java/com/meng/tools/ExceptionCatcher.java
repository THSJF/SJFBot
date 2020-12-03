package com.meng.tools;

import com.meng.SBot;
import com.meng.modules.AimMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionCatcher implements Thread.UncaughtExceptionHandler {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static ExceptionCatcher mInstance;
	private String fileName;
    private SBot bot;

    private ExceptionCatcher(SBot bw) {
        bot = bw;
	}

    public static synchronized ExceptionCatcher getInstance(SBot bw) {
        if (null == mInstance) {
            mInstance = new ExceptionCatcher(bw);
		}
        return mInstance;
	}

    public static synchronized ExceptionCatcher getInstance() {
        return mInstance;
	}

    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
	}

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        saveCrashInfo2File(ex);
        try {
            bot.moduleManager.getModule(AimMessage.class).addTipSingleton(2856986197L, "出现了一个错误:" + ex.toString().replace("java", "jvav"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String saveCrashInfo2File(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        Writer writer=new StringWriter();
        PrintWriter printWriter=new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause=ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
		}
        printWriter.close();
        String result=writer.toString();
        sb.append(result);
        try {
            long timestamp=System.currentTimeMillis();
            String time=format.format(new Date());
			fileName = "crash-" + time + "-" + timestamp + ".log";
			String path = bot.appDirectory + "/crash/";
			File dir=new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream fos=new FileOutputStream(path + fileName);
			fos.write(sb.toString().getBytes());
			fos.close();
            return fileName;
		} catch (Exception e) {
            e.printStackTrace();
		}
        return null;
	}
}
