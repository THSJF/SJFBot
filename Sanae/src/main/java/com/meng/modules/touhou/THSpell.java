package com.meng.modules.touhou;
import com.meng.tools.TextLexer;

public class THSpell {
    public static final int Easy = 1 << 0;
    public static final int Normal = 1 << 1;
    public static final int Hard = 1 << 2;
    public static final int Lunatic = 1 << 3;
    public static final int Extra = 1 << 4;
    public static final int Phantasm = 1 << 5;
    public static final int LastSpell = 1 << 6;
    public static final int LastWord = 1 << 7;
    public static final int Overdrive = 1 << 8;
    public static final int FinalSpellCard = 1 << 9;
    public static final int StoryAll = Easy | Normal | Hard | Lunatic;

    public int difficult = 0;
    public String cnName;
    public String jpName;
    public THCharacter master;
    public String[] nick;
    public THGameData game;

    public THSpell(THGameData game, THCharacter master, String cnName, String jpName, int diffcult, String... nick) {
        this.game = game;
        this.master = master;
        this.cnName = cnName;
        this.jpName = jpName;
        this.difficult = diffcult;
        this.nick = nick;
    }

    public String getPs() {
        StringBuilder sb = new StringBuilder();
        sb.append(cnName).append("(").append(jpName).append(")").append("是").append(master);
        if (difficult != THSpell.LastSpell && difficult != THSpell.LastWord) {
            sb.append("在");
            if ((difficult & THSpell.Easy) == THSpell.Easy) {
                sb.append(" easy");
            }
            if ((difficult & THSpell.Normal) == THSpell.Normal) {
                sb.append(" normal");
            }
            if ((difficult & THSpell.Hard) == THSpell.Hard) {
                sb.append(" hard");
            }
            if ((difficult & THSpell.Lunatic) == THSpell.Lunatic) {
                sb.append(" lunatic");
            }
            if (difficult == THSpell.Extra) {
                sb.append(" extra");
            }
            if (difficult == THSpell.Phantasm) {
                sb.append(" phantasm");
            }
            if (difficult == THSpell.Overdrive) {
                sb.append(" overdrive");
            }
            sb.append("难度下的符卡");
        } else {
            if (difficult == THSpell.LastSpell) {
                sb.append("的lastspell");
            } else if (difficult == THSpell.LastWord) {
                sb.append("的lastword");
            }
        }
        return sb.toString();
    }

    public String getNameEng() {
        String s = cnName.substring(cnName.indexOf("「"), cnName.indexOf("」"));
        return TextLexer.isAlpha(s.charAt(0)) ? s : null;
    }

    public String getNameAbbr() {
        String s = getNameEng();
        if (s == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i < s.length();++i) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return cnName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof THSpell)) {
            return false;
        }
        THSpell spc = (THSpell)o;
        return cnName.equals(spc.cnName) && difficult == spc.difficult;
    }
}
