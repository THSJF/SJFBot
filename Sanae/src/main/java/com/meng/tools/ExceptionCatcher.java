package com.meng.tools;

import com.meng.adapter.BotWrapperEntity;
import com.meng.modules.MAimMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExceptionCatcher implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultHandler;
	private Map<String,String> paramsMap=new HashMap<>();
    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private String TAG=this.getClass().getSimpleName();
    private static ExceptionCatcher mInstance;
	private String fileName;
    private BotWrapperEntity wrapper;

    private ExceptionCatcher(BotWrapperEntity bw) {
        wrapper = bw;
	}

    public static synchronized ExceptionCatcher getInstance(BotWrapperEntity bw) {
        if (null == mInstance) {
            mInstance = new ExceptionCatcher(bw);
		}
        return mInstance;
	}

    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
	}

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement ste:ex.getStackTrace()) {
            sb.append(ste.toString()).append("\n");
        }
        saveCrashInfo2File(ex);
        wrapper.moduleManager.getModule(MAimMessage.class).addTip(2856986197L, sb.toString());
        //  mDefaultHandler.uncaughtException(thread, ex);
	}

    private String saveCrashInfo2File(Throwable ex) {

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,String> entry : paramsMap.entrySet()) {
            String key=entry.getKey();
            String value=entry.getValue();
            sb.append(key + "=" + value + "\n");
		}

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
			String path = wrapper.appDirectory + "/crash/";
			File dir=new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream fos=new FileOutputStream(path + fileName);
			fos.write(sb.toString().getBytes());
			fos.close();
            return fileName;
		} catch (Exception e) {

		}
        return null;
	}
}
