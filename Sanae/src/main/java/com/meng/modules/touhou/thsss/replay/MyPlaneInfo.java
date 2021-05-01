package com.meng.modules.touhou.thsss.replay;

public class MyPlaneInfo {
    public int life;
    public int spell;
    public int power;
    public long score;
    public int graze;
    public float posX;
    public float posY;
    public int lifeChip;
    public int spellChip;
    public int lifeUpCount;
    public int starPoint;
    public int highItemScore;
    public float rate;
    public EnchantmentType lastColor;
    public long dataPosition;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("残:").append(life).append(" ")
            .append("雷:").append(spell).append(" ")
            .append("P:").append(power).append(" ")
            .append("分数:").append(score).append(" ")
            .append("擦弹:").append(graze).append(" ")
            .append("残碎:").append(lifeChip).append(" ")
            .append("雷碎:").append(spellChip).append(" ")
            .append("已得残机:").append(lifeUpCount).append(" ")
            .append("最大得点:").append(highItemScore).append(" ")
            .append("星星槽:").append(starPoint).append(" ")
            .append("星色:").append(lastColor).append("\n");
        return builder.toString();
    }
}
