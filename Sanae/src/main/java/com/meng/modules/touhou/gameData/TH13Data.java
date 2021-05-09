package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class TH13Data extends THGameData {

    private static TH13Data instance;

    public static TH13Data getInstance() {
        if (instance == null) {
            instance = new TH13Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方神灵庙";
    }

    @Override
    public String getNameEng() {
        return "Ten Desires";
    }

    @Override
    public String getNameAbbr() {
        return "TD";
    }

    private TH13Data() {
        charas = new THCharacter[]{
            new THCharacter("西行寺幽幽子", this, "心无彷徨的亡灵"),
            new THCharacter("幽谷响子", this, "诵经的山彦"),
            new THCharacter("多多良小伞", this, "为难的遗忘之物"),
            new THCharacter("宫古芳香", this, "忠诚的尸体"),
            new THCharacter("霍青娥", this, "穿墙的邪仙"),
            new THCharacter("苏我屠自古", this, "神明后裔的亡灵"),
            new THCharacter("物部布都", this, "古代日本的尸解仙"),
            new THCharacter("丰聪耳神子", this, "圣德道士"),
            new THCharacter("封兽鵺", this, "古代妖怪之一"),
            new THCharacter("二岩瑞藏", this, "狸猫怪 十变化")
        };

        THCharacter chara_西行寺幽幽子 = getCharacter("西行寺幽幽子");
        THCharacter chara_幽谷响子 = getCharacter("幽谷响子");
        THCharacter chara_多多良小伞 = getCharacter("多多良小伞");
        THCharacter chara_宫古芳香 = getCharacter("宫古芳香");
        THCharacter chara_霍青娥 = getCharacter("霍青娥");
        THCharacter chara_苏我屠自古 = getCharacter("苏我屠自古");
        THCharacter chara_物部布都 = getCharacter("物部布都");
        THCharacter chara_丰聪耳神子 = getCharacter("丰聪耳神子");
        THCharacter chara_封兽鵺 = getCharacter("封兽鵺");
        THCharacter chara_二岩猯藏 = getCharacter("二岩猯藏");

        spells = new THSpell[]{
            new THSpell(this, chara_西行寺幽幽子, "符牒「死蝶之舞」", "符牒「死蝶の舞」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_西行寺幽幽子, "符牒「死蝶之舞 - 樱花 -」", "符牒「死蝶の舞 - 桜花 -」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_西行寺幽幽子, "幽蝶「Ghost Spot」（幽魂聚地）", "幽蝶「ゴーストスポット」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_西行寺幽幽子, "幽蝶「Ghost Spot - 樱花 -」（幽魂聚地 - 樱花 -）", "幽蝶「ゴーストスポット - 桜花 -」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_西行寺幽幽子, "冥符「常夜樱」", "冥符「常夜桜」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_西行寺幽幽子, "樱符「西行樱吹雪」", "桜符「西行桜吹雪」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_西行寺幽幽子, "樱符「樱吹雪地狱」", "桜符「桜吹雪地獄」", THSpell.Overdrive),


            new THSpell(this, chara_幽谷响子, "响符「Mountain Echo」（山谷回声）", "響符「マウンテンエコー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_幽谷响子, "响符「Mountain Echo Scramble」（混乱的山谷回声）", "響符「マウンテンエコースクランブル」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_幽谷响子, "响符「Power Resonance」（强力共振）", "響符「パワーレゾナンス」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_幽谷响子, "山彦「Long-Range Echo」（远距离回声）", "山彦「ロングレンジエコー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_幽谷响子, "山彦「Amplify Echo」（扩大回声）", "山彦「アンプリファイエコー」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_幽谷响子, "大声「Charged Cry」（激动的呼喊）", "大声「チャージドクライ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_幽谷响子, "大声「Charged Yahoo」（激动Yahoo）", "大声「チャージドヤッホー」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_幽谷响子, "山彦「山彦的本领发挥回音」", "山彦「ヤマビコの本領発揮エコー」", THSpell.Overdrive),

            new THSpell(this, chara_多多良小伞, "虹符「Umbrella Cyclone」（雨伞风暴）", "虹符「アンブレラサイクロン」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_宫古芳香, "回复「Heal by Desire」（借由欲望的恢复）", "回復「ヒールバイデザイア」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_宫古芳香, "毒爪「Poison Raze」（剧毒抹消）", "毒爪「ポイズンレイズ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_宫古芳香, "毒爪「Poison Murder」（剧毒杀害）", "毒爪「ポイズンマーダー」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_宫古芳香, "欲符「赚钱欲灵招来」", "欲符「稼欲霊招来」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_宫古芳香, "欲灵「Score Desire Eater」（贪分欲吞噬者）", "欲霊「スコアデザイアイーター」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_宫古芳香, "毒爪「不死杀人鬼」", "毒爪「死なない殺人鬼」", THSpell.Overdrive),

            new THSpell(this, chara_霍青娥, "邪符「养小鬼」", "邪符「ヤンシャオグイ」", THSpell.Normal),
            new THSpell(this, chara_霍青娥, "邪符「孤魂野鬼」", "邪符「グーフンイエグイ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_霍青娥, "入魔「走火入魔」", "入魔「ゾウフォルゥモォ」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_霍青娥, "降灵「死人童乩」", "降霊「死人タンキー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_霍青娥, "通灵「通灵芳香」", "通霊「トンリン芳香」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_霍青娥, "道符「道胎动」", "道符「タオ胎動」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_霍青娥, "道符「TAO胎动 ～道～」", "道符「ＴＡＯ胎動 ～道～」", THSpell.Overdrive),

            new THSpell(this, chara_苏我屠自古, "雷矢「Gagouji Cyclone」（元兴寺的旋风）", "雷矢「ガゴウジサイクロン」", THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_苏我屠自古, "雷矢「Gagouji Tornado」（元兴寺的龙卷）", "雷矢「ガゴウジトルネード」", THSpell.Lunatic),
            new THSpell(this, chara_苏我屠自古, "怨灵「入鹿之雷」", "怨霊「入鹿の雷」", THSpell.Overdrive),

            new THSpell(this, chara_物部布都, "天符「雨之磐舟」", "天符「雨の磐舟」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_物部布都, "天符「天之磐舟哟，向天飞升吧」", "天符「天の磐舟よ天へ昇れ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_物部布都, "投皿「物部氏的八十平瓮」", "投皿「物部の八十平瓮」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_物部布都, "炎符「废佛之炎风」", "炎符「廃仏の炎風」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_物部布都, "炎符「火烧樱井寺」", "炎符「桜井寺炎上」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_物部布都, "圣童女「大物忌正餐」", "聖童女「大物忌正餐」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_物部布都, "圣童女「太阳神的贡品」", "聖童女「太陽神の贄」", THSpell.Overdrive),

            new THSpell(this, chara_丰聪耳神子, "名誉「十二阶之色彩」", "名誉「十二階の色彩」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_丰聪耳神子, "名誉「十二阶之冠位」", "名誉「十二階の冠位」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_丰聪耳神子, "仙符「日出之处的道士」", "仙符「日出ずる処の道士」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_丰聪耳神子, "仙符「日出之处的天子」", "仙符「日出ずる処の天子」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_丰聪耳神子, "召唤「豪族乱舞」", "召喚「豪族乱舞」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_丰聪耳神子, "秘宝「斑鸠寺的天球仪」", "秘宝「斑鳩寺の天球儀」", THSpell.Easy | THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_丰聪耳神子, "秘宝「圣德太子的欧帕兹」", "秘宝「聖徳太子のオーパーツ」", THSpell.Lunatic),
            new THSpell(this, chara_丰聪耳神子, "光符「救世观音的佛光」", "光符「救世観音の光後光」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_丰聪耳神子, "光符「Guse Flash」（救世之光）", "光符「グセフラッシュ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_丰聪耳神子, "眼光「十七条的光芒」", "眼光「十七条のレーザー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_丰聪耳神子, "神光「无忤为宗」", "神光「逆らう事なきを宗とせよ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_丰聪耳神子, "「星辰降落的神灵庙」", "「星降る神霊廟」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_丰聪耳神子, "「新生的神灵」", "「生まれたての神霊」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_丰聪耳神子, "「神灵大宇宙」", "「神霊大宇宙」", THSpell.Overdrive),

            new THSpell(this, chara_封兽鵺, "未知「轨道不明的鬼火」", "アンノウン「軌道不明の鬼火」", THSpell.Extra),
            new THSpell(this, chara_封兽鵺, "未知「姿态不明的空鱼」", "アンノウン「姿態不明の空魚」", THSpell.Extra),
            new THSpell(this, chara_封兽鵺, "未知「原理不明的妖怪玉」", "アンノウン「原理不明の妖怪玉」", THSpell.Extra),

            new THSpell(this, chara_二岩猯藏, "一回胜负「灵长类弹幕变化」", "壱番勝負「霊長化弾幕変化」", THSpell.Extra),
            new THSpell(this, chara_二岩猯藏, "二回胜负「肉食类弹幕变化」", "弐番勝負「肉食化弾幕変化」", THSpell.Extra),
            new THSpell(this, chara_二岩猯藏, "三回胜负「羽鸟类弹幕变化」", "参番勝負「延羽化弾幕変化」", THSpell.Extra),
            new THSpell(this, chara_二岩猯藏, "四回胜负「两栖类弹幕变化」", "四番勝負「両生化弾幕変化」", THSpell.Extra),
            new THSpell(this, chara_二岩猯藏, "五回胜负「鸟兽戏画」", "伍番勝負「鳥獣戯画」", THSpell.Extra),
            new THSpell(this, chara_二岩猯藏, "六回胜负「狸猫的变身学校」", "六番勝負「狸の化け学校」", THSpell.Extra),
            new THSpell(this, chara_二岩猯藏, "七回胜负「野生的离岛」", "七番勝負「野生の離島」", THSpell.Extra),
            new THSpell(this, chara_二岩猯藏, "变化「魔奴化巫女的伪退治」", "変化「まぬけ巫女の偽調伏」", THSpell.Extra),
            new THSpell(this, chara_二岩猯藏, "「猯藏化弹幕十变化」", "「マミゾウ化弾幕十変化」", THSpell.Extra),
            new THSpell(this, chara_二岩猯藏, "貉符「满月下的腹鼓舞」", "狢符「満月のポンポコリン」", THSpell.Extra),
            new THSpell(this, chara_二岩猯藏, "「Wild Carpet」（野生地毯）", "「ワイルドカーペット」", THSpell.Overdrive),
        };
        music = new THMusic[]{
            new THMusic("欲望深重的灵魂", this),
            new THMusic("死灵的夜樱", this),
            new THMusic("Ghost Lead", this),
            new THMusic("欢迎来到妖怪寺", this),
            new THMusic("门前的妖怪小姑娘", this),
            new THMusic("在美妙的墓地里住下吧", this),
            new THMusic("Rigid Paradise", this),
            new THMusic("Desire Drive", this),
            new THMusic("古老的元神", this),
            new THMusic("梦殿大祀庙", this),
            new THMusic("大神神话传", this),
            new THMusic("小小欲望的星空", this),
            new THMusic("圣德传说 ~ True Administrator", this),
            new THMusic("后院的妖怪参拜道", this),
            new THMusic("佐渡的二岩", this),
            new THMusic("神社的新风", this),
            new THMusic("Desire Dream", this),
            new THMusic("Player's Score", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦"),
            new THPlayer("雾雨魔理沙"),
            new THPlayer("东风谷早苗"),
            new THPlayer("魂魄妖梦")
        };
    }
}
