package com.meng.modules.qq.modules;

import com.meng.gameData.TouHou.UserInfo;
import com.meng.modules.CmdExecuter;
import com.meng.modules.Ffmpeg;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.touhou.THGameDataManager;
import com.meng.modules.touhou.gameData.TH10Data;
import com.meng.modules.touhou.gameData.TH11Data;
import com.meng.modules.touhou.gameData.TH12Data;
import com.meng.modules.touhou.gameData.TH14Data;
import com.meng.modules.touhou.gameData.TH15Data;
import com.meng.modules.touhou.gameData.TH16Data;
import com.meng.modules.touhou.zun.music.FmtFile;
import com.meng.modules.touhou.zun.music.WavHeader;
import com.meng.tools.FileTool;
import com.meng.tools.Tools;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Random;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public class MusicRecongnition extends BaseModule implements IGroupMessageEvent {

    public static String musicFolder = "C://thbgm/";

    public MusicRecongnition(SBot bot) {
        super(bot);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        String msg = event.getMessage().contentToString();
        long group = event.getGroup().getId();
        long qq = event.getSender().getId();
        if (msg.equals("原曲认知")) {
            File musicFragment = createMusicCut(new Random().nextInt(16), 3, group, qq);
            sendGroupMessage(group, entity.toVoice(musicFragment, event.getGroup())); 
            UserInfo module = entity.moduleManager.getModule(UserInfo.class);
            module.getUserData(event).qaCount++;
            module.save();
            return true;
        }
        if (msg.startsWith("原曲认知 ")) {
            switch (msg.toLowerCase()) {
                case "原曲认知 e":
                case "原曲认知 easy":
                    sendGroupMessage(group, entity.toVoice(createMusicCut(new Random().nextInt(16), 10, group, qq), event.getGroup()));   
                    break;
                case "原曲认知 n":
                case "原曲认知 normal":
                    sendGroupMessage(group, entity.toVoice(createMusicCut(new Random().nextInt(16), 6, group, qq), event.getGroup()));
                    break;
                case "原曲认知 h":
                case "原曲认知 hard":
                    sendGroupMessage(group, entity.toVoice(createMusicCut(new Random().nextInt(16), 3, group, qq), event.getGroup()));
                    break;
                case "原曲认知 l":
                case "原曲认知 lunatic":
                    sendGroupMessage(group, entity.toVoice(createMusicCut(new Random().nextInt(16), 1, group, qq), event.getGroup()));
                    break;      
            }
            return true;
		}
        return false;
    }

    @Override
    public String getModuleName() {
        return getClass().getSimpleName();
    }

    @Override
    public MusicRecongnition load() {
        return this;
    }

    private File createMusicCut(int musicNum, int needSeconeds, long group,  long qq) {
        File[] games = new File(musicFolder).listFiles();
        Random random = new Random();
        int game = random.nextInt(games.length);
        File fmtFile = new File(musicFolder + games[game].getName() + "/thbgm.fmt");
        FmtFile thfmt = new FmtFile(fmtFile);
        FmtFile.MusicInfo muiscInfo = thfmt.musicInfos[musicNum];
        byte[] music = new byte[needSeconeds * muiscInfo.bitsPerSample * muiscInfo.channels * muiscInfo.rate / 8];
        readFile(music, getStartBytes(musicNum, thfmt, needSeconeds), games[game].getName());
        WavHeader wavHeader = new WavHeader();
        byte[] finalFileBytes = Tools.ArrayTool.mergeArray(wavHeader.getWavHeader(musicNum, thfmt, needSeconeds), music);
        File resultFile = new File(SBot.appDirectory + "/touhou/musicCut/" + System.currentTimeMillis() + ".wav");
        FileTool.saveFile(resultFile, finalFileBytes);
        resultFile = convert(resultFile, new File(SBot.appDirectory + "/touhou/musicCut/" + System.currentTimeMillis() + 1 + ".wav"));
        QuestionAndAnswer.QABean bean = new QuestionAndAnswer.QABean();
        bean.fromQar = true;
        for (int i = 0;i < 3 ;++i) {
            while (true) {
                String musicName = THGameDataManager.randomMusic().name;
                if (!bean.a.contains(musicName)) {
                    bean.a.add(musicName);
                    break;
                }
            }
		}
        int trueAnswer = random.nextInt(4);
        bean.setTrueAns(trueAnswer);
        switch (games[game].getName()) {
            case "th10":
                bean.a.add(trueAnswer, TH10Data.getInstance().getMusics()[musicNum].name);
                break;
            case "th11":
                bean.a.add(trueAnswer, TH11Data.getInstance().getMusics()[musicNum].name);
                break;
            case "th12":
                bean.a.add(trueAnswer, TH12Data.getInstance().getMusics()[musicNum].name);
                break;
            case "th14":
                bean.a.add(trueAnswer, TH14Data.getInstance().getMusics()[musicNum].name);
                break;
            case "th15":
                bean.a.add(trueAnswer, TH15Data.getInstance().getMusics()[musicNum].name);
                break;
            case "th16":
                bean.a.add(trueAnswer, TH16Data.getInstance().getMusics()[musicNum].name);
                break;
        }
        entity.moduleManager.getModule(QuestionAndAnswer.class).addQuestion(qq, bean);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append("名字是:\n");
        for (String s:bean.a) {
            sb.append(i++).append(": ").append(s).append("\n");
        }
        sb.append("回答序号即可");
        sendGroupMessage(group, sb.toString());
        return resultFile;
    }

    private byte[] readFile(byte[] data, int offset, String name) {
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(musicFolder + name + "/thbgm.dat", "r");
            randomAccessFile.seek(offset);
            randomAccessFile.readFully(data);
            randomAccessFile.close();
        } catch (Exception e) {
            throw new RuntimeException("bgm read failed");
        }
        return data;
    }

    private int getStartBytes(int musicNum, FmtFile thfmt, int needSeconeds) {
        FmtFile.MusicInfo muiscInfo = thfmt.musicInfos[musicNum];
        int oneFrameBytes = muiscInfo.bitsPerSample / 8 * muiscInfo.channels;   
        int startFtame = new Random().nextInt(muiscInfo.length / oneFrameBytes);
        int SecNeedBytes = needSeconeds * muiscInfo.bitsPerSample * muiscInfo.channels * muiscInfo.rate / 8;
        int questionStartBytes = muiscInfo.start + startFtame * oneFrameBytes;
        if (muiscInfo.length - startFtame * oneFrameBytes < SecNeedBytes) {
            questionStartBytes = startFtame * oneFrameBytes - SecNeedBytes;
        }
        return questionStartBytes;
	}

    public File convert(File input, File output) {
        Ffmpeg.AudioCommandBuilder builder = new Ffmpeg.AudioCommandBuilder(input);
        //    builder.coverExistFile().author("SJF").comment("from 2un");
        builder.bitrate(64).freq(22050).channels(1);
        if (CmdExecuter.execute(builder.build(output), null).getProcess().exitValue() == 0) {
            return output;
        } else {
            return null;
        }
    }
}
