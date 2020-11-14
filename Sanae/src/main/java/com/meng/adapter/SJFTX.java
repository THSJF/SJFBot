package com.meng.adapter;

import com.meng.tools.Tools;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.QuoteReply;
import com.meng.modules.MainSwitch;

/**
 * @Description: bot发送数据
 * @author: 司徒灵羽
 **/

public class SJFTX {
    public final Bot bot;
    public BotWrapperEntity entity;

    public SJFTX(Bot bot) {
        this.bot = bot;
    }

    public int sendGroupMessage(long fromGroup, Message msg) {
        if (entity.sleeping || !entity.configManager.isFunctionEnbled(fromGroup, MainSwitch.class)) {
            return -1;
        }
        return bot.getGroup(fromGroup).sendMessage(msg).getSource().getId();
    }

    public int sendGroupMessage(long fromGroup, String msg) {
        return sendGroupMessage(fromGroup, new PlainText(msg));
    }

    public int sendGroupMessage(long fromGroup, Message... msgs) {
        MessageChainBuilder mcb = new MessageChainBuilder();
        for (Message msg:msgs) {
            mcb.add(msg);
        }
        return sendGroupMessage(fromGroup, mcb.asMessageChain());
    }

    public int sendQuote(GroupMessageEvent gme, String msg) {
        return sendGroupMessage(gme.getGroup().getId(), new QuoteReply(gme.getSource()).plus(msg));
    }

    public int sendQuote(GroupMessageEvent gme, Message msg) {
        return sendGroupMessage(gme.getGroup().getId(), new QuoteReply(gme.getSource()).plus(msg));
    }

    public int sendGroupMessage(long fromGroup, String[] msg) {
        return sendGroupMessage(fromGroup, Tools.ArrayTool.rfa(msg));
    }

    public int sendGroupMessage(long fromGroup, ArrayList<String> msg) {
        return sendGroupMessage(fromGroup, msg.get(ThreadLocalRandom.current().nextInt(msg.size())));
    }

    public int sendPrivateMessage(long qqId, String msg) {
        bot.getFriend(qqId).sendMessage(msg);
        return 0;
    }

}
