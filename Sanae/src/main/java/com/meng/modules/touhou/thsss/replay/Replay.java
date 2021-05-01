package com.meng.modules.touhou.thsss.replay;

import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileTool;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Replay {

    public String path;
    public KeyData keyData;
    public PlayerInfo info;

    public Replay(File file) {
        this(file.getAbsolutePath());
    }

    public Replay(String path) {
        this.path = path;
        keyData = new KeyData(FileTool.readBytes(new File(path)));
        info = readPlayerInfo();
    }

    private PlayerInfo readPlayerInfo() {
        List<String> listNum = null ;
        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File(path)));
            String line;
            listNum = new ArrayList<String>();
            while (!"".equals(line = bf.readLine()) && line != null) {
                listNum.add(line);  
            }
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] strArray1 = listNum.toArray(new String[listNum.size()]);
        try {
            //int num = 0;
            //do { } while(!strArray1[++num].equals("ReplayInformation") && num < strArray1.length - 1);
            //其实就是跳过第一行 直接num=1即可
            int num = 1;
            PlayerInfo replayFile = new PlayerInfo();
            replayFile.version = strArray1[num + 1];
            replayFile.playerName = strArray1[num + 2];
            replayFile.date = strArray1[num + 3];
            replayFile.time = strArray1[num + 4];
            replayFile.myPlaneName = strArray1[num + 5];
            replayFile.weaponType = strArray1[num + 6];
            replayFile.rank = DifficultLevel.valueOf(Integer.parseInt(strArray1[num + 7]));
            replayFile.startStage = strArray1[num + 8];
            replayFile.lastStage = strArray1[num + 9];
            replayFile.slowRate = strArray1[num + 10];
            for (int index = num + 11;index < strArray1.length;++index) {
                String[] strArray2 = strArray1[index].split("\t");
                MyPlaneInfo myPlaneInfo = new MyPlaneInfo();
                myPlaneInfo.life = Integer.parseInt(strArray2[0]);
                myPlaneInfo.spell = Integer.parseInt(strArray2[1]);
                myPlaneInfo.power = Integer.parseInt(strArray2[2]);
                myPlaneInfo.score = Long.parseLong(strArray2[3]);
                myPlaneInfo.graze = Integer.parseInt(strArray2[4]);
                myPlaneInfo.posX = Float.parseFloat(strArray2[5]);
                myPlaneInfo.posY = Float.parseFloat(strArray2[6]);
                myPlaneInfo.lifeChip = Integer.parseInt(strArray2[7]);
                myPlaneInfo.spellChip = Integer.parseInt(strArray2[8]);
                myPlaneInfo.lifeUpCount = Integer.parseInt(strArray2[9]);
                myPlaneInfo.starPoint = Integer.parseInt(strArray2[10]);
                myPlaneInfo.highItemScore = Integer.parseInt(strArray2[11]);
                myPlaneInfo.rate = Float.parseFloat(strArray2[12]);
                myPlaneInfo.lastColor = EnchantmentType.valueOf(Integer.parseInt(strArray2[13]));
                myPlaneInfo.dataPosition = Long.parseLong(strArray2[14]);
                replayFile.myPlaneData.add(myPlaneInfo);
            }
            return replayFile;
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);e.printStackTrace();
            return null;
        }
    }

    public int readKey() {
        return keyData.readUShort();
    }

    @Override
    public String toString() {
        return info.toString();
    }
}
