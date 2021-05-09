package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class TH15Data extends THGameData {

    private static TH15Data instance;

    public static TH15Data getInstance() {
        if (instance == null) {
            instance = new TH15Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方绀珠传";
    }

    @Override
    public String getNameEng() {
        return "Legacy of Lunatic Kingdom";
    }

    @Override
    public String getNameAbbr() {
        return "LoLK";
    }

    private TH15Data() {
        charas = new THCharacter[] {
            new THCharacter("清兰", this, "浅葱色的Eagle Rabbit"),
            new THCharacter("铃瑚", this, "橘色的Eagle Rabbit"),
            new THCharacter("哆来咪·苏伊特", this, "梦之支配者"),
            new THCharacter("稀神探女", this, "招来口舌之祸的女神"),
            new THCharacter("克劳恩皮丝", this, "地狱的妖精"),
            new THCharacter("纯狐", this, "(无)"),
            new THCharacter("赫卡提亚·拉碧斯拉祖利", this, "地狱的女神")
        };

        THCharacter chara_清兰 = getCharacter("清兰");
        THCharacter chara_铃瑚 = getCharacter("铃瑚");
        THCharacter chara_哆来咪_苏伊特 = getCharacter("哆来咪·苏伊特");
        THCharacter chara_稀神探女 = getCharacter("稀神探女");
        THCharacter chara_克劳恩皮丝 = getCharacter("克劳恩皮丝");
        THCharacter chara_纯狐 = getCharacter("纯狐");
        THCharacter chara_赫卡提亚_拉碧斯拉祖利 = getCharacter("赫卡提亚·拉碧斯拉祖利"); 

        spells = new THSpell[]{
            new THSpell(this, chara_清兰, "凶弹「Speed Strike」（高速撞击）", "凶弾「スピードストライク」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_清兰, "弹符「Eagle Shooting」（鹰在射击）", "弾符「イーグルシューティング」", THSpell.Easy | THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_清兰, "弹符「鹰已击中」", "弾符「鷹は撃ち抜いた」", THSpell.Lunatic),
            new THSpell(this, chara_清兰, "铳符「Lunatic Gun」（月狂之枪）", "銃符「ルナティックガン」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_铃瑚, "兔符「Strawberry Dango」（草莓团子）", "兎符「ストロベリーダンゴ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_铃瑚, "兔符「Berry Berry Dango」（浆果浆果团子）", "兎符「ベリーベリーダンゴ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_铃瑚, "兔符「Dango Influence」（团子影响力）", "兎符「ダンゴインフリューエンス」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_铃瑚, "赏月「September Fullmoon」（九月的满月）", "月見「セプテンバーフルムーン」", THSpell.Easy | THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_铃瑚, "赏月酒「Lunatic September」（月狂的九月）", "月見酒「ルナティックセプテンバー」", THSpell.Lunatic),

            new THSpell(this, chara_哆来咪_苏伊特, "梦符「绯红色的噩梦」", "夢符「緋色の悪夢」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_哆来咪_苏伊特, "梦符「绯红色的压迫噩梦」", "夢符「緋色の圧迫悪夢」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_哆来咪_苏伊特, "梦符「蔚蓝色的愁梦」", "夢符「藍色の愁夢」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_哆来咪_苏伊特, "梦符「蔚蓝色的愁三重梦」", "夢符「藍色の愁三重夢」", THSpell.Hard),
            new THSpell(this, chara_哆来咪_苏伊特, "梦符「愁永远之梦」", "夢符「愁永遠の夢」", THSpell.Lunatic),
            new THSpell(this, chara_哆来咪_苏伊特, "梦符「刈安色的迷梦」", "夢符「刈安色の迷夢」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_哆来咪_苏伊特, "梦符「刈安色的错综迷梦」", "夢符「刈安色の錯綜迷夢」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_哆来咪_苏伊特, "梦符「Dream Catcher」（捕梦网）", "夢符「ドリームキャッチャー」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_哆来咪_苏伊特, "梦符「苍蓝色的Dream Catcher」（苍蓝色的捕梦网）", "夢符「蒼色のドリームキャッチャー」", THSpell.Hard),
            new THSpell(this, chara_哆来咪_苏伊特, "梦符「梦我梦中」", "夢符「夢我夢中」", THSpell.Lunatic),
            new THSpell(this, chara_哆来咪_苏伊特, "月符「绀色的狂梦」", "月符「紺色の狂夢」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_哆来咪_苏伊特, "蝴蝶「Butterfly Supplantation」（取而代之的蝴蝶）", "胡蝶「バタフライサプランテーション」", THSpell.Extra),
            new THSpell(this, chara_哆来咪_苏伊特, "超特急「Dream Express」（梦幻快车）", "超特急「ドリームエクスプレス」", THSpell.Extra),
            new THSpell(this, chara_哆来咪_苏伊特, "爬梦「Creeping Bullet」（爬行的子弹）", "這夢「クリーピングバレット」", THSpell.Extra),

            new THSpell(this, chara_稀神探女, "玉符「乌合之咒」", "玉符「烏合の呪」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_稀神探女, "玉符「乌合的逆咒」", "玉符「烏合の逆呪」", THSpell.Hard),
            new THSpell(this, chara_稀神探女, "玉符「乌合的二重咒」", "玉符「烏合の二重呪」", THSpell.Lunatic),
            new THSpell(this, chara_稀神探女, "玉符「秽身探知型水雷」", "玉符「穢身探知型機雷」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_稀神探女, "玉符「秽身探知型水雷　改」", "玉符「穢身探知型機雷　改」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_稀神探女, "玉符「众神的弹冠」", "玉符「神々の弾冠」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_稀神探女, "玉符「众神的光辉弹冠」", "玉符「神々の光り輝く弾冠」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_稀神探女, "「孤翼的白鹭」", "「片翼の白鷺」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_克劳恩皮丝, "狱符「Hell Eclipse」（地狱月食）", "獄符「ヘルエクリプス」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_克劳恩皮丝, "狱符「地狱之蚀」", "獄符「地獄の蝕」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_克劳恩皮丝, "狱符「Flash and Stripe」（闪光与条纹）", "獄符「フラッシュアンドストライプ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_克劳恩皮丝, "狱符「Star and Stripe」（星与条纹）", "獄符「スターアンドストライプ」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_克劳恩皮丝, "狱炎「Graze Inferno」（擦弹地狱火）", "獄炎「グレイズインフェルノ」", THSpell.Easy | THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_克劳恩皮丝, "狱炎「擦弹的狱意」", "獄炎「かすりの獄意」", THSpell.Lunatic),
            new THSpell(this, chara_克劳恩皮丝, "地狱「Striped Abyss」（条纹状的深渊）", "地獄「ストライプドアビス」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_克劳恩皮丝, "「Fake Apollo」（伪阿波罗）", "「フェイクアポロ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_克劳恩皮丝, "「阿波罗捏造说」", "「アポロ捏造説」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_纯狐, "「掌上的纯光」", "「掌の純光」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_纯狐, "「杀意的百合」", "「殺意の百合」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_纯狐, "「原始的神灵界」", "「原始の神霊界」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_纯狐, "「现代的神灵界」", "「現代の神霊界」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_纯狐, "「战栗的寒冷之星」", "「震え凍える星」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_纯狐, "「纯粹的狂气」", "「純粋なる狂気」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_纯狐, "「溢出的瑕秽」", "「溢れ出る瑕穢」", THSpell.Easy | THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_纯狐, "「地上秽的纯化」", "「地上穢の純化」", THSpell.Lunatic),
            new THSpell(this, chara_纯狐, "纯符「Purely Bullet Hell」（单纯的子弹地狱）", "純符「ピュアリーバレットヘル」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_纯狐, "纯符「纯粹的弹幕地狱」", "純符「純粋な弾幕地獄」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_纯狐, "「用于逼死瓮中鼠的单纯弹幕」", "「袋の鼠を追い詰める為の単純な弾幕」", THSpell.Extra),
            new THSpell(this, chara_纯狐, "「用于杀人的纯粹弹幕」", "「人を殺める為の純粋な弾幕」", THSpell.Extra),
            new THSpell(this, chara_纯狐, "「最初与最后的无名弹幕」", "「最初で最後の無名の弾幕」", THSpell.Extra),

            new THSpell(this, chara_赫卡提亚_拉碧斯拉祖利, "异界「逢魔之刻」", "異界「逢魔ガ刻」", THSpell.Extra),
            new THSpell(this, chara_赫卡提亚_拉碧斯拉祖利, "地球「邪秽在身」", "地球「邪穢在身」", THSpell.Extra),
            new THSpell(this, chara_赫卡提亚_拉碧斯拉祖利, "月「阿波罗反射镜」（阿波罗反射镜）", "月「アポロ反射鏡」", THSpell.Extra),
            new THSpell(this, chara_赫卡提亚_拉碧斯拉祖利, "异界「地狱的非理想弹幕」（地狱的非理想弹幕）", "異界「地獄のノンイデアル弾幕」", THSpell.Extra),
            new THSpell(this, chara_赫卡提亚_拉碧斯拉祖利, "地球「落向地狱的雨」", "地球「地獄に降る雨」", THSpell.Extra),
            new THSpell(this, chara_赫卡提亚_拉碧斯拉祖利, "月「Lunatic Impact」（月狂冲击）", "月「ルナティックインパクト」", THSpell.Extra),
            new THSpell(this, chara_赫卡提亚_拉碧斯拉祖利, "「Trinitarian Rhapsody」（三位一体论狂想曲）", "「トリニタリアンラプソディ」", THSpell.Extra),
            new THSpell(this, chara_赫卡提亚_拉碧斯拉祖利, "「最初与最后的无名弹幕」", "「最初で最後の無名の弾幕」", THSpell.Extra),
        };
        music = new THMusic[]{
            new THMusic("宇宙巫女现身", this),
            new THMusic("忘不了,那曾依藉的绿意", this),
            new THMusic("兔已着陆", this),
            new THMusic("湖上倒映着洁净的月光", this),
            new THMusic("九月的南瓜", this),
            new THMusic("飞翔于宇宙的不可思议巫女", this),
            new THMusic("永远的春梦", this),
            new THMusic("冻结的永远之都", this),
            new THMusic("逆转的命运之轮", this),
            new THMusic("遥遥38万公里的航程", this),
            new THMusic("星条旗的小丑", this),
            new THMusic("故乡之星倒映之海", this),
            new THMusic("Pure Furies ~ 心之所在", this),
            new THMusic("前所未见的噩梦世界", this),
            new THMusic("Pandemonic Planet", this),
            new THMusic("从神社所见之月", this),
            new THMusic("宇宙巫女归还", this),
            new THMusic("Player's Score", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦"), 
            new THPlayer("雾雨魔理沙"), 
            new THPlayer("东风谷早苗"),
            new THPlayer("铃仙·优昙华院·因幡") 
        };
    }
}
