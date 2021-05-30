package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class TH18Data extends THGameData {

    private static TH18Data instance;

    public static TH18Data getInstance() {
        if (instance == null) {
            instance = new TH18Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方虹龙洞";
    }

    @Override
    public String getNameEng() {
        return "Unconnected Marketeers";
    }

    @Override
    public String getNameAbbr() {
        return "UM";
    }

    private TH18Data() {
        charas = new THCharacter[]{
            new THCharacter("豪德寺三花", this, "生意兴隆的吉祥物"),
            new THCharacter("山城高岭", this, "深山的经商妖怪"),
            new THCharacter("驹草山如", this, "栖息于高地的山女郎"),
            new THCharacter("玉造魅须丸", this, "真正的勾玉匠人"),
            new THCharacter("饭纲丸龙", this, "鸦天狗的首领"),
            new THCharacter("菅牧典", this, "耳边低语的邪恶白狐"),
            new THCharacter("天弓千亦", this, "无主物之神"),
            new THCharacter("姬虫百百世", this, "漆黑的噬龙者")
        };

        THCharacter chara_豪德寺三花 = getCharacter("豪德寺三花");
        THCharacter chara_山城高岭 = getCharacter("山城高岭");
        THCharacter chara_驹草山如 = getCharacter("驹草山如");
        THCharacter chara_玉造魅须丸 = getCharacter("玉造魅须丸");
        THCharacter chara_饭纲丸龙 = getCharacter("饭纲丸龙");
        THCharacter chara_菅牧典 = getCharacter("菅牧典");
        THCharacter chara_天弓千亦 = getCharacter("天弓千亦");
        THCharacter chara_姬虫百百世 = getCharacter("姬虫百百世");

        spells = new THSpell[]{

            new THSpell(this, chara_豪德寺三花, "招符「弹幕万来」", "招符「弾幕万来」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_豪德寺三花, "招符「弹灾招福」", "招符「弾災招福」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_山城高岭, "森符「木隐的技术」", "森符「木隠れの技術」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_山城高岭, "森符「极·木隐的技术」", "森符「極·木隠れの技術」", THSpell.Hard),
            new THSpell(this, chara_山城高岭, "森符「真·木隐的技术」", "森符「真·木隠れの技術」", THSpell.Lunatic),
            new THSpell(this, chara_山城高岭, "森符「最深的森域」", "森符「最奧の森域」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_山城高岭, "森符「极·最深的森域」", "森符「極·最奧の森域」", THSpell.Hard),
            new THSpell(this, chara_山城高岭, "森符「真·最深的森域」", "森符「真·最奧の森域」", THSpell.Lunatic),
            new THSpell(this, chara_山城高岭, "叶技「Green Spiral」（绿色螺旋）", "葉技「グリーンスパイラル」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_山城高岭, "叶技「Green Cyclone」（绿色旋风）", "葉技「グリーンサイクロン」", THSpell.Hard),
            new THSpell(this, chara_山城高岭, "叶技「Green Tornado」（绿色龙卷）", "葉技「グリーントルネード」", THSpell.Lunatic),

            new THSpell(this, chara_驹草山如, "山符「惊天的云间草」", "山符「動天の雲間草」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_驹草山如, "山怪「惊愕的云间草」", "山怪「驚愕の雲間草」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_驹草山如, "山符「妖光闪闪的薄雪草」", "山符「妖光輝く薄雪草」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_驹草山如, "山怪「妖魔喧嚣的薄雪草」", "山怪「妖魔犇めく薄雪草」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_驹草山如, "山花「杀戮的驹草」", "山花「殺戮の駒草」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_驹草山如, "山花「杀戮的山之女王」", "山花「殺戮の山の女王」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_玉造魅须丸, "玉符「虹龙阴阳玉」", "玉符「虹龍陰陽玉」", THSpell.Easy | THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_玉造魅须丸, "玉符「阴阳神玉」", "玉符「陰陽神玉」", THSpell.Lunatic),
            new THSpell(this, chara_玉造魅须丸, "玉将「Queen of Yin Yang Sphere」（阴阳玉女王）", "玉将「クイーンオブインヤンスフィア」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_玉造魅须丸, "女王珠「彩虹门的另一侧」", "女王珠「虹の扉の向こうに」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_玉造魅须丸, "「阴阳窒息」", "「陰陽サフォケイション」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_饭纲丸龙, "祸星「星火燎原之舞」", "禍星「星火燎原の舞」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_饭纲丸龙, "祸星「星火燎原乱舞」", "禍星「星火燎原乱舞」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_饭纲丸龙, "星风「虹彩陆离之舞」", "星風「虹彩陸離の舞」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_饭纲丸龙, "星风「虹彩陆离乱舞」", "星風「虹彩陸離乱舞」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_饭纲丸龙, "光马「天马行空之舞」", "光馬「天馬行空の舞」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_饭纲丸龙, "光马「天马行空乱舞」", "光馬「天馬行空乱舞」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_饭纲丸龙, "虹光「光风霁月」", "虹光「光風霽月」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_菅牧典, "狐符「Fox Winder」（狐之绞盘）", "狐符「フォックスワインダー」", THSpell.Extra),
            new THSpell(this, chara_菅牧典, "管狐「Cylinder Fox」（圆管之狐）", "管狐「シリンダーフォックス」", THSpell.Extra),
            new THSpell(this, chara_菅牧典, "星狐「天狐龙星之舞」", "星狐「天狐龍星の舞」", THSpell.Extra),

            new THSpell(this, chara_天弓千亦, "「向无主的贡品」", "「無主への供物」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_天弓千亦, "「弹幕收集狂的妄执」", "「弾幕狂蒐家の妄執」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_天弓千亦, "「Bullet Market」（弹幕市场）", "「バレットマーケット」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_天弓千亦, "「高密度弹幕市场」", "「密度の高いバレットマーケット」", THSpell.Hard),
            new THSpell(this, chara_天弓千亦, "「弹幕自由市场」", "「弾幕自由市場」", THSpell.Lunatic),
            new THSpell(this, chara_天弓千亦, "「虹人环」", "「虹人環」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_天弓千亦, "「Bullet Dominion」（弹幕领土）", "「バレットドミニオン」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_天弓千亦, "「暴虐的弹幕领土」", "「暴虐のバレットドミニオン」", THSpell.Hard),
            new THSpell(this, chara_天弓千亦, "「无道的弹幕领土」", "「無道のバレットドミニオン」", THSpell.Lunatic),
            new THSpell(this, chara_天弓千亦, "「弹幕的庇护所」", "「弾幕のアジール」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_姬虫百百世, "蛊毒「Cannibalistic Insect」（食人昆虫）", "蠱毒「カニバリスティックインセクト」", THSpell.Extra),
            new THSpell(this, chara_姬虫百百世, "蛊毒「Cave Swarmer」（洞穴蜂群）", "蠱毒「ケイブスウォーマー」", THSpell.Extra),
            new THSpell(this, chara_姬虫百百世, "蛊毒「Sky Pendra」（飞天蜈蚣）", "蠱毒「スカイペンドラ」", THSpell.Extra),
            new THSpell(this, chara_姬虫百百世, "采掘「不断累积的矿山废石」", "採掘「積もり続けるマインダンプ」", THSpell.Extra),
            new THSpell(this, chara_姬虫百百世, "采掘「Mine Blast」（矿山爆破）", "採掘「マインブラスト」", THSpell.Extra),
            new THSpell(this, chara_姬虫百百世, "采掘「妖怪们的盾构法」", "採掘「妖怪達のシールドメソッド」", THSpell.Extra),
            new THSpell(this, chara_姬虫百百世, "大蜈蚣「Snake Eater」（噬蛇者）", "大蜈蚣「スネークイーター」", THSpell.Extra),
            new THSpell(this, chara_姬虫百百世, "大蜈蚣「Dragon Eater」（噬龙者）", "大蜈蚣「ドラゴンイーター」", THSpell.Extra),
            new THSpell(this, chara_姬虫百百世, "「蛊毒的美食家」", "「蠱毒のグルメ」", THSpell.Extra),
            new THSpell(this, chara_姬虫百百世, "「虫姬殿下的闪耀忙乱的日常」", "「蟲姫さまの輝かしく落ち着かない毎日」", THSpell.Extra)
        };
        music = new THMusic[]{
            new THMusic("架起虹桥的幻想乡", this),
            new THMusic("妖异们的骤雨", this),
            new THMusic("大吉猫咪", this),
            new THMusic("深绿掩映的断崖", this),
            new THMusic("Banditry Technology", this),
            new THMusic("驹草盛开的万年积雪", this),
            new THMusic("Smoking Dragon", this),
            new THMusic("日渐荒废的工业遗址", this),
            new THMusic("神代矿石", this),
            new THMusic("渴盼已久的逢魔之刻", this),
            new THMusic("天魔之山漫天星", this),
            new THMusic("Lunar Rainbow", this),
            new THMusic("熙攘市场今何在　～ Immemorial Marketeers", this),
            new THMusic("幻想地下大轨道网", this),
            new THMusic("灭杀龙王的公主", this),
            new THMusic("风暴过后的星期日", this),
            new THMusic("虹色的世界", this),
            new THMusic("Player's Score", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦"),
            new THPlayer("雾雨魔理沙"),
            new THPlayer("东风谷早苗"),
            new THPlayer("十六夜咲夜")
        };
    }
}
