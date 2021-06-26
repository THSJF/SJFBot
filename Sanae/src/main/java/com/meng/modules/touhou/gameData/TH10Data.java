package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class TH10Data extends THGameData {

    private static TH10Data instance;

    public static TH10Data getInstance() {
        if (instance == null) {
            instance = new TH10Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方风神录";
    }

    @Override
    public String getNameEng() {
        return "Mountain of Faith";
    }

    @Override
    public String getNameAbbr() {
        return "MoF";
    }

    private TH10Data() {
        charas = new THCharacter[]{
            new THCharacter("秋静叶", this, "寂寞与终焉的象征"),
            new THCharacter("秋穰子", this, "丰裕与收成的象征"),
            new THCharacter("键山雏", this, "秘神流雏"),
            new THCharacter("河城荷取", this, "超妖怪弹头"),
            new THCharacter("犬走椛", this, "下端警戒天狗"),
            new THCharacter("射命丸文", this, "最接近村落的天狗"),
            new THCharacter("东风谷早苗", this, "祭祀风的人类"),
            new THCharacter("八坂神奈子", this, "山坂与湖水的化身"),
            new THCharacter("洩矢诹访子", this, "土著神的顶点")
        };

        THCharacter chara_秋静叶 = getCharacter("秋静叶");
        THCharacter chara_秋穰子 = getCharacter("秋穰子");
        THCharacter chara_键山雏 = getCharacter("键山雏");
        THCharacter chara_河城荷取 = getCharacter("河城荷取");
        THCharacter chara_射命丸文 = getCharacter("射命丸文");
        THCharacter chara_东风谷早苗 = getCharacter("东风谷早苗");
        THCharacter chara_八坂神奈子 = getCharacter("八坂神奈子");
        THCharacter chara_洩矢诹访子 = getCharacter("洩矢诹访子"); 

        spells = new THSpell[]{
            new THSpell(this, chara_秋静叶, "叶符「纷乱的落叶」", "葉符「狂いの落葉」", THSpell.Hard | THSpell.Lunatic, "叶符「狂舞的落叶」"),

            new THSpell(this, chara_秋穰子, "秋符「Autumn Sky」（秋季的天空）", "秋符「オータムスカイ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_秋穰子, "秋符「无常秋日与少女的心」", "秋符「秋の空と乙女の心」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_秋穰子, "丰符「Otoshi Harvester」（大年收获者）", "豊符「オヲトシハーベスター」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_秋穰子, "丰收「谷物神的允诺」", "豊作「穀物神の約束」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_键山雏, "厄符「Bad Fortune」（厄运）", "厄符「バッドフォーチュン」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_键山雏, "厄符「厄神大人的生理节律」", "厄符「厄神様のバイオリズム」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_键山雏, "疵符「Broken Amulet」（破裂的护符）", "疵符「ブロークンアミュレット」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_键山雏, "疵痕「损坏的护符」", "疵痕「壊されたお守り」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_键山雏, "恶灵「Misfortune's Wheel」（厄运之轮）", "悪霊「ミスフォーチュンズホイール」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_键山雏, "悲运「大钟婆之火」", "悲運「大鐘婆の火」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_键山雏, "创符「Pain Flow」（痛苦之流）", "創符「ペインフロー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_键山雏, "创符「流刑人偶」", "創符「流刑人形」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_河城荷取, "光学「Optical Camouflage」（光学迷彩）", "光学「オプティカルカモフラージュ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_河城荷取, "光学「Hydro-Camouflage」（水迷彩）", "光学「ハイドロカモフラージュ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_河城荷取, "洪水「Ooze Flooding」（泥浆泛滥）", "洪水「ウーズフラッディング」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_河城荷取, "洪水「Diluvial Mare」（冲积梦魇）", "洪水「デリューヴィアルメア」", THSpell.Hard),
            new THSpell(this, chara_河城荷取, "漂溺「粼粼水底之心伤」", "漂溺「光り輝く水底のトラウマ」", THSpell.Lunatic),
            new THSpell(this, chara_河城荷取, "水符「河童之河口浪潮」", "水符「河童のポロロッカ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_河城荷取, "水符「河童之山洪暴发」", "水符「河童のフラッシュフラッド」", THSpell.Hard),
            new THSpell(this, chara_河城荷取, "水符「河童之幻想大瀑布」", "水符「河童の幻想大瀑布」", THSpell.Lunatic),
            new THSpell(this, chara_河城荷取, "河童「妖怪黄瓜」", "河童「お化けキューカンバー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_河城荷取, "河童「延展手臂」", "河童「のびーるアーム」", THSpell.Hard),
            new THSpell(this, chara_河城荷取, "河童「Spin the Cephalic Plate」（回转顶板）", "河童「スピン・ザ・セファリックプレート」", THSpell.Lunatic),

            new THSpell(this, chara_射命丸文, "岐符「天之八衢」", "岐符「天の八衢」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_射命丸文, "岐符「猿田彦神之岔路」", "岐符「サルタクロス」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_射命丸文, "风神「风神木叶隐身术」", "風神「風神木の葉隠れ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_射命丸文, "风神「天狗旋降之风」", "風神「天狗颪」", THSpell.Hard),
            new THSpell(this, chara_射命丸文, "风神「二百十日」", "風神「二百十日」", THSpell.Lunatic),
            new THSpell(this, chara_射命丸文, "「幻想风靡」", "「幻想風靡」", THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_射命丸文, "「无双风神」", "「無双風神」", THSpell.Lunatic),
            new THSpell(this, chara_射命丸文, "塞符「山神渡御」", "塞符「山神渡御」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_射命丸文, "塞符「天孙降临」", "塞符「天孫降臨」", THSpell.Hard),
            new THSpell(this, chara_射命丸文, "塞符「天上天下的照国」", "塞符「天上天下の照國」", THSpell.Lunatic),

            new THSpell(this, chara_东风谷早苗, "秘术「Gray Thaumaturgy」（灰色奇术）", "秘術「グレイソーマタージ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_东风谷早苗, "秘术「遗忘之祭仪」", "秘術「忘却の祭儀」", THSpell.Hard),
            new THSpell(this, chara_东风谷早苗, "秘术「一脉单传之弹幕」", "秘術「一子相伝の弾幕」", THSpell.Lunatic),
            new THSpell(this, chara_东风谷早苗, "奇迹「白昼的客星」", "奇跡「白昼の客星」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_东风谷早苗, "奇迹「客星璀璨之夜」", "奇跡「客星の明るい夜」", THSpell.Hard),
            new THSpell(this, chara_东风谷早苗, "奇迹「客星辉煌之夜」", "奇跡「客星の明るすぎる夜」", THSpell.Lunatic),
            new THSpell(this, chara_东风谷早苗, "开海「割海成路之日」", "開海「海が割れる日」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_东风谷早苗, "开海「摩西之奇迹」", "開海「モーゼの奇跡」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_东风谷早苗, "准备「呼唤神风的星之仪式」", "準備「神風を喚ぶ星の儀式」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_东风谷早苗, "准备「Summon Takeminakata」（召请建御名方神）", "準備「サモンタケミナカタ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_东风谷早苗, "奇迹「神之风」", "奇跡「神の風」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_东风谷早苗, "大奇迹「八坂之神风」", "大奇跡「八坂の神風」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_八坂神奈子, "神祭「Expanded Onbashira」（扩展御柱）", "神祭「エクスパンデッド・オンバシラ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_八坂神奈子, "奇祭「目处梃子乱舞」", "奇祭「目処梃子乱舞」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_八坂神奈子, "筒粥「神之粥」", "筒粥「神の粥」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_八坂神奈子, "忘谷「Unremembered Crop」（遗忘之谷）", "忘穀「アンリメンバードクロップ」", THSpell.Hard),
            new THSpell(this, chara_八坂神奈子, "神谷「Divining Crop」（神谕之谷）", "神穀「ディバイニングクロップ」", THSpell.Lunatic),
            new THSpell(this, chara_八坂神奈子, "贽符「御射山御狩神事」", "贄符「御射山御狩神事」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_八坂神奈子, "神秘「葛泉清水」", "神秘「葛井の清水」", THSpell.Hard),
            new THSpell(this, chara_八坂神奈子, "神秘「Yamato Torus」（大和矛环）", "神秘「ヤマトトーラス」", THSpell.Lunatic),
            new THSpell(this, chara_八坂神奈子, "天流「天水奇迹」", "天流「お天水の奇跡」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_八坂神奈子, "天龙「雨之源泉」", "天竜「雨の源泉」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_八坂神奈子, "「Mountain of Faith」（信仰之山）", "「マウンテン・オブ・フェイス」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_八坂神奈子, "「风神之神德」", "「風神様の神徳」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_八坂神奈子, "神符「如水眼之美丽源泉」", "神符「水眼の如き美しき源泉」", THSpell.Extra),
            new THSpell(this, chara_八坂神奈子, "神符「结于杉木之古缘」", "神符「杉で結ぶ古き縁」", THSpell.Extra),
            new THSpell(this, chara_八坂神奈子, "神符「神所踏足之御神渡」", "神符「神が歩かれた御神渡り」", THSpell.Extra),

            new THSpell(this, chara_洩矢诹访子, "开宴「二拜二拍一拜」", "開宴「二拝二拍一拝」", THSpell.Extra),
            new THSpell(this, chara_洩矢诹访子, "土著神「手长足长大人」", "土着神「手長足長さま」", THSpell.Extra),
            new THSpell(this, chara_洩矢诹访子, "神具「洩矢的铁轮」", "神具「洩矢の鉄の輪」", THSpell.Extra),
            new THSpell(this, chara_洩矢诹访子, "源符「厌川的翡翠」", "源符「厭い川の翡翠」", THSpell.Extra),
            new THSpell(this, chara_洩矢诹访子, "蛙狩「蛙以口鸣，方致蛇祸」", "蛙狩「蛙は口ゆえ蛇に呑まるる」", THSpell.Extra),
            new THSpell(this, chara_洩矢诹访子, "土著神「七石七木」", "土着神「七つの石と七つの木」", THSpell.Extra),
            new THSpell(this, chara_洩矢诹访子, "土著神「小小青蛙不输风雨」", "土着神「ケロちゃん風雨に負けず」", THSpell.Extra),
            new THSpell(this, chara_洩矢诹访子, "土著神「宝永四年的赤蛙」", "土着神「宝永四年の赤蛙」", THSpell.Extra),
            new THSpell(this, chara_洩矢诹访子, "「诹访大战 ～ 土著神话 vs 中央神话」", "「諏訪大戦 ～ 土着神話 vs 中央神話」", THSpell.Extra),
            new THSpell(this, chara_洩矢诹访子, "祟符「洩矢大人」", "祟符「ミシャグジさま」", THSpell.Extra)      
        };
        music = new THMusic[]{
            new THMusic("被封印的众神", this),
            new THMusic("眷爱众生之神 ~ Romantic Fall", this),
            new THMusic("受稻田公主的斥责啦", this),
            new THMusic("厄神降临之路 ~ Dark Road", this),
            new THMusic("命运的阴暗面", this),
            new THMusic("众神眷恋的幻想乡", this),
            new THMusic("芥川龙之介的河童 ~ Candid Friend", this),
            new THMusic("Fall of Fall ~ 秋意渐浓之瀑", this),
            new THMusic("妖怪之山 ~ Mysterious Mountain", this),
            new THMusic("少女曾见的日本原风景", this),
            new THMusic("信仰是为了虚幻之人", this),
            new THMusic("御柱的墓场 ~ Grave of Being", this),
            new THMusic("神圣庄严的古战场 ~ Suwa Foughten Field", this),
            new THMusic("明日之盛,昨日之俗", this),
            new THMusic("Native Faith", this),
            new THMusic("山脚的神社", this),
            new THMusic("神明降下恩惠之雨 ~ Sylphid", this),
            new THMusic("Player's Score", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦", "诱导装备", "前方集中装备", "封印装备"),
            new THPlayer("雾雨魔理沙", "高威力装备", "贯通装备", "魔法使装备")
        };
    }
}
