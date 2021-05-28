package com.meng.bot;

import com.meng.config.ConfigManager;
import com.meng.modules.bilibili.BilibiliBotMain;
import com.meng.modules.qq.QqBotMain;
import com.meng.tools.FileFormat;
import com.meng.tools.FileWatcher;

public class Main {

    public static void main(String... args) {
        FileWatcher.getInstance().init();
        ConfigManager.getInstance().init();
        FileFormat.init();
        QqBotMain.getInstance().init();
        BilibiliBotMain.getInstance().init();
     }
}
