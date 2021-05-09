package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class TH14Data extends THGameData {

    private static TH14Data instance;

    public static TH14Data getInstance() {
        if (instance == null) {
            instance = new TH14Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方辉针城";
    }

    @Override
    public String getNameEng() {
        return "Double Dealing Character";
    }

    @Override
    public String getNameAbbr() {
        return "DDC";
    }

    private TH14Data() {
        charas = new THCharacter[]{
            new THCharacter("琪露诺", this, "湖的冰精"),
            new THCharacter("若鹭姬", this, "栖息于淡水的人鱼"),
            new THCharacter("赤蛮奇", this, "辘轳首的怪奇"),
            new THCharacter("今泉影狼", this, "竹林的Loup-Garou"),
            new THCharacter("九十九八桥", this, "古旧的琴的付丧神"),
            new THCharacter("九十九弁弁", this, "古旧琵琶的付丧神"),
            new THCharacter("鬼人正邪", this, "逆袭的天邪鬼"),
            new THCharacter("少名针妙丸", this, "小人的后裔"),
            new THCharacter("堀川雷鼓", this, "梦幻的打击乐手")
        };
        THCharacter chara_琪露诺 = getCharacter("琪露诺");
        THCharacter chara_若鹭姬 = getCharacter("若鹭姬");
        THCharacter chara_赤蛮奇 = getCharacter("赤蛮奇");
        THCharacter chara_今泉影狼 = getCharacter("今泉影狼");
        THCharacter chara_九十九八桥 = getCharacter("九十九八桥");
        THCharacter chara_九十九弁弁 = getCharacter("九十九弁弁");
        THCharacter chara_鬼人正邪 = getCharacter("鬼人正邪");
        THCharacter chara_少名针妙丸 = getCharacter("少名针妙丸");
        THCharacter chara_堀川雷鼓 = getCharacter("堀川雷鼓");

        spells = new THSpell[]{
            new THSpell(this, chara_琪露诺, "冰符「Ultimate Blizzard」", "氷符「アルティメットブリザード」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_若鹭姬, "水符「Tail Fin Slap」（尾鳍拍击）", "水符「テイルフィンスラップ」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_若鹭姬, "鳞符「Scale Wave」（鳞之波）", "鱗符「スケールウェイブ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_若鹭姬, "鳞符「逆鳞的惊涛」", "鱗符「逆鱗の荒波」", THSpell.Hard),
            new THSpell(this, chara_若鹭姬, "鳞符「逆鳞的大惊涛」", "鱗符「逆鱗の大荒波」", THSpell.Lunatic),

            new THSpell(this, chara_赤蛮奇, "飞符「Flying Head」（飞行之头）", "飛符「フライングヘッド」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_赤蛮奇, "首符「Close-Eye Shot」（闭目射击）", "首符「クローズアイショット」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_赤蛮奇, "首符「辘轳首飞来」", "首符「ろくろ首飛来」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_赤蛮奇, "飞头「Multiplicative Head」（倍增之头）", "飛頭「マルチプリケイティブヘッド」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_赤蛮奇, "飞头「Seventh Head」（第七个头）", "飛頭「セブンズヘッド」", THSpell.Hard),
            new THSpell(this, chara_赤蛮奇, "飞头「Ninth Head」（第九个头）", "飛頭「ナインズヘッド」", THSpell.Lunatic),
            new THSpell(this, chara_赤蛮奇, "飞头「Dullahan Night」（杜拉罕之夜）", "飛頭「デュラハンナイト」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_今泉影狼, "牙符「月下的犬齿」", "牙符「月下の犬歯」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_今泉影狼, "变身「Triangle Fang」（三角齿）", "変身「トライアングルファング」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_今泉影狼, "变身「Star Fang」（星形齿）", "変身「スターファング」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_今泉影狼, "咆哮「Strange Roar」（陌生的咆哮）", "咆哮「ストレンジロア」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_今泉影狼, "咆哮「满月的远吠」", "咆哮「満月の遠吠え」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_今泉影狼, "狼符「Star Ring Pounce」（星环猛扑）", "狼符「スターリングパウンス」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_今泉影狼, "天狼「High-Speed Pounce」（高速猛扑）", "天狼「ハイスピードパウンス」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_九十九八桥, "琴符「诸行无常的琴声」", "琴符「諸行無常の琴の音」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_九十九八桥, "响符「平安的残响」", "響符「平安の残響」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_九十九八桥, "响符「Echo Chamber」（回音之庭）", "響符「エコーチェンバー」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_九十九八桥, "筝曲「下克上送筝曲」", "箏曲「下克上送箏曲」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_九十九八桥, "筝曲「下克上安魂曲」", "箏曲「下克上レクイエム」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_九十九八桥, "弦乐「风暴的合奏」", "弦楽「嵐のアンサンブル」", THSpell.Extra),
            new THSpell(this, chara_九十九八桥, "弦乐「净琉璃世界」", "弦楽「浄瑠璃世界」", THSpell.Extra),
            
            new THSpell(this, chara_九十九弁弁, "平曲「祇园精舍的钟声」", "平曲「祇園精舎の鐘の音」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_九十九弁弁, "怨灵「无耳芳一」", "怨霊「耳無し芳一」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_九十九弁弁, "怨灵「平家的大怨灵」", "怨霊「平家の大怨霊」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_九十九弁弁, "乐符「邪恶的五线谱」", "楽符「邪悪な五線譜」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_九十九弁弁, "乐符「凶恶的五线谱」", "楽符「凶悪な五線譜」", THSpell.Hard),
            new THSpell(this, chara_九十九弁弁, "乐符「Double Score」（双重乐谱）", "楽符「ダブルスコア」", THSpell.Lunatic),
            new THSpell(this, chara_九十九弁弁, "弦乐「风暴的合奏」", "弦楽「嵐のアンサンブル」", THSpell.Extra),
            new THSpell(this, chara_九十九弁弁, "弦乐「净琉璃世界」", "弦楽「浄瑠璃世界」", THSpell.Extra),

            new THSpell(this, chara_鬼人正邪, "欺符「逆针击」", "欺符「逆針撃」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_鬼人正邪, "逆符「镜之国的弹幕」", "逆符「鏡の国の弾幕」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_鬼人正邪, "逆符「Evil in the Mirror」（镜中的邪恶）", "逆符「イビルインザミラー」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_鬼人正邪, "逆符「天地有用」", "逆符「天地有用」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_鬼人正邪, "逆符「天下翻覆」", "逆符「天下転覆」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_鬼人正邪, "逆弓「天壤梦弓」", "逆弓「天壌夢弓」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_鬼人正邪, "逆弓「天壤梦弓的诏敕」", "逆弓「天壌夢弓の詔勅」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_鬼人正邪, "逆转「Reverse Hierarchy」（阶级反转）", "逆転「リバースヒエラルキー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_鬼人正邪, "逆转「Change Air Brave」（变革空勇士）", "逆転「チェンジエアブレイブ」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_少名针妙丸, "小弹「小人的道路」", "小弾「小人の道」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_少名针妙丸, "小弹「小人的荆棘路」", "小弾「小人の茨道」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_少名针妙丸, "小槌「变大吧」", "小槌「大きくなあれ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_少名针妙丸, "小槌「变得更大吧」", "小槌「もっと大きくなあれ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_少名针妙丸, "妖剑「辉针剑」", "妖剣「輝針剣」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_少名针妙丸, "小槌「你给我变大吧」", "小槌「お前が大きくなあれ」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_少名针妙丸, "「进击的小人」", "「進撃の小人」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_少名针妙丸, "「Wall of Issun」（一寸之壁）", "「ウォールオブイッスン」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_少名针妙丸, "「Hop-o'-My-Thumb Seven」（七个小拇指）", "「ホップオマイサムセブン」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_少名针妙丸, "「七个一寸法师」", "「七人の一寸法師」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_堀川雷鼓, "一鼓「暴乱宫太鼓」", "一鼓「暴れ宮太鼓」", THSpell.Extra),
            new THSpell(this, chara_堀川雷鼓, "二鼓「怨灵绫鼓」", "二鼓「怨霊アヤノツヅミ」", THSpell.Extra),
            new THSpell(this, chara_堀川雷鼓, "三鼓「午夜零时的三振」", "三鼓「午前零時のスリーストライク」", THSpell.Extra),
            new THSpell(this, chara_堀川雷鼓, "死鼓「Land Percuss」（轻敲大地）", "死鼓「ランドパーカス」", THSpell.Extra),
            new THSpell(this, chara_堀川雷鼓, "五鼓「雷电拨浪鼓」", "五鼓「デンデン太鼓」", THSpell.Extra),
            new THSpell(this, chara_堀川雷鼓, "六鼓「Alternate Sticking」（交替打击法）", "六鼓「オルタネイトスティッキング」", THSpell.Extra),
            new THSpell(this, chara_堀川雷鼓, "七鼓「高速和太鼓火箭」", "七鼓「高速和太鼓ロケット」", THSpell.Extra),
            new THSpell(this, chara_堀川雷鼓, "八鼓「雷神之怒」", "八鼓「雷神の怒り」", THSpell.Extra),
            new THSpell(this, chara_堀川雷鼓, "「Blue Lady Show」（蓝色佳人的演出）", "「ブルーレディショー」", THSpell.Extra),
            new THSpell(this, chara_堀川雷鼓, "「Pristine beat」（原初的节拍）", "「プリスティンビート」", THSpell.Extra),
        };
        music = new THMusic[]{
            new THMusic("不可思议的驱魔棒", this),
            new THMusic("Mist Lake", this),
            new THMusic("秘境的人鱼", this),
            new THMusic("往来于运河的人与妖", this),
            new THMusic("柳树下的杜拉罕", this),
            new THMusic("满月的竹林", this),
            new THMusic("孤独的狼人", this),
            new THMusic("Magical Storm", this),
            new THMusic("幻想净琉璃", this),
            new THMusic("沉向空中的辉针城", this),
            new THMusic("Reverse Ideology", this),
            new THMusic("针小棒大的天守阁", this),
            new THMusic("辉光之针的小人族 ~ Little Princess", this),
            new THMusic("魔力的雷云", this),
            new THMusic("原初的节拍 ~ Pristine Beat", this),
            new THMusic("小槌的魔力", this),
            new THMusic("非常非常神奇的道具们", this),
            new THMusic("Player's Score", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦", "使用妖器", "不使用妖器"),
            new THPlayer("雾雨魔理沙", "使用妖器", "不使用妖器"),
            new THPlayer("十六夜咲夜", "使用妖器", "不使用妖器")
        };
    }
}
