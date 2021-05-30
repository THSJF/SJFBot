package com.meng.bot;

import com.meng.config.ConfigManager;
import com.meng.modules.bilibili.BilibiliBotMain;
import com.meng.modules.qq.QqBotMain;
import com.meng.modules.touhou.THGameDataManager;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileWatcher;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

public class Main {

    public static Instrumentation INST;

    public static void premain(String agentArgs, Instrumentation inst) {
        INST = inst;
    }

    public static void main(String... args) {
        FileWatcher.getInstance().init();
        ConfigManager.getInstance().init();
        THGameDataManager.getThGameData();
        FileFormat.init();
        QqBotMain.getInstance().init();
        BilibiliBotMain.getInstance().init();
    }

    public static Throwable reload(Class<?> clazz, byte[] data) {
        try {
            ClassDefinition classDefinition = new ClassDefinition(clazz, data);
            INST.redefineClasses(classDefinition);
        } catch (Throwable e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return e;
        }
        return null;
    }
}
 
