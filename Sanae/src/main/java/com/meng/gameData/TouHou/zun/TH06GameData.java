package com.meng.gameData.TouHou.zun;

import com.meng.gameData.TouHou.SpellCard;
import com.meng.gameData.TouHou.TouhouCharacter;

/**
 * @author: 司徒灵羽
 **/
public class TH06GameData {
	public static final String gameName = "东方红魔乡 ~ the Embodiment of Scarlet Devil.";
	public static final String gameNameCN = "东方红魔乡";
	public static final String gameNameAbbr = "EoSD";
	public static String[] players = {"博丽灵梦 灵符", "博丽灵梦 梦符", "雾雨魔理沙 魔符", "雾雨魔理沙 恋符"};
	public static TouhouCharacter[] charaName = {
		new TouhouCharacter("露米娅", gameNameCN, "宵暗的妖怪"),
		new TouhouCharacter("大妖精", gameNameCN, "(无)"),
		new TouhouCharacter("琪露诺", gameNameCN, "湖上的冰精"),
		new TouhouCharacter("红美铃", gameNameCN, "华人小姑娘"),
		new TouhouCharacter("帕秋莉·诺蕾姬", gameNameCN, "知识与避世的少女"),
		new TouhouCharacter("十六夜咲夜", gameNameCN, "红魔馆的女仆"),
		new TouhouCharacter("蕾米莉亚·斯卡雷特", gameNameCN, "永远幼小的红月"),
		new TouhouCharacter("芙兰朵露·斯卡雷特", gameNameCN, "恶魔之妹")
	};
	public static String[] musicName = {
		"比赤色更红的梦",
		"如鬼灯般的红色之魂",
		"妖魔夜行",
		"Lunate Elf",
		"活泼的纯情小姑娘",
		"上海红茶馆 ~ Chinese Tea",
		"明治十七年的上海爱丽丝",
		"巴瓦鲁魔法图书馆",
		"Locked Girl ~ 少女密室",
		"女仆与血之怀表",
		"月时计 ~ Luna Dial",
		"特佩斯的年幼末裔",
		"献给已逝公主的七重奏",
		"魔法少女们的百年祭",
		"U.N.OWEN就是她吗？",
		"比红色更虚无的永远",
		"红楼 ~ Eastern Dream...",
	};
	public static SpellCard[] spellcards = {
		new SpellCard("月符「月光」", "露米娅", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("夜符「夜雀」", "露米娅", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("暗符「境界线」", "露米娅", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("冰符「冰瀑」", "琪露诺", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("雹符「冰雹暴风」", "琪露诺", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("冻符「完美冻结」", "琪露诺", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("雪符「钻石风暴」", "琪露诺", SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("华符「芳华绚烂」", "红美铃", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("华符「Selaginella 9」", "红美铃", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("虹符「彩虹风铃」", "红美铃", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("幻符「华想梦葛」", "红美铃", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("彩符「彩雨」", "红美铃", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("彩符「彩光乱舞」", "红美铃", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("彩符「极彩台风」", "红美铃", SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("火符「火神之光」", "帕秋莉·诺蕾姬", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("水符「水精公主」", "帕秋莉·诺蕾姬", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("木符「风灵的角笛」", "帕秋莉·诺蕾姬", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("土符「慵懒三石塔」", "帕秋莉·诺蕾姬", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("金符「金属疲劳」", "帕秋莉·诺蕾姬", SpellCard.Normal),
		new SpellCard("火符「火神之光 上级」", "帕秋莉·诺蕾姬", SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("木符「风灵的角笛 上级」", "帕秋莉·诺蕾姬", SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("土符「慵懒三石塔 上级」", "帕秋莉·诺蕾姬", SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("水符「湖葬」", "帕秋莉·诺蕾姬", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("木符「翠绿风暴」", "帕秋莉·诺蕾姬", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("土符「三石塔的震动」", "帕秋莉·诺蕾姬", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("金符「银龙」", "帕秋莉·诺蕾姬", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("火&土符「环状熔岩带」", "帕秋莉·诺蕾姬", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("木&火符「森林大火」", "帕秋莉·诺蕾姬", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("水&木符「水精灵」", "帕秋莉·诺蕾姬", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("金&水符「水银之毒」", "帕秋莉·诺蕾姬", SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("土&金符「翡翠巨石」", "帕秋莉·诺蕾姬", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("奇术「误导」", "十六夜咲夜", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("奇术「幻惑误导」", "十六夜咲夜", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("幻在「钟表的残骸」", "十六夜咲夜", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("幻幽「迷幻杰克」", "十六夜咲夜", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("幻象「月神之钟」", "十六夜咲夜", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("幻世「世界」", "十六夜咲夜", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("女仆秘技「操弄玩偶」", "十六夜咲夜", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("女仆秘技「杀人玩偶」", "十六夜咲夜", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("奇术「永恒的温柔」", "十六夜咲夜", SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("天罚「大卫之星」", "蕾米莉亚·斯卡雷特", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神罚「年幼的恶魔之王」", "蕾米莉亚·斯卡雷特", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("冥符「红色的冥界」", "蕾米莉亚·斯卡雷特", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("狱符「千根针的针山」", "蕾米莉亚·斯卡雷特", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("诅咒「弗拉德·特佩斯的诅咒」", "蕾米莉亚·斯卡雷特", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神术「吸血鬼幻想」", "蕾米莉亚·斯卡雷特", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("红符「绯红射击」", "蕾米莉亚·斯卡雷特", SpellCard.Easy | SpellCard.Normal),
		new SpellCard("红符「绯红之主」", "蕾米莉亚·斯卡雷特", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「Red Magic」", "蕾米莉亚·斯卡雷特", SpellCard.Normal),
		new SpellCard("「红色的幻想乡」", "蕾米莉亚·斯卡雷特", SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("月符「静息的月神」", "帕秋莉·诺蕾姬", SpellCard.Extra),
		new SpellCard("日符「皇家烈焰」", "帕秋莉·诺蕾姬", SpellCard.Extra),
		new SpellCard("火水木金土符「贤者之石」", "帕秋莉·诺蕾姬", SpellCard.Extra),
		new SpellCard("禁忌「红莓陷阱」", "芙兰朵露·斯卡雷特", SpellCard.Extra),
		new SpellCard("禁忌「莱瓦汀」", "芙兰朵露·斯卡雷特", SpellCard.Extra),
		new SpellCard("禁忌「四重存在」", "芙兰朵露·斯卡雷特", SpellCard.Extra),
		new SpellCard("禁忌「笼中鸟」", "芙兰朵露·斯卡雷特", SpellCard.Extra),
		new SpellCard("禁忌「恋之迷宫」", "芙兰朵露·斯卡雷特", SpellCard.Extra),
		new SpellCard("禁弹「星弧破碎」", "芙兰朵露·斯卡雷特", SpellCard.Extra),
		new SpellCard("禁弹「折反射」", "芙兰朵露·斯卡雷特", SpellCard.Extra),
		new SpellCard("禁弹「刻着过去的钟表」", "芙兰朵露·斯卡雷特", SpellCard.Extra),
		new SpellCard("秘弹「之后就一个人都没有了吗？」", "芙兰朵露·斯卡雷特", SpellCard.Extra),
		new SpellCard("QED「495年的波纹」", "芙兰朵露·斯卡雷特", SpellCard.Extra)
	};
}
