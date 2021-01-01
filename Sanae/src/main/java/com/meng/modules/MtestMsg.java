package com.meng.modules;


/**
 * @author: 司徒灵羽
 **/
import com.meng.SBot;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.tools.Base64Converter;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileTool;
import com.meng.tools.Hash;
import com.meng.tools.Youtu;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.PttMessage;

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
            File fileAmr = new File(SBot.appDirectory + "/tts/此生无悔入东方_来世愿生幻想乡.amr");
            PttMessage ptt = event.getGroup().uploadVoice(convert(fileMp3, fileAmr));
            if (ptt == null) {
                entity.sendQuote(event, "生成失败");
            }
            entity.sendQuote(event, ptt);
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString().replace("java", "jvav"));
        }
    }

    public InputStream convert(File mp3, File amr) {
        StringBuilder cmdBuilder = new StringBuilder();
        cmdBuilder.append("ffmpeg -i ");
        cmdBuilder.append(mp3.getAbsolutePath());
        cmdBuilder.append(" ");
        cmdBuilder.append(amr.getAbsolutePath());
        //    String args = "ffmpeg -i c:\\1.jpg c:\\11.png";
        String args = cmdBuilder.toString();
        try { 
            String[] cmd = new String[]{
                "cmd.exe",
                "/C",
                args
            };
            Runtime rt = Runtime.getRuntime();
            System.out.println("Execing " + cmd[0] + " " + cmd[1]  + " " + cmd[2]);
            Process proc = rt.exec(cmd);
            TTS.StreamGobbler errorGobbler = new TTS.StreamGobbler(proc.getErrorStream(), "error");
            TTS.StreamGobbler outputGobbler = new  TTS.StreamGobbler(proc.getInputStream(), "output");
            errorGobbler.start();
            outputGobbler.start();
            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal);
            if (exitVal != 0) {
                return null;
            }
        } catch (Throwable t) {
            t.printStackTrace();
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), t);
            return null;
        }
        try {
            return new FileInputStream(amr);
        } catch (FileNotFoundException e) {
            return null;
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
