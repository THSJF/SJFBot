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
        sb.append(name).append(" ")
            .append(date).append(" ")
            .append(character).append(" ")
            .append(difficulty).append(" ")
            .append(stage).append(" ")
            .append(score).append(" ")
            .append(slow);
        return sb.toString();
    }
}
