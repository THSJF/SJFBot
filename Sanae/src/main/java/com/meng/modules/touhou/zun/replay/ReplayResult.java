package com.meng.modules.touhou.zun.replay;

public class ReplayResult {
    public String game;
    public String name;
    public String date;
    public String character;
    public String difficulty;
    public String stage;
    public String score;
    public String slow;
    public ReplaySplits[] splits;
    public String thprac = "未使用";

    public ReplayResult() {

    }

    public ReplayResult(String game) {
        this.game = game;
    }

    public static class ReplaySplits {
        public int stage;
        public int score;
        public String power;
        public int piv;
        public String lives;
        public String bombs;
        public String additional;
        public int graze;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("关卡:").append(stage)
                .append("\n分数:").append(score)
                .append("\n灵力:").append(power)
                .append("\n最大得点:").append(piv)
                .append("\n残机:").append(lives)
                .append("\nbombs:").append(bombs)
                .append("\n").append(additional)
                .append("\n擦弹:").append(graze);
            return sb.append("\n").toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(game)
            .append("\n机签:").append(name)
            .append("\n日期:").append(date)
            .append("\n自机:").append(character)
            .append("\n难度:").append(difficulty)
            .append("\n进度:").append(stage)
            .append("\n得分:").append(score)
            .append("\nthprac信息:").append(thprac);
        if (slow != null) {
            sb.append("\n处理落率:").append(slow);
        }
        if (splits != null) {
            sb.append("\n\n各面数初始信息:");
            for (ReplaySplits rs : splits) {
                if (rs != null) {
                    sb.append("\n").append(rs.toString());
                }
            }
        }
        return sb.toString();
    }
}
