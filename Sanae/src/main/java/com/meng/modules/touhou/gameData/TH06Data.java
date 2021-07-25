package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class TH06Data extends THGameData {

    private static TH06Data instance;

    public static TH06Data getInstance() {
        if (instance == null) {
            instance = new TH06Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方红魔乡";
    }

    @Override
    public String getNameEng() {
        return "the Embodiment of Scarlet Devil";
    }

    @Override
    public String getNameAbbr() {
        return "EoSD";
    }

    private TH06Data() {
        charas = new THCharacter[]{
            new THCharacter("露米娅", this, "宵暗的妖怪"),
            new THCharacter("大妖精", this, "(无)"),
            new THCharacter("琪露诺", this, "湖上的冰精"),
            new THCharacter("红美铃", this, "华人小姑娘"),
            new THCharacter("帕秋莉·诺蕾姬", this, "知识与避世的少女"),
            new THCharacter("十六夜咲夜", this, "红魔馆的女仆"),
            new THCharacter("蕾米莉亚·斯卡雷特", this, "永远幼小的红月"),
            new THCharacter("芙兰朵露·斯卡雷特", this, "恶魔之妹")
        };
        THCharacter chara_露米娅 = getCharacter("露米娅");
        THCharacter chara_琪露诺 = getCharacter("琪露诺");
        THCharacter chara_红美铃 = getCharacter("红美铃");
        THCharacter chara_帕秋莉_诺蕾姬 = getCharacter("帕秋莉·诺蕾姬");
        THCharacter chara_十六夜咲夜 = getCharacter("十六夜咲夜");
        THCharacter chara_蕾米莉亚_斯卡蕾特 = getCharacter("蕾米莉亚·斯卡蕾特");
        THCharacter chara_芙兰朵露_斯卡蕾特 = getCharacter("芙兰朵露·斯卡蕾特");
        
        spells = new THSpell[]{

            new THSpell(this, chara_露米娅, "月符「Moonlight Ray」（月光）", "月符「ムーンライトレイ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_露米娅, "夜符「Night Bird」（夜雀）", "夜符「ナイトバード」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_露米娅, "暗符「Demarcation」（境界线）", "闇符「ディマーケイション」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_琪露诺, "冰符「Icicle Fall」（冰瀑）", "氷符「アイシクルフォール」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_琪露诺, "雹符「Hailstorm」（冰雹暴风）", "雹符「ヘイルストーム」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_琪露诺, "冻符「Perfect Freeze」（完美冻结）", "凍符「パーフェクトフリーズ」　", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_琪露诺, "雪符「Diamond Blizzard」（钻石风暴）", "雪符「ダイアモンドブリザード」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            
            new THSpell(this, chara_红美铃, "华符「芳华绚烂」", "華符「芳華絢爛」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_红美铃, "华符「Selaginella 9」", "華符「セラギネラ９」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_红美铃, "虹符「彩虹的风铃」", "虹符「彩虹の風鈴」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_红美铃, "幻符「华想梦葛」", "幻符「華想夢葛」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_红美铃, "彩符「彩雨」", "彩符「彩雨」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_红美铃, "彩符「彩光乱舞」", "彩符「彩光乱舞」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_红美铃, "彩符「极彩台风」", "彩符「極彩颱風」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_帕秋莉_诺蕾姬, "火符「Agni Shine」（火神之光）", "火符「アグニシャイン」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "水符「Princess Undine」（水精公主）", "水符「プリンセスウンディネ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "木符「Sylphy Horn」（风灵的角笛）", "木符「シルフィホルン」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "土符「Lazy Trilithon」（慵懒三石塔）2", "土符「レイジィトリリトン」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "金符「Metal Fatigue」（金属疲劳）", "金符「メタルファティーグ」", THSpell.Normal),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "火符「Agni Shine上级」（火神之光 上级）", "火符「アグニシャイン上級」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "木符「Sylphy Horn上级」（风灵的角笛 上级）", "木符「シルフィホルン上級」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "土符「Lazy Trilithon上级」（慵懒三石塔 上级）", "土符「レイジィトリリトン上級」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "火符「Agni Radiance」（火神的光辉）", "火符「アグニレイディアンス」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "水符「Bury in Lake」（湖葬）", "水符「ベリーインレイク」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "木符「Green Storm」（翠绿风暴）", "木符「グリーンストーム」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "土符「Trilithon Shake」（三石塔的震动）", "土符「トリリトンシェイク」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "金符「Silver Dragon」（银龙）", "金符「シルバードラゴン」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "火&土符「Lava Cromlech」（环状熔岩带）", "火＆土符「ラーヴァクロムレク」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "木&火符「Forest Blaze」（森林大火）", "木＆火符「フォレストブレイズ」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "水&木符「Water Elf」（水精灵）", "水＆木符「ウォーターエルフ」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "金&水符「Mercury Poison」（水银之毒）", "金＆水符「マーキュリポイズン」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "土&金符「Emerald Megalith」（翡翠巨石）", "土＆金符「エメラルドメガリス」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "月符「Silent Selene」（沉静的月神）", "月符「サイレントセレナ」", THSpell.Extra),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "日符「Royal Flare」（皇家烈焰）", "日符「ロイヤルフレア」", THSpell.Extra),
            new THSpell(this, chara_帕秋莉_诺蕾姬, "火水木金土符「贤者之石」", "火水木金土符「賢者の石」", THSpell.Extra),
            new THSpell(this, chara_十六夜咲夜, "奇术「Misdirection」（误导）", "奇術「ミスディレクション」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_十六夜咲夜, "奇术「幻惑Misdirection」（幻惑误导）", "奇術「幻惑ミスディレクション」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_十六夜咲夜, "幻在「Clock Corpse」（钟表的残骸）", "幻在「クロックコープス」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_十六夜咲夜, "幻幽「Jack the Ludo Bile」（迷幻杰克）", "幻幽「ジャック・ザ・ルドビレ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_十六夜咲夜, "幻象「Luna Clock」（月神之钟）", "幻象「ルナクロック」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_十六夜咲夜, "幻世「The World」（世界）", "幻世「ザ・ワールド」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_十六夜咲夜, "女仆秘技「操弄玩偶」", "メイド秘技「操りドール」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_十六夜咲夜, "女仆秘技「杀人木偶」", "メイド秘技「殺人ドール」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_十六夜咲夜, "奇术「Eternal Meek」（永恒的温柔）", "奇術「エターナルミーク」", THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_蕾米莉亚_斯卡蕾特, "天罚「Star of David」（大卫之星）", "天罰「スターオブダビデ」", THSpell.Normal),
            new THSpell(this, chara_蕾米莉亚_斯卡蕾特, "神罚「年幼的恶魔之王」", "神罰「幼きデーモンロード」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_蕾米莉亚_斯卡蕾特, "冥符「红色的冥界」", "冥符「紅色の冥界」", THSpell.Normal),
            new THSpell(this, chara_蕾米莉亚_斯卡蕾特, "狱符「千根针的针山」", "獄符「千本の針の山」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_蕾米莉亚_斯卡蕾特, "诅咒「弗拉德·特佩斯的诅咒」", "呪詛「ブラド・ツェペシュの呪い」", THSpell.Normal),
            new THSpell(this, chara_蕾米莉亚_斯卡蕾特, "神术「吸血鬼幻想」", "神術「吸血鬼幻想」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_蕾米莉亚_斯卡蕾特, "红符「Scarlet Shoot」（绯红射击）", "紅符「スカーレットシュート」", THSpell.Normal),
            new THSpell(this, chara_蕾米莉亚_斯卡蕾特, "红符「Scarlet Meister」（绯红之主）", "紅符「スカーレットマイスタ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_蕾米莉亚_斯卡蕾特, "「Red Magic」（红魔法）", "「レッドマジック」", THSpell.Normal),
            new THSpell(this, chara_蕾米莉亚_斯卡蕾特, "「红色的幻想乡」", "「紅色の幻想郷」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_芙兰朵露_斯卡蕾特, "禁忌「Cranberry Trap」（红莓陷阱）", "禁忌「クランベリートラップ」", THSpell.Extra),
            new THSpell(this, chara_芙兰朵露_斯卡蕾特, "禁忌「Laevatein」（莱瓦汀）", "禁忌「レーヴァテイン」", THSpell.Extra),
            new THSpell(this, chara_芙兰朵露_斯卡蕾特, "禁忌「Four of a Kind」（四重存在）", "禁忌「フォーオブアカインド」", THSpell.Extra),
            new THSpell(this, chara_芙兰朵露_斯卡蕾特, "禁忌「Kagome Kagome」（笼中鸟）", "禁忌「カゴメカゴメ」", THSpell.Extra),
            new THSpell(this, chara_芙兰朵露_斯卡蕾特, "禁忌「恋之迷宫」", "禁忌「恋の迷路」", THSpell.Extra),
            new THSpell(this, chara_芙兰朵露_斯卡蕾特, "禁弹「Starbow Break」（星弧破碎）", "禁弾「スターボウブレイク」", THSpell.Extra),
            new THSpell(this, chara_芙兰朵露_斯卡蕾特, "禁弹「Catadioptric」（折反射）", "禁弾「カタディオプトリック」", THSpell.Extra),
            new THSpell(this, chara_芙兰朵露_斯卡蕾特, "禁弹「刻着过去的钟表」", "禁弾「過去を刻む時計」", THSpell.Extra),
            new THSpell(this, chara_芙兰朵露_斯卡蕾特, "秘弹「之后就一个人都没有了吗？」", "秘弾「そして誰もいなくなるか？」", THSpell.Extra),
            new THSpell(this, chara_芙兰朵露_斯卡蕾特, "QED「495年的波纹」", "QED「495年の波紋」", THSpell.Extra)

        };
        music = new THMusic[]{
            new THMusic("比赤色更红的梦", this),
            new THMusic("如鬼灯般的红色之魂", this),
            new THMusic("妖魔夜行", this),
            new THMusic("Lunate Elf", this),
            new THMusic("活泼的纯情小姑娘", this),
            new THMusic("上海红茶馆 ~ Chinese Tea", this),
            new THMusic("明治十七年的上海爱丽丝", this),
            new THMusic("巴瓦鲁魔法图书馆", this),
            new THMusic("Locked Girl ~ 少女密室", this),
            new THMusic("女仆与血之怀表", this),
            new THMusic("月时计 ~ Luna Dial", this),
            new THMusic("特佩斯的年幼末裔", this),
            new THMusic("献给已逝公主的七重奏", this),
            new THMusic("魔法少女们的百年祭", this),
            new THMusic("U.N.OWEN就是她吗？", this),
            new THMusic("比红色更虚无的永远", this),
            new THMusic("红楼 ~ Eastern Dream...", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦", "灵符", "梦符"),
            new THPlayer("雾雨魔理沙", "魔符", "恋符")
        };
    }
}
