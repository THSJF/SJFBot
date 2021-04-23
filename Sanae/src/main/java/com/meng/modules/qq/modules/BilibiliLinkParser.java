package com.meng.modules.qq.modules;

import com.meng.Functions;
import com.meng.modules.bilibili.BilibiliPair;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import java.net.MalformedURLException;
import java.net.URL;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import com.meng.config.ConfigManager;

public class BilibiliLinkParser extends BaseModule implements IGroupMessageEvent {

    private com.meng.modules.bilibili.BilibiliLinkParser parser = new com.meng.modules.bilibili.BilibiliLinkParser();

    public BilibiliLinkParser(SBot bot) {
        super(bot);
    }

    @Override
    public BilibiliLinkParser load() {
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
        BilibiliPair pair = parser.parse(event.getMessage().contentToString());
        if (pair != null) {
            MessageChainBuilder builder = new MessageChainBuilder();
            try {
                builder.append(entity.toImage(new URL(pair.url), event.getGroup()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            builder.add(pair.text);
            entity.sendMessage(event.getGroup(), builder.asMessageChain());
        }
        return false;
    }
}
