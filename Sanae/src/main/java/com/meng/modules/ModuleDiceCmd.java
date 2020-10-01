package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.gameData.TouHou.zun.TH06GameData;
import com.meng.gameData.TouHou.zun.TH07GameData;
import com.meng.gameData.TouHou.zun.TH08GameData;
import com.meng.gameData.TouHou.zun.TH10GameData;
import com.meng.gameData.TouHou.zun.TH11GameData;
import com.meng.gameData.TouHou.zun.TH12GameData;
import com.meng.gameData.TouHou.zun.TH13GameData;
import com.meng.gameData.TouHou.zun.TH14GameData;
import com.meng.gameData.TouHou.zun.TH15GameData;
import com.meng.gameData.TouHou.zun.TH16GameData;
import com.meng.gameData.TouHou.zun.TH17GameData;
import com.meng.sjfmd.libs.Hash;
import com.meng.tools.Tools;
import java.util.Random;
import net.mamoe.mirai.message.GroupMessageEvent;
import com.meng.config.javabeans.PersonConfig;

/**
 * @Description: 模拟骰子
 * @author: 司徒灵羽
 **/

public class ModuleDiceCmd extends BaseGroupModule {

	private String[] cmdMsg;
	private int pos=0;

    public static String[] spells;
    public static String[] neta;
    public static String[] music;
    public static String[] name;

    private static String[] pl01 = new String[]{"别打砖块了，来飞机"};
    private static String[] pl02 = new String[]{"范围重视型", "高灵击伤害 平衡型", "威力重视型"};
    private static String[] pl03 = new String[]{"博丽灵梦", "魅魔", "雾雨魔理沙", "爱莲", "小兔姬", "卡娜·安娜贝拉尔", "朝仓理香子", "北白河千百合", "冈崎梦美"};
    private static String[] pl04 = new String[]{"博丽灵梦 诱导", "博丽灵梦 大范围", "雾雨魔理沙 激光", "雾雨魔理沙 高速射击"};
    private static String[] pl05 = new String[]{"博丽灵梦", "雾雨魔理沙", "魅魔", "幽香"};
    private static String[] pl09 = new String[]{"博丽灵梦", "雾雨魔理沙", "十六夜咲夜", "魂魄妖梦", "铃仙·优昙华院·因幡", "琪露诺", "莉莉卡·普莉兹姆利巴", "梅露兰·普莉兹姆利巴", "露娜萨·普莉兹姆利巴", "米斯蒂娅·萝蕾拉", "因幡帝", "射命丸文", "梅蒂欣·梅兰可莉", "风见幽香", "小野冢小町", "四季映姬·亚玛萨那度"};
    private static String[] plDiff = new String[]{"easy", "normal", "hard", "lunatic"};


