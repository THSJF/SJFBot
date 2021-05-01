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
import com.meng.tools.Tools;
import java.io.File;
import java.io.FileOutputStream;
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
        long qq = event.getGroup().getId();
        if (msg.equals("原曲认知")) {
            File musicFragment = createMusicCut(new Random().nextInt(16), 10, group, qq);
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
        int game = new Random().nextInt(games.length);
        File fmtFile = new File(musicFolder + games[game].getName() + "/thbgm.fmt");
        File resultFile = null;
        FmtFile thfmt = new FmtFile(fmtFile);
        FmtFile.MusicInfo muiscInfo = thfmt.musicInfos[musicNum];
        byte[] music = new byte[needSeconeds * muiscInfo.bitsPerSample * muiscInfo.channels * muiscInfo.rate / 8];
        readFile(music, getStartBytes(musicNum, thfmt, needSeconeds), games[game].getName());
        WavHeader wavHeader = new WavHeader();
        byte[] finalFile = Tools.ArrayTool.mergeArray(wavHeader.getWavHeader(musicNum, thfmt, needSeconeds), music);
        final String newFileName = "C://Users/Administrator/Desktop/酷Q Pro/data/record/" + System.currentTimeMillis() + ".wav";
        try {
            resultFile = new File(newFileName);
            if (resultFile.exists()) {
                resultFile.delete();
            }
            FileOutputStream fom = new FileOutputStream(resultFile);
            fom.write(finalFile, 0, finalFile.length);
            fom.flush();
            fom.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        QuestionAndAnswer.QABean bean = new QuestionAndAnswer.QABean();
        bean.fromQar = true;
        bean.setTrueAns(0);
        switch (games[game].getName()) {
            case "th10":
                bean.a.add(TH10GameData.musicName[musicNum]);
                break;
            case "th11":
                bean.a.add(TH11GameData.musicName[musicNum]);
                break;
            case "th12":
                bean.a.add(TH12GameData.musicName[musicNum]);
                break;
            case "th14":
                bean.a.add(TH14GameData.musicName[musicNum]);
                break;
            case "th15":
                bean.a.add(TH15GameData.musicName[musicNum]);
                break;
            case "th16":
                bean.a.add(TH16GameData.musicName[musicNum]);
                break;
        }
        for (int i = 0;i < 3 ;++i) {
            while (true) {
                Random random = new Random();
                String[] musics = THDataHolder.music[random.nextInt(THDataHolder.music.length)];
                String musicName = musics[random.nextInt(musics.length)];
                if (!bean.a.contains(musicName)) {
                    bean.a.add(musicName);
                    break;
                }
            }
		}
        bean.setTrueAns(0);
        bean.shuffleAnswer();
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
}
