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

    public static String analyze(ThsssReplay replay) {
        StringBuilder builder = new StringBuilder();
        builder.append(replay.playerInfo.toString());
        builder.append("s:shoot f:focus b:bomb c:ctrl\n");
        for (int i = 0;i < replay.playerInfo.myPlaneData.size();++i) {
            builder.append("stage ").append(i + 1).append("\n");
            replay.keyData.position = (int) replay.playerInfo.myPlaneData.get(i).dataPosition;
            int keyValue = replay.readKey();
            if (keyValue == 57358) {
                int flag = 0;
                keyValue = replay.readKey();
                while (keyValue != 4080 && keyValue != 57358) {
                    hex2key(keyValue, builder);
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

    private static void hex2key(int keyValue, StringBuilder builder) {
        boolean arrowUp = false;
        boolean arrowDown = false;
        boolean arrowLeft = false;
        boolean arrowRight = false;
        boolean key_Shift = false;
        boolean key_Z = false;
        boolean key_X = false;
        boolean key_C = false;
        boolean key_Ctrl = false;
        key_Ctrl = (keyValue & 0b1000_0000) != 0;
        key_C = (keyValue & 0b1000_0000_0) != 0;
        key_X = (keyValue & 0b1000_0000_00) != 0;
        key_Z = (keyValue & 0b1000_0000_000) != 0;
        key_Shift = (keyValue & 0b1000_0000_0000) != 0;
        arrowRight = (keyValue & 0b1000_0000_0000_0) != 0;
        arrowLeft = (keyValue & 0b1000_0000_0000_00) != 0;
        arrowDown = (keyValue & 0b1000_0000_0000_000) != 0;
        arrowUp = (keyValue & 0b1000_0000_0000_0000) != 0;
        if (arrowUp && arrowLeft) {
            builder.append("↖");
        } else if (arrowUp && arrowRight) {
            builder.append("↗");
        } else if (arrowDown && arrowLeft) {
            builder.append("↙");
        } else if (arrowDown && arrowRight) {
            builder.append("↘");
        } else if (arrowUp) {
            builder.append("↑");
        } else if (arrowDown) {
            builder.append("↓");
        } else if (arrowLeft) {
            builder.append("←");
        } else if (arrowRight) {
            builder.append("→");
        } else {
            builder.append("○");
        }
        if (key_Shift) {
            builder.append("f");
        }
        if (key_Z) {
            builder.append("s");
        }
        if (key_X) {
            builder.append("b");
        }
        if (key_Ctrl) {
            builder.append("c");
        }
        builder.append("|");
    }
}
