package com.meng.gameData.TouHou.zun;

import com.meng.gameData.TouHou.SpellCard;
import com.meng.gameData.TouHou.TouhouCharacter;

/**
 * @author: 司徒灵羽
 **/
public class TH08GameData {
	public static final String gameName = "东方永夜抄 ~ Imperishable Night.";
	public static final String gameNameCN = "东方永夜抄";
	public static final String gameNameAbbr = "IN";
	
	public static String[] players = {"幻想的结界组", "咏唱禁咒组", "梦幻的红魔组", "幽冥之住人组", "博丽灵梦", "八云紫", "雾雨魔理沙", "爱丽丝·玛格特罗依德", "蕾米莉亚·斯卡蕾特", "十六夜咲夜", "西行寺幽幽子", "魂魄妖梦"};

	public static final String WriggleNightbug = "莉格露·奈特巴格";
	public static final String MystiaLorelei = "米斯蒂娅·萝蕾拉";
	public static final String KeineKamishirasawa = "上白泽慧音";
	public static final String HakureiReimu = "博丽灵梦";
	public static final String KirisameMarisa = "雾雨魔理沙";
	public static final String TewiInaba = "因幡帝";
	public static final String ReisenUdongeinInaba = "铃仙· 优昙华院·因幡";
	public static final String EirinYagokoro = "八意永琳";
	public static final String HouraisanKaguya = "蓬莱山辉夜";
	public static final String HuziwaraNoMokou = "藤原妹红";

