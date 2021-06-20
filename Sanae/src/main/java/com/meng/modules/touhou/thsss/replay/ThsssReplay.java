package com.meng.modules.touhou.thsss.replay;

import com.meng.tools.FileTool;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ThsssReplay {

    public KeyData keyData;
    public PlayerInfo info;

    public ThsssReplay(File file) {
        this(file.getAbsolutePath());
    }

    public ThsssReplay(String path) {
        keyData = new KeyData(FileTool.readBytes(new File(path)));
        List<String> listNum = new ArrayList<>() ;
        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File(path)));
            String line;
            while (!"".equals(line = bf.readLine()) && line != null) {
                listNum.add(line);  
            }
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] strArray1 = listNum.toArray(new String[0]);
        int num = 0;
        while (!strArray1[++num].equals("ReplayInformation") && num < strArray1.length - 1) {}
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.version = strArray1[num + 1];
        playerInfo.playerName = strArray1[num + 2];
        playerInfo.date = strArray1[num + 3];
        playerInfo.time = strArray1[num + 4];
        playerInfo.myPlaneName = strArray1[num + 5];
        playerInfo.weaponType = strArray1[num + 6];
        playerInfo.rank = DifficultLevel.valueOf(Integer.parseInt(strArray1[num + 7]));
        playerInfo.startStage = strArray1[num + 8];
        playerInfo.lastStage = strArray1[num + 9];
        playerInfo.slowRate = strArray1[num + 10];
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
            playerInfo.myPlaneData.add(myPlaneInfo);
        }
        info = playerInfo;
    }

    public int readKey() {
        return keyData.readUShort();
    }

    @Override
    public String toString() {
        return info.toString();
    }

    public String getKeys() {
        return keyData.toString();
    }

    public class PlayerInfo {
        public ArrayList<MyPlaneInfo> myPlaneData = new ArrayList<MyPlaneInfo>();
        public String version;
        public String playerName;
        public String myPlaneName;
        public String date;
        public String time;
        public String weaponType;
        public DifficultLevel rank;
        public String startStage;
        public String lastStage;
        public String slowRate;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("机签:").append(playerName).append("\n");
            builder.append("机体:").append(myPlaneName).append("\n");
            builder.append("时间:").append(date).append(" ").append(time).append("\n");
            builder.append("难度:").append(rank).append("\n");
            builder.append("开始时关卡:").append(startStage).append("\n");
            builder.append("结束时关卡:").append(lastStage.equals("7") ? "通关" : lastStage).append("\n");
            builder.append("处理落率:").append(slowRate).append("\n");
            builder.append("各关卡初始资源:\n");
            for (MyPlaneInfo info : myPlaneData) {
                builder.append(info.toString());
            }
            return builder.toString();
        }
    }

    public class KeyData {
        public byte[] fileByte;
        public int position = 0;

        public KeyData(byte[] bs) {
            fileByte = bs;
        }

        public int readUShort() {
            return 0xffff & (fileByte[position++] & 0xff | (fileByte[position++] & 0xff) << 8);
        }
    }
}
