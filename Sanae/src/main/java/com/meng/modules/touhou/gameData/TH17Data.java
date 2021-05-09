package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class TH17Data extends THGameData {

    private static TH17Data instance;

    public static TH17Data getInstance() {
        if (instance == null) {
            instance = new TH17Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方鬼形兽";
    }

    @Override
    public String getNameEng() {
        return "Wily Beast and Weakest Creature";
    }

    @Override
    public String getNameAbbr() {
        return "WBaWC";
    }

    private TH17Data() {
        charas = new THCharacter[]{
            new THCharacter("戎璎花", this),
            new THCharacter("牛崎润美", this),
            new THCharacter("庭渡久侘歌", this),
            new THCharacter("吉吊八千慧", this),
            new THCharacter("杖刀偶磨弓", this),
            new THCharacter("埴安神袿姬", this),
            new THCharacter("骊驹早鬼", this)
        };
        THCharacter chara_戎璎花 = getCharacter("戎璎花");
        THCharacter chara_牛崎润美 = getCharacter("牛崎润美");
        THCharacter chara_庭渡久侘歌 = getCharacter("庭渡久侘歌");
        THCharacter chara_吉吊八千慧 = getCharacter("吉吊八千慧");
        THCharacter chara_杖刀偶磨弓 = getCharacter("杖刀偶磨弓");
        THCharacter chara_埴安神袿姬 = getCharacter("埴安神袿姬");
        THCharacter chara_骊驹早鬼 = getCharacter("骊驹早鬼");
        spells = new THSpell[]{
            new THSpell(this, chara_戎璎花, "石符「Stone Woods」（石林）", "石符「ストーンウッズ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_戎璎花, "石符「Stone Conifer」（石头针叶树）", "石符「ストーンコニファー」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_戎璎花, "石符「Children's Limbo」（孩子们的灵薄狱）", "石符「チルドレンズリンボ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_戎璎花, "石符「Adult Children's Limbo」（大孩子们的灵薄狱）", "石符「アダルトチルドレンズリンボ」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_牛崎润美, "石符「Stone Baby」（石之婴儿）", "石符「ストーンベイビー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_牛崎润美, "石符「Heavy Stone Baby」（沉重的石之婴儿）", "石符「ヘビーストーンベイビー」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_牛崎润美, "溺符「三途的沦溺」", "溺符「三途の淪溺」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_牛崎润美, "鬼符「Demon Siege」（魔鬼围城）", "鬼符「デーモンシージ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_牛崎润美, "鬼符「Hungry Demon Siege」（饿鬼围城）", "鬼符「ハングリーデーモンシージ」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_庭渡久侘歌, "水符「分水的试练」", "水符「水配りの試練」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_庭渡久侘歌, "水符「分水的上级试炼」", "水符「水配りの上級試煉」", THSpell.Hard),
            new THSpell(this, chara_庭渡久侘歌, "水符「分水的顶级试炼」", "水符「水配りの極級試煉」", THSpell.Lunatic),
            new THSpell(this, chara_庭渡久侘歌, "光符「瞭望的试练」", "光符「見渡しの試練」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_庭渡久侘歌, "光符「瞭望的上级试炼」", "光符「見渡しの上級試煉」", THSpell.Hard),
            new THSpell(this, chara_庭渡久侘歌, "光符「瞭望的顶级试炼」", "光符「見渡しの極級試煉」", THSpell.Lunatic),
            new THSpell(this, chara_庭渡久侘歌, "鬼符「鬼渡的试练」", "鬼符「鬼渡の試練」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_庭渡久侘歌, "鬼符「鬼渡的上级试炼」", "鬼符「鬼渡の上級試煉」", THSpell.Hard),
            new THSpell(this, chara_庭渡久侘歌, "鬼符「鬼渡的狱级试炼」", "鬼符「鬼渡の獄級試煉」", THSpell.Lunatic),
            new THSpell(this, chara_庭渡久侘歌, "血战「血之分水岭」", "血戦「血の分水嶺」", THSpell.Extra),
            new THSpell(this, chara_庭渡久侘歌, "血战「狱界视线」", "血戦「獄界視線」", THSpell.Extra),
            new THSpell(this, chara_庭渡久侘歌, "血战「全灵鬼渡」", "血戦「全霊鬼渡り」", THSpell.Extra),

            new THSpell(this, chara_吉吊八千慧, "龟符「龟甲地狱」", "亀符「亀甲地獄」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_吉吊八千慧, "鬼符「邪道之畜生」", "鬼符「搦手の畜生」1", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_吉吊八千慧, "鬼符「邪道之狗畜生」", "鬼符「搦手の犬畜生」", THSpell.Hard),
            new THSpell(this, chara_吉吊八千慧, "鬼符「邪道之鬼畜生」", "鬼符「搦手の鬼畜生」", THSpell.Lunatic),
            new THSpell(this, chara_吉吊八千慧, "龙符「龙纹弹」", "龍符「龍紋弾」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_杖刀偶磨弓, "埴轮「弓兵埴轮」", "埴輪「弓兵埴輪」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_杖刀偶磨弓, "埴轮「熟练弓兵埴轮」", "埴輪「熟練弓兵埴輪」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_杖刀偶磨弓, "埴轮「剑士埴轮」", "埴輪「剣士埴輪」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_杖刀偶磨弓, "埴轮「熟练剑士埴轮」", "埴輪「熟練剣士埴輪」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_杖刀偶磨弓, "埴轮「骑马兵埴轮」", "埴輪「騎馬兵埴輪」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_杖刀偶磨弓, "埴轮「熟练骑马兵埴轮」", "埴輪「熟練騎馬兵埴輪」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_杖刀偶磨弓, "埴轮「空洞的无尽兵团」", "埴輪「がらんどうの無尽兵団」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_杖刀偶磨弓, "埴轮「不败的无尽兵团」", "埴輪「不敗の無尽兵団」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_埴安神袿姬, "方形「方形造形术」", "方形「方形造形術」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_埴安神袿姬, "方形「Square Creature」（方形造物）", "方形「スクエアクリーチャー」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_埴安神袿姬, "圆形「正圆造形术」", "円形「真円造形術」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_埴安神袿姬, "圆形「Circle Creature」（圆形造物）", "円形「サークルクリーチャー」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_埴安神袿姬, "线形「线形造形术」", "線形「線形造形術」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_埴安神袿姬, "线形「Linear Creature」（线形造物）", "線形「リニアクリーチャー」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_埴安神袿姬, "埴轮「偶像人马造形术」", "埴輪「偶像人馬造形術」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_埴安神袿姬, "埴轮「Idol Creature」（偶像造物）", "埴輪「アイドルクリーチャー」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_埴安神袿姬, "「鬼形造形术」", "「鬼形造形術」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_埴安神袿姬, "「Geometric Creature」（几何造物）", "「ジオメトリッククリーチャー」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_埴安神袿姬, "「Idola Diabolus」（造形恶魔）", "「イドラディアボルス」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_骊驹早鬼, "劲疾技「Thrilling Shot」（惊险射击）", "勁疾技「スリリングショット」", THSpell.Extra),
            new THSpell(this, chara_骊驹早鬼, "劲疾技「Lightning Neigh」（闪电嘶鸣）", "勁疾技「ライトニングネイ」", THSpell.Extra),
            new THSpell(this, chara_骊驹早鬼, "劲疾技「Dense Cloud」（浓云）", "勁疾技「デンスクラウド」", THSpell.Extra),
            new THSpell(this, chara_骊驹早鬼, "劲疾技「Beast Epidemicity」（兽性感染）", "勁疾技「ビーストエピデミシティ」", THSpell.Extra),
            new THSpell(this, chara_骊驹早鬼, "劲疾技「Triangle Chase」（三角追击）", "勁疾技「トライアングルチェイス」", THSpell.Extra),
            new THSpell(this, chara_骊驹早鬼, "劲疾技「黑色天马流星弹」", "勁疾技「ブラックペガサス流星弾」", THSpell.Extra),
            new THSpell(this, chara_骊驹早鬼, "劲疾技「Muscle Explosion」（肌肉爆破）", "勁疾技「マッスルエクスプロージョン」", THSpell.Extra),
            new THSpell(this, chara_骊驹早鬼, "「Follow Me, Unafraid」（莫慌跟上我）", "「フォロミーアンアフライド」", THSpell.Extra),
            new THSpell(this, chara_骊驹早鬼, "「鬼形的乌合之众」", "「鬼形のホイポロイ」", THSpell.Extra),
            new THSpell(this, chara_骊驹早鬼, "「鬼畜生之所为」", "「鬼畜生の所業」", THSpell.Extra),
        };
        music = new THMusic[]{
            new THMusic("沉默的兽灵", this),
            new THMusic("只有地藏知晓的哀叹", this),
            new THMusic("Jelly Stone", this),
            new THMusic("Lost River", this),
            new THMusic("石之婴儿与水中牛", this),
            new THMusic("不朽的曼珠沙华", this),
            new THMusic("Seraphic Chicken", this),
            new THMusic("Unlocated Hell", this),
            new THMusic("Tortoise Dragon ~ 幸运与不幸", this),
            new THMusic("Beast Metropolis", this),
            new THMusic("陶瓷的杖刀人", this),
            new THMusic("Electric Heritage", this),
            new THMusic("寄世界于偶像 ~ Idoratrize World", this),
            new THMusic("闪耀的弱肉强食法则", this),
            new THMusic("圣德太子的天马 ~ Dark Pegasus", this),
            new THMusic("畜生们的休息", this),
            new THMusic("从地下的归还", this),
            new THMusic("Player's Score", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦", "🐻哥", "🦅哥", "🐶哥"),
            new THPlayer("雾雨魔理沙", "🐻哥", "🦅哥", "🐶哥"),
            new THPlayer("魂魄妖梦", "🐻哥", "🦅哥", "🐶哥")
            //🐻:天星の熊猫
            //🦅:
            //🐶:
        };
    }
}
