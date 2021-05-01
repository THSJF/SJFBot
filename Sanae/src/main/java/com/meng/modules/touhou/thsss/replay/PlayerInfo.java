package com.meng.modules.touhou.thsss.replay;

import com.meng.modules.touhou.thsss.replay.MyPlaneInfo;
import java.util.ArrayList;

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
