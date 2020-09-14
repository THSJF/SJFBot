package com.meng.game.TouHou.zun;

import com.meng.game.TouHou.*;

public class TH13GameData {
	public static final String gameName="东方神灵庙 ~ Ten Desires.";
	public static final String gameNameCN="东方神灵庙";
	public static final String gameNameAbbr="TD";
	public static String[] players = new String[]{"博丽灵梦", "雾雨魔理沙", "东风谷早苗", "魂魄妖梦"};
	public static TouhouCharacter[] charaName=new TouhouCharacter[]{
		new TouhouCharacter("西行寺幽幽子", gameNameCN, "心无彷徨的亡灵"),
		new TouhouCharacter("幽谷响子", gameNameCN, "诵经的山彦"),
		new TouhouCharacter("多多良小伞", gameNameCN, "为难的遗忘之物"),
		new TouhouCharacter("宫古芳香", gameNameCN, "忠诚的尸体"),
		new TouhouCharacter("霍青娥", gameNameCN, "穿墙的邪仙"),
		new TouhouCharacter("苏我屠自古", gameNameCN, "神明后裔的亡灵"),
		new TouhouCharacter("物部布都", gameNameCN, "古代日本的尸解仙"),
		new TouhouCharacter("丰聪耳神子", gameNameCN, "圣德道士"),
		new TouhouCharacter("封兽鵺", gameNameCN, "古代妖怪之一"),
		new TouhouCharacter("二岩瑞藏", gameNameCN, "狸猫怪 十变化")
	};
	public static String[] musicName=new String[]{
		"欲望深重的灵魂",
		"死灵的夜樱",
		"Ghost Lead",
		"欢迎来到妖怪寺",
		"门前的妖怪小姑娘",
		"在美妙的墓地里住下吧",
		"Rigid Paradise",
		"Desire Drive",
		"古老的元神",
		"梦殿大祀庙",
		"大神神话传",
		"小小欲望的星空",
		"圣德传说 ~ True Administrator",
		"后院的妖怪参拜道",
		"佐渡的二岩",
		"神社的新风",
		"Desire Dream",
		"Player's Score"	
	};
	public static SpellCard[] spellcards=new SpellCard[]{
		new SpellCard("符牒「死蝶之舞」", "西行寺幽幽子", SpellCard.E | SpellCard.N),
		new SpellCard("符牒「死蝶之舞 -樱花-」", "西行寺幽幽子", SpellCard.H | SpellCard.L),
		new SpellCard("幽蝶「幽魂聚地」", "西行寺幽幽子", SpellCard.E | SpellCard.N),
		new SpellCard("幽蝶「幽魂聚地 -樱花-」", "西行寺幽幽子", SpellCard.H | SpellCard.L),
		new SpellCard("冥符「常夜樱」", "西行寺幽幽子", SpellCard.E | SpellCard.N | SpellCard.H | SpellCard.L),
		new SpellCard("樱符「西行樱吹雪」", "西行寺幽幽子", SpellCard.H | SpellCard.L),
		new SpellCard("樱符「樱吹雪地狱」", "西行寺幽幽子", SpellCard.O),
		new SpellCard("响符「山谷回声」", "幽谷响子", SpellCard.E | SpellCard.N),
		new SpellCard("响符「混乱的山谷回声」", "幽谷响子", SpellCard.H | SpellCard.L),
		new SpellCard("响符「强力共振」", "幽谷响子", SpellCard.E | SpellCard.N | SpellCard.H | SpellCard.L),
		new SpellCard("山彦「远距离回声」", "幽谷响子", SpellCard.E | SpellCard.N),
		new SpellCard("山彦「扩大回声」", "幽谷响子", SpellCard.H | SpellCard.L),
		new SpellCard("大声「激动的呼喊」", "幽谷响子", SpellCard.E | SpellCard.N),
		new SpellCard("大声「激动Yahoo」", "幽谷响子", SpellCard.H | SpellCard.L),
		new SpellCard("山彦「山彦的发挥本领之回音」", "幽谷响子", SpellCard.O),
		new SpellCard("虹符「雨伞风暴」", "多多良小伞", SpellCard.H | SpellCard.L),
		new SpellCard("回复「借由欲望的恢复」", "宫古芳香", SpellCard.E | SpellCard.N | SpellCard.H | SpellCard.L),
		new SpellCard("毒爪「剧毒抹消」", "宫古芳香", SpellCard.E | SpellCard.N),
		new SpellCard("毒爪「剧毒杀害」", "宫古芳香", SpellCard.H | SpellCard.L),
		new SpellCard("欲符「赚钱欲灵招来」", "宫古芳香", SpellCard.E | SpellCard.N),
		new SpellCard("欲灵「贪分欲吞噬者」", "宫古芳香", SpellCard.H | SpellCard.L),
		new SpellCard("毒爪「不死的杀人鬼」", "宫古芳香", SpellCard.O),
		new SpellCard("邪符「养小鬼」", "霍青娥", 2),
		new SpellCard("邪符「孤魂野鬼」", "霍青娥", SpellCard.H | SpellCard.L),
		new SpellCard("入魔「走火入魔」", "霍青娥", SpellCard.E | SpellCard.N | SpellCard.H | SpellCard.L),
		new SpellCard("降灵「死人童乩」", "霍青娥", SpellCard.E | SpellCard.N),
		new SpellCard("通灵「通灵芳香」", "霍青娥", SpellCard.H | SpellCard.L),
		new SpellCard("道符「道胎动」", "霍青娥", SpellCard.E | SpellCard.N | SpellCard.H | SpellCard.L),
		new SpellCard("道符「TAO胎动 ~道~」", "霍青娥", SpellCard.O),
		new SpellCard("雷矢「元兴寺的旋风」", "苏我屠自古", SpellCard.N | SpellCard.H),
		new SpellCard("雷矢「元兴寺的龙卷」", "苏我屠自古", SpellCard.L),
		new SpellCard("怨灵「入鹿之雷」", "苏我屠自古", SpellCard.O),
		new SpellCard("天符「雨之磐舟」", "物部布都", SpellCard.E | SpellCard.N),
		new SpellCard("天符「天之磐舟哟，向天飞升吧」", "物部布都", SpellCard.H | SpellCard.L),
		new SpellCard("投皿「物部氏的八十平瓮」", "物部布都", SpellCard.E | SpellCard.N | SpellCard.H | SpellCard.L),
		new SpellCard("炎符「废佛之炎风」", "物部布都", SpellCard.E | SpellCard.N),
		new SpellCard("炎符「火烧樱井寺」", "物部布都", SpellCard.H | SpellCard.L),
		new SpellCard("圣童女「大物忌正餐」", "物部布都", SpellCard.E | SpellCard.N | SpellCard.H | SpellCard.L),
		new SpellCard("圣童女「太阳神的贡品」", "物部布都", SpellCard.O),
		new SpellCard("名誉「十二阶之色彩」", "丰聪耳神子", SpellCard.E | SpellCard.N),
		new SpellCard("名誉「十二阶之冠位」", "丰聪耳神子", SpellCard.H | SpellCard.L),
		new SpellCard("仙符「日出之处的道士」", "丰聪耳神子", SpellCard.E | SpellCard.N),
		new SpellCard("仙符「日出之处的天子」", "丰聪耳神子", SpellCard.H | SpellCard.L),
		new SpellCard("召唤「豪族乱舞」", "丰聪耳神子", SpellCard.E | SpellCard.N | SpellCard.H | SpellCard.L),
		new SpellCard("秘宝「斑鸠寺的天球仪」", "丰聪耳神子", SpellCard.E | SpellCard.N | SpellCard.H),
		new SpellCard("秘宝「圣德太子的欧帕兹」", "丰聪耳神子", SpellCard.L),
		new SpellCard("光符「救世观音的佛光」", "丰聪耳神子", SpellCard.E | SpellCard.N),
		new SpellCard("光符「救世之光」", "丰聪耳神子", SpellCard.H | SpellCard.L),
		new SpellCard("眼光「十七条的光芒」", "丰聪耳神子", SpellCard.E | SpellCard.N),
		new SpellCard("神光「无忤为宗」", "丰聪耳神子", SpellCard.H | SpellCard.L),
		new SpellCard("「星辰降落的神灵庙」", "丰聪耳神子", SpellCard.E | SpellCard.N),
		new SpellCard("「新生的神灵」", "丰聪耳神子", SpellCard.H | SpellCard.L),
		new SpellCard("「神灵大宇宙」", "丰聪耳神子", SpellCard.O),
		new SpellCard("未知「轨道不明的鬼火」", "封兽鵺", SpellCard.Ex),
		new SpellCard("未知「姿态不明的空鱼」", "封兽鵺", SpellCard.Ex),
		new SpellCard("未知「原理不明的妖怪玉」", "封兽鵺", SpellCard.Ex),
		new SpellCard("一回胜负「灵长类弹幕变化」", "二岩瑞藏", SpellCard.Ex),
		new SpellCard("二回胜负「肉食类弹幕变化」", "二岩瑞藏", SpellCard.Ex),
		new SpellCard("三回胜负「羽鸟类弹幕变化」", "二岩瑞藏", SpellCard.Ex),
		new SpellCard("四回胜负「两栖类弹幕变化」", "二岩瑞藏", SpellCard.Ex),
		new SpellCard("五回胜负「鸟兽戏画」", "二岩瑞藏", SpellCard.Ex),
		new SpellCard("六回胜负「狸猫的变身学校」", "二岩瑞藏", SpellCard.Ex),
		new SpellCard("七回胜负「野生的离岛」", "二岩瑞藏", SpellCard.Ex),
		new SpellCard("变化「魔奴化巫女的伪退治」", "二岩瑞藏", SpellCard.Ex),
		new SpellCard("「猯藏化弹幕十变化」", "二岩瑞藏", SpellCard.Ex),
		new SpellCard("貉符「满月下的腹鼓舞」", "二岩瑞藏", SpellCard.Ex),
		new SpellCard("「Wild Carpet」", "二岩瑞藏", SpellCard.O)
	};
}