    public ModuleDiceCmd(BotWrapperEntity bw) {
        super(bw);
        spells = Tools.ArrayTool.mergeArray(TH06GameData.spells,
                                            TH07GameData.spells,
                                            TH08GameData.spells,
                                            TH10GameData.spells,
                                            TH11GameData.spells,
                                            TH12GameData.spells,
                                            TH13GameData.spells,
                                            TH14GameData.spells,
                                            TH15GameData.spells,
                                            TH16GameData.spells,
                                            TH17GameData.spells);
        neta = new String[]{
            "红lnb",
            "红lnm",
            "妖lnm",
            "妖lnn",
            "永lnm",
            "风lnm",
            "风lnn",
            "殿lnm",
            "船lnm",
            "船lnn",
            "庙lnm",
            "城lnm",
            "绀lnm",
            "璋lnn"};
        music = new String[]{
            //th4
            "bad apple",
        };
        music = Tools.ArrayTool.mergeArray(music,
                                           TH06GameData.musicName,
                                           TH07GameData.musicName,
                                           TH08GameData.musicName,
                                           TH10GameData.musicName,
                                           TH11GameData.musicName,
                                           TH12GameData.musicName,
                                           TH13GameData.musicName,
                                           TH14GameData.musicName,
                                           TH15GameData.musicName,
                                           TH16GameData.musicName,
                                           TH17GameData.musicName);
        name = new String[]{
            //th2
            "里香",
            "明罗",
            "魅魔",
            //th3
            "爱莲", 
            "小兔姬", 
            "卡娜·安娜贝拉尔",
            "朝仓理香子", 
            "北白河千百合", 
            "冈崎梦美",
            //th4
            "奥莲姬",
            "胡桃",
            "艾丽",
            "梦月",
            "幻月",
            //th5
            "萨拉",
            "露易兹",
            "雪",
            "舞",
            "梦子",
            "神绮"};
        name = Tools.ArrayTool.mergeArray(name,
                                          TH06GameData.charaName,
                                          TH07GameData.charaName,
                                          TH08GameData.charaName,
                                          new String[]{
                                              //th9
                                              "梅蒂欣·梅兰可莉",
                                              "风见幽香",
                                              "小野冢小町",
                                              "四季映姬"},
                                          TH10GameData.charaName,
                                          TH11GameData.charaName,
                                          TH12GameData.charaName,
                                          new String[]{
                                              //th12.8
                                              "桑尼·米尔克",
                                              "露娜·切露德",
                                              "斯塔·萨菲雅"},
                                          TH13GameData.charaName,
                                          new String[]{
                                              //th13.5
                                              "秦心"},
                                          TH14GameData.charaName,
                                          new String[]{
                                              //th14.5
                                              "宇佐见堇子"},
                                          TH15GameData.charaName,
                                          new String[]{
                                              //th15.5
                                              "依神紫苑",
                                              "依神女苑"},
                                          TH16GameData.charaName,
                                          TH17GameData.charaName);

    }

