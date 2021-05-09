package com.meng.modules.touhou;

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
