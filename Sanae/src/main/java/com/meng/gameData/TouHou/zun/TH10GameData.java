package com.meng.gameData.TouHou.zun;

import com.meng.gameData.TouHou.SpellCard;
import com.meng.gameData.TouHou.TouhouCharacter;

/**
 * @author: 司徒灵羽
 **/
public class TH10GameData {
	public static final String gameName = "东方风神录 ~ Mountain of Faith.";
	public static final String gameNameCN = "东方风神录";
	public static final String gameNameAbbr = "MoF";
	public static String[] players = {"博丽灵梦 诱导装备", "博丽灵梦 前方集中装备", "博丽灵梦 封印装备", "雾雨魔理沙 高威力装备", "雾雨魔理沙 贯通装备", "雾雨魔理沙 魔法使装备"};
	public static TouhouCharacter[] charaName = {
		new TouhouCharacter("秋静叶", gameNameCN, "寂寞与终焉的象征"),
		new TouhouCharacter("秋穰子", gameNameCN, "丰裕与收成的象征"),
		new TouhouCharacter("键山雏", gameNameCN, "秘神流雏"),
		new TouhouCharacter("河城荷取", gameNameCN, "超妖怪弹头"),
		new TouhouCharacter("犬走椛", gameNameCN, "下端警戒天狗"),
		new TouhouCharacter("射命丸文", gameNameCN, "最接近村落的天狗"),
		new TouhouCharacter("东风谷早苗", gameNameCN, "祭祀风的人类"),
		new TouhouCharacter("八坂神奈子", gameNameCN, "山坂与湖水的化身"),
		new TouhouCharacter("洩矢诹访子", gameNameCN, "土著神的顶点")
	};
	public static String[] musicName = {
		"被封印的众神",
		"眷爱众生之神 ~ Romantic Fall",
		"受稻田公主的斥责啦",
		"厄神降临之路 ~ Dark Road",
		"命运的阴暗面",
		"众神眷恋的幻想乡",
		"芥川龙之介的河童 ~ Candid Friend",
		"Fall of Fall ~ 秋意渐浓之瀑",
		"妖怪之山 ~ Mysterious Mountain",
		"少女曾见的日本原风景",
		"信仰是为了虚幻之人",
		"御柱的墓场 ~ Grave of Being",
		"神圣庄严的古战场 ~ Suwa Foughten Field",
		"明日之盛,昨日之俗",
		"Native Faith",
		"山脚的神社",
		"神明降下恩惠之雨 ~ Sylphid",
		"Player's Score"	
	};
	public static SpellCard[] spellcards = {
		new SpellCard("叶符「狂舞的落叶」", "秋静叶", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("秋符「秋季的天空」", "秋穰子", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("秋符「无常秋日与少女的心」", "秋穰子", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("丰符「大年收获者」", "秋穰子", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("丰收「谷物神的允诺」", "秋穰子", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("厄符「厄运」", "键山雏", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("厄符「厄神大人的生理节律」", "键山雏", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("疵符「破裂的护符」", "键山雏", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("疵痕「损坏的护身符」", "键山雏", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("恶灵「厄运之轮」", "键山雏", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("悲运「大钟婆之火」", "键山雏", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("创符「痛苦之流」", "键山雏", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("创符「流刑人偶」", "键山雏", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("光学「光学迷彩」", "河城荷取", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("光学「水迷彩」", "河城荷取", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("洪水「泥浆泛滥」", "河城荷取", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("洪水「冲积梦魇」", "河城荷取", SpellCard.Normal),
		new SpellCard("漂溺「粼粼水底之心伤」", "河城荷取", SpellCard.Lunatic),
		new SpellCard("水符「河童之河口浪潮」", "河城荷取", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("水符「河童之山洪暴发」", "河城荷取", SpellCard.Normal),
		new SpellCard("水符「河童之幻想大瀑布」", "河城荷取", SpellCard.Lunatic),
		new SpellCard("河童「妖怪黄瓜」", "河城荷取", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("河童「延展手臂」", "河城荷取", SpellCard.Normal),
		new SpellCard("河童「回转顶板」", "河城荷取", SpellCard.Lunatic),
		new SpellCard("岐符「天之八衢」", "射命丸文", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("岐符「猿田彦神之岔路」", "射命丸文", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("风神「风神木叶隐身术」", "射命丸文", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("风神「天狗颪」", "射命丸文", SpellCard.Normal),
		new SpellCard("风神「二百十日」", "射命丸文", SpellCard.Lunatic),
		new SpellCard("「幻想风靡」", "射命丸文", SpellCard.Normal | SpellCard.Hard),
		new SpellCard("「无双风神」", "射命丸文", SpellCard.Lunatic),
		new SpellCard("塞符「山神渡御」", "射命丸文", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("塞符「天孙降临」", "射命丸文", SpellCard.Normal),
		new SpellCard("塞符「天上天下的照国」", "射命丸文", SpellCard.Lunatic),
		new SpellCard("秘术「灰色奇术」", "东风谷早苗", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("秘术「遗忘之祭仪」", "东风谷早苗", SpellCard.Normal),
		new SpellCard("秘术「一脉相传的弹幕」", "东风谷早苗", SpellCard.Lunatic),
		new SpellCard("奇迹「白昼的客星」", "东风谷早苗", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("奇迹「客星璀璨之夜」", "东风谷早苗", SpellCard.Normal),
		new SpellCard("奇迹「客星辉煌之夜」", "东风谷早苗", SpellCard.Lunatic),
		new SpellCard("开海「割海成路之日」", "东风谷早苗", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("开海「摩西之奇迹」", "东风谷早苗", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("准备「呼唤神风的星之仪式」", "东风谷早苗", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("准备「召请建御名方神」", "东风谷早苗", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("奇迹「神之风」", "东风谷早苗", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("大奇迹「八坂之神风」", "东风谷早苗", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("神祭「扩展御柱」", "八坂神奈子", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("奇祭「目处梃子乱舞」", "八坂神奈子", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("筒粥「神の粥」", "八坂神奈子", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("忘谷「遗忘之谷」", "八坂神奈子", SpellCard.Normal),
		new SpellCard("神谷「神谕之谷」", "八坂神奈子", SpellCard.Lunatic),
		new SpellCard("贽符「御射山御狩神事」", "八坂神奈子", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神秘「葛泉清水」", "八坂神奈子", SpellCard.Normal),
		new SpellCard("神秘「大和茅环」", "八坂神奈子", SpellCard.Lunatic),
		new SpellCard("天流「天水奇迹」", "八坂神奈子", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("天龙「雨之源泉」", "八坂神奈子", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「信仰之山」", "八坂神奈子", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("「风神之神德」", "八坂神奈子", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("神符「如水眼之美丽源泉」", "八坂神奈子", SpellCard.Extra),
		new SpellCard("神符「结于杉木之古缘」", "八坂神奈子", SpellCard.Extra),
		new SpellCard("神符「神所踏足之御神渡」", "八坂神奈子", SpellCard.Extra),
		new SpellCard("开宴「二拜二拍一拜」", "洩矢诹访子", SpellCard.Extra),
		new SpellCard("土著神「手长足长大人」", "洩矢诹访子", SpellCard.Extra),
		new SpellCard("神具「洩矢的铁轮」", "洩矢诹访子", SpellCard.Extra),
		new SpellCard("源符「厌川的翡翠」", "洩矢诹访子", SpellCard.Extra),
		new SpellCard("蛙狩「蛙以口鸣，方致蛇祸」", "洩矢诹访子", SpellCard.Extra),
		new SpellCard("土著神「七石七木」", "洩矢诹访子", SpellCard.Extra),
		new SpellCard("土著神「小小青蛙不输风雨」", "洩矢诹访子", SpellCard.Extra),
		new SpellCard("土著神「宝永四年的赤蛙」", "洩矢诹访子", SpellCard.Extra),
		new SpellCard("「诹访大战 ~ 土著神话 vs 中央神话」", "洩矢诹访子", SpellCard.Extra),
		new SpellCard("祟符「洩矢大人」", "洩矢诹访子", SpellCard.Extra)
	};
}
