package com.meng.gameData.TouHou.zun;

import com.meng.gameData.TouHou.SpellCard;
import com.meng.gameData.TouHou.TouhouCharacter;

/**
 * @author: 司徒灵羽
 **/
public class TH12GameData {
	public static final String gameName = "东方星莲船 ~ Undefined Fantastic Object.";
	public static final String gameNameCN = "东方星莲船";
	public static final String gameNameAbbr = "UFO";
	public static String[] players = {"博丽灵梦 诱导型", "博丽灵梦 威力重视型", "雾雨魔理沙 贯通型", "雾雨魔理沙 范围重视型", "东风谷早苗 诱导型", "东风谷早苗 广范围炸裂型"};
	public static TouhouCharacter[] charaName = {
		new TouhouCharacter("纳兹琳", gameNameCN, "探宝的小小大将"),
		new TouhouCharacter("多多良小伞", gameNameCN, "愉快的遗忘之伞"),
		new TouhouCharacter("云居一轮&云山", gameNameCN, "守护与被守护的大轮"),
		new TouhouCharacter("村纱水蜜", gameNameCN, "水难事故的念缚灵"),
		new TouhouCharacter("寅丸星", gameNameCN, "毘沙门天的弟子"),
		new TouhouCharacter("圣白莲", gameNameCN, "被封印的大魔法师"),
		new TouhouCharacter("封兽鵺", gameNameCN, "未确认幻想飞行少女")
	};
	public static String[] musicName = {
		"青空之影",
		"春之岸边",
		"小小的贤将",
		"封闭的云中通路",
		"请注意万年备用伞",
		"Sky Ruin",
		"守旧老爹与前卫少女",
		"幽灵客船的穿越时空之旅",
		"Captain Murasa",
		"魔界地方都市秘境",
		"虎纹的毘沙门天",
		"法界之火",
		"感情的摩天楼 ~ Cosmic Mind",
		"夜空中的UFO恋曲",
		"平安时代的外星人",
		"妖怪寺",
		"空中的归路 ~ Sky Dream",
		"Player's Score"	
	};
	public static SpellCard[] spellcards = {
		new SpellCard("棒符「忙碌探知棒」", "纳兹琳", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("搜符「稀有金属探测器」", "纳兹琳", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("搜符「G黄金探测器」", "纳兹琳", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("视符「娜兹玲灵摆」", "纳兹琳", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("视符「高感度娜兹玲灵摆」", "纳兹琳", SpellCard.Hard),
		new SpellCard("守符「灵摆防御」", "纳兹琳", SpellCard.Lunatic),
		new SpellCard("大轮「唐伞光晕」", "多多良小伞", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("大轮「Hello,Forgotten World」", "多多良小伞", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("伞符「雨伞的星之交响」", "多多良小伞", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("伞符「雨伞的星之追忆」", "多多良小伞", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("雨符「雨夜怪谈」", "多多良小伞", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("雨伞「超防水干爽伞妖」", "多多良小伞", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("化符「遗忘之伞的夜行列车」", "多多良小伞", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("化铁「备用伞特急夜晚狂欢号」", "多多良小伞", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("铁拳「问答无用妖怪拳」", "云居一轮", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神拳「凌云地狱冲」", "云居一轮", SpellCard.Hard),
		new SpellCard("神拳「天海地狱冲」", "云居一轮", SpellCard.Lunatic),
		new SpellCard("拳符「天网沙袋」", "云居一轮", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("连打「云界海妖来袭」", "云居一轮", SpellCard.Hard),
		new SpellCard("连打「帝王海妖来袭」", "云居一轮", SpellCard.Lunatic),
		new SpellCard("拳打「重拳碎击」", "云居一轮", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("溃灭「天上天下连续勾拳」", "云居一轮", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("大喝「守旧尊老之怒眼」", "云居一轮", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("忿怒「天变巨眼焚身」", "云居一轮", SpellCard.Hard),
		new SpellCard("忿怒「空前绝后巨眼焚身」", "云居一轮", SpellCard.Lunatic),
		new SpellCard("倾覆「同路人之锚」", "村纱水蜜", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("倾覆「沉没之锚」", "村纱水蜜", SpellCard.Hard),
		new SpellCard("倾覆「击沉之锚」", "村纱水蜜", SpellCard.Lunatic),
		new SpellCard("溺符「深海漩涡」", "村纱水蜜", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("溺符「沉底漩涡」", "村纱水蜜", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("港符「幽灵船之泊」", "村纱水蜜", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("港符「幽灵船之港」", "村纱水蜜", SpellCard.Hard),
		new SpellCard("港符「幽灵船永久停泊」", "村纱水蜜", SpellCard.Lunatic),
		new SpellCard("幽灵「Sinker Ghost」", "村纱水蜜", SpellCard.Normal),
		new SpellCard("幽灵「悄然袭来的长勺」", "村纱水蜜", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("宝塔「最优良的宝物」", "纳兹琳", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("宝塔「光辉之宝」", "寅丸星", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("宝塔「光辉宝枪」", "寅丸星", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("光符「绝对正义」", "寅丸星", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("光符「正义之威光」", "寅丸星", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("法力「至宝之独钴杵」", "寅丸星", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("法灯「无瑕佛法之独钴杵」", "寅丸星", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("光符「净化之魔」", "寅丸星", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard),
		new SpellCard("「完全净化」", "寅丸星", SpellCard.Lunatic),
		new SpellCard("魔法「紫云之兆」", "圣白莲", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("吉兆「紫色云路」", "圣白莲", SpellCard.Hard),
		new SpellCard("吉兆「极乐的紫色云路」", "圣白莲", SpellCard.Lunatic),
		new SpellCard("魔法「魔界蝶的妖香」", "圣白莲", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("魔法「魔法之蝶」", "圣白莲", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("光魔「星辰漩涡」", "圣白莲", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("光魔「魔法银河」", "圣白莲", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("大魔法「魔神复诵」", "圣白莲", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「圣尼公的气之魔法卷轴」", "圣白莲", SpellCard.Normal | SpellCard.Hard),
		new SpellCard("超人「圣白莲」", "圣白莲", SpellCard.Lunatic),
		new SpellCard("飞钵「飞行幻想」", "圣白莲", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("飞钵「传说的飞空圆盘」", "圣白莲", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("伞符「大颗的泪雨」", "多多良小伞", SpellCard.Extra),
		new SpellCard("惊雨「台风骤雨」", "多多良小伞", SpellCard.Extra),
		new SpellCard("光晕「唐伞惊吓闪光」", "多多良小伞", SpellCard.Extra),
		new SpellCard("妖云「平安时代的黑云」", "封兽鵺", SpellCard.Extra),
		new SpellCard("真相不明「愤怒的红色UFO袭来」", "封兽鵺", SpellCard.Extra),
		new SpellCard("鵺符「鵺的蛇行表演」", "封兽鵺", SpellCard.Extra),
		new SpellCard("真相不明「哀愁的蓝色UFO袭来」", "封兽鵺", SpellCard.Extra),
		new SpellCard("鵺符「弹幕奇美拉」", "封兽鵺", SpellCard.Extra),
		new SpellCard("真相不明「忠义的绿色UFO袭来」", "封兽鵺", SpellCard.Extra),
		new SpellCard("鵺符「真相不明的黑暗」", "封兽鵺", SpellCard.Extra),
		new SpellCard("真相不明「恐怖的虹色UFO袭来」", "封兽鵺", SpellCard.Extra),
		new SpellCard("「平安京的恶梦」", "封兽鵺", SpellCard.Extra),
		new SpellCard("恨弓「源三位赖政之弓」", "封兽鵺", SpellCard.Extra)	
	};
}
