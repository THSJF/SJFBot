package com.meng.modules.qq.modules;

import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import java.io.File;
import net.mamoe.mirai.event.events.GroupMessageEvent;
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
        if (gme.getMessage().contentToString().equals("voice")) {
            processText(gme);
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
            entity.sendQuote(event, e.toString().replace("java", "jvav"));
        }
    }

    @Override
    public BaseModule load() {
        return this;
    }
}
