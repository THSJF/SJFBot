package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class TH11Data extends THGameData {

    private static TH11Data instance;

    public static TH11Data getInstance() {
        if (instance == null) {
            instance = new TH11Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方地灵殿";
    }

    @Override
    public String getNameEng() {
        return "Subterranean Animism";
    }

    @Override
    public String getNameAbbr() {
        return "SA";
    }

    private TH11Data() {
        charas = new THCharacter[]{
            new THCharacter("琪斯美", this),
            new THCharacter("黑谷山女", this),
            new THCharacter("水桥帕露西", this),
            new THCharacter("星熊勇仪", this),
            new THCharacter("古明地觉", this),
            new THCharacter("火焰猫燐", this),
            new THCharacter("灵乌路空", this),
            new THCharacter("东风谷早苗", this),
            new THCharacter("古明地恋", this)
        };
        THCharacter chara_琪斯美 = getCharacter("琪斯美");
        THCharacter chara_黑谷山女 = getCharacter("黑谷山女");
        THCharacter chara_水桥帕露西 = getCharacter("水桥帕露西");
        THCharacter chara_星熊勇仪 = getCharacter("星熊勇仪");
        THCharacter chara_古明地觉 = getCharacter("古明地觉");
        THCharacter chara_火焰猫燐 = getCharacter("火焰猫燐");
        THCharacter chara_灵乌路空 = getCharacter("灵乌路空");
        THCharacter chara_东风谷早苗 = getCharacter("东风谷早苗");
        THCharacter chara_古明地恋 = getCharacter("古明地恋");

        spells = new THSpell[]{
            new THSpell(this, chara_琪斯美, "怪奇「钓瓶落之怪」", "怪奇「釣瓶落としの怪」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_黑谷山女, "罠符「Capture Web」（捕捉之网）", "罠符「キャプチャーウェブ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_黑谷山女, "蜘蛛「石窟的蜘蛛巢」", "蜘蛛「石窟の蜘蛛の巣」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_黑谷山女, "瘴符「Filled Miasma」（瘴气充溢）", "瘴符「フィルドミアズマ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_黑谷山女, "瘴气「原因不明的热病」", "瘴気「原因不明の熱病」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_水桥帕露西, "妒符「Green-Eyed Monster」（绿眼怪兽）", "妬符「グリーンアイドモンスター」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_水桥帕露西, "嫉妒「看不见的绿眼怪兽」", "嫉妬「緑色の目をした見えない怪物」　", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_水桥帕露西, "开花爷爷「对华丽的仁者之嫉妒」", "花咲爺「華やかなる仁者への嫉妬」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_水桥帕露西, "开花爷爷「小白的灰烬」", "花咲爺「シロの灰」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_水桥帕露西, "剪舌麻雀「对谦虚的富者之记恨」", "舌切雀「謙虚なる富者への片恨」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_水桥帕露西, "剪舌麻雀「大葛笼与小葛笼」", "舌切雀「大きな葛籠と小さな葛籠」　", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_水桥帕露西, "恨符「丑时参拜」", "恨符「丑の刻参り」　", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_水桥帕露西, "恨符「丑时参拜第七日」", "恨符「丑の刻参り七日目」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_星熊勇仪, "鬼符「怪力乱神」", "鬼符「怪力乱神」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_星熊勇仪, "怪轮「地狱之苦轮」", "怪輪「地獄の苦輪」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_星熊勇仪, "枷符「罪人不释之枷」", "枷符「咎人の外さぬ枷」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_星熊勇仪, "力业「大江山岚」", "力業「大江山嵐」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_星熊勇仪, "力业「大江山颪」", "力業「大江山颪」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_星熊勇仪, "四天王奥义「三步必杀」", "四天王奥義「三歩必殺」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「Terrible Souvenir」（恐怖的回忆）", "想起「テリブルスーヴニール」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_古明地觉, "回忆「恐怖催眠术」", "想起「恐怖催眠術」　", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「二重黑死蝶」", "想起「二重黒死蝶」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「飞行虫之巢」", "想起「飛行虫ネスト」　", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「波与粒的境界」", "想起「波と粒の境界」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「户隐山之投」", "想起「戸隠山投げ」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「百万鬼夜行」", "想起「百万鬼夜行」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「蒙蒙迷雾」", "想起「濛々迷霧」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「风神木叶隐身术」", "想起「風神木の葉隠れ」　", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「天狗巨暴流」", "想起「天狗のマクロバースト」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「鸟居旋风」", "想起「鳥居つむじ風」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「春之京都人偶」", "想起「春の京人形」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「Straw Doll Kamikaze」（稻草人偶敢死队）", "想起「ストロードールカミカゼ」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「Return Inanimateness」（回归虚无）", "想起「リターンイナニメトネス」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「Mercury Poison」（水银之毒）", "想起「マーキュリポイズン」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「Princess Undine」（水精公主）", "想起「プリンセスウンディネ」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「贤者之石」", "想起「賢者の石」　", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「延展手臂」", "想起「のびーるアーム」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「河童之河口浪潮」", "想起「河童のポロロッカ」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古明地觉, "回忆「粼粼水底之心伤」", "想起「光り輝く水底のトラウマ」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_火焰猫燐, "猫符「Cat's Walk」", "猫符「キャッツウォーク」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_火焰猫燐, "猫符「怨灵猫乱步」", "猫符「怨霊猫乱歩」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_火焰猫燐, "咒精「Zombie Fairy」（僵尸妖精）", "呪精「ゾンビフェアリー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_火焰猫燐, "咒精「怨灵凭依妖精」", "呪精「怨霊憑依妖精」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_火焰猫燐, "恨灵「Spleen Eater」（脾脏蛀食者）", "恨霊「スプリーンイーター」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_火焰猫燐, "尸灵「食人怨灵」", "屍霊「食人怨霊」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_火焰猫燐, "赎罪「旧地狱的针山」", "贖罪「旧地獄の針山」　", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_火焰猫燐, "赎罪「古时之针与痛楚的怨灵」", "贖罪「昔時の針と痛がる怨霊」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_火焰猫燐, "「死灰复燃」", "「死灰復燃」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_火焰猫燐, "「小恶灵复活」", "「小悪霊復活せし」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_火焰猫燐, "妖怪「火焰的车轮」", "妖怪「火焔の車輪」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_灵乌路空, "核热「Nuclear Fusion」（核聚变）", "核熱「ニュークリアフュージョン」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_灵乌路空, "核热「Nuclear Excursion」（核功率骤增）", "核熱「ニュークリアエクスカーション」", THSpell.Hard),
            new THSpell(this, chara_灵乌路空, "核热「核反应失控」", "核熱「核反応制御不能」", THSpell.Lunatic),
            new THSpell(this, chara_灵乌路空, "爆符「Petit Flare」（小型耀斑）", "爆符「プチフレア」", THSpell.Easy),
            new THSpell(this, chara_灵乌路空, "爆符「Mega-Flare」（百万耀斑）", "爆符「メガフレア」", THSpell.Normal),
            new THSpell(this, chara_灵乌路空, "爆符「Giga-Flare」（十亿耀斑）", "爆符「ギガフレア」", THSpell.Hard),
            new THSpell(this, chara_灵乌路空, "爆符「Peta-Flare」（千兆耀斑）", "爆符「ぺタフレア」", THSpell.Lunatic),
            new THSpell(this, chara_灵乌路空, "焰星「Fixed Star」（恒星）", "焔星「フィクストスター」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_灵乌路空, "焰星「Planetary Revolution」（行星公转）", "焔星「プラネタリーレボリューション」", THSpell.Hard),
            new THSpell(this, chara_灵乌路空, "焰星「十凶星」", "焔星「十凶星」", THSpell.Lunatic),
            new THSpell(this, chara_灵乌路空, "「地狱极乐熔毁」", "「地獄極楽メルトダウン」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_灵乌路空, "「Hell's Tokamak」（地狱的托卡马克装置）", "「ヘルズトカマク」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_灵乌路空, "「地狱的人造太阳」", "「地獄の人工太陽」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_灵乌路空, "「Subterranean Sun」（地底太阳）", "「サブタレイニアンサン」　", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_东风谷早苗, "秘法「九字切」", "秘法「九字刺し」", THSpell.Extra),
            new THSpell(this, chara_东风谷早苗, "奇迹「Miracle Fruit」（神秘果）", "奇跡「ミラクルフルーツ」", THSpell.Extra),
            new THSpell(this, chara_东风谷早苗, "神德「五谷丰穰米之浴」", "神徳「五穀豊穣ライスシャワー」", THSpell.Extra),

            new THSpell(this, chara_古明地恋, "表象「列祖列宗入梦来」", "表象「夢枕にご先祖総立ち」", THSpell.Extra),
            new THSpell(this, chara_古明地恋, "表象「弹幕偏执症」", "表象「弾幕パラノイア」", THSpell.Extra),
            new THSpell(this, chara_古明地恋, "本能「本我的解放」", "本能「イドの解放」", THSpell.Extra),
            new THSpell(this, chara_古明地恋, "抑制「Super Ego」（超我）", "抑制「スーパーエゴ」", THSpell.Extra),
            new THSpell(this, chara_古明地恋, "反应「妖怪测谎机」", "反応「妖怪ポリグラフ」", THSpell.Extra),
            new THSpell(this, chara_古明地恋, "无意识「弹幕的墨迹测验」", "無意識「弾幕のロールシャッハ」", THSpell.Extra),
            new THSpell(this, chara_古明地恋, "复燃「恋爱的埋火」", "復燃「恋の埋火」", THSpell.Extra),
            new THSpell(this, chara_古明地恋, "深层「无意识的基因」", "深層「無意識の遺伝子」", THSpell.Extra),
            new THSpell(this, chara_古明地恋, "「被厌恶者的哲学」", "「嫌われ者のフィロソフィ」", THSpell.Extra),
            new THSpell(this, chara_古明地恋, "「Subterranean Rose」（地底蔷薇）", "「サブタレイニアンローズ」", THSpell.Extra),     
        };
        music = new THMusic[]{
            new THMusic("地灵们的起床", this),
            new THMusic("昏暗的风穴", this),
            new THMusic("被封印的妖怪 ~ Lost Place", this),
            new THMusic("阻绝人迹之桥", this),
            new THMusic("绿眼的嫉妒", this),
            new THMusic("漫游旧地狱街道", this),
            new THMusic("大江山的花之酒宴", this),
            new THMusic("Heartfelt Fancy", this),
            new THMusic("少女觉 ~ 3rd eye", this),
            new THMusic("废狱摇篮曲", this),
            new THMusic("尸体旅行 ~ Be of good cheer!", this),
            new THMusic("业火地幔", this),
            new THMusic("灵知的太阳信仰 ~ Nuclear Fusion", this),
            new THMusic("Last Remote", this),
            new THMusic("哈德曼的妖怪少女", this),
            new THMusic("地灵们的归家", this),
            new THMusic("能源黎明 ~ Future Dream...", this),
            new THMusic("Player's Score", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦", "八云紫", "伊吹萃香", "射命丸文"),
            new THPlayer("雾雨魔理沙", "爱丽丝·玛格特罗依德", "帕秋莉", "河城荷取")
        };
    }
}
