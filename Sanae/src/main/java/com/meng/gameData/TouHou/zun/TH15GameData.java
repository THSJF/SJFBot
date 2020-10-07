package com.meng.gameData.TouHou.zun;

import com.meng.gameData.TouHou.SpellCard;
import com.meng.gameData.TouHou.TouhouCharacter;

public class TH15GameData {
	public static final String gameName = "东方绀珠传 ~ Legacy of Lunatic Kingdom.";
	public static final String gameNameCN = "东方绀珠传";
	public static final String gameNameAbbr = "LoLK";
	
	public static final String junko = "纯狐";
	
	public static String[] players = {"博丽灵梦", "雾雨魔理沙", "东风谷早苗", "铃仙·优昙华院·因幡"};
	public static TouhouCharacter[] charaName = {
		new TouhouCharacter("清兰", gameNameCN, "浅葱色的Eagle Rabbit"),
		new TouhouCharacter("铃瑚", gameNameCN, "橘色的Eagle Rabbit"),
		new TouhouCharacter("哆来咪·苏伊特", gameNameCN, "梦之支配者"),
		new TouhouCharacter("稀神探女", gameNameCN, "招来口舌之祸的女神"),
		new TouhouCharacter("克劳恩皮丝", gameNameCN, "地狱的妖精"),
		new TouhouCharacter(junko, gameNameCN, "(无)"),
		new TouhouCharacter("赫卡提亚·拉碧斯拉祖利", gameNameCN, "地狱的女神")
	};
	public static String[] musicName = {
		"宇宙巫女现身",
		"忘不了,那曾依藉的绿意",
		"兔已着陆",
		"湖上倒映着洁净的月光",
		"九月的南瓜",
		"飞翔于宇宙的不可思议巫女",
		"永远的春梦",
		"冻结的永远之都",
		"逆转的命运之轮",
		"遥遥38万公里的航程",
		"星条旗的小丑",
		"故乡之星倒映之海",
		"Pure Furies ~ 心之所在",
		"前所未见的噩梦世界",
		"Pandemonic Planet",
		"从神社所见之月",
		"宇宙巫女归还",
		"Player's Score"	
	};

	public static SpellCard[] spellcards = {
		new SpellCard("凶弹「高速撞击」", "清兰", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("弹符「射鹰」", "清兰", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("弹符「鹰已击中」", "清兰", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("铳符「月狂之枪」", "清兰", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("兔符「草莓团子」", "铃瑚", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("兔符「浆果浆果团子」", "铃瑚", SpellCard.Hard),
		new SpellCard("兔符「团子影响力」", "铃瑚", SpellCard.Lunatic),
		new SpellCard("月见「九月的满月」", "铃瑚", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("月见酒「月狂的九月」", "铃瑚", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("梦符「绯红色的噩梦」", "哆来咪·苏伊特", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("梦符「绯红色的压迫噩梦」", "哆来咪·苏伊特", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("梦符「蔚蓝色的愁梦」", "哆来咪·苏伊特", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("梦符「蔚蓝色的愁三重梦」", "哆来咪·苏伊特", SpellCard.Hard),
		new SpellCard("梦符「愁永远之梦」", "哆来咪·苏伊特", SpellCard.Lunatic),
		new SpellCard("梦符「刈安色的迷梦」", "哆来咪·苏伊特", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("梦符「刈安色的错综迷梦」", "哆来咪·苏伊特", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("梦符「捕梦网」", "哆来咪·苏伊特", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("梦符「苍蓝色的捕梦网」", "哆来咪·苏伊特", SpellCard.Hard),
		new SpellCard("梦符「梦我梦中」", "哆来咪·苏伊特", SpellCard.Lunatic),
		new SpellCard("月符「绀色的狂梦」", "哆来咪·苏伊特", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("玉符「乌合之咒」", "稀神探女", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("玉符「乌合的逆咒」", "稀神探女", SpellCard.Hard),
		new SpellCard("玉符「乌合的二重咒」", "稀神探女", SpellCard.Lunatic),
		new SpellCard("玉符「秽身探知型水雷」", "稀神探女", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("玉符「秽身探知型水雷 改」", "稀神探女", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("玉符「众神的弹冠」", "稀神探女", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("玉符「众神的光辉弹冠」", "稀神探女", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「孤翼的白鹭」", "稀神探女", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("狱符「地狱日食」", "克劳恩皮丝", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("狱符「地狱之蚀」", "克劳恩皮丝", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("狱符「闪光与条纹」", "克劳恩皮丝", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("狱符「星与条纹」", "克劳恩皮丝", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("狱炎「擦弹地狱火」", "克劳恩皮丝", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("狱炎「擦弹的狱意」", "克劳恩皮丝", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("地狱「条纹状的深渊」", "克劳恩皮丝", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「伪阿波罗」", "克劳恩皮丝", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("「阿波罗捏造说」", "克劳恩皮丝", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「掌上的纯光」", junko, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「杀意的百合」", junko, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「原始的神灵界」", junko, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("「现代的神灵界」", junko, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「战栗的寒冷之星」", junko, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「纯粹的疯狂」", junko, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「溢出的暇秽」", junko, 7),
		new SpellCard("「地上秽的纯化」", junko, SpellCard.Lunatic),
		new SpellCard("纯符「单纯的子弹地狱」", junko, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("纯符「纯粹的弹幕地狱」", junko, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("蝴蝶「取而代之的蝴蝶」", "哆来咪·苏伊特", SpellCard.Extra),
		new SpellCard("超特急「梦幻快车」", "哆来咪·苏伊特", SpellCard.Extra),
		new SpellCard("爬梦「爬行的子弹」", "哆来咪·苏伊特", SpellCard.Extra),
		new SpellCard("异界「逢魔之刻」", "赫卡提亚·拉碧斯拉祖利", SpellCard.Extra),
		new SpellCard("地球「邪秽在身」", "赫卡提亚·拉碧斯拉祖利", SpellCard.Extra),
		new SpellCard("月「阿波罗反射镜」", "赫卡提亚·拉碧斯拉祖利", SpellCard.Extra),
		new SpellCard("「用于杀人的纯粹弹幕」", junko, SpellCard.Extra),
		new SpellCard("异界「地狱的非理想弹幕」", "赫卡提亚·拉碧斯拉祖利", SpellCard.Extra),
		new SpellCard("地球「落向地狱的雨」", "赫卡提亚·拉碧斯拉祖利", SpellCard.Extra),
		new SpellCard("「用于逼死瓮中鼠的单纯弹幕」", junko, SpellCard.Extra),
		new SpellCard("月「月狂冲击」", "赫卡提亚·拉碧丝拉祖利", SpellCard.Extra),
		new SpellCard("「三位一体论狂想曲」", "赫卡提亚·拉碧丝拉祖利", SpellCard.Extra),
		new SpellCard("「最初与最后的无名弹幕」", "纯狐&赫卡提亚·拉碧斯拉祖利", SpellCard.Extra)
	};
}
