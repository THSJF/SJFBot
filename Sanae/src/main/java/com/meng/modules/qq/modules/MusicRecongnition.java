package com.meng.modules.qq.modules;
import com.meng.gameData.TouHou.THDataHolder;
import com.meng.gameData.TouHou.zun.TH10GameData;
import com.meng.gameData.TouHou.zun.TH11GameData;
import com.meng.gameData.TouHou.zun.TH12GameData;
import com.meng.gameData.TouHou.zun.TH14GameData;
import com.meng.gameData.TouHou.zun.TH15GameData;
import com.meng.gameData.TouHou.zun.TH16GameData;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.touhou.zun.music.FmtFile;
import com.meng.modules.touhou.zun.music.WavHeader;
import com.meng.tools.ExceptionCatcher;
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
                String[] musics = THDataHolder.music[random.nextInt(THDataHolder.music.length)];
                String musicName = musics[random.nextInt(musics.length)];
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
                bean.a.add(trueAnswer, TH10GameData.musicName[musicNum]);
                break;
            case "th11":
                bean.a.add(trueAnswer, TH11GameData.musicName[musicNum]);
                break;
            case "th12":
                bean.a.add(trueAnswer, TH12GameData.musicName[musicNum]);
                break;
            case "th14":
                bean.a.add(trueAnswer, TH14GameData.musicName[musicNum]);
                break;
            case "th15":
                bean.a.add(trueAnswer, TH15GameData.musicName[musicNum]);
                break;
            case "th16":
                bean.a.add(trueAnswer, TH16GameData.musicName[musicNum]);
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
        StringBuilder cmdBuilder = new StringBuilder();
        cmdBuilder.append("ffmpeg -i \"");
        cmdBuilder.append(input.getAbsolutePath());
        cmdBuilder.append("\" -ac 2 -ar 22040 -ab 64 \"");
        cmdBuilder.append(output.getAbsolutePath());
        cmdBuilder.append("\"");
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
            TTS.StreamGobbler outputGobbler = new TTS.StreamGobbler(proc.getInputStream(), "output");
            errorGobbler.start();
            outputGobbler.start();
            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal);
            if (exitVal != 0) {
                throw new RuntimeException("exit value:" + exitVal);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), t);
        }
        return output;
    }
}
