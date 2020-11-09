package com.sohu.inputmethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Environment;

public class ExceptionCatcher implements Thread.UncaughtExceptionHandler {

    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static ExceptionCatcher mInstance;
    private String fileName;

    public static synchronized ExceptionCatcher getInstance() {
        if (null == mInstance) {
            mInstance = new ExceptionCatcher();
        }
        return mInstance;
    }

    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        saveCrashInfo2File(ex);
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
            String path = Environment.getExternalStorageDirectory() + "/crash/";
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