	@Override
	public ModuleDiceCmd load() {
		return this;
	}

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long fromQQ = gme.getSender().getId();
        long fromGroup = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
		if (msg.charAt(0) != '.' && msg.charAt(0) != '。') {
			return false;
		}
        Random random = new Random();
		cmdMsg = msg.split(" ");
		pos = 0;
		if (pos < cmdMsg.length) {
			try {
                String pname = entity.configManager.getNickName(fromGroup, fromQQ);
                String md5 = Hash.getMd5Instance().calculate(String.valueOf(fromQQ + System.currentTimeMillis() / (24 * 60 * 60 * 1000)));
                char c = md5.charAt(0);
				switch (next()) {
                    case ".r":
						String rs = next();
						entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s投掷%s:D100 = %d", entity.configManager.getNickName(fromGroup, fromQQ), rs == null ?"": rs, random.nextInt(100)));
						return true;
					case ".ra":
						String ras = next();
						entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s进行检定:D100 = %d/%s", entity.configManager.getNickName(fromGroup, fromQQ), random.nextInt(Integer.parseInt(ras)), ras));
						return true;
					case ".li":
						entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s的疯狂发作-总结症状:\n1D10=%d\n症状: 狂躁：调查员患上一个新的狂躁症，在1D10=%d小时后恢复理智。在这次疯狂发作中，调查员将完全沉浸于其新的狂躁症状。这是否会被其他人理解（apparent to other people）则取决于守秘人和此调查员。\n1D100=%d\n具体狂躁症: 臆想症（Nosomania）：妄想自己正在被某种臆想出的疾病折磨。(KP也可以自行从狂躁症状表中选择其他症状)", entity.configManager.getNickName(fromGroup, fromQQ), random.nextInt(11), random.nextInt(11), random.nextInt(101)));
						return true;
					case ".ti":
						entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s的疯狂发作-临时症状:\n1D10=%d\n症状: 逃避行为：调查员会用任何的手段试图逃离现在所处的位置，状态持续1D10=%d轮。", entity.configManager.getNickName(fromGroup, fromQQ), random.nextInt(11), random.nextInt(11)));
						return true;
					case ".rd":
						entity.sjfTx.sendGroupMessage(fromGroup, String.format("由于%s %s骰出了: D100=%d", next(), entity.configManager.getNickName(fromGroup, fromQQ), random.nextInt(101)));
						return true;
					case ".nn":
						String name = next();
						if (name == null) {
							entity.configManager.setNickName(fromQQ, null);
							entity.sjfTx.sendGroupMessage(fromGroup, "我以后会用你的QQ昵称称呼你");
							return true;
						}
						if (name.length() > 30) {
							entity.sjfTx.sendGroupMessage(fromGroup, "太长了,记不住");
							return true;
						}
						entity.configManager.setNickName(fromQQ, name);
						entity.sjfTx.sendGroupMessage(fromGroup, "我以后会称呼你为" + name);
						return true;
					case ".help":

						return true;
                    case ".jrrp":
                        {
                            float fpro=0f;
                            if (c == '0') {
                                fpro = 99.61f;
                            } else if (c == '1') {
                                fpro = 97.60f;
                            } else {
                                fpro = ((float)(md5Random(fromQQ) % 10001)) / 100;
                            }
                            entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天会在%.2f%%处疮痍", pname, fpro));
                        }
                        return true;    
                    case "。jrrp":
                        entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天会在%s疮痍", pname, md5RanStr(fromQQ, spells)));
                        return true;
                    case ".welcome":
                        entity.configManager.setWelcome(fromGroup, next());
                        break;
                    case ".draw":
                        String drawcmd = msg.substring(6);
                        switch (drawcmd) {
                            case "help":
                                entity.sjfTx.sendGroupMessage(fromGroup, "当前有:spell neta music grandma game all");
                                return true;
                            case "spell":
                                entity.sjfTx.sendGroupMessage(fromGroup, spells[new Random().nextInt(spells.length)]);
                                return true;
                            case "neta":
                                entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天宜打%s", pname, md5RanStr(fromQQ, neta)));
                                return true;
                            case "music":
                                entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天宜听%s", pname, md5RanStr(fromQQ, music)));
                                return true;
                            case "grandma":
                                if (Hash.getMd5Instance().calculate(String.valueOf(fromQQ + System.currentTimeMillis() / (24 * 60 * 60 * 1000))).charAt(0) == '0') {
                                    entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天宜认八云紫当奶奶", pname));
                                    return true;
                                }
                                entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天宜认%s当奶奶", pname, md5RanStr(fromQQ, this.name)));
                                return true;
                            case "game":
                                String s=randomGame(pname, fromQQ, true);
                                s += ",";
                                s += randomGame(pname, fromQQ + 1, false);
                                entity.sjfTx.sendGroupMessage(fromGroup, s);
                                return true;
                            case "jrrp":
                                entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天会在%s疮痍", pname, md5RanStr(fromQQ, spells)));
                                return true;
                            case "all":
                                {
                                    String sss=String.format("%s今天宜打%s", pname, md5RanStr(fromQQ, neta));
                                    sss += "\n";
                                    sss += String.format("%s今天宜听%s", pname, md5RanStr(fromQQ, music));
                                    sss += "\n";
                                    if (Hash.getMd5Instance().calculate(String.valueOf(fromQQ + System.currentTimeMillis() / (24 * 60 * 60 * 1000))).charAt(0) == '0') {
                                        sss += String.format("%s今天宜认八云紫当奶奶", pname);
                                    } else {
                                        sss += String.format("%s今天宜认%s当奶奶", pname, md5RanStr(fromQQ, this.name));
                                    }
                                    sss += "\n";
                                    sss += randomGame(pname, fromQQ, true);
                                    sss += ",";
                                    sss += randomGame(pname, fromQQ + 1, false);
                                    sss += "\n";
                                    float fpro=0f;
                                    if (c == '0') {
                                        fpro = 99.61f;
                                    } else if (c == '1') {
                                        fpro = 97.60f;
                                    } else {
                                        fpro = ((float)(md5Random(fromQQ) % 10001)) / 100;
                                    }
                                    sss += String.format("%s今天会在%.2f%%处疮痍", pname, fpro);
                                    entity.sjfTx.sendGroupMessage(fromGroup, sss);
                                }
                                return true;            
                            default:
                                entity.sjfTx.sendGroupMessage(fromGroup, "可用.draw help查看帮助");
                        }
				}
			} catch (Exception ne) {
				entity.sjfTx.sendGroupMessage(fromGroup, "参数错误");
			}
		}
		return false;
	}

	private String next() {
		try {
			return cmdMsg[pos++];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

    private int md5Random(long fromQQ) {
        String md5=Hash.getMd5Instance().calculate(String.valueOf(fromQQ + System.currentTimeMillis() / (24 * 60 * 60 * 1000)));
        return Integer.parseInt(md5.substring(26), 16);
    }

    public String md5RanStr(long fromQQ, String[] arr) {
        return arr[md5Random(fromQQ) % arr.length];
    }

    private String randomGame(String pname, long fromQQ, boolean goodAt) {
        int gameNo=md5Random(fromQQ) % 16 + 2;
        String gameName = null;
        String charaName = null;
        switch (gameNo) {
            case 2:
                gameName = "封魔录";
                charaName = md5RanStr(fromQQ + 2, pl02);
                break;
            case 3:
                gameName = "梦时空";
                charaName = md5RanStr(fromQQ + 2, pl03);
                break;
            case 4:
                gameName = "幻想乡";
                charaName = md5RanStr(fromQQ + 2, pl04);
                break;
            case 5:
                gameName = "怪绮谈";
                charaName = md5RanStr(fromQQ + 2, pl05);
                break;
            case 6:
                gameName = "红魔乡";
                charaName = md5RanStr(fromQQ + 2, TH06GameData.players);
                break;
            case 7:
                gameName = "妖妖梦";
                charaName = md5RanStr(fromQQ + 2, TH07GameData.players);
                break;
            case 8:
                gameName = "永夜抄";
                charaName = md5RanStr(fromQQ + 2, TH08GameData.players);
                break;
            case 9:
                gameName = "花映冢";
                charaName = md5RanStr(fromQQ + 2, pl09);
                break;
            case 10:
                gameName = "风神录";
                charaName = md5RanStr(fromQQ + 2, TH10GameData.players);
                break;
            case 11:
                gameName = "地灵殿";
                charaName = md5RanStr(fromQQ + 2, TH11GameData.players);
                break;
            case 12:
                gameName = "星莲船";
                charaName = md5RanStr(fromQQ + 2, TH12GameData.players);
                break;
            case 13:
                gameName = "神灵庙";
                charaName = md5RanStr(fromQQ + 2, TH13GameData.players);
                break;
            case 14:
                gameName = "辉针城";
                charaName = md5RanStr(fromQQ + 2, TH14GameData.players);
                if (goodAt) {
                    return String.format("%s今天宜用%s-%s打%s", pname, charaName, md5RanStr(fromQQ + 1, TH14GameData.playerSub), gameName);
                } else {
                    return String.format("忌用%s-%s打%s", charaName, md5RanStr(fromQQ + 1, TH14GameData.playerSub), gameName);
                }
            case 15:
                gameName = "绀珠传";
                charaName = md5RanStr(fromQQ + 2, TH15GameData.players);
                break;
            case 16:
                gameName = "天空璋";
                charaName = md5RanStr(fromQQ + 2, TH16GameData.players);
                if (goodAt) {
                    return String.format("%s今天宜用%s-%s打%s", pname, charaName, md5RanStr(fromQQ + 1, TH16GameData.playerSub), gameName);
                } else {
                    return String.format("忌用%s-%s打%s", charaName, md5RanStr(fromQQ + 1, TH16GameData.playerSub), gameName);
                }
            case 17:
                gameName = "鬼形兽";
                charaName = md5RanStr(fromQQ + 2, TH17GameData.players);
                if (goodAt) {
                    return String.format("%s今天宜用%s-%s打%s", pname, charaName, md5RanStr(fromQQ + 1, TH17GameData.playerSub), gameName);
                } else {
                    return String.format("忌用%s-%s打%s", charaName, md5RanStr(fromQQ + 1, TH17GameData.playerSub), gameName);
                }
            default:
                return "";
        }
        if (goodAt) {
            return String.format("%s今天宜用%s打%s", pname, charaName, gameName);
        } else {
            return String.format("忌用%s打%s", charaName, gameName);
		}
    }
}
