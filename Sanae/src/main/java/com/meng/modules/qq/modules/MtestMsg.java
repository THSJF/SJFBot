package com.meng.modules.qq.modules;

import com.meng.gameData.TouHou.THDataHolder;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import java.io.File;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.Voice;

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
                               "[mirai:app:{\"app\"\\:\"com.tencent.miniapp\"\\,\"desc\"\\:\"\"\\,\"view\"\\:\"notification\"\\,\"ver\"\\:\"0.0.0.1\"\\,\"prompt\"\\:\"%s的今日人品\"\\,\"meta\"\\:{\"notification\"\\:{\"appInfo\"\\:{\"appName\"\\:\"%s的今日人品\"\\,\"appType\"\\:4\\,\"appid\"\\:1109659848\\,\"iconUrl\"\\:\"http\\:\\\\/\\\\/q1.qlogo.cn\\\\/g?b=qq&nk=%s&s=640\"}\\,\"data\"\\:\\[{\"title\"\\:\"今日人品\"\\,\"value\"\\:\"%.2f%\"}\\,{\"title\"\\:\"今日适宜\"\\,\"value\"\\:\"%s\"}\\]\\,\"title\"\\:\"\"\\,\"emphasis_keyword\"\\:\"今日人品\"}}}]",
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
