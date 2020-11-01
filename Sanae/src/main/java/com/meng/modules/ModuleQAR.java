package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.gameData.TouHou.SpellCard;
import java.util.HashMap;
import java.util.Random;
import net.mamoe.mirai.message.GroupMessageEvent;

import static com.meng.modules.ModuleQA.QA;

public class ModuleQAR extends BaseGroupModule {

    public HashMap<Long,QA> onGoingMap = new HashMap<>();

    public ModuleQAR(BotWrapperEntity bwe) {
        super(bwe);
    }

    @Override
    public ModuleQAR load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "Qar";
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        long fromGroup = gme.getGroup().getId();
        if (!entity.configManager.getGroupConfig(fromGroup).isQAREnable()) {
            return false;
        }
        String msg = gme.getMessage().contentToString();
        long fromQQ = gme.getSender().getId();
        QA onGoingQA = onGoingMap.get(fromQQ);
        if (onGoingQA != null && msg.equalsIgnoreCase("-qar")) {
            entity.sjfTx.sendQuote(gme, "你还没有回答");
            return true;
        }
        if (onGoingQA != null) {
            int userAnser=-1;
            try {
                userAnser = Integer.parseInt(msg);
            } catch (NumberFormatException e) {}
            if (onGoingQA.getTrueAns().contains(userAnser) && onGoingQA.getTrueAns().size() == 1) {
                entity.sjfTx.sendQuote(gme, "回答正确");
            } else {
                entity.sjfTx.sendQuote(gme, "回答错误");
            }
            onGoingMap.remove(fromQQ);
            return true;
        }
        if (msg.equalsIgnoreCase("-qar")) {
            QA qar = createQA();
            qar.shuffleAnswer();
            StringBuilder sb=new StringBuilder("\n");
            sb.append(qar.q);
            int i=0;
            for (String s:qar.a) {
                if (s.equals("")) {
                    continue;
                }
                sb.append(i++).append(": ").append(s).append("\n");
            }
            sb.append("回答序号即可");
            onGoingMap.put(fromQQ, qar);
            entity.sjfTx.sendQuote(gme, sb.toString());
            return true;
        }
        return false;
    }

    private QA createQA() {
        int diff = 1 << new Random().nextInt(9);
        SpellCard spellCard = entity.moduleManager.getModule(ModuleDiceCmd.class).thData.getSpellFromDiff(diff);
        SpellCard[] sps = entity.moduleManager.getModule(ModuleDiceCmd.class).thData.getSpellFromNotDiff(3, diff);
        QA qa = new QA();
        qa.a.add(spellCard.name);
        for (SpellCard spc:sps) {
            qa.a.add(spc.name);
        }
        qa.setTrueAns(0);
        qa.shuffleAnswer();
        StringBuilder sb = new StringBuilder();
        sb.append("以下符卡在");
        switch (diff) {
            case SpellCard.Easy:
                sb.append("easy难度");
                break;
            case SpellCard.Normal:
                sb.append("normal难度");
                break;
            case SpellCard.Hard:
                sb.append("hard难度");
                break;
            case SpellCard.Lunatic:
                sb.append("lunatic难度");
                break;
            case SpellCard.Overdrive:
                sb.append("overdrive难度");
                break;
            case SpellCard.LastSpell:
                sb.append("last spell");
                break;
            case SpellCard.LastWord:
                sb.append("lastword");
                break;
            case SpellCard.Extra:
                sb.append("extra关卡");
                break;
            case SpellCard.Phantasm:
                sb.append("phamtasm关卡");
                break;
            default:
                System.out.println(spellCard.name);
                System.out.println(diff);
        }
        sb.append("中出现的是:\n");
        qa.q = sb.toString();
        return qa;
    }
}

