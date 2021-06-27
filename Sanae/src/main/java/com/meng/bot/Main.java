package com.meng.bot;

import com.meng.config.ConfigManager;
import com.meng.config.NetServer;
import com.meng.modules.bilibili.BilibiliBotMain;
import com.meng.modules.bilibili.live.AimLiveListener;
import com.meng.modules.qq.QqBotMain;
import com.meng.modules.touhou.THGameDataManager;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileWatcher;
import com.meng.tools.SJFExecutors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String... args) {
        FileWatcher.getInstance().init();
        ConfigManager.getInstance().init();
        THGameDataManager.getThGameData();
        FileFormat.init();
        QqBotMain.getInstance().init();
        BilibiliBotMain.getInstance().init();
        try {
            NetServer.getInstance().init();
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        SJFExecutors.executeWithFixedDelay(AimLiveListener.getInstance(), 4, TimeUnit.SECONDS);
    }
}
 
