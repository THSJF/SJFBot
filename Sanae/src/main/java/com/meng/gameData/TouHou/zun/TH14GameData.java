package com.meng.gameData.TouHou.zun;

import com.meng.gameData.TouHou.SpellCard;
import com.meng.gameData.TouHou.TouhouCharacter;

/**
 * @author: 司徒灵羽
 **/
public class TH14GameData {
	public static final String gameName = "东方辉针城 ~ Double Dealing Character.";
	public static final String gameNameCN = "东方辉针城";
	public static final String gameNameAbbr = "DDC";
	public static String[] players = {"博丽灵梦", "雾雨魔理沙", "十六夜咲夜"};
    public static String[] playerSub = {"使用妖器", "不使用妖器"};
	public static TouhouCharacter[] charaName = {
		new TouhouCharacter("琪露诺", gameNameCN, "湖的冰精"),
		new TouhouCharacter("若鹭姬", gameNameCN, "栖息于淡水的人鱼"),
		new TouhouCharacter("赤蛮奇", gameNameCN, "辘轳首的怪奇"),
		new TouhouCharacter("今泉影狼", gameNameCN, "竹林的Loup-Garou"),
		new TouhouCharacter("九十九八桥", gameNameCN, "古旧的琴的付丧神"),
		new TouhouCharacter("九十九弁弁", gameNameCN, "古旧琵琶的付丧神"),
		new TouhouCharacter("鬼人正邪", gameNameCN, "逆袭的天邪鬼"),
		new TouhouCharacter("少名针妙丸", gameNameCN, "小人的后裔"),
		new TouhouCharacter("堀川雷鼓", gameNameCN, "梦幻的打击乐手")
	};
	public static String[] musicName = {
		"不可思议的驱魔棒",
		"Mist Lake",
		"秘境的人鱼",
		"往来于运河的人与妖",
		"柳树下的杜拉罕",
		"满月的竹林",
		"孤独的狼人",
		"Magical Storm",
		"幻想净琉璃",
		"沉向空中的辉针城",
		"Reverse Ideology",
		"针小棒大的天守阁",
		"辉光之针的小人族 ~ Little Princess",
		"魔力的雷云",
		"原初的节拍 ~ Pristine Beat",
		"小槌的魔力",
		"非常非常神奇的道具们",
		"Player's Score"	
	};
	public static SpellCard[] spellcards = {
		new SpellCard("冰符「Ultimate Blizzard」", "琪露诺", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("水符「尾鳍拍击」", "若鹭姬", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("鳞符「鳞之波」", "若鹭姬", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("鳞符「逆鳞的惊涛」", "若鹭姬", SpellCard.Hard),
		new SpellCard("鳞符「逆鳞的大惊涛」", "若鹭姬", SpellCard.Lunatic),
		new SpellCard("飞符「飞行之头」", "赤蛮奇", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("首符「闭目射击」", "赤蛮奇", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("首符「辘轳首飞来」", "赤蛮奇", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("飞头「倍增之头」", "赤蛮奇", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("飞头「第七个头」", "赤蛮奇", SpellCard.Hard),
		new SpellCard("飞头「第九个头」", "赤蛮奇", SpellCard.Lunatic),
		new SpellCard("飞头「杜拉罕之夜」", "赤蛮奇", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("牙符「月下的犬齿」", "今泉影狼", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("变身「三角齿」", "今泉影狼", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("变身「星形齿」", "今泉影狼", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("咆哮「陌生的咆哮」", "今泉影狼", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("咆哮「满月的远吠」", "今泉影狼", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("狼符「星环猛扑」", "今泉影狼", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("天狼「高速猛扑」", "今泉影狼", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("琴符「诸行无常的琴声」", "九十九八桥", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("响符「平安的残响」", "九十九八桥", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("响符「回音之庭」", "九十九八桥", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("筝曲「下克上送筝曲」", "九十九八桥", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("筝曲「下克上安魂曲」", "九十九八桥", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("平曲「祗园精舍的钟声」", "九十九弁弁", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("怨灵「无耳芳一」", "九十九弁弁", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("怨灵「平家的大怨灵」", "九十九弁弁", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("乐符「邪恶的五线谱」", "九十九弁弁", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("乐符「凶恶的五线谱」", "九十九弁弁", SpellCard.Hard),
		new SpellCard("乐符「Double Score」", "九十九弁弁", SpellCard.Lunatic),
		new SpellCard("欺符「逆针击」", "鬼人正邪", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("逆符「镜之国的弹幕」", "鬼人正邪", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("逆符「镜中的邪恶」", "鬼人正邪", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("逆符「天地有用」", "鬼人正邪", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("逆符「天下翻覆」", "鬼人正邪", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("逆弓「天壤梦弓」", "鬼人正邪", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("逆弓「天壤梦弓的诏敕」", "鬼人正邪", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("逆转「阶级反转」", "鬼人正邪", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("逆转「变革空勇士」", "鬼人正邪", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("小弹「小人的道路」", "少名针妙丸", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("小弹「小人的荆棘路」", "少名针妙丸", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("小槌「变大吧」", "少名针妙丸", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("小槌「变得更大吧」", "少名针妙丸", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("妖剑「辉针剑」", "少名针妙丸", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("小槌「你给我变大吧」", "少名针妙丸", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「进击的小人」", "少名针妙丸", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("「一寸之壁」", "少名针妙丸", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「七个小拇指」", "少名针妙丸", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("「七个一寸法师」", "少名针妙丸", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("弦乐「风暴的合奏」", "九十九八桥&九十九弁弁", SpellCard.Extra),
		new SpellCard("弦乐「净琉璃世界」", "九十九八桥&九十九弁弁", SpellCard.Extra),
		new SpellCard("一鼓「暴乱宫太鼓」", "堀川雷鼓", SpellCard.Extra),
		new SpellCard("二鼓「怨灵绫鼓」", "堀川雷鼓", SpellCard.Extra),
		new SpellCard("三鼓「午夜零时的三振」", "堀川雷鼓", SpellCard.Extra),
		new SpellCard("死鼓「轻敲大地」", "堀川雷鼓", SpellCard.Extra),
		new SpellCard("五鼓「雷电拨浪鼓」", "堀川雷鼓", SpellCard.Extra),
		new SpellCard("六鼓「交替打击法」", "堀川雷鼓", SpellCard.Extra),
		new SpellCard("七鼓「高速和太鼓火箭」", "堀川雷鼓", SpellCard.Extra),
		new SpellCard("八鼓「雷神之怒」", "堀川雷鼓", SpellCard.Extra),
		new SpellCard("「蓝色佳人的演出」", "堀川雷鼓", SpellCard.Extra),
		new SpellCard("「Pristine Beat」", "堀川雷鼓", SpellCard.Extra)
	};
}
