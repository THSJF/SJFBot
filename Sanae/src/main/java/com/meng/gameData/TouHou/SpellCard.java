package com.meng.gameData.TouHou;

/**
 * @author: 司徒灵羽
 **/
public class SpellCard {

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

	public int difficultFlag = 0;
	public String name;
	public String master;
    public String[] spellNick;

	public SpellCard(String name, String master, int diffcult, String... nick) {
		this.name = name;
		this.master = master;
		difficultFlag = diffcult;
        spellNick = nick;
	}

	public SpellCard(String firstName, String lastName, String master, int diffcult, String... nick) {
		name = firstName + "「" + lastName + "」";
		this.master = master;
		difficultFlag = diffcult;
        spellNick = nick;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
		if (!(o instanceof SpellCard)) {
			return false;
		}
		SpellCard spc = (SpellCard)o;
		return name.equals(spc.name) && difficultFlag == spc.difficultFlag;
	}
}
