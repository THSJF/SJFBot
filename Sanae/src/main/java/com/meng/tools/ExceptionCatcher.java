package com.meng.tools;

import com.meng.modules.SJFPermissionDeniedException;
import com.meng.modules.qq.SBot;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class ExceptionCatcher implements Thread.UncaughtExceptionHandler {

    private static ExceptionCatcher mInstance;

    private ExceptionCatcher() {
	}

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
        if (ex instanceof SJFPermissionDeniedException) {
            SJFPermissionDeniedException pde = (SJFPermissionDeniedException)ex;
            SBot.instance.sendQuote(pde.event, "Permission Denied.");
            return;
        }
        ex.printStackTrace();
        saveCrashInfo2File(ex);
    }

    private String saveCrashInfo2File(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
		}
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            File file = SJFPathTool.getCrashLog();
            FileTool.saveFile(file, sb.toString().replace("java", "jvav").getBytes(StandardCharsets.UTF_8));
            return file.getAbsolutePath();
		} catch (Exception e) {
            e.printStackTrace();
		}
        return null;
	}
}