	public static TouhouCharacter[] charaName = {
		new TouhouCharacter(WriggleNightbug, gameNameCN, "暗中蠢动的光虫"),
		new TouhouCharacter(MystiaLorelei, gameNameCN, "夜雀妖怪"),
		new TouhouCharacter(KeineKamishirasawa, gameNameCN, "知识与历史的半兽"),
		new TouhouCharacter(HakureiReimu, gameNameCN, "乐园的可爱巫女"),
		new TouhouCharacter(KirisameMarisa, gameNameCN, "普通的黑魔术少女"),
		new TouhouCharacter(TewiInaba, gameNameCN),
		new TouhouCharacter(ReisenUdongeinInaba, gameNameCN, "疯狂的月兔"),
		new TouhouCharacter(EirinYagokoro, gameNameCN, "月之头脑"),
		new TouhouCharacter(HouraisanKaguya, gameNameCN, "永远和须臾的罪人"),
		new TouhouCharacter(HuziwaraNoMokou, gameNameCN, "蓬莱的人形")
	};
	public static String[] musicName = {
		"永夜抄 ~ Eastern Night.",
		"幻视之夜 ~ Ghostly Eyes",
		"蠢蠢的秋月 ~ Mooned Insect",
		"夜雀的歌声 ~ Night Bird",
		"已经只能听见歌声了",
		"令人怀念的东方之血 ~ Old World",
		"Plain Asia",
		"永夜的报应 ~ Imperishable Night",
		"少女绮想曲 ~ Dream Battle",
		"恋色Master spark",
		"灰姑娘的笼子 ~ Kagome-Kagome",
		"狂气之瞳 ~ Invisible Full Moon",
		"旅人1969",
		"千年幻想乡 ~ History of the Moon",
		"竹取飞翔 ~ Lunatic Princess",
		"旅人1970",
		"Extend Ash ~ 蓬莱人",
		"飘上月球，不死之烟",
		"月见草",
		"Eternal Dream ~ 幽玄的枫树",
		"东方妖怪小町"	
	};
	public static SpellCard[] spellcards = {
		new SpellCard("萤符「地上的流星」", WriggleNightbug, SpellCard.Hard),
		new SpellCard("萤符「地上的彗星」", WriggleNightbug, SpellCard.Lunatic),
		new SpellCard("灯符「萤光现象」", WriggleNightbug, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("蠢符「小虫」", WriggleNightbug, SpellCard.Easy),
		new SpellCard("蠢符「小虫风暴」", WriggleNightbug, SpellCard.Normal),
		new SpellCard("蠢符「夜虫风暴」", WriggleNightbug, SpellCard.Hard),
		new SpellCard("蠢符「夜虫龙卷」", WriggleNightbug, SpellCard.Lunatic),
		new SpellCard("隐虫「永夜蛰居」", WriggleNightbug, SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("声符「枭的夜鸣声」", MystiaLorelei, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("声符「木菟的咆哮」", MystiaLorelei, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("蛾符「天蛾的蛊道」", MystiaLorelei, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("毒符「毒蛾的鳞粉」", MystiaLorelei, SpellCard.Hard),
		new SpellCard("猛毒「毒蛾的黑暗演舞」", MystiaLorelei, SpellCard.Lunatic),
		new SpellCard("鹰符「祸延疾冲」", MystiaLorelei, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("夜盲「夜雀之歌」", MystiaLorelei, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("夜雀「午夜的合唱指挥」", MystiaLorelei, SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("产灵「最初的金字塔」", KeineKamishirasawa, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("始符「短暂的137」", KeineKamishirasawa, SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("野符「武烈的危机」", KeineKamishirasawa, SpellCard.Easy),
		new SpellCard("野符「将门的危机」", KeineKamishirasawa, SpellCard.Normal),
		new SpellCard("野符「义满的危机」", KeineKamishirasawa, SpellCard.Hard),
		new SpellCard("野符「GHQ的危机」", KeineKamishirasawa, SpellCard.Lunatic),
		new SpellCard("国符「三种神器 剑」", KeineKamishirasawa, SpellCard.Easy),
		new SpellCard("国符「三种神器 玉」", KeineKamishirasawa, SpellCard.Normal),
		new SpellCard("国符「三种神器 镜」", KeineKamishirasawa, SpellCard.Hard),
		new SpellCard("国体「三种神器 乡」", KeineKamishirasawa, SpellCard.Lunatic),
		new SpellCard("终符「幻想天皇」", KeineKamishirasawa, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("虚史「幻想乡传说」", KeineKamishirasawa, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("未来「高天原」", KeineKamishirasawa, SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("梦符「二重结界」", HakureiReimu, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("梦境「二重大结界」", HakureiReimu, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("灵符「梦想封印 散」", HakureiReimu, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("散灵「梦想封印 寂」", HakureiReimu, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("梦符「封魔阵」", HakureiReimu, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神技「八方鬼缚阵」", HakureiReimu, SpellCard.Hard),
		new SpellCard("神技「八方龙杀阵」", HakureiReimu, SpellCard.Lunatic),
		new SpellCard("灵符「梦想封印 集」", HakureiReimu, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("回灵「梦想封印 侘」", HakureiReimu, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("境界「二重弹幕结界」", HakureiReimu, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("大结界「博丽弹幕结界」", HakureiReimu, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("神灵「梦想封印 瞬」", HakureiReimu, SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("魔符「银河」", KirisameMarisa, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("魔空「小行星带」", KirisameMarisa, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("魔符「星尘幻想」", KirisameMarisa, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("黑魔「黑洞边缘」", KirisameMarisa, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("恋符「非定向光线」", KirisameMarisa, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("恋风「星光台风」", KirisameMarisa, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("恋符「极限火花」", KirisameMarisa, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("恋心「二重火花」", KirisameMarisa, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("光符「地球光」", KirisameMarisa, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("光击「击月」", KirisameMarisa, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("魔炮「Final Spark」", KirisameMarisa, SpellCard.Normal | SpellCard.LastSpell),
		new SpellCard("魔炮「Final Master Spark」", KirisameMarisa, SpellCard.Hard | SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("波符「赤眼催眠(Mind Shaker)」", ReisenUdongeinInaba, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("幻波「赤眼催眠(Mind Blowing)」", ReisenUdongeinInaba, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("狂符「幻视调律(Visionary Tuning)」", ReisenUdongeinInaba, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("狂视「狂视调律(Illusion Seeker)」", ReisenUdongeinInaba, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("懒符「生神停止(Idling Wave)」", ReisenUdongeinInaba, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("懒惰「生神停止(Mind Stopper)」", ReisenUdongeinInaba, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("散符「真实之月(Invisible Full Moon)」", ReisenUdongeinInaba, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("月眼「月兔远隔催眠术(Tele-Mesmerism)」", ReisenUdongeinInaba, SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("天丸「壶中的天地」", EirinYagokoro, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("觉神「神代的记忆」", EirinYagokoro, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神符「天人的族谱」", EirinYagokoro, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("苏活「生命游戏 -Life Game-」", EirinYagokoro, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("苏生「Rising Game」", EirinYagokoro, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("操神「思兼装置」", EirinYagokoro, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神脑「思兼的头脑」", EirinYagokoro, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("天咒「阿波罗13」", EirinYagokoro, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("秘术「天文密葬法」", EirinYagokoro, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("禁药「蓬莱之药」", EirinYagokoro, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("药符「壶中的大银河」", EirinYagokoro, SpellCard.Easy | SpellCard.Normal | SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("难题「龙颈之玉 -五色的弹丸-」", HouraisanKaguya, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神宝「耀眼的龙玉」", HouraisanKaguya, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("难题「佛御石之钵 -不碎的意志-」", HouraisanKaguya, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神宝「佛体的金刚石」", HouraisanKaguya, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("难题「火鼠的皮衣 -不焦躁的内心-」", HouraisanKaguya, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神宝「火蜥蜴之盾」", HouraisanKaguya, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("难题「燕的子安贝 -永命线-」", HouraisanKaguya, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神宝「无限的生命之泉」", HouraisanKaguya, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("难题「蓬莱的弹枝 -七色的弹幕-」", HouraisanKaguya, SpellCard.Easy | SpellCard.Normal),
		new SpellCard("神宝「蓬莱的玉枝 -梦色之乡-」", HouraisanKaguya, SpellCard.Hard | SpellCard.Lunatic),
		new SpellCard("「永夜返 -初月-」", HouraisanKaguya, SpellCard.Easy | SpellCard.LastSpell),
		new SpellCard("「永夜返 -新月-」", HouraisanKaguya, SpellCard.Normal | SpellCard.LastSpell),
		new SpellCard("「永夜返 -上弦月-」", HouraisanKaguya, SpellCard.Hard | SpellCard.LastSpell),
		new SpellCard("「永夜返 -待宵-」", HouraisanKaguya, SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("「永夜返 -子之刻-」", HouraisanKaguya, SpellCard.Easy | SpellCard.LastSpell),
		new SpellCard("「永夜返 -子时二刻-」", HouraisanKaguya, SpellCard.Normal | SpellCard.LastSpell),
		new SpellCard("「永夜返 -子时三刻-」", HouraisanKaguya, SpellCard.Hard | SpellCard.LastSpell),
		new SpellCard("「永夜返 -子时四刻-」", HouraisanKaguya, SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("「永夜返 -丑之刻-」", HouraisanKaguya, SpellCard.Easy | SpellCard.LastSpell),
		new SpellCard("「永夜返 -丑时二刻-」", HouraisanKaguya, SpellCard.Normal | SpellCard.LastSpell),
		new SpellCard("「永夜返 -丑时三刻-」", HouraisanKaguya, SpellCard.Hard | SpellCard.LastSpell),
		new SpellCard("「永夜返 -丑时四刻-」", HouraisanKaguya, SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("「永夜返 -寅之刻-」", HouraisanKaguya, SpellCard.Easy | SpellCard.LastSpell),
		new SpellCard("「永夜返 -寅时二刻-」", HouraisanKaguya, SpellCard.Normal | SpellCard.LastSpell),
		new SpellCard("「永夜返 -寅时三刻-」", HouraisanKaguya, SpellCard.Hard | SpellCard.LastSpell),
		new SpellCard("「永夜返 -寅时四刻-」", HouraisanKaguya, SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("「永夜返 -朝霭-」", HouraisanKaguya, SpellCard.Easy | SpellCard.LastSpell),
		new SpellCard("「永夜返 -拂晓-」", HouraisanKaguya, SpellCard.Normal | SpellCard.LastSpell),
		new SpellCard("「永夜返 -耀世的启明星-」", HouraisanKaguya, SpellCard.Hard | SpellCard.LastSpell),
		new SpellCard("「永夜返 -光明之世-」", HouraisanKaguya, SpellCard.Lunatic | SpellCard.LastSpell),
		new SpellCard("旧史「旧秘境史 -古代史-」", KeineKamishirasawa, SpellCard.Extra),
		new SpellCard("转世「一条归桥」", KeineKamishirasawa, SpellCard.Extra),
		new SpellCard("新史「新幻想史 -现代史-」", KeineKamishirasawa, SpellCard.Extra),
		new SpellCard("时效「月岩笠的诅咒」", HuziwaraNoMokou, SpellCard.Extra),
		new SpellCard("不死「火鸟 -凤翼天翔-」", HuziwaraNoMokou, SpellCard.Extra),
		new SpellCard("藤原「灭罪寺院伤」", HuziwaraNoMokou, SpellCard.Extra),
		new SpellCard("不死「徐福时空」", HuziwaraNoMokou, SpellCard.Extra),
		new SpellCard("灭罪「正直者之死」", HuziwaraNoMokou, SpellCard.Extra),
		new SpellCard("虚人「无一」", HuziwaraNoMokou, SpellCard.Extra),
		new SpellCard("不灭「不死鸟之尾」", HuziwaraNoMokou, SpellCard.Extra),
		new SpellCard("蓬莱「凯风快晴 -Fujiyama Volcano-」", HuziwaraNoMokou, SpellCard.Extra),
		new SpellCard("「不死鸟附体」", HuziwaraNoMokou, SpellCard.Extra),
		new SpellCard("「蓬莱人形」", HuziwaraNoMokou, SpellCard.Extra),
		new SpellCard("「不朽的弹幕」", HuziwaraNoMokou, SpellCard.Extra | SpellCard.LastSpell),
		new SpellCard("「不合时令的蝶雨」", WriggleNightbug, SpellCard.LastWord),
		new SpellCard("「失明的夜雀」", MystiaLorelei, SpellCard.LastWord),
		new SpellCard("「日出之国的天子」", KeineKamishirasawa, SpellCard.LastWord),
		new SpellCard("「似有似无的净化」", KeineKamishirasawa, SpellCard.LastWord),
		new SpellCard("「Ancient Duper」", TewiInaba, SpellCard.LastWord),
		new SpellCard("「幻胧月睨（Lunatic Red Eyes）」", ReisenUdongeinInaba, SpellCard.LastWord),
		new SpellCard("「天网蛛网捕蝶之法」", EirinYagokoro, SpellCard.LastWord),
		new SpellCard("「蓬莱的树海」", HouraisanKaguya, SpellCard.LastWord),
		new SpellCard("「不死鸟重生」", HuziwaraNoMokou, SpellCard.LastWord),
		new SpellCard("「梦想天生」", HakureiReimu, SpellCard.LastWord),
		new SpellCard("「Blazing Star」", KirisameMarisa, SpellCard.LastWord),
		new SpellCard("「收缩的世界」", "十六夜咲夜", SpellCard.LastWord),
		new SpellCard("「待宵反射卫星斩」", "魂魄妖梦", SpellCard.LastWord),
		new SpellCard("「猎奇剧团的怪人」", "爱丽丝·玛格特罗依德", SpellCard.LastWord),
		new SpellCard("「绯红的宿命」", "蕾米莉亚·斯卡雷特", SpellCard.LastWord),
		new SpellCard("「西行寺无余涅槃」", "西行寺幽幽子", SpellCard.LastWord),
		new SpellCard("「深弹幕结界 梦幻泡影」", "八云紫", SpellCard.LastWord)
	};
}
