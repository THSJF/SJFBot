package com.meng.modules;

import com.meng.SBot;
import com.meng.gameData.TouHou.SpellCard;
import com.meng.gameData.TouHou.THDataHolder;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.tools.Hash;
import com.meng.tools.TextLexer;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;

/**
 * @Description: 模拟骰子
 * @author: 司徒灵羽
 **/
public class ModuleDiceCmd extends BaseModule implements IGroupMessageEvent {

    public THDataHolder thData = new THDataHolder();

    public ModuleDiceCmd(SBot bw) {
        super(bw);
    }

	@Override
	public ModuleDiceCmd load() {
		return this;
	}

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long qqId = gme.getSender().getId();
        long groupId = gme.getGroup().getId();
        if (!entity.configManager.isFunctionEnbled(groupId, Modules.DICE)) {
            return false;
        }
        String msg = gme.getMessage().contentToString();
        if (msg.equals("。jrrp")) {
            entity.sendGroupMessage(groupId, String.format("%s今天会在%s疮痍", entity.configManager.getNickName(groupId, qqId), thData.hashRandomSpell(qqId).name));
            return true;
        }
		if (msg.charAt(0) != '.') {
			return false;
		}
        Random random = ThreadLocalRandom.current();
		ArrayList<String> list = TextLexer.analyze(msg);
        Iterator<String> iter = list.iterator();
        iter.next();//.
        try {
            String pname = entity.configManager.getNickName(groupId, qqId);
            String md5 = Hash.getMd5Instance().calculate(String.valueOf(qqId + System.currentTimeMillis() / (24 * 60 * 60 * 1000)));
            char c = md5.charAt(0);   
            switch (iter.next()) {
                case "r":
                    entity.sendGroupMessage(groupId, String.format("%s投掷%s:D100 = %d", entity.configManager.getNickName(groupId, qqId), iter.hasNext() ?iter.next(): "", random.nextInt(100)));
                    return true;
                case "ra":
                    String ras = iter.next();
                    entity.sendGroupMessage(groupId, String.format("%s进行检定:D100 = %d/%s", entity.configManager.getNickName(groupId, qqId), random.nextInt(Integer.parseInt(ras)), ras));
                    return true;
                case "li":
                    entity.sendGroupMessage(groupId, String.format("%s的疯狂发作-总结症状:\n1D10=%d\n症状: 狂躁：调查员患上一个新的狂躁症，在1D10=%d小时后恢复理智。在这次疯狂发作中，调查员将完全沉浸于其新的狂躁症状。这是否会被其他人理解（apparent to other people）则取决于守秘人和此调查员。\n1D100=%d\n具体狂躁症: 臆想症（Nosomania）：妄想自己正在被某种臆想出的疾病折磨。(KP也可以自行从狂躁症状表中选择其他症状)", entity.configManager.getNickName(groupId, qqId), random.nextInt(11), random.nextInt(11), random.nextInt(101)));
                    return true;
                case "ti":
                    entity.sendGroupMessage(groupId, String.format("%s的疯狂发作-临时症状:\n1D10=%d\n症状: 逃避行为：调查员会用任何的手段试图逃离现在所处的位置，状态持续1D10=%d轮。", entity.configManager.getNickName(groupId, qqId), random.nextInt(11), random.nextInt(11)));
                    return true;
                case "rd":
                    entity.sendGroupMessage(groupId, String.format("由于%s %s骰出了: D100=%d", iter.next(), entity.configManager.getNickName(groupId, qqId), random.nextInt(101)));
                    return true;
                case "nn":
                    if (!iter.hasNext()) {
                        entity.configManager.setNickName(qqId, null);
                        entity.sendGroupMessage(groupId, "我以后会用你的QQ昵称称呼你");
                        return true;
                    }
                    String name = iter.next();
                    if (name.length() > 30) {
                        entity.sendGroupMessage(groupId, "太长了,记不住");
                        return true;
                    }
                    entity.configManager.setNickName(qqId, name);
                    entity.sendGroupMessage(groupId, "我以后会称呼你为" + name);
                    return true;
                case "jrrp":
                    float fpro = 0f;
                    if (c == '0') {
                        fpro = 99.61f;
                    } else if (c == '1') {
                        fpro = 97.60f;
                    } else if (c == '2') {
                        fpro = 100.00f;
                    } else {
                        fpro = ((float)(thData.hashRandomInt(qqId) % 10001)) / 100;
                    }
                    entity.sendGroupMessage(groupId, String.format("%s今天会在%.2f%%处疮痍", pname, fpro));
                    return true;
                case "roll":
                    switch (iter.next()) {
                        case "plane":
                        case "pl":
                        case "player":
                            entity.sendGroupMessage(groupId, thData.randomPlane(iter.next()));
                            return true;
                    }
                    return true;
                case "draw":
                    String drawcmd = iter.next();
                    switch (drawcmd) {
                        case "spell":
                            if (list.size() == 3) {
                                entity.sendGroupMessage(groupId, thData.randomSpell().name);
                            } else if (list.size() == 4) {
                                String spellName = iter.next();
                                SpellCard sc = thData.getSpellCard(spellName);
                                if (sc == null) {
                                    entity.sendGroupMessage(groupId, "没有找到这张符卡");
                                    return true;
                                }
                                float allPro = ((float)(thData.hashRandom(qqId, sc.name) % 10001)) / 100;
                                entity.sendGroupMessage(groupId, "你今天" + sc.name + "的收率是" + allPro + "%");
                            }
                            return true;
                        case "neta":
                            entity.sendGroupMessage(groupId, String.format("%s今天宜打%s", pname, thData.hashRandomString(qqId, thData.neta)));
                            return true;
                        case "music":
                            entity.sendGroupMessage(groupId, String.format("%s今天宜听%s", pname, thData.hashRandomMusic(qqId)));
                            return true;
                        case "grandma":
                            if (Hash.getMd5Instance().calculate(String.valueOf(qqId + System.currentTimeMillis() / (24 * 60 * 60 * 1000))).charAt(0) == '0') {
                                entity.sendGroupMessage(groupId, String.format("%s今天宜认八云紫当奶奶", pname));
                                return true;
                            }
                            entity.sendGroupMessage(groupId, String.format("%s今天宜认%s当奶奶", pname, thData.hashRandomCharacter(qqId).charaName));
                            return true;
                        case "game":
                            String s = thData.randomGame(pname, qqId, true);
                            s += ",";
                            s += thData.randomGame(pname, qqId + 1, false);
                            entity.sendGroupMessage(groupId, s);
                            return true;
                        case "ufo":
                            int ufor = random.nextInt(10);
                            if (ufor < 8) {
                                String[] fileName = { "blue.gif", "green.gif", "red.gif" };
                                MessageChainBuilder ufoMsgB=new MessageChainBuilder();
                                ThreadLocalRandom current = ThreadLocalRandom.current();
                                ufoMsgB.add(entity.image(new File(entity.appDirectory + "ufo/" + fileName[current.nextInt(3)]), groupId));
                                ufoMsgB.add(entity.image(new File(entity.appDirectory + "ufo/" + fileName[current.nextInt(3)]), groupId));
                                ufoMsgB.add(entity.image(new File(entity.appDirectory + "ufo/" + fileName[current.nextInt(3)]), groupId));
                                entity.sendGroupMessage(groupId, ufoMsgB.asMessageChain());        
                            } else if (ufor == 8) {
                                entity.sendGroupMessage(groupId, entity.image(new File(entity.appDirectory + "ufo/yellow.gif"), groupId));
                            } else {
                                entity.sendGroupMessage(groupId, entity.image(new File(entity.appDirectory + "ufo/colorful.gif"), groupId));
                            }
                            return true;
                        case "all":
                            String allStr = String.format("%s今天宜打%s", pname, thData.hashRandomString(qqId, thData.neta));
                            allStr += "\n";
                            allStr += String.format("%s今天宜听%s", pname, thData.hashRandomMusic(qqId));
                            allStr += "\n";
                            if (Hash.getMd5Instance().calculate(String.valueOf(qqId + System.currentTimeMillis() / (24 * 60 * 60 * 1000))).charAt(0) == '0') {
                                allStr += String.format("%s今天宜认八云紫当奶奶", pname);
                            } else {
                                allStr += String.format("%s今天宜认%s当奶奶", pname, thData.hashRandomCharacter(qqId).charaName);
                            }
                            allStr += "\n";
                            allStr += thData.randomGame(pname, qqId, true);
                            allStr += ",";
                            allStr += thData.randomGame(pname, qqId + 1, false);
                            allStr += "\n";
                            float allPro=0f;
                            if (c == '0') {
                                allPro = 99.61f;
                            } else if (c == '1') {
                                allPro = 97.60f;
                            } else if (c == '2') {
                                allPro = 100.00f;
                            } else {
                                allPro = ((float)(thData.hashRandomInt(qqId) % 10001)) / 100;
                            }
                            allStr += String.format("%s今天会在%.2f%%处疮痍", pname, allPro);
                            entity.sendGroupMessage(groupId, allStr);
                            return true;            
                        default:
                            entity.sendGroupMessage(groupId, "可用.draw help查看帮助");
                    }
                    return true;
                case "spellInfo":
                    SpellCard sc = thData.getSpellCard(iter.next());
                    if (sc == null) {
                        entity.sendQuote(gme, "没有找到这张符卡");
                        return true;
                    }
                    entity.sendQuote(gme, thData.getSpellCardPs(sc));
                    return true;
                case "charaInfo":
                    entity.sendQuote(gme, thData.getCharaNick(iter.next()));
                    return true;
            }
        } catch (Exception e) {
            entity.sendGroupMessage(groupId, "参数错误:" + e.toString());
        }
        return false;
	}

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        return false;
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        return false;
    }

    @Override
    public String getModuleName() {
        return "dice";
    }
}
