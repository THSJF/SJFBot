package com.meng.modules;

import com.meng.SBot;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import java.io.File;
import java.io.FileInputStream;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.PttMessage;

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
            PttMessage ptt = event.getGroup().uploadVoice(new FileInputStream(fileMp3));
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
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        return false;
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        return false;
    }

    @Override
    public BaseModule load() {
        return this;
    }
}
