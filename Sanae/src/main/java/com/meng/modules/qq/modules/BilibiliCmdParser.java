package com.meng.modules.qq.modules;

import com.meng.bot.Functions;
import com.meng.config.ConfigManager;
import com.meng.modules.bilibili.BilibiliPair;
import com.meng.modules.bilibili.LinkAnalyzer;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import java.net.MalformedURLException;
import java.net.URL;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class BilibiliCmdParser extends BaseModule implements IGroupMessageEvent {

    private LinkAnalyzer parser = new LinkAnalyzer();

    public BilibiliCmdParser(SBot bot) {
        super(bot);
    }

    @Override
    public BilibiliCmdParser load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return getClass().getName();
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        if (!ConfigManager.getInstance().isFunctionEnabled(event.getGroup().getId(), Functions.BilibiliVideo)) {
            return false; 
        }
        String string = event.getMessage().contentToString();
//        if (string.equals(".live")) {
//            LiveListener listener = LiveListener.getInstance();
//            sendMessage(event, listener.getLiving());
//        }
        BilibiliPair pair = parser.parse(string);
        if (pair != null) {
            MessageChainBuilder builder = new MessageChainBuilder();
            try {
                builder.append(entity.toImage(new URL(pair.url), event.getGroup()));
            } catch (MalformedURLException e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
            builder.add(pair.text);
            sendMessage(event.getGroup(), builder.asMessageChain());
        }
        return false;
    }
}
