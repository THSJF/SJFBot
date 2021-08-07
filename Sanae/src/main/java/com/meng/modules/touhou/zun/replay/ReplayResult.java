package com.meng.modules.touhou.zun.replay;

import com.meng.tools.JsonHelper;
import java.util.LinkedHashMap;
import java.util.Map;

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
            .append("\nthprac信息:");
        if (thprac.equals("未使用")) {
            sb.append(thprac);  
        } else {
            Map<String,Object> info = new LinkedHashMap<>();
            info = JsonHelper.fromJson(thprac, info.getClass());
            for (Map.Entry<String,Object> entry : info.entrySet()) {
                //      System.out.println("case \"" + entry.getKey() + " :\n\nbreak;");
                sb.append("\n");
                switch (entry.getKey()) {
                    case "version":
                        sb.append("版本");
                        break;
                    case "game":
                        sb.append("游戏");
                        break;
                    case "mode":
                        sb.append("模式").append(":");
                        int mode = ((Number)entry.getValue()).intValue();
                        /*if (mode == 1) {
                         sb.append("单关练习");
                         } else {
                         sb.append("unknown");
                         }*/
                        sb.append(mode);
                        continue;
                    case "stage":
                        sb.append("关卡");
                        break;
                    case "score":
                        sb.append("得分");
                        break;
                    case "point":
                        sb.append("点数");
                        break;
                    case "rankLock":
                        sb.append("rank锁定");
                        break;
                    case "fakeType":
                        sb.append("自机伪装");
                        break;
                    case "life_fragment":
                        sb.append("残机碎片");
                        break;
                    case "bomb":
                        sb.append("雷");
                        break;
                    case "bomb_fragment":
                        sb.append("雷碎片");
                        break;
                    case "power":
                        sb.append("灵力");
                        break;
                    case "value":
                        sb.append("最大得点");
                        break;
                    case "graze":
                        sb.append("擦弹");
                        break;
                    case "cherryMax":
                        sb.append("樱+上限");
                        break;
                    case "cherryPlus":
                        sb.append("最大得点");
                        break;
                    case "spellBonus":
                        sb.append("收卡数");
                        break;
                    case "gauge":
                        sb.append("人妖槽");
                        break;
                    case "night":
                        sb.append("夜点");
                        break;
                    case "faith":
                        sb.append("信仰");
                        break;
                    case "faith_bar":
                        sb.append("信仰保持时间");
                        break;
                    case "signal":
                        sb.append("信号");
                        break;
                    case "ventra_1":
                        sb.append("飞碟1");
                        break;
                    case "ventra_2":
                        sb.append("飞碟2");
                        break;
                    case "ventra_3":
                        sb.append("飞碟3");
                        break;
                    case "motivation":
                        sb.append("干劲");
                        break;
                    case "perfect_freeze":
                        sb.append("完美冻结");
                        break;
                    case "ice_power":
                        sb.append("冰冻力");
                        break;
                    case "ice_area":
                        sb.append("冰冻范围");
                        break;
                    case "trance_meter": //thprac单词拼错了
                        sb.append("灵界槽");
                        break;
                    case "cycle":
                        sb.append("收点循环");
                        break;
                    case "season_gauge":
                        sb.append("季节槽");
                        break;
                    case "bug_fix":
                        sb.append("bug修复");
                        break;
                    case "goast_1":
                        sb.append("灵1");
                        break;
                    case "goast_2":
                        sb.append("灵2");
                        break;
                    case "goast_3":
                        sb.append("灵3");
                        break;
                    case "goast_4":
                        sb.append("灵4");
                        break;
                    case "goast_5":
                        sb.append("灵5");
                        break;
                    case "mukade":
                        sb.append("mukade");
                        break;
                    case "life":
                        sb.append("残机");
                        break;
                    case "extend":
                        sb.append("已得残机");
                        break;
                    case "point_total":
                        sb.append("点数");
                        break;
                    case "point_stage":
                        sb.append("关卡点数");
                        break;
                    default :
                        sb.append(entry.getKey());
                        break;
                }
                sb.append(":");
                Object value = entry.getValue();
                if (value instanceof Number) {
                    sb.append(((Number)value).intValue());
                } else {
                    sb.append(value);
                }
            }
        }
        if (slow != null) {
            sb.append("\n处理落率:").append(slow);
        }
        if (splits != null && thprac.equals("未使用")) {
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
