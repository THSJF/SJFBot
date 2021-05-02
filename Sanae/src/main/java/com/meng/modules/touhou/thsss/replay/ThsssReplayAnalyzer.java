package com.meng.modules.touhou.thsss.replay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class ThsssReplayAnalyzer {
    /*夏夜祭replay结构:
     (数字存储都是小端模式)

     关卡key数据在前,机体信息时间信息等在后
     每一个关卡的key数据首尾相连,每一面的开头都是ushort 57358(0xE00E)
     key数据和信息之间用ushort 4080分开(0x0FF0)
     一帧的key数据为2字节


     57358 本面key数据
     57358 本面key数据
     .....
     57358 本面key数据
     4080
     机体信息,时间信息

     */
     
    public static String analyze(ThsssReplay replay){
        StringBuilder builder = new StringBuilder();
        builder.append(replay.info.toString());
        builder.append("s:shoot f:focus b:bomb c:ctrl\n");
        KeyDataItem keyclass = new KeyDataItem();
        for (int i = 0;i < replay.info.myPlaneData.size();++i) {
            builder.append("stage ").append((i + 1)).append("\n");
            replay.keyData.position = (int) replay.info.myPlaneData.get(i).dataPosition;
            int keyValue = replay.readKey();
            if (keyValue == 57358) {
                int flag = 0;
                keyValue = replay.readKey();
                while (keyValue != 4080 && keyValue != 57358) {
                    keyclass.hex2Key(keyValue);
                    builder.append(keyclass.toString()).append("|");
                    if (++flag % 60 == 0) {
                        builder.append("\n");
                    }
                    keyValue = replay.readKey();
                } 
            }
            builder.append("\n\n\n");
        }
        return builder.toString();  
    }
}
