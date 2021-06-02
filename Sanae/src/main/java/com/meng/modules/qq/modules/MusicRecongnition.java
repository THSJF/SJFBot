package com.meng.modules.qq.modules;

import com.meng.config.CommandDescribe;
import com.meng.gameData.TouHou.UserInfo;
import com.meng.modules.CmdExecuter;
import com.meng.modules.Ffmpeg;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.SJFRandom;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jaudiotagger.audio.AudioFileIO;

public class MusicRecongnition extends BaseModule implements IGroupMessageEvent {

    public static String musicFolder = "C://thbgm/";
    private List<String> musicNames = null;

    public MusicRecongnition(SBot bot) {
        super(bot);
    }

    @Override
    @CommandDescribe(cmd = "原曲认知", note = "可通过添加后缀E N H L来调节难度")
    public boolean onGroupMessage(GroupMessageEvent event) {
        String msg = event.getMessage().contentToString();
        long group = event.getGroup().getId();
        long qq = event.getSender().getId();
        if (msg.startsWith("原曲认知")) {
            UserInfo module = entity.moduleManager.getModule(UserInfo.class);
            module.getUserData(event).qaCount++;
            module.save();
            int needSeconds = 3;
            switch (msg.toLowerCase()) {
                case "原曲认知 e":
                case "原曲认知 easy":
                    needSeconds = 10;   
                    break;
                case "原曲认知 n":
                case "原曲认知 normal":
                    needSeconds = 5;
                    break;
                case "原曲认知":
                case "原曲认知 h":
                case "原曲认知 hard":
                    needSeconds = 3;
                    break;
                case "原曲认知 l":
                case "原曲认知 lunatic":
                    needSeconds = 1;
                    break;      
            }
            File[] gameFolders = new File(musicFolder).listFiles(new FileFilter(){

                    @Override
                    public boolean accept(File p1) {
                        return p1.isDirectory() && p1.getName().startsWith("th");
                    }
                });
            if (musicNames == null) {
                musicNames = new ArrayList<>();
                FilenameFilter filter = new FilenameFilter(){

                    @Override
                    public boolean accept(File p1, String p2) {
                        return p2.endsWith(".mp3");
                    }
                };
                for (File gameFolder : gameFolders) {
                    Collections.addAll(musicNames, gameFolder.list(filter));
                }
            }
            File gameFolder = SJFRandom.randomSelect(gameFolders);
            File[] musics = gameFolder.listFiles(new FileFilter(){

                    @Override
                    public boolean accept(File p1) {
                        return p1.getName().endsWith(".mp3");
                    }
                });
            File input = SJFRandom.randomSelect(musics);
            try {
                sendMessage(event, entity.toVoice(generalCut(input, new File(SBot.appDirectory + "/touhou/musicCut/" + System.currentTimeMillis() + "1.wav"), needSeconds), event.getGroup()));
            } catch (Exception e) {
                sendGroupMessage(group, e.toString());
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                return true;
            }
            QuestionAndAnswer.QABean bean = new QuestionAndAnswer.QABean();
            bean.fromQar = true;
            for (int i = 0;i < 3 ;++i) {
                while (true) {
                    String musicName = SJFRandom.randomSelect(musicNames);
                    if (!bean.answersToSelect.contains(musicName)) {
                        bean.answersToSelect.add(musicName);
                        break;
                    }
                }
            }
            int trueAnswer = SJFRandom.randomInt(4);
            bean.setTrueAns(trueAnswer);
            String name = input.getName();
            name = name.replace("上海アリス幻樂団 - ", "");
            name = name.replace(".mp3", "");
            bean.answersToSelect.add(trueAnswer, name);
            entity.moduleManager.getModule(QuestionAndAnswer.class).addQuestion(qq, bean);
            StringBuilder builder = new StringBuilder();
            builder.append("名字是:\n");
            for (int i = 0;i < bean.answersToSelect.size();++i) {
                builder.append(i).append(": ").append(bean.answersToSelect.get(i)).append("\n");
            }
            builder.append("回答序号即可");
            sendMessage(event, builder.toString());
            return true;
		}
        return false;
    }

    private File generalCut(File input, File output, int needSeconds) throws Exception {
        int audioStart = SJFRandom.nextInRange(0, AudioFileIO.read(input).getAudioHeader().getTrackLength() - needSeconds);
        Date start = new Date(audioStart * 1000 - 8 * 60 * 60 * 1000);
        Date end = new Date((audioStart + needSeconds) * 1000 - 8 * 60 * 60 * 1000);
        DateFormat fmt = new SimpleDateFormat("mm:ss");

        Ffmpeg.AudioCommandBuilder builder = new Ffmpeg.AudioCommandBuilder(input);
        //    builder.coverExistFile().author("SJF").comment("from 2un");
        builder.bitrate(64).freq(22050).channels(1).select("00:" + fmt.format(start), "00:" + fmt.format(end));
        String cmd = builder.build(output);
        try(CmdExecuter execute = CmdExecuter.execute(cmd, null)){
            if (execute.getProcess().exitValue() == 0) {
                return output;
            } else {
                return null;
            }
        }
    }
}
