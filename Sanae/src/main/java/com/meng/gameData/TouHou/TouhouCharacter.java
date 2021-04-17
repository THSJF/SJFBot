package com.meng.gameData.TouHou;

/**
 * @author: 司徒灵羽
 **/
public class TouhouCharacter {
	
	public String charaName;
	public String game;
	public String nick = "该角色信息未填坑";
	
	public TouhouCharacter(String name, String game, String nick) {
		charaName = name;
		this.game = game;
		this.nick = nick;
	}
	
	public TouhouCharacter(String name, String game) {
		charaName = name;
		this.game = game;
	}
}
