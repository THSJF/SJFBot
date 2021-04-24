package com.meng.modules.qq.modules;

import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import java.io.File;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Voice;
import net.mamoe.mirai.message.code.MiraiCode;
import com.meng.modules.qq.QqBotMain;
import com.meng.gameData.TouHou.THDataHolder;

/**
 * @author: 司徒灵羽
 **/

public class MtestMsg extends BaseModule implements IGroupMessageEvent {

    @Override
    public String getModuleName() {
        return "测试模块";
    }

    public MtestMsg(SBot bw) {
        super(bw);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        if (gme.getMessage().contentToString().equals("mcode")) {
          //  processText(gme);
            entity.sendMessage(gme.getGroup(),
                               MiraiCode.deserializeMiraiCode(String.format(
                               "[mirai:app:{\"app\"\\:\"com.tencent.miniapp\"\\,\"desc\"\\:\"\"\\,\"view\"\\:\"notification\"\\,\"ver\"\\:\"0.0.0.1\"\\,\"prompt\"\\:\"蓝的今日人品\"\\,\"meta\"\\:{\"notification\"\\:{\"appInfo\"\\:{\"appName\"\\:\"蓝的今日人品\"\\,\"appType\"\\:4\\,\"appid\"\\:1109659848\\,\"iconUrl\"\\:\"http\\:\\\\/\\\\/q1.qlogo.cn\\\\/g?b=qq&nk=2856986197&s=640\"}\\,\"data\"\\:\\[{\"title\"\\:\"今日人品\"\\,\"value\"\\:\"99.61%\"}\\,{\"title\"\\:\"今日适宜\"\\,\"value\"\\:\"东方绀珠传\"}\\]\\,\"title\"\\:\"\"\\,\"emphasis_keyword\"\\:\"今日人品\"}}}]",
                                                                  gme.getSender().getNick(),gme.getSender().getNick(),gme.getSender().getId(),THDataHolder.hashRandomFloat(gme.getSender().getId()),THDataHolder.hashRandomSpell(gme.getSender().getId()).name)));
        }
        return false;
    }

    private void processText(GroupMessageEvent event) {
        try {
            File fileMp3 = new File(SBot.appDirectory + "/tts/此生无悔入东方_来世愿生幻想乡.mp3");
            Voice ptt = entity.toVoice(fileMp3, event.getGroup());
            if (ptt == null) {
                entity.sendQuote(event, "生成失败");
            }
            entity.sendQuote(event, ptt);
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString());
        }
    }

    @Override
    public BaseModule load() {
        return this;
    }
}
