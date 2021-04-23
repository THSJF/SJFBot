package com.meng.bot;

import com.meng.modules.qq.BotMessageHandler;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.MessageManager;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.JsonHelper;
import com.meng.tools.SJFExecutors;
import java.io.File;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.BotConfiguration;
import com.meng.modules.qq.QqBotMain;
import com.meng.config.ConfigManager;

public class Main {

    public static void main(String... args) {
        ConfigManager.init();
        FileFormat.init();
        QqBotMain.main();
     }
}
