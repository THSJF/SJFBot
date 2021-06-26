package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THSpell;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;

public class TH16Data extends THGameData {

    private static TH16Data instance;

    public static TH16Data getInstance() {
        if (instance == null) {
            instance = new TH16Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方天空璋";
    }

    @Override
    public String getNameEng() {
        return "Hidden Star in Four Seasons";
    }

    @Override
    public String getNameAbbr() {
        return "HSiFS";
    }

    private TH16Data() {
        charas = new THCharacter[]{
            new THCharacter("爱塔妮缇拉尔瓦", this),
            new THCharacter("坂田合欢", this),
            new THCharacter("高丽野阿吽", this),
            new THCharacter("矢田寺成美", this),
            new THCharacter("尔子田里乃", this),
            new THCharacter("丁礼田舞", this),
            new THCharacter("摩多罗隐岐奈", this)  
        };
        THCharacter chara_爱塔妮缇拉尔瓦 = getCharacter("爱塔妮缇拉尔瓦");
        THCharacter chara_坂田合欢 = getCharacter("坂田合欢");
        THCharacter chara_高丽野阿吽 = getCharacter("高丽野阿吽");
        THCharacter chara_矢田寺成美 = getCharacter("矢田寺成美");
        THCharacter chara_尔子田里乃 = getCharacter("尔子田里乃");
        THCharacter chara_丁礼田舞 = getCharacter("丁礼田舞");
        THCharacter chara_摩多罗隐岐奈 = getCharacter("摩多罗隐岐奈");
        spells = new THSpell[]{
            new THSpell(this, chara_爱塔妮缇拉尔瓦, "蝶符「Minute Scales」（细碎鳞粉）", "蝶符「ミニットスケールス」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_爱塔妮缇拉尔瓦, "蝶符「凤蝶的鳞粉」", "蝶符「アゲハの鱗粉」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_爱塔妮缇拉尔瓦, "蝶符「Fluttering Summer」（扑翅之夏）", "蝶符「フラッタリングサマー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_爱塔妮缇拉尔瓦, "蝶符「盛夏振翅」", "蝶符「真夏の羽ばたき」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_坂田合欢, "雨符「被囚禁的秋雨」", "雨符「囚われの秋雨」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_坂田合欢, "雨符「被诅咒的豪雨」", "雨符「呪われた柴榑雨」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_坂田合欢, "刃符「山姥的菜刀研磨」", "刃符「山姥の包丁研ぎ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_坂田合欢, "刃符「山姥的鬼菜刀研磨」", "刃符「山姥の鬼包丁研ぎ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_坂田合欢, "尽符「Mountain Murder」（深山谋杀）", "尽符「マウンテンマーダー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_坂田合欢, "尽符「Bloody Mountain Murder」（血腥的深山谋杀）", "尽符「ブラッディマウンテンマーダー」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_高丽野阿吽, "犬符「野犬的散步」", "犬符「野良犬の散歩」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_高丽野阿吽, "狗符「山狗的散步」", "狗符「山狗の散歩」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_高丽野阿吽, "陀螺「狛犬旋转」", "独楽「コマ犬回し」", THSpell.Easy | THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_高丽野阿吽, "陀螺「Curl Up and Die」（蜷缩死去）", "独楽「カールアップアンドダイ」", THSpell.Lunatic),
            new THSpell(this, chara_高丽野阿吽, "狛符「单人式阿吽的呼吸」", "狛符「独り阿吽の呼吸」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_矢田寺成美, "魔符「Instant Bodhi」（顷刻菩提）", "魔符「インスタントボーディ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_矢田寺成美, "魔符「即席菩提」", "魔符「即席菩提」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_矢田寺成美, "魔符「Bullet Golem」（弹丸魔像）", "魔符「バレットゴーレム」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_矢田寺成美, "魔符「作宠物的巨大弹生命体」", "魔符「ペットの巨大弾生命体」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_矢田寺成美, "地藏「Criminal Salvation」（罪业救赎）", "地蔵「クリミナルサルヴェイション」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_矢田寺成美, "地藏「业火救济」", "地蔵「業火救済」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_尔子田里乃, "茗荷「Forget Your Name」（忘却你的名字）", "茗荷「フォゲットユアネーム」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_尔子田里乃, "冥加「Behind You」（在你背后）", "冥加「ビハインドユー」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_尔子田里乃, "舞符「Behind Festival」（背后之祭）", "舞符「ビハインドフェスティバル」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_尔子田里乃, "狂舞「天狗怖吓」", "狂舞「テングオドシ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_尔子田里乃, "狂舞「狂乱天狗怖吓」", "狂舞「狂乱天狗怖し」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_尔子田里乃, "鼓舞「Powerful Cheers」（强力助威）", "鼓舞「パワフルチアーズ」", THSpell.Extra),
            new THSpell(this, chara_尔子田里乃, "狂舞「Crazy Back Dance」（疯狂的背景舞）", "狂舞「クレイジーバックダンス」", THSpell.Extra),
            new THSpell(this, chara_尔子田里乃, "弹舞「双目台风」", "弹舞「双目台风」", THSpell.Extra),

            new THSpell(this, chara_丁礼田舞, "竹符「Bamboo Spear Dance」（竹矛之舞）", "竹符「バンブースピアダンス」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_丁礼田舞, "竹符「Bamboo Crazy Dance」（竹之狂舞）", "竹符「バンブークレイジーダンス」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_丁礼田舞, "笹符「Tanabata Star Festival」（七夕星祭）", "笹符「タナバタスターフェスティバル」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_丁礼田舞, "舞符「Behind Festival」（背后之祭）", "舞符「ビハインドフェスティバル」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_丁礼田舞, "狂舞「天狗怖吓」", "狂舞「テングオドシ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_丁礼田舞, "狂舞「狂乱天狗怖吓」", "狂舞「狂乱天狗怖し」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_丁礼田舞, "鼓舞「Powerful Cheers」（强力助威）", "鼓舞「パワフルチアーズ」", THSpell.Extra),
            new THSpell(this, chara_丁礼田舞, "狂舞「Crazy Back Dance」（疯狂的背景舞）", "狂舞「クレイジーバックダンス」", THSpell.Extra),
            new THSpell(this, chara_丁礼田舞, "弹舞「双目台风」", "弹舞「双目台风」", THSpell.Extra),

            new THSpell(this, chara_摩多罗隐岐奈, "后符「秘神的后光」", "後符「秘神の後光」", THSpell.Easy | THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_摩多罗隐岐奈, "后符「绝对秘神的后光」", "後符「絶対秘神の後光」", THSpell.Lunatic),
            new THSpell(this, chara_摩多罗隐岐奈, "里夏「Scorch by Hot Summer」（暑夏炙烤）", "裏夏「スコーチ・バイ・ホットサマー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_摩多罗隐岐奈, "里夏「异常酷暑之焦土」", "裏夏「異常猛暑の焦土」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_摩多罗隐岐奈, "里秋「Die of Famine」（死于饥荒）", "裏秋「ダイ・オブ・ファミン」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_摩多罗隐岐奈, "里秋「异常枯死之饿鬼」", "裏秋「異常枯死の餓鬼」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_摩多罗隐岐奈, "里冬「Black Snowman」（黑色雪人）", "裏冬「ブラックスノーマン」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_摩多罗隐岐奈, "里冬「异常降雪之雪人」", "裏冬「異常降雪の雪だるま」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_摩多罗隐岐奈, "里春「April Wizard」（四月巫师）", "裏春「エイプリルウィザード」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_摩多罗隐岐奈, "里春「异常落花之魔术使」", "裏春「異常落花の魔術使い」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_摩多罗隐岐奈, "「里·Breezy Cherry Blossom」（里·吹拂樱花）", "「裏・ブリージーチェリーブロッサム」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_摩多罗隐岐奈, "「里·Perfect Summer Ice」（里·完美夏冰）", "「裏・パーフェクトサマーアイス」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_摩多罗隐岐奈, "「里·Crazy Fall Wind」（里·疯狂秋风）", "「裏・クレイジーフォールウィンド」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_摩多罗隐岐奈, "「里·Extreme Winter」（里·极端寒冬）", "「裏・エクストリームウィンター」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_摩多罗隐岐奈, "秘仪「Reverse Invoker」（逆向呼神者）", "秘儀「リバースインヴォーカー」", THSpell.Extra),
            new THSpell(this, chara_摩多罗隐岐奈, "秘仪「背叛的后方射击」", "秘儀「裏切りの後方射撃」", THSpell.Extra),
            new THSpell(this, chara_摩多罗隐岐奈, "秘仪「弹幕的玉茧」", "秘儀「弾幕の玉繭」", THSpell.Extra),
            new THSpell(this, chara_摩多罗隐岐奈, "秘仪「秽那之火」", "秘儀「穢那の火」", THSpell.Extra),
            new THSpell(this, chara_摩多罗隐岐奈, "秘仪「后户的狂言」", "秘儀「後戸の狂言」", THSpell.Extra),
            new THSpell(this, chara_摩多罗隐岐奈, "秘仪「Matarah Dukkha」（摩多罗苦谛）", "秘儀「マターラドゥッカ」", THSpell.Extra),
            new THSpell(this, chara_摩多罗隐岐奈, "秘仪「七星之剑」", "秘儀「七星の剣」", THSpell.Extra),
            new THSpell(this, chara_摩多罗隐岐奈, "秘仪「无纽带的艺人」", "秘儀「無縁の芸能者」", THSpell.Extra),
            new THSpell(this, chara_摩多罗隐岐奈, "「背面的暗黑猿乐」", "「背面の暗黒猿楽」", THSpell.Extra),
            new THSpell(this, chara_摩多罗隐岐奈, "「Anarchy Bullet Hell」（无秩序弹幕地狱）", "「アナーキーバレットヘル」", THSpell.Extra),
        };
        music = new THMusic[]{
            new THMusic("樱花舞落的天空", this),
            new THMusic("希望之星直升青霄", this),
            new THMusic("仲夏的妖精梦", this),
            new THMusic("妖怪山间无色风", this),
            new THMusic("深山的遭遇", this),
            new THMusic("徜徉于樱色之海", this),
            new THMusic("一对的神兽", this),
            new THMusic("幻想的White Traveler", this),
            new THMusic("魔法的笠地藏", this),
            new THMusic("禁断之门对面，是此世还是彼世", this),
            new THMusic("Crazy Back Dancers", this),
            new THMusic("Into Backdoor", this),
            new THMusic("被秘匿的四个季节", this),
            new THMusic("门再也进不去了", this),
            new THMusic("秘神摩多罗 ~ Hidden Star in All Seasons.", this),
            new THMusic("不自然的自然", this),
            new THMusic("白色旅人", this),
            new THMusic("Player's Score", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦", "春", "夏", "秋", "冬"),
            new THPlayer("琪露诺", "春", "夏", "秋", "冬"),
            new THPlayer("射命丸文", "春", "夏", "秋", "冬"),
            new THPlayer("雾雨魔理沙", "春", "夏", "秋", "冬"),
        };
    }
}
