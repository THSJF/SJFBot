package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class TH08Data extends THGameData {

    private static TH08Data instance;

    public static TH08Data getInstance() {
        if (instance == null) {
            instance = new TH08Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方永夜抄";
    }

    @Override
    public String getNameEng() {
        return "Imperishable Nigh";
    }

    @Override
    public String getNameAbbr() {
        return "IN";
    }

    private TH08Data() {
        charas = new THCharacter[]{
            new THCharacter("莉格露·奈特巴格", this, "暗中蠢动的光虫"),
            new THCharacter("米斯蒂娅·萝蕾拉", this, "夜雀妖怪"),
            new THCharacter("上白泽慧音", this, "知识与历史的半兽"),
            new THCharacter("博丽灵梦", this, "乐园的可爱巫女"),
            new THCharacter("雾雨魔理沙", this, "普通的黑魔术少女"),
            new THCharacter("因幡帝", this,"地上兔"),
            new THCharacter("铃仙· 优昙华院·因幡", this, "疯狂的月兔"),
            new THCharacter("八意永琳", this, "月之头脑"),
            new THCharacter("蓬莱山辉夜", this, "永远和须臾的罪人"),
            new THCharacter("藤原妹红", this, "蓬莱的人形"),

            new THCharacter("十六夜咲夜", this, "红魔馆的女仆"),
            new THCharacter("魂魄妖梦", this,"半人半灵"),
            new THCharacter("爱丽丝·玛格特洛依德", this, "七色的人偶师"),
            new THCharacter("蕾米莉亚·斯卡蕾特,", this, "红色恶魔"),
            new THCharacter("西行寺幽幽子", this, "华胥的亡灵"),
            new THCharacter("八云紫", this, "境界的妖怪")

        };

        THCharacter chara_莉格露_奈特巴格 = getCharacter("莉格露·奈特巴格");
        THCharacter chara_米斯蒂娅_萝蕾拉 = getCharacter("米斯蒂娅·萝蕾拉");
        THCharacter chara_上白泽慧音 = getCharacter("上白泽慧音");
        THCharacter chara_博丽灵梦 = getCharacter("博丽灵梦");
        THCharacter chara_雾雨魔理沙 = getCharacter("雾雨魔理沙");
        THCharacter chara_因幡帝 = getCharacter("因幡帝");
        THCharacter chara_铃仙_优昙华院_因幡 = getCharacter("铃仙·优昙华院·因幡");
        THCharacter chara_八意永琳 = getCharacter("八意永琳");
        THCharacter chara_蓬莱山辉夜 = getCharacter("蓬莱山辉夜");
        THCharacter chara_藤原妹红 = getCharacter("藤原妹红");

        THCharacter chara_十六夜咲夜 = getCharacter("十六夜咲夜");
        THCharacter chara_魂魄妖梦 = getCharacter("魂魄妖梦");
        THCharacter chara_爱丽丝_玛格特洛依德 = getCharacter("爱丽丝·玛格特洛依德");
        THCharacter chara_蕾米莉亚_斯卡蕾特 = getCharacter("蕾米莉亚·斯卡蕾特");
        THCharacter chara_西行寺幽幽子 = getCharacter("西行寺幽幽子");
        THCharacter chara_八云紫 = getCharacter("八云紫");

        spells = new THSpell[]{
            new THSpell(this, chara_莉格露_奈特巴格, "萤符「地上的流星」", "蛍符「地上の流星」", THSpell.Hard),
            new THSpell(this, chara_莉格露_奈特巴格, "萤符「地上的彗星」", "蛍符「地上の彗星」", THSpell.Lunatic),
            new THSpell(this, chara_莉格露_奈特巴格, "灯符「Firefly Phenomenon」（萤光现象）", "灯符「ファイヤフライフェノメノン」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_莉格露_奈特巴格, "蠢符「Little Bug」（小虫）", "蠢符「リトルバグ」", THSpell.Easy),
            new THSpell(this, chara_莉格露_奈特巴格, "蠢符「Little Bug Storm」（小虫风暴）", "蠢符「リトルバグストーム」", THSpell.Normal),
            new THSpell(this, chara_莉格露_奈特巴格, "蠢符「Night Bug Storm」（夜虫风暴）", "蠢符「ナイトバグストーム」", THSpell.Hard),
            new THSpell(this, chara_莉格露_奈特巴格, "蠢符「Night Bug Tornado」（夜虫龙卷）", "蠢符「ナイトバグトルネード」", THSpell.Lunatic),
            new THSpell(this, chara_莉格露_奈特巴格, "隐虫「永夜蛰居」", "隠蟲「永夜蟄居」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic | THSpell.LastSpell),

            new THSpell(this, chara_米斯蒂娅_萝蕾拉, "声符「枭的夜鸣声」", "声符「梟の夜鳴声」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_米斯蒂娅_萝蕾拉, "声符「木菟的咆哮」", "声符「木菟咆哮」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_米斯蒂娅_萝蕾拉, "蛾符「天蛾的蛊道」", "蛾符「天蛾の蠱道」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_米斯蒂娅_萝蕾拉, "毒符「毒蛾的鳞粉」", "毒符「毒蛾の鱗粉」", THSpell.Hard),
            new THSpell(this, chara_米斯蒂娅_萝蕾拉, "猛毒「毒蛾的黑暗演舞」", "猛毒「毒蛾の暗闇演舞」", THSpell.Lunatic),
            new THSpell(this, chara_米斯蒂娅_萝蕾拉, "鹰符「Ill-Starred Dive」（祸延疾冲）", "鷹符「イルスタードダイブ」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_米斯蒂娅_萝蕾拉, "夜盲「夜雀之歌」", "夜盲「夜雀の歌」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_米斯蒂娅_萝蕾拉, "夜雀「午夜中的合唱指挥」", "夜雀「真夜中のコーラスマスター」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic | THSpell.LastSpell),

            new THSpell(this, chara_上白泽慧音, "产灵「First Pyramid」（最初的金字塔）", "産霊「ファーストピラミッド」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_上白泽慧音, "始符「Ephemerality 137」（短命的137）", "始符「エフェメラリティ137」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_上白泽慧音, "野符「武烈的危机」", "野符「武烈クライシス」", THSpell.Easy),
            new THSpell(this, chara_上白泽慧音, "野符「将门的危机」", "野符「将門クライシス」", THSpell.Normal),
            new THSpell(this, chara_上白泽慧音, "野符「义满的危机」", "野符「義満クライシス」", THSpell.Hard),
            new THSpell(this, chara_上白泽慧音, "野符「GHQ的危机」", "野符「GHQクライシス」", THSpell.Lunatic),
            new THSpell(this, chara_上白泽慧音, "国符「三种神器　剑」", "国符「三種の神器　剣」", THSpell.Easy),
            new THSpell(this, chara_上白泽慧音, "国符「三种神器　玉」", "国符「三種の神器　玉」", THSpell.Normal),
            new THSpell(this, chara_上白泽慧音, "国符「三种神器　镜」", "国符「三種の神器　鏡」", THSpell.Hard),
            new THSpell(this, chara_上白泽慧音, "国体「三种神器　乡」", "国体「三種の神器　郷」", THSpell.Lunatic),
            new THSpell(this, chara_上白泽慧音, "终符「幻想天皇」", "終符「幻想天皇」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_上白泽慧音, "虚史「幻想乡传说」", "虚史「幻想郷伝説」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_上白泽慧音, "未来「高天原」", "未来「高天原」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic | THSpell.LastSpell),
            new THSpell(this, chara_上白泽慧音, "旧史「旧秘境史 -古代史-」", "旧史「旧秘境史　-オールドヒストリー-」", THSpell.Extra),
            new THSpell(this, chara_上白泽慧音, "转世「一条归桥」", "転世「一条戻り橋」", THSpell.Extra),
            new THSpell(this, chara_上白泽慧音, "新史「新幻想史 -现代史-」", "新史「新幻想史　-ネクストヒストリー-」", THSpell.Extra),

            new THSpell(this, chara_博丽灵梦, "梦符「二重结界」", "夢符「二重結界」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_博丽灵梦, "梦境「二重大结界」", "夢境「二重大結界」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_博丽灵梦, "灵符「梦想封印　散」", "霊符「夢想封印　散」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_博丽灵梦, "散灵「梦想封印　寂」", "散霊「夢想封印　寂」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_博丽灵梦, "梦符「封魔阵」", "夢符「封魔陣」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_博丽灵梦, "神技「八方鬼缚阵」", "神技「八方鬼縛陣」", THSpell.Hard),
            new THSpell(this, chara_博丽灵梦, "神技「八方龙杀阵」", "神技「八方龍殺陣」", THSpell.Lunatic),
            new THSpell(this, chara_博丽灵梦, "灵符「梦想封印　集」", "霊符「夢想封印　集」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_博丽灵梦, "回灵「梦想封印　侘」", "回霊「夢想封印　侘」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_博丽灵梦, "境界「二重弹幕结界」", "境界「二重弾幕結界」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_博丽灵梦, "大结界「博丽弹幕结界」", "大結界「博麗弾幕結界」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_博丽灵梦, "神灵「梦想封印　瞬」", "神霊「夢想封印　瞬」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_雾雨魔理沙, "魔符「Milky Way」（银河）", "魔符「ミルキーウェイ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_雾雨魔理沙, "魔空「Asteroid Belt」（小行星带）", "魔空「アステロイドベルト」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_雾雨魔理沙, "魔符「Stardust Reverie」（星尘幻想）", "魔符「スターダストレヴァリエ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_雾雨魔理沙, "黑魔「Event Horizon」（黑洞边缘）", "黒魔「イベントホライズン」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_雾雨魔理沙, "恋符「Non-Directional Laser」（非定向光线）", "恋符「ノンディレクショナルレーザー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_雾雨魔理沙, "恋风「Starlight Typhoon」（星光台风）", "恋風「スターライトタイフーン」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_雾雨魔理沙, "恋符「Master Spark」（极限火花）", "恋符「マスタースパーク」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_雾雨魔理沙, "恋心「Double Spark」（二重火花）", "恋心「ダブルスパーク」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_雾雨魔理沙, "光符「Earth Light Ray」（地球光）", "光符「アースライトレイ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_雾雨魔理沙, "光击「Shoot the Moon」（射月）", "光撃「シュート・ザ・ムーン」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_雾雨魔理沙, "魔炮「Final Spark」（究极火花）", "魔砲「ファイナルスパーク」", THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_雾雨魔理沙, "魔炮「Final Master Spark」（超究极火花）", "魔砲「ファイナルマスタースパーク」", THSpell.Lunatic),

            new THSpell(this, chara_铃仙_优昙华院_因幡, "波符「赤眼催眠(Mind Shaker)」（心灵颤动）", "波符「赤眼催眠（マインドシェイカー）」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_铃仙_优昙华院_因幡, "幻波「赤眼催眠(Mind Blowing)」（心灵风暴）", "幻波「赤眼催眠（マインドブローイング）」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_铃仙_优昙华院_因幡, "狂符「幻视调律(Visionary Tuning)」（幻视调律）", "狂符「幻視調律（ビジョナリチューニング）」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_铃仙_优昙华院_因幡, "狂视「狂视调律(Illusion Seeker)」（幻觉追迹者）", "狂視「狂視調律（イリュージョンシーカー）」　", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_铃仙_优昙华院_因幡, "懒符「生神停止(Idling Wave)」（惰性之波）", "懶符「生神停止（アイドリングウェーブ）」　", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_铃仙_优昙华院_因幡, "懒惰「生神停止(Mind Stopper)」（心灵制止）", "懶惰「生神停止（マインドストッパー）」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_铃仙_优昙华院_因幡, "散符「真实之月(Invisible Full Moon)」（隐形满月）", "散符「真実の月（インビジブルフルムーン）」　", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_铃仙_优昙华院_因幡, "月眼「月兔远程催眠术(Tele-Mesmerism)」（远程催眠）", "月眼「月兎遠隔催眠術（テレメスメリズム）」　", THSpell.Normal | THSpell.Hard | THSpell.Lunatic | THSpell.LastSpell),

            new THSpell(this, chara_八意永琳, "天丸「壶中的天地」", "天丸「壺中の天地」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_八意永琳, "觉神「神代的记忆」", "覚神「神代の記憶」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_八意永琳, "神符「天人的族谱」", "神符「天人の系譜」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_八意永琳, "苏活「生命游戏 -Life Game-」", "蘇活「生命遊戯　-ライフゲーム-」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_八意永琳, "苏生「Rising Game」", "蘇生「ライジングゲーム」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_八意永琳, "操神「Omoikane Device」（思兼装置）", "操神「オモイカネディバイス」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_八意永琳, "神脑「Omoikane Brain」（思兼的头脑）", "神脳「オモイカネブレイン」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_八意永琳, "天咒「Apollo 13」（阿波罗13）", "天呪「アポロ１３」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_八意永琳, "秘术「天文密葬法」", "秘術「天文密葬法」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_八意永琳, "禁药「蓬莱之药」", "禁薬「蓬莱の薬」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic | THSpell.LastSpell),
            new THSpell(this, chara_八意永琳, "药符「壶中的大银河」", "薬符「壺中の大銀河」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_蓬莱山辉夜, "难题「龙颈之玉　-五色的弹丸-」", "難題「龍の頸の玉　-五色の弾丸-」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_蓬莱山辉夜, "神宝「Brilliant Dragon Bullet」（耀眼的龙玉）", "神宝「ブリリアントドラゴンバレッタ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_蓬莱山辉夜, "难题「佛御石之钵　-不碎的意志-」", "難題「仏の御石の鉢　-砕けぬ意思-」　", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_蓬莱山辉夜, "神宝「Buddhist Diamond」（佛体的金刚石）", "神宝「ブディストダイアモンド」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_蓬莱山辉夜, "难题「火鼠的皮衣　-不焦躁的内心-」", "難題「火鼠の皮衣　-焦れぬ心-」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_蓬莱山辉夜, "神宝「Salamander Shield」（火蜥蜴之盾）", "神宝「サラマンダーシールド」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_蓬莱山辉夜, "难题「燕的子安贝　-永命线-」", "難題「燕の子安貝　-永命線-」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_蓬莱山辉夜, "神宝「Life Spring Infinity」（无限的生命之泉）", "神宝「ライフスプリングインフィニティ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_蓬莱山辉夜, "难题「蓬莱的弹枝　-七色的弹幕-」", "難題「蓬莱の弾の枝　-虹色の弾幕-」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_蓬莱山辉夜, "神宝「蓬莱的玉枝　-梦色之乡-」", "神宝「蓬莱の玉の枝　-夢色の郷-」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-初月-」", "「永夜返し　-初月-」", THSpell.Easy | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-新月-」", "「永夜返し　-三日月-」", THSpell.Normal | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-上弦月-」", "「永夜返し　-上つ弓張-」", THSpell.Hard | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-待宵-」", "「永夜返し　-待宵-」", THSpell.Lunatic | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-子之刻-」", "「永夜返し　-子の刻-」", THSpell.Easy | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-子时二刻-」", "「永夜返し　-子の二つ-」", THSpell.Normal | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-子时三刻-」", "「永夜返し　-子の三つ-」", THSpell.Hard | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-子时四刻-」", "「永夜返し　-子の四つ-」", THSpell.Lunatic | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-丑之刻-」", "「永夜返し　-丑の刻-」", THSpell.Easy | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-丑时二刻-」", "「永夜返し　-丑の二つ-」", THSpell.Normal | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-丑时三刻-」", "「永夜返し　-丑三つ時-」", THSpell.Hard | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-丑时四刻-」", "「永夜返し　-丑の四つ-」", THSpell.Lunatic | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-寅之刻-」", "「永夜返し　-寅の刻-」", THSpell.Easy | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-寅时二刻-」", "「永夜返し　-寅の二つ-」", THSpell.Normal | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-寅时三刻-」", "「永夜返し　-寅の三つ-」", THSpell.Hard | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-寅时四刻-」", "「永夜返し　-寅の四つ-」", THSpell.Lunatic | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-朝霭-」", "「永夜返し　-朝靄-」", THSpell.Easy | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-拂晓-」", "「永夜返し　-夜明け-」", THSpell.Normal | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-破晓明星-」", "「永夜返し　-明けの明星-」", THSpell.Hard | THSpell.LastSpell),
            new THSpell(this, chara_蓬莱山辉夜, "「永夜归返　-世间开明-」", "「永夜返し　-世明け-」", THSpell.Lunatic | THSpell.LastSpell),

            new THSpell(this, chara_藤原妹红, "时效「月岩笠的诅咒」", "時効「月のいはかさの呪い」", THSpell.Extra),
            new THSpell(this, chara_藤原妹红, "不死「火鸟　-凤翼天翔-」", "不死「火の鳥　-鳳翼天翔-」", THSpell.Extra),
            new THSpell(this, chara_藤原妹红, "藤原「灭罪寺院伤」", "藤原「滅罪寺院傷」", THSpell.Extra),
            new THSpell(this, chara_藤原妹红, "不死「徐福时空」", "不死「徐福時空」", THSpell.Extra),
            new THSpell(this, chara_藤原妹红, "灭罪「正直者之死」", "滅罪「正直者の死」", THSpell.Extra),
            new THSpell(this, chara_藤原妹红, "虚人「无」", "虚人「ウー」", THSpell.Extra),
            new THSpell(this, chara_藤原妹红, "不灭「不死鸟之尾」", "不滅「フェニックスの尾」", THSpell.Extra),
            new THSpell(this, chara_藤原妹红, "蓬莱「凯风快晴　-Fujiyama Volcano-」", "蓬莱「凱風快晴　-フジヤマヴォルケイノ-」", THSpell.Extra),
            new THSpell(this, chara_藤原妹红, "「Possessed by Phoenix」（不死鸟附体）", "「パゼストバイフェニックス」", THSpell.Extra),
            new THSpell(this, chara_藤原妹红, "「蓬莱人形」", "「蓬莱人形」", THSpell.Extra),
            new THSpell(this, chara_藤原妹红, "「Imperishable Shooting」（不朽的弹幕）", "「インペリシャブルシューティング」", THSpell.Extra | THSpell.LastSpell),

            new THSpell(this, chara_莉格露_奈特巴格, "「不合时令的蝶雨」", "「季節外れのバタフライストーム」", THSpell.LastWord),
            new THSpell(this, chara_米斯蒂娅_萝蕾拉, "「Blind Night-Bird」（失明的夜雀）", "「ブラインドナイトバード」", THSpell.LastWord),
            new THSpell(this, chara_上白泽慧音, "「日出国之天子」", "「日出づる国の天子」", THSpell.LastWord),
            new THSpell(this, chara_上白泽慧音, "「无何有的浄化」", "「無何有浄化」", THSpell.LastWord),
            new THSpell(this, chara_因幡帝, "「Ancient Duper」（远古的欺骗者）", "「エンシェントデューパー」", THSpell.LastWord, "「远古的骗术」"),
            new THSpell(this, chara_铃仙_优昙华院_因幡, "「幻胧月睨(Lunatic Red Eyes)」（疯狂红眼）", "「幻朧月睨（ルナティックレッドアイズ）」　", THSpell.LastWord),
            new THSpell(this, chara_八意永琳, "「天网蛛网捕蝶之法」", "「天網蜘網捕蝶の法」", THSpell.LastWord),
            new THSpell(this, chara_蓬莱山辉夜, "「蓬莱的树海」", "「蓬莱の樹海」", THSpell.LastWord),
            new THSpell(this, chara_藤原妹红, "「不死鸟重生」", "「フェニックス再誕」", THSpell.LastWord),
            new THSpell(this, chara_博丽灵梦, "「梦想天生」", "「夢想天生」", THSpell.LastWord),
            new THSpell(this, chara_雾雨魔理沙, "「Blazing Star」（彗星）", "「ブレイジングスター」", THSpell.LastWord),
            new THSpell(this, chara_十六夜咲夜, "「Deflation World」（收缩的世界）", "「デフレーションワールド」", THSpell.LastWord),
            new THSpell(this, chara_魂魄妖梦, "「待宵反射卫星斩」", "「待宵反射衛星斬」", THSpell.LastWord),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "「猎奇剧团里的怪人」", "「グランギニョル座の怪人」", THSpell.LastWord),
            new THSpell(this, chara_蕾米莉亚_斯卡蕾特, "「Scarlet Destiny」（绯色命运）", "「スカーレットディスティニー」", THSpell.LastWord, "「绯红的宿命」"),
            new THSpell(this, chara_西行寺幽幽子, "「西行寺无余涅槃」", "「西行寺無余涅槃」", THSpell.LastWord),
            new THSpell(this, chara_八云紫, "「深弹幕结界 -梦幻泡影-」", "「深弾幕結界　-夢幻泡影-」", THSpell.LastWord)

        };
        music = new THMusic[]{
            new THMusic("永夜抄 ~ Eastern Night.", this),
            new THMusic("幻视之夜 ~ Ghostly Eyes", this),
            new THMusic("蠢蠢的秋月 ~ Mooned Insect", this),
            new THMusic("夜雀的歌声 ~ Night Bird", this),
            new THMusic("已经只能听见歌声了", this),
            new THMusic("令人怀念的东方之血 ~ Old World", this),
            new THMusic("Plain Asia", this),
            new THMusic("永夜的报应 ~ Imperishable Night", this),
            new THMusic("少女绮想曲 ~ Dream Battle", this),
            new THMusic("恋色Master spark", this),
            new THMusic("灰姑娘的笼子 ~ Kagome-Kagome", this),
            new THMusic("狂气之瞳 ~ Invisible Full Moon", this),
            new THMusic("旅人1969", this),
            new THMusic("千年幻想乡 ~ History of the Moon", this),
            new THMusic("竹取飞翔 ~ Lunatic Princess", this),
            new THMusic("旅人1970", this),
            new THMusic("Extend Ash ~ 蓬莱人", this),
            new THMusic("飘上月球，不死之烟", this),
            new THMusic("月见草", this),
            new THMusic("Eternal Dream ~ 幽玄的枫树", this),
            new THMusic("东方妖怪小町", this)
        };
        players = new THPlayer[]{
            new THPlayer("幻想的结界组"), 
            new THPlayer("咏唱禁咒组"), 
            new THPlayer("梦幻的红魔组"), 
            new THPlayer("幽冥之住人组"), 
            new THPlayer("博丽灵梦"), 
            new THPlayer("八云紫"), 
            new THPlayer("雾雨魔理沙"), 
            new THPlayer("爱丽丝·玛格特罗依德"), 
            new THPlayer("蕾米莉亚·斯卡蕾特"), 
            new THPlayer("十六夜咲夜"), 
            new THPlayer("西行寺幽幽子"), 
            new THPlayer("魂魄妖梦")
        };
    }
}
