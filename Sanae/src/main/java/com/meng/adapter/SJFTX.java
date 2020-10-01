package com.meng.adapter;

import com.meng.tools.Tools;
import java.util.ArrayList;
import java.util.Random;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.QuoteReply;

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
        if (entity.sleeping || entity.configManager.isBotOff(fromGroup)) {
            return -1;
        }
        return bot.getGroup(fromGroup).sendMessage(msg).getSource().getId();
    }

    public int sendGroupMessage(long fromGroup, String msg) {
        return sendGroupMessage(fromGroup, new PlainText(msg));
    }

    public int sendGroupMessage(long fromGroup, String msg, MessageSource ms) {
        return sendGroupMessage(fromGroup, new QuoteReply(ms).plus(msg));
    }

    public int sendGroupMessage(long fromGroup, Message msg, MessageSource ms) {
        return sendGroupMessage(fromGroup, new QuoteReply(ms).plus(msg));
    }
    
    public int sendGroupMessage(long fromGroup, String[] msg) {
        return sendGroupMessage(fromGroup, Tools.ArrayTool.rfa(msg));
    }

    public int sendGroupMessage(long fromGroup, ArrayList<String> msg) {
        return sendGroupMessage(fromGroup, msg.get(new Random().nextInt(msg.size())));
    }

    public int sendPrivateMessage(long qqId, String msg) {
        bot.getFriend(qqId).sendMessage(msg);
        return 0;
    }

}
