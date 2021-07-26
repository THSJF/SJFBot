package com.meng.modules.touhou.zun.replay;

public class ZunReplay {
    public int game;
    public String name;
    public String date;
    public String character;
    public String difficulty;
    public String stage;
    public String score;
    public String slow;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("机签:").append(name)
            .append("\n日期:").append(date)
            .append("\n自机:").append(character)
            .append("\n难度:").append(difficulty)
            .append("\n进度:").append(stage)
            .append("\n得分:").append(score);
        if (slow != null) {
            sb.append("\n处理落率:").append(slow);
        }
        return sb.toString();
    }
}
