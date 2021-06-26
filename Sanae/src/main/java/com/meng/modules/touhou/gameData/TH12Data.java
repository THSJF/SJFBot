package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class TH12Data extends THGameData {

    private static TH12Data instance;

    public static TH12Data getInstance() {
        if (instance == null) {
            instance = new TH12Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方星莲船";
    }

    @Override
    public String getNameEng() {
        return "Undefined Fantastic Object";
    }

    @Override
    public String getNameAbbr() {
        return "UFO";
    }

    private TH12Data() {
        charas = new THCharacter[]{
            new THCharacter("纳兹铃", this, "探宝的小小大将"),
            new THCharacter("多多良小伞", this, "愉快的遗忘之伞"),
            new THCharacter("云居一轮&云山", this, "守护与被守护的大轮"),
            new THCharacter("村纱水蜜", this, "水难事故的念缚灵"),
            new THCharacter("寅丸星", this, "毘沙门天的弟子"),
            new THCharacter("圣白莲", this, "被封印的大魔法师"),
            new THCharacter("封兽鵺", this, "未确认幻想飞行少女")
        };

        THCharacter chara_娜兹玲 = getCharacter("娜兹玲");
        THCharacter chara_多多良小伞 = getCharacter("多多良小伞");
        THCharacter chara_云居一轮 = getCharacter("云居一轮");
        THCharacter chara_村纱水蜜 = getCharacter("村纱水蜜");
        THCharacter chara_寅丸星 = getCharacter("寅丸星");
        THCharacter chara_圣白莲 = getCharacter("圣白莲");
        THCharacter chara_封兽鵺 = getCharacter("封兽鵺");
        
        spells = new THSpell[]{

            new THSpell(this, chara_娜兹玲, "棒符「Busy Rod」（忙碌探知棒）", "棒符「ビジーロッド」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_娜兹玲, "搜符「Rare Metal Detector」（稀有金属探测器）", "捜符「レアメタルディテクター」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_娜兹玲, "搜符「Gold Detector」（黄金探测器）", "捜符「ゴールドディテクター」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_娜兹玲, "视符「Nazrin Pendulum」（娜兹玲灵摆）", "視符「ナズーリンペンデュラム」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_娜兹玲, "视符「高感度娜兹玲灵摆」（高感度娜兹玲灵摆）", "視符「高感度ナズーリンペンデュラム」", THSpell.Hard),
            new THSpell(this, chara_娜兹玲, "守符「Pendulum Guard」（灵摆防御）", "守符「ペンデュラムガード」", THSpell.Lunatic),
            new THSpell(this, chara_娜兹玲, "宝塔「Greatest Treasure」（最优良的宝物）", "宝塔「グレイテストトレジャー」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_多多良小伞, "大轮「唐伞光晕」", "大輪「からかさ後光」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_多多良小伞, "大轮「Hello, Forgotten World」", "大輪「ハロウフォゴットンワールド」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_多多良小伞, "伞符「Parasol Star Symphony」（雨伞的星之交响）", "傘符「パラソルスターシンフォニー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_多多良小伞, "伞符「Parasol Star Memories」（雨伞的星之追忆）", "傘符「パラソルスターメモリーズ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_多多良小伞, "雨符「雨夜怪谈」", "雨符「雨夜の怪談」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_多多良小伞, "雨伞「超防水干爽伞妖」", "雨傘「超撥水かさかさお化け」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_多多良小伞, "化符「遗忘之伞的夜行列车」", "化符「忘れ傘の夜行列車」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_多多良小伞, "化铁「备用伞特急夜晚狂欢号」", "化鉄「置き傘特急ナイトカーニバル」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_多多良小伞, "伞符「大颗的泪雨」", "傘符「大粒の涙雨」", THSpell.Extra),
            new THSpell(this, chara_多多良小伞, "惊雨「台风骤雨」", "驚雨「ゲリラ台風」", THSpell.Extra),
            new THSpell(this, chara_多多良小伞, "光晕「唐伞惊吓闪光」", "後光「からかさ驚きフラッシュ」", THSpell.Extra),

            new THSpell(this, chara_云居一轮, "铁拳「问答无用妖怪拳」", "鉄拳「問答無用の妖怪拳」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_云居一轮, "神拳「凌云地狱冲」", "神拳「雲上地獄突き」", THSpell.Hard),
            new THSpell(this, chara_云居一轮, "神拳「天海地狱冲」", "神拳「天海地獄突き」", THSpell.Lunatic),
            new THSpell(this, chara_云居一轮, "拳符「天网沙袋」", "拳符「天網サンドバッグ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_云居一轮, "连打「云界海妖来袭」", "連打「雲界クラーケン殴り」", THSpell.Hard),
            new THSpell(this, chara_云居一轮, "连打「帝王海妖来袭」", "連打「キングクラーケン殴り」", THSpell.Lunatic),
            new THSpell(this, chara_云居一轮, "拳打「重拳碎击」", "拳打「げんこつスマッシュ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_云居一轮, "溃灭「天上天下连续勾拳」", "潰滅「天上天下連続フック」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_云居一轮, "大喝「守旧尊老之怒眼」", "大喝「時代親父大目玉」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_云居一轮, "忿怒「天变巨眼焚身」", "忿怒「天変大目玉焼き」", THSpell.Hard),
            new THSpell(this, chara_云居一轮, "忿怒「空前绝后巨眼焚身」", "忿怒「空前絶後大目玉焼き」", THSpell.Lunatic),

            new THSpell(this, chara_村纱水蜜, "倾覆「同路人之锚」", "転覆「道連れアンカー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_村纱水蜜, "倾覆「沉没之锚」", "転覆「沈没アンカー」", THSpell.Hard),
            new THSpell(this, chara_村纱水蜜, "倾覆「击沉之锚」", "転覆「撃沈アンカー」", THSpell.Lunatic),
            new THSpell(this, chara_村纱水蜜, "溺符「Deep Vortex」（深海漩涡）", "溺符「ディープヴォーテックス」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_村纱水蜜, "溺符「Sinkable Vortex」（沉底漩涡）", "溺符「シンカブルヴォーテックス」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_村纱水蜜, "港符「Phantom Ship Harbor」（幽灵船之泊）", "湊符「ファントムシップハーバー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_村纱水蜜, "港符「幽灵船之港」", "湊符「幽霊船の港」", THSpell.Hard),
            new THSpell(this, chara_村纱水蜜, "港符「幽灵船永久停泊」", "湊符「幽霊船永久停泊」", THSpell.Lunatic),
            new THSpell(this, chara_村纱水蜜, "幽灵「Sinker Ghost」", "幽霊「シンカーゴースト」", THSpell.Normal),
            new THSpell(this, chara_村纱水蜜, "幽灵「悄然袭来的长勺」", "幽霊「忍び寄る柄杓」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_寅丸星, "宝塔「Radiant Treasure」（光辉之宝）", "宝塔「レイディアントトレジャー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_寅丸星, "宝塔「Radiant Treasure Gun」（光辉宝枪）", "宝塔「レイディアントトレジャーガン」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_寅丸星, "光符「Absolute Justice」（绝对正义）", "光符「アブソリュートジャスティス」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_寅丸星, "光符「正义之威光」", "光符「正義の威光」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_寅丸星, "法力「至宝之独钴杵」", "法力「至宝の独鈷杵」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_寅丸星, "法灯「无瑕佛法之独钴杵」", "法灯「隙間無い法の独鈷杵」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_寅丸星, "光符「净化之魔」", "光符「浄化の魔」", THSpell.Easy | THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_寅丸星, "「Complete Clarification」（完全净化）", "「コンプリートクラリフィケイション」", THSpell.Lunatic),

            new THSpell(this, chara_圣白莲, "魔法「紫云之兆」", "魔法「紫雲のオーメン」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_圣白莲, "吉兆「紫色云路」", "吉兆「紫の雲路」", THSpell.Hard),
            new THSpell(this, chara_圣白莲, "吉兆「极乐的紫色云路」", "吉兆「極楽の紫の雲路」", THSpell.Lunatic),
            new THSpell(this, chara_圣白莲, "魔法「魔界蝶的妖香」", "魔法「魔界蝶の妖香」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_圣白莲, "魔法「Magic Butterfly」（魔法之蝶）", "魔法「マジックバタフライ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_圣白莲, "光魔「Star Maelstrom」（星辰漩涡）", "光魔「スターメイルシュトロム」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_圣白莲, "光魔「魔法银河系」", "光魔「魔法銀河系」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_圣白莲, "大魔法「魔神复诵」", "大魔法「魔神復誦」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_圣白莲, "「圣尼公的气之魔法卷轴」", "「聖尼公のエア巻物」", THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_圣白莲, "超人「圣白莲」", "超人「聖白蓮」", THSpell.Lunatic),
            new THSpell(this, chara_圣白莲, "飞钵「Flying Fantastica」（飞行幻想）", "飛鉢「フライングファンタスティカ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_圣白莲, "飞钵「传说的飞空圆盘」", "飛鉢「伝説の飛空円盤」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_封兽鵺, "妖云「平安时代的黑云」", "妖雲「平安のダーククラウド」", THSpell.Extra),
            new THSpell(this, chara_封兽鵺, "真相不明「愤怒的红色UFO袭来」", "正体不明「忿怒のレッドUFO襲来」", THSpell.Extra),
            new THSpell(this, chara_封兽鵺, "鵺符「鵺的蛇行表演」", "鵺符「鵺的スネークショー」", THSpell.Extra),
            new THSpell(this, chara_封兽鵺, "真相不明「哀愁的蓝色UFO袭来」", "正体不明「哀愁のブルーUFO襲来」", THSpell.Extra),
            new THSpell(this, chara_封兽鵺, "鵺符「弹幕奇美拉」", "鵺符「弾幕キメラ」", THSpell.Extra),
            new THSpell(this, chara_封兽鵺, "真相不明「忠义的绿色UFO袭来」", "正体不明「義心のグリーンUFO襲来」", THSpell.Extra),
            new THSpell(this, chara_封兽鵺, "鵺符「Undefined Darkness」（真相不明的黑暗）", "鵺符「アンディファインドダークネス」", THSpell.Extra),
            new THSpell(this, chara_封兽鵺, "真相不明「恐怖的虹色UFO袭来」", "正体不明「恐怖の虹色ＵＦＯ襲来」", THSpell.Extra),
            new THSpell(this, chara_封兽鵺, "「平安京的恶梦」", "「平安京の悪夢」", THSpell.Extra),
            new THSpell(this, chara_封兽鵺, "恨弓「源三位赖政之弓」", "恨弓「源三位頼政の弓」", THSpell.Extra)
        };
        music = new THMusic[]{
            new THMusic("青空之影", this),
            new THMusic("春之岸边", this),
            new THMusic("小小的贤将", this),
            new THMusic("封闭的云中通路", this),
            new THMusic("请注意万年备用伞", this),
            new THMusic("Sky Ruin", this),
            new THMusic("守旧老爹与前卫少女", this),
            new THMusic("幽灵客船的穿越时空之旅", this),
            new THMusic("Captain Murasa", this),
            new THMusic("魔界地方都市秘境", this),
            new THMusic("虎纹的毘沙门天", this),
            new THMusic("法界之火", this),
            new THMusic("感情的摩天楼 ~ Cosmic Mind", this),
            new THMusic("夜空中的UFO恋曲", this),
            new THMusic("平安时代的外星人", this),
            new THMusic("妖怪寺", this),
            new THMusic("空中的归路 ~ Sky Dream", this),
            new THMusic("Player's Score", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦", "诱导型", "威力重视型"), 
            new THPlayer("雾雨魔理沙", "贯通型", "范围重视型"),
            new THPlayer("东风谷早苗", "诱导型", "广范围炸裂型")
        };
    }
}
