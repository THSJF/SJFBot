package com.meng.modules.qq.modules;

import com.meng.Functions;
import com.meng.config.ConfigManager;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.Base64Converter;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileTool;
import com.meng.tools.Hash;
import com.meng.tools.Youtu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Voice;

public class TTS extends BaseModule implements IGroupMessageEvent {

    public TTS(SBot sb) {
        super(sb);
    }

    @Override
    public TTS load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return getClass().getName();
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        if (!ConfigManager.getInstance().isFunctionEnabled(event.getGroup().getId(), Functions.TTS)) {
            return false;
        }
        String contentToString = event.getMessage().contentToString();
        if (contentToString.startsWith("tts")) {
            String text = contentToString.substring(4);
            processText(text, event);
            return true;
        }
        return false;
    }

    private void processText(String text, GroupMessageEvent event) {
        try {
            sendQuote(event, "正在生成……");
            Youtu.TtsResult response = Youtu.getFaceYoutu().doTtsWithUrl(text);
            String base64voice = response.voice;
            byte[] mp3 = Base64Converter.getInstance().decode(base64voice);
            String calculate = Hash.getMd5Instance().calculate(mp3);
            File fileMp3 = new File(SBot.appDirectory + "/tts/" + calculate + ".mp3");
            FileTool.saveFile(fileMp3, mp3);
            File fileAmr = new File(SBot.appDirectory + "/tts/" + calculate + ".amr");

            Voice ptt = entity.toVoice(convert(fileMp3, fileAmr), event.getGroup());
            if (ptt == null) {
                sendQuote(event, "生成失败");
            }
            sendMessage(event, ptt);
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            sendQuote(event, e.toString());
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
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "error");
            StreamGobbler outputGobbler = new  StreamGobbler(proc.getInputStream(), "output");
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

    public static class StreamGobbler extends Thread {
        private InputStream is;
        private String type;
        private OutputStream os;

        public StreamGobbler(InputStream is, String type) {
            this(is, type, null);
        }

        public StreamGobbler(InputStream is, String type, OutputStream redirect) {
            this.is = is;
            this.type = type;
            this.os = redirect;
        }

        public void run() {
            try {
                //   try(PrintWriter pw = new PrintWriter(os)){

                //     }
                PrintWriter pw = null;
                if (os != null) {
                    pw = new PrintWriter(os);
                }
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (pw != null) {
                        pw.println(line);
                    }
                    System.out.println(type + ">" + line); 
                }
                if (pw != null) {
                    pw.flush();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace(); 
            }
        }
    }
}
