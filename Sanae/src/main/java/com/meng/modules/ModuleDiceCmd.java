package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.gameData.TouHou.SpellCard;
import com.meng.gameData.TouHou.THDataHolder;
import com.meng.sjfmd.libs.Hash;
import java.util.Random;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Description: 模拟骰子
 * @author: 司徒灵羽
 **/

public class ModuleDiceCmd extends BaseGroupModule {

	private String[] cmdMsg;
	private int pos = 0;

    public THDataHolder thData = new THDataHolder();

    public ModuleDiceCmd(BotWrapperEntity bw) {
        super(bw);
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
                        float fpro = 0f;
                        if (c == '0') {
                            fpro = 99.61f;
                        } else if (c == '1') {
                            fpro = 97.60f;
                        } else if (c == '2') {
                            fpro = 100.00f;
                        } else {
                            fpro = ((float)(thData.md5Random(fromQQ) % 10001)) / 100;
                        }
                        entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天会在%.2f%%处疮痍", pname, fpro));
                        return true;    
                    case "。jrrp":
                        entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天会在%s疮痍", pname, thData.md5RanSpell(fromQQ).n));
                        return true;
                    case ".draw":
                        String drawcmd = msg.substring(6);
                        switch (drawcmd) {
                            case "help":
                                entity.sjfTx.sendGroupMessage(fromGroup, "当前有:spell neta music grandma game all");
                                return true;
                            case "spell":
                                entity.sjfTx.sendGroupMessage(fromGroup, thData.randomSpell().n);
                                return true;
                            case "neta":
                                entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天宜打%s", pname, thData.md5RanStr(fromQQ, thData.neta)));
                                return true;
                            case "music":
                                entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天宜听%s", pname, thData.md5RanStr(fromQQ, thData.music)));
                                return true;
                            case "grandma":
                                if (Hash.getMd5Instance().calculate(String.valueOf(fromQQ + System.currentTimeMillis() / (24 * 60 * 60 * 1000))).charAt(0) == '0') {
                                    entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天宜认八云紫当奶奶", pname));
                                    return true;
                                }
                                entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天宜认%s当奶奶", pname, thData.md5RanChara(fromQQ).charaName));
                                return true;
                            case "game":
                                String s = thData.randomGame(pname, fromQQ, true);
                                s += ",";
                                s += thData.randomGame(pname, fromQQ + 1, false);
                                entity.sjfTx.sendGroupMessage(fromGroup, s);
                                return true;
                            case "jrrp":
                                entity.sjfTx.sendGroupMessage(fromGroup, String.format("%s今天会在%s疮痍", pname, thData.md5RanSpell(fromQQ).n));
                                return true;
                            case "all":
                                String allStr = String.format("%s今天宜打%s", pname, thData.md5RanStr(fromQQ, thData.neta));
                                allStr += "\n";
                                allStr += String.format("%s今天宜听%s", pname, thData.md5RanStr(fromQQ, thData.music));
                                allStr += "\n";
                                if (Hash.getMd5Instance().calculate(String.valueOf(fromQQ + System.currentTimeMillis() / (24 * 60 * 60 * 1000))).charAt(0) == '0') {
                                    allStr += String.format("%s今天宜认八云紫当奶奶", pname);
                                } else {
                                    allStr += String.format("%s今天宜认%s当奶奶", pname, thData.md5RanChara(fromQQ).charaName);
                                }
                                allStr += "\n";
                                allStr += thData.randomGame(pname, fromQQ, true);
                                allStr += ",";
                                allStr += thData.randomGame(pname, fromQQ + 1, false);
                                allStr += "\n";
                                float allPro=0f;
                                if (c == '0') {
                                    allPro = 99.61f;
                                } else if (c == '1') {
                                    allPro = 97.60f;
                                } else if (c == '2') {
                                    allPro = 100.00f;
                                } else {
                                    allPro = ((float)(thData.md5Random(fromQQ) % 10001)) / 100;
                                }
                                allStr += String.format("%s今天会在%.2f%%处疮痍", pname, allPro);
                                entity.sjfTx.sendGroupMessage(fromGroup, allStr);
                                return true;            
                            default:
                                entity.sjfTx.sendGroupMessage(fromGroup, "可用.draw help查看帮助");
                        }
                        return true;
                    case ".spellInfo":
                        SpellCard sc = thData.getSpellCard(next());
                        if (sc == null) {
                            entity.sjfTx.sendGroupMessage(fromGroup, gme, "没有找到这张符卡");
                            return true;
                        }
                        entity.sjfTx.sendGroupMessage(fromGroup, gme, thData.getSpellCardPs(sc));
                        return true;
                    case ".charaInfo":
                        entity.sjfTx.sendGroupMessage(fromGroup, gme, thData.getCharaNick(next()));
                        return true;
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

}
