package com.meng.modules.touhou.gameData;

import com.meng.modules.touhou.THCharacter;
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THPlayer;
import com.meng.modules.touhou.THSpell;

public class TH07Data extends THGameData {

    private static TH07Data instance;

    public static TH07Data getInstance() {
        if (instance == null) {
            instance = new TH07Data();
        }
        return instance;
    }

    @Override
    public String getNameCN() {
        return "东方妖妖梦";
    }

    @Override
    public String getNameEng() {
        return "Perfect Cherry Blossom";
    }

    @Override
    public String getNameAbbr() {
        return "PCB";
    }

    private TH07Data() {
        charas = new THCharacter[]{
            new THCharacter("琪露诺", this, "冰之妖怪"),
            new THCharacter("蕾蒂·霍瓦特洛克", this, "冬天的遗忘之物"),
            new THCharacter("橙", this , "凶兆的黑猫"),
            new THCharacter("爱丽丝·玛格特罗依德", this, "七色的人偶师"),
            new THCharacter("莉莉白", this, "带来春天的妖精"),
            new THCharacter("露娜萨·普莉兹姆利巴", this, "骚灵小号手"),
            new THCharacter("梅露兰·普莉兹姆利巴", this, "骚灵提琴手"),
            new THCharacter("莉莉卡·普莉兹姆利巴", this, "骚灵键盘手"),
            new THCharacter("魂魄妖梦", this, "半分虚幻的园艺师"),
            new THCharacter("西行寺幽幽子", this, "幽冥楼阁的亡灵少女"),
            new THCharacter("橙", this, "隙间妖怪的式神的式神"),
            new THCharacter("八云蓝", this, "隙间妖怪的式神"),
            new THCharacter("八云紫", this, "神隐的主犯")
        };
        THCharacter chara_琪露诺 = getCharacter("琪露诺");
        THCharacter chara_蕾蒂 = getCharacter("蕾蒂");
        THCharacter chara_橙 = getCharacter("橙");
        THCharacter chara_爱丽丝_玛格特洛依德 = getCharacter("爱丽丝·玛格特洛依德");
        THCharacter chara_梅露兰 = getCharacter("梅露兰");
        THCharacter chara_露娜萨 = getCharacter("露娜萨");
        THCharacter chara_莉莉卡 = getCharacter("莉莉卡");
        THCharacter chara_魂魄妖梦 = getCharacter("魂魄妖梦");
        THCharacter chara_西行寺幽幽子 = getCharacter("西行寺幽幽子");
        THCharacter chara_八云蓝 = getCharacter("八云蓝");
        THCharacter chara_八云紫 = getCharacter("八云紫");
        spells = new THSpell[]{
            new THSpell(this, chara_琪露诺, "冻符「Freeze Actress」（冰冻演艺人）", "凍符「フリーズアクトレス」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_琪露诺, "霜符「Frost Columns」（冰袭方阵）", "霜符「フロストコラムス」", THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_蕾蒂, "寒符「Lingering Cold」（延长的冬日）", "寒符「リンガリングコールド」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_蕾蒂, "冬符「Flower Wither Away」（花之凋零）", "冬符「フラワーウィザラウェイ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_蕾蒂, "白符「Undulation Ray」（波状光）", "白符「アンデュレイションレイ」", THSpell.Hard),
            new THSpell(this, chara_蕾蒂, "怪符「Table Turning」（桌灵转）", "怪符「テーブルターニング」", THSpell.Lunatic),

            new THSpell(this, chara_橙, "仙符「凤凰卵」", "仙符「鳳凰卵」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_橙, "仙符「凤凰展翅」", "仙符「鳳凰展翅」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_橙, "式符「飞翔晴明」", "式符「飛翔晴明」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_橙, "阴阳「道满晴明」", "陰陽「道満晴明」", THSpell.Hard),
            new THSpell(this, chara_橙, "阴阳「晴明大纹」", "陰陽「晴明大紋」", THSpell.Lunatic),
            new THSpell(this, chara_橙, "天符「天仙鸣动」", "天符「天仙鳴動」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_橙, "翔符「飞翔韦驮天」", "翔符「飛翔韋駄天」", THSpell.Hard),
            new THSpell(this, chara_橙, "童符「护法天童乱舞」", "童符「護法天童乱舞」", THSpell.Lunatic),
            new THSpell(this, chara_橙, "仙符「尸解永远」", "仙符「屍解永遠」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_橙, "鬼符「鬼门金神」", "鬼符「鬼門金神」", THSpell.Hard),
            new THSpell(this, chara_橙, "方符「奇门遁甲」", "方符「奇門遁甲」", THSpell.Lunatic),
            new THSpell(this, chara_橙, "鬼符「青鬼赤鬼」", "鬼符「青鬼赤鬼」", THSpell.Extra),
            new THSpell(this, chara_橙, "鬼神「飞翔毘沙门天」", "鬼神「飛翔毘沙門天」", THSpell.Extra),

            new THSpell(this, chara_爱丽丝_玛格特洛依德, "操符「Marionette Parrar」（广范围操纵人偶）", "操符「マリオネットパラル」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "操符「Manipulate Puppet」（操纵人偶）", "操符「マニピュレイトパペット」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "操符「少女文乐」", "操符「乙女文楽」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "苍符「博爱的法兰西人偶」", "蒼符「博愛の仏蘭西人形」", THSpell.Easy | THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "苍符「博爱的奥尔良人偶」", "蒼符「博愛のオルレアン人形」", THSpell.Lunatic),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "红符「红发的荷兰人偶」", "紅符「紅毛の和蘭人形」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "白符「白垩的俄罗斯人偶」", "白符「白亜の露西亜人形」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "暗符「雾之伦敦人偶」", "闇符「霧の倫敦人形」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "回符「轮回的西藏人偶」", "廻符「輪廻の西蔵人形」", THSpell.Hard),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "雅符「春之京都人偶」", "雅符「春の京人形」", THSpell.Lunatic),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "诅咒「魔彩光的上海人偶」", "咒詛「魔彩光の上海人形」", THSpell.Easy | THSpell.Normal | THSpell.Hard),
            new THSpell(this, chara_爱丽丝_玛格特洛依德, "诅咒「上吊的蓬莱人偶」", "咒詛「首吊り蓬莱人形」", THSpell.Lunatic),

            new THSpell(this, chara_梅露兰, "管灵「Hino Phantasm」（日野幻想）", "管霊「ヒノファンタズム」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_梅露兰, "冥管「Ghost Clifford」（灵之克里福德）", "冥管「ゴーストクリフォード」", THSpell.Hard),
            new THSpell(this, chara_梅露兰, "管灵「Ghost Clifford」（灵之克里福德）", "管霊「ゴーストクリフォード」1", THSpell.Lunatic),
            new THSpell(this, chara_梅露兰, "合葬「Prism Concerto」（棱镜协奏曲）", "合葬「プリズムコンチェルト」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_梅露兰, "骚葬「Stygian Riverside」（冥河边缘）", "騒葬「スティジャンリバーサイド」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_梅露兰, "大合葬「灵车大协奏曲」", "大合葬「霊車コンチェルトグロッソ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_梅露兰, "大合葬「灵车大协奏曲改」", "大合葬「霊車コンチェルトグロッソ改」", THSpell.Hard),
            new THSpell(this, chara_梅露兰, "大合葬「灵车大协奏曲怪」", "大合葬「霊車コンチェルトグロッソ怪」", THSpell.Lunatic),

            new THSpell(this, chara_露娜萨, "弦奏「Guarneri del Gesù」（瓜尔内里·德尔·杰苏）", "弦奏「グァルネリ・デル・ジェス」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_露娜萨, "神弦「Stradivarius」（斯特拉迪瓦里）", "神弦「ストラディヴァリウス」", THSpell.Hard),
            new THSpell(this, chara_露娜萨, "伪弦「Pseudo Stradivarius」（伪斯特拉迪瓦里）", "偽弦「スードストラディヴァリウス」", THSpell.Lunatic),
            new THSpell(this, chara_露娜萨, "合葬「Prism Concerto」（棱镜协奏曲）", "合葬「プリズムコンチェルト」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_露娜萨, "骚葬「Stygian Riverside」（冥河边缘）", "騒葬「スティジャンリバーサイド」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_露娜萨, "大合葬「灵车大协奏曲」", "大合葬「霊車コンチェルトグロッソ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_露娜萨, "大合葬「灵车大协奏曲改」", "大合葬「霊車コンチェルトグロッソ改」", THSpell.Hard),
            new THSpell(this, chara_露娜萨, "大合葬「灵车大协奏曲怪」", "大合葬「霊車コンチェルトグロッソ怪」", THSpell.Lunatic),

            new THSpell(this, chara_莉莉卡, "冥键「法奇奥里冥奏」（Fazioli冥奏）", "冥鍵「ファツィオーリ冥奏」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_莉莉卡, "键灵「贝森朵夫神奏」（Bösendorfer神奏）", "鍵霊「ベーゼンドルファー神奏」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_莉莉卡, "骚符「Phantom Dinning」（幽灵絮语）", "騒符「ファントムディニング」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_莉莉卡, "骚符「Live Poltergeist」（活着的骚灵）", "騒符「ライブポルターガイスト」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_莉莉卡, "合葬「Prism Concerto」（棱镜协奏曲）", "合葬「プリズムコンチェルト」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_莉莉卡, "骚葬「Stygian Riverside」（冥河边缘）", "騒葬「スティジャンリバーサイド」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_莉莉卡, "大合葬「灵车大协奏曲」", "大合葬「霊車コンチェルトグロッソ」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_莉莉卡, "大合葬「灵车大协奏曲改」", "大合葬「霊車コンチェルトグロッソ改」", THSpell.Hard),
            new THSpell(this, chara_莉莉卡, "大合葬「灵车大协奏曲怪」", "大合葬「霊車コンチェルトグロッソ怪」", THSpell.Lunatic),

            new THSpell(this, chara_魂魄妖梦, "幽鬼剑「妖童饿鬼之断食」", "幽鬼剣「妖童餓鬼の断食」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_魂魄妖梦, "饿鬼剑「饿鬼道草纸」", "餓鬼剣「餓鬼道草紙」", THSpell.Hard),
            new THSpell(this, chara_魂魄妖梦, "饿王剑「饿鬼十王的报应」", "餓王剣「餓鬼十王の報い」", THSpell.Lunatic),
            new THSpell(this, chara_魂魄妖梦, "狱界剑「二百由旬之一闪」", "獄界剣「二百由旬の一閃」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_魂魄妖梦, "狱炎剑「业风闪影阵」", "獄炎剣「業風閃影陣」", THSpell.Hard),
            new THSpell(this, chara_魂魄妖梦, "狱神剑「业风神闪斩」", "獄神剣「業風神閃斬」", THSpell.Lunatic),
            new THSpell(this, chara_魂魄妖梦, "畜趣剑「无为无策之冥罚」", "畜趣剣「無為無策の冥罰」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_魂魄妖梦, "修罗剑「现世妄执」", "修羅剣「現世妄執」", THSpell.Hard | THSpell.Lunatic),
            new THSpell(this, chara_魂魄妖梦, "人界剑「悟入幻想」", "人界剣「悟入幻想」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_魂魄妖梦, "人世剑「大悟显晦」", "人世剣「大悟顕晦」", THSpell.Hard),
            new THSpell(this, chara_魂魄妖梦, "人神剑「俗谛常住」", "人神剣「俗諦常住」", THSpell.Lunatic),
            new THSpell(this, chara_魂魄妖梦, "天上剑「天人之五衰」", "天上剣「天人の五衰」", THSpell.Easy | THSpell.Normal),
            new THSpell(this, chara_魂魄妖梦, "天界剑「七魄忌讳」", "天界剣「七魄忌諱」", THSpell.Hard),
            new THSpell(this, chara_魂魄妖梦, "天神剑「三魂七魄」", "天神剣「三魂七魄」", THSpell.Lunatic),
            new THSpell(this, chara_魂魄妖梦, "六道剑「一念无量劫」", "六道剣「一念無量劫」", THSpell.Easy | THSpell.Normal | THSpell.Hard | THSpell.Lunatic),

            new THSpell(this, chara_西行寺幽幽子, "亡乡「亡我乡 -彷徨的灵魂-」", "亡郷「亡我郷 -さまよえる魂-」", THSpell.Easy),
            new THSpell(this, chara_西行寺幽幽子, "亡乡「亡我乡 -宿罪-」", "亡郷「亡我郷 -宿罪-」", THSpell.Normal),
            new THSpell(this, chara_西行寺幽幽子, "亡乡「亡我乡 -无道之路-」", "亡郷「亡我郷 -道無き道-」", THSpell.Hard),
            new THSpell(this, chara_西行寺幽幽子, "亡乡「亡我乡 -自尽-」", "亡郷「亡我郷 -自尽-」", THSpell.Lunatic),
            new THSpell(this, chara_西行寺幽幽子, "亡舞「生者必灭之理 -眩惑-」", "亡舞「生者必滅の理 -眩惑-」", THSpell.Easy),
            new THSpell(this, chara_西行寺幽幽子, "亡舞「生者必灭之理 -死蝶-」", "亡舞「生者必滅の理 -死蝶-」　", THSpell.Normal),
            new THSpell(this, chara_西行寺幽幽子, "亡舞「生者必灭之理 -毒蛾-」", "亡舞「生者必滅の理 -毒蛾-」", THSpell.Hard),
            new THSpell(this, chara_西行寺幽幽子, "亡舞「生者必灭之理 -魔境-」", "亡舞「生者必滅の理 -魔境-」", THSpell.Lunatic),
            new THSpell(this, chara_西行寺幽幽子, "华灵「Ghost Butterfly」（亡灵蝶）", "華霊「ゴーストバタフライ」", THSpell.Easy, "华灵「死蝶」"),
            new THSpell(this, chara_西行寺幽幽子, "华灵「Swallowtail Butterfly」（燕尾蝶）", "華霊「スワローテイルバタフライ」", THSpell.Normal),
            new THSpell(this, chara_西行寺幽幽子, "华灵「Deep-Rooted Butterfly」（执念蝶）", "華霊「ディープルーティドバタフライ」", THSpell.Hard, "华灵「深固难徙之蝶」"),
            new THSpell(this, chara_西行寺幽幽子, "华灵「Butterfly Delusion」（蝶妄想）", "華霊「バタフライディルージョン」", THSpell.Lunatic, "华灵「蝶幻」"),
            new THSpell(this, chara_西行寺幽幽子, "幽曲「埋骨于弘川 -伪灵-」", "幽曲「リポジトリ・オブ・ヒロカワ -偽霊-」", THSpell.Easy),
            new THSpell(this, chara_西行寺幽幽子, "幽曲「埋骨于弘川 -亡灵-」", "幽曲「リポジトリ・オブ・ヒロカワ -亡霊-」", THSpell.Normal),
            new THSpell(this, chara_西行寺幽幽子, "幽曲「埋骨于弘川 -幻灵-」", "幽曲「リポジトリ・オブ・ヒロカワ -幻霊-」", THSpell.Hard),
            new THSpell(this, chara_西行寺幽幽子, "幽曲「埋骨于弘川 -神灵-」", "幽曲「リポジトリ・オブ・ヒロカワ -神霊-」", THSpell.Lunatic),
            new THSpell(this, chara_西行寺幽幽子, "樱符「完全墨染的樱花 -封印-」", "桜符「完全なる墨染の桜 -封印-」", THSpell.Easy),
            new THSpell(this, chara_西行寺幽幽子, "樱符「完全墨染的樱花 -亡我-」", "桜符「完全なる墨染の桜 -亡我-」", THSpell.Normal),
            new THSpell(this, chara_西行寺幽幽子, "樱符「完全墨染的樱花 -春眠-」", "桜符「完全なる墨染の桜 -春眠-」", THSpell.Hard),
            new THSpell(this, chara_西行寺幽幽子, "樱符「完全墨染的樱花 -开花-」", "桜符「完全なる墨染の桜 -開花-」", THSpell.Lunatic),
            new THSpell(this, chara_西行寺幽幽子, "「反魂蝶 -一分咲-」", "「反魂蝶 -一分咲-」", THSpell.Easy),
            new THSpell(this, chara_西行寺幽幽子, "「反魂蝶 -三分咲-」", "「反魂蝶 -参分咲-」", THSpell.Normal),
            new THSpell(this, chara_西行寺幽幽子, "「反魂蝶 -五分咲-」", "「反魂蝶 -伍分咲-」", THSpell.Hard),
            new THSpell(this, chara_西行寺幽幽子, "「反魂蝶 -八分咲-」", "「反魂蝶 -八分咲-」　", THSpell.Lunatic),

            new THSpell(this, chara_八云蓝, "式神「仙狐思念」", "式神「仙狐思念」", THSpell.Extra),
            new THSpell(this, chara_八云蓝, "式神「十二神将之宴」", "式神「十二神将の宴」", THSpell.Extra),
            new THSpell(this, chara_八云蓝, "式辉「狐狸妖怪激光」", "式輝「狐狸妖怪レーザー」", THSpell.Extra),
            new THSpell(this, chara_八云蓝, "式辉「迷人的四面楚歌」", "式輝「四面楚歌チャーミング」", THSpell.Extra),
            new THSpell(this, chara_八云蓝, "式辉「天狐公主 -Illusion-」", "式輝「プリンセス天狐 -Illusion-」", THSpell.Extra),
            new THSpell(this, chara_八云蓝, "式弹「Ultimate Buddhist」（最后的佛徒）", "式弾「アルティメットブディスト」", THSpell.Extra),
            new THSpell(this, chara_八云蓝, "式弹「Unilateral Contact」（片面接触）", "式弾「ユーニラタルコンタクト」", THSpell.Extra),
            new THSpell(this, chara_八云蓝, "式神「橙」", "式神「橙」", THSpell.Extra),
            new THSpell(this, chara_八云蓝, "「狐狗狸先生的契约」", "「狐狗狸さんの契約」", THSpell.Extra, "「狐狗狸先生的契约」"),
            new THSpell(this, chara_八云蓝, "幻神「饭纲权现降临」", "幻神「飯綱権現降臨」", THSpell.Extra),
            new THSpell(this, chara_八云蓝, "式神「前鬼后鬼的守护」", "式神「前鬼後鬼の守護」", THSpell.Phantasm),
            new THSpell(this, chara_八云蓝, "式神「凭依荼吉尼天」", "式神「憑依荼吉尼天」", THSpell.Phantasm),

            new THSpell(this, chara_八云紫, "结界「梦境与现实的诅咒」", "結界「夢と現の呪」", THSpell.Phantasm),
            new THSpell(this, chara_八云紫, "结界「动与静的均衡」", "結界「動と静の均衡」", THSpell.Phantasm),
            new THSpell(this, chara_八云紫, "结界「光明与黑暗的网目」", "結界「光と闇の網目」", THSpell.Phantasm),
            new THSpell(this, chara_八云紫, "罔两「直球与曲球的梦乡」", "罔両「ストレートとカーブの夢郷」", THSpell.Phantasm),
            new THSpell(this, chara_八云紫, "罔两「八云紫的神隐」", "罔両「八雲紫の神隠し」", THSpell.Phantasm),
            new THSpell(this, chara_八云紫, "罔两「栖息于禅寺的妖蝶」", "罔両「禅寺に棲む妖蝶」", THSpell.Phantasm),
            new THSpell(this, chara_八云紫, "魍魉「二重黑死蝶」", "魍魎「二重黒死蝶」", THSpell.Phantasm),
            new THSpell(this, chara_八云紫, "式神「八云蓝」", "式神「八雲藍」", THSpell.Phantasm),
            new THSpell(this, chara_八云紫, "「人类与妖怪的境界」", "「人間と妖怪の境界」", THSpell.Phantasm),
            new THSpell(this, chara_八云紫, "结界「生与死的境界」", "結界「生と死の境界」", THSpell.Phantasm),
            new THSpell(this, chara_八云紫, "紫奥义「弹幕结界」", "紫奥義「弾幕結界」", THSpell.Phantasm)
        };
        music = new THMusic[]{
            new THMusic("妖妖梦 ~ Snow or Cherry Petal", this),
            new THMusic("无何有之乡 ~ Deep Mountain", this),
            new THMusic("Crystallize Silver", this),
            new THMusic("远野幻想物语", this),
            new THMusic("凋叶棕（withered leaf）", this),
            new THMusic("布加勒斯特的人偶师", this),
            new THMusic("人偶裁判 ~ 玩弄人形的少女", this),
            new THMusic("天空的花都", this),
            new THMusic("幽灵乐团 ~ Phantom Ensemble", this),
            new THMusic("东方妖妖梦 ~ Ancient Temple", this),
            new THMusic("广有射怪鸟事 ~ Till When?", this),
            new THMusic("Ultimate Truth", this),
            new THMusic("幽雅地绽放吧，墨染的樱花 ~ Border of Life", this),
            new THMusic("Border of Life", this),
            new THMusic("妖妖跋扈", this),
            new THMusic("少女幻葬 ~ Necro-Fantasy", this),
            new THMusic("妖妖跋扈 ~ Who done it!", this),
            new THMusic("Necro-Fantasia", this),
            new THMusic("春风之梦", this),
            new THMusic("樱花樱花 ~ Japanize Dream...", this)
        };
        players = new THPlayer[]{
            new THPlayer("博丽灵梦", "灵符", "梦符"),
            new THPlayer("雾雨魔理沙", "魔符", "恋符"),
            new THPlayer("十六夜咲夜", "幻符", "时符")
        };
    }
}
