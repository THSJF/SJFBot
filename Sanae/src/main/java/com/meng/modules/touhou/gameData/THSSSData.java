package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class THSSSData extends THGameData {

    private static THSSSData instance;

    public static THSSSData getInstance() {
        if (instance == null) {
            instance = new THSSSData();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方夏夜祭";
    }

    @Override
    public String getNameEng() {
        return "Shining Shooting Star";
    }

    @Override
    public String getNameAbbr() {
        return "SSS";
    }

    private THSSSData() {
        charas = new THCharacter[]{
            new THCharacter("娅米", this, "夏夜的微风妖精"),
            new THCharacter("桃雨洛熙", this, "迅雷的长弓使"),
            new THCharacter("古灵灵", this, "调皮的火精灵"),
            new THCharacter("夏青柳", this, "等待千年的妖柳"),
            new THCharacter("魅明魔影", this, "倒映一切的魔镜"),
            new THCharacter("桃雨洛薰", this, "星夜祭典大主持"),
            new THCharacter("梦璃夜天星", this, "成为星星的传说"),
            new THCharacter("雾隐梨花", this, "东之国度的仙人")
        };

        THCharacter chara_娅米 = getCharacter("娅米");
        THCharacter chara_桃雨洛熙 = getCharacter("桃雨洛熙");
        THCharacter chara_古灵灵 = getCharacter("古灵灵");
        THCharacter chara_夏青柳 = getCharacter("夏青柳");
        THCharacter chara_魅明魔影 = getCharacter("魅明魔影");
        THCharacter chara_桃雨洛薰 = getCharacter("桃雨洛薰");
        THCharacter chara_梦璃夜天星 = getCharacter("梦璃夜天星");
        THCharacter chara_雾隐梨花 = getCharacter("雾隐梨花");   

        spells = new THSpell[]{
            new THSpell(this, chara_娅米, "微风「摇曳的三叶草」 ", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_娅米, "星符「夏夜的晚星」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_娅米, "四叶「大自然之花环」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_娅米, "夜风「轮回的幸运之星」", "", THSpell.FinalSpellCard),

            new THSpell(this, chara_桃雨洛熙, "魔矢「无限之箭」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_桃雨洛熙, "魔矢「轮回的万箭」", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_桃雨洛熙, "天诏「琴引山之诏琴」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_桃雨洛熙, "异器「琴引山之神弓」", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_桃雨洛熙, "使魔「自动狩猎装置」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_桃雨洛熙, "魔狩「星夜的狩猎舞曲」", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_桃雨洛熙, "天箭「星之箭」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_桃雨洛熙, "华箭「紫星之花」", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_桃雨洛熙, "秘术「龙卷旋风之箭」", "", THSpell.FinalSpellCard),
            new THSpell(this, chara_桃雨洛熙, "杀箭「新星爆炸箭」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_古灵灵, "火符「逼近的火焰」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_古灵灵, "火符「燃烧地狱火海」", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_古灵灵, "火光「火精灵之灾」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_古灵灵, "火光「午夜的大火灾」", "", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_夏青柳, "木灵「大屋津姬命」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_夏青柳, "木魅「枛津姬命」", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_夏青柳, "妖符「柳下妖蝉」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_夏青柳, "绿符「柳叶风暴」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_夏青柳, "绿符「舞动的翠绿之风」", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_夏青柳, "叶符「德律阿得斯的圣枝」", "", THSpell.FinalSpellCard),
            new THSpell(this, chara_夏青柳, "青符「东之国度的树叶」", "", THSpell.Extra),
            new THSpell(this, chara_夏青柳, "柳符「新生的柳叶绿芽」", "", THSpell.Extra),
            new THSpell(this, chara_夏青柳, "蝶恋「千年之愿」", "", THSpell.Extra),

            new THSpell(this, chara_魅明魔影, "魔镜「幻影之境」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_魅明魔影, "魔镜「虚无幽幻之境」", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "镜符「琉璃色银河裂隙」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "镜符「月季色红梦裂隙」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "镜符「若竹色翠石裂隙」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "镜符「山吹色波纹裂隙」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "岚符「大孔雀风暴」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "阴阳「神鬼一击」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "空想「恋之宣告」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "流光「幻影魔炮」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "秘术「遗忘之封印」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "辉针「闪耀万针剑」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "「古明地恋的消失」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "星符「极速彗星」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "虹奥义「光幕结界」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魅明魔影, "境界「新星之梦」", "", THSpell.FinalSpellCard),

            new THSpell(this, chara_桃雨洛薰, "火舞「夏夜流火」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_桃雨洛薰, "机关「火焰发射装置」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_桃雨洛薰, "焰机「星夜的火焰舞曲」", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_桃雨洛薰, "混沌「守护的心意」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_桃雨洛薰, "混沌「守护之心」", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_桃雨洛薰, "焰符「原始的烈焰」", "", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_桃雨洛薰, "焰符「八百里的烈焰」", "", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_桃雨洛薰, "「星神降临仪式」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_桃雨洛薰, "烈焰「焚秽净世」", "", THSpell.FinalSpellCard),

            new THSpell(this, chara_梦璃夜天星, "许愿星「星火辉迹」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_梦璃夜天星, "耀星「白夜永昼之光」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_梦璃夜天星, "星雨「故乡之星」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_梦璃夜天星, "星图「宇宙棋盘」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_梦璃夜天星, "「弹幕之海」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_梦璃夜天星, "「流星神话」", "", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_梦璃夜天星, "第一个愿望「无尽之财」", "", THSpell.FinalSpellCard),
            new THSpell(this, chara_梦璃夜天星, "第二个愿望「无穷之力」", "", THSpell.FinalSpellCard),
            new THSpell(this, chara_梦璃夜天星, "第三个愿望「永生不灭」", "", THSpell.FinalSpellCard),

            new THSpell(this, chara_雾隐梨花, "风符「环形气流」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "绽放「爱意的玫瑰」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "冬临「初雪之寒径」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "迴梦「月色琉璃光」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "速风「双龙卷」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "幻花「仙晶玉莲」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "仙器「太乙冰凌」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "幽光「幻月蝶」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "风切「狂风交错」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "萱草「忘却之爱」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "霜冻「寒霜封印」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "倒映「天之月水之月」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "舞风「阵风螺旋」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "荣符「生长与枯萎的循环」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "米雪「冷云雪晶」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "仙幻「月葬」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "飓风「台风眼」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "花咲「百花缭乱」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "六花「冰结界的雪花阵」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "净符「月光世界」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "疾风「旋风绝息斩」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "花符「云上的花田」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "雪幕「冰雪流凌」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "皎符「星月炮」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "「雾隐流剑技~暴风疾斩」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "「雾隐流剑技~血染之樱」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "「雾隐流剑技~落雪凝霜」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "「雾隐流剑技~天地银蛇」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "仙境「风花雪月」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "仙蝶「无限的彩灵」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "「雾隐之蝶」", "", THSpell.Extra),
            new THSpell(this, chara_雾隐梨花, "霞符「幻紫仙门」", "", THSpell.FinalSpellCard)        };

        music = new THMusic[]{
            new THMusic("被遗忘的久远星辉", this),
            new THMusic("宁静夏夜的微风", this),
            new THMusic("喧嚣吧！在这不眠之夜", this),
            new THMusic("灯火竹林", this),
            new THMusic("疾风闪电", this),
            new THMusic("木灵们的夏夜祭", this),
            new THMusic("青柳传说", this),
            new THMusic("万花世界的光与影", this),
            new THMusic("镜中的幻象", this),
            new THMusic("记忆中遥远的星星", this),
            new THMusic("引燃夜空的星火", this),
            new THMusic("银河的彼方", this),
            new THMusic("琉璃星之愿 ~ Dream Star's wish", this),
            new THMusic("闪耀在世界尽头", this),
            new THMusic("来自仙界的新风", this),
            new THMusic("风中花，雪中月", this),
            new THMusic("晚星之梦", this),
            new THMusic("璀璨无比的星空 ~ Star Dream.", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦"),
            new THPlayer("雾雨魔理沙"),
            new THPlayer("东风谷早苗"),
            new THPlayer("古明地恋"),
        };
    }
}
