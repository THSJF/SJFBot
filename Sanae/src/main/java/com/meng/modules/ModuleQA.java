package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.annotation.SanaeData;
import com.meng.config.DataPersistenter;
import com.meng.gameData.TouHou.SpellCard;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;

public class ModuleQA extends BaseGroupModule {

    @SanaeData("qa.json")
    private ArrayList<QA> qaList = new ArrayList<>();

    public HashMap<Long,QA> onGoingQA = new HashMap<>();
    public String imagePath;
    public static final int easy = 0;
    public static final int normal = 1;
    public static final int hard = 2;
    public static final int lunatic = 3;

    public static final int touhouBase = 1;
    public static final int _2unDanmakuIntNew = 2;
    public static final int _2unDanmakuAll = 3;
    public static final int _2unNotDanmaku = 4;
    public static final int _2unAll = 5;
    public static final int otherDanmaku = 6;
    public static final int luastg = 7;

    public ModuleQA(BotWrapperEntity bwe) {
        super(bwe);
        imagePath = bwe.appDirectory + "/qaImages";
    }

    @Override
    public ModuleQA load() {
        DataPersistenter.read(this);
        return this;
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        if (processQa(gme)) {
            return true;
        }
        if (processQar(gme)) {
            return true;
        }
        return false;
    }

    private boolean processQa(GroupMessageEvent gme) {
        long groupId = gme.getGroup().getId();
        if (!entity.configManager.getGroupConfig(groupId).isQAEnable()) {
            return false;
        }
        String msg = gme.getMessage().contentToString();
        long qqId = gme.getSender().getId();
        QA qaLast = onGoingQA.get(qqId);
        if (qaLast != null && msg.equalsIgnoreCase("-qa")) {
            entity.sjfTx.sendQuote(gme, "你还没有回答");
            return true;
        }
        if (qaLast != null) {
            HashSet<Integer> userAnss = new HashSet<>();
            String[] usAnsStrs = msg.split(" ");
            for (String s : usAnsStrs) {
                try {
                    userAnss.add(Integer.parseInt(s));
                } catch (NumberFormatException ignore) {
                    //if not number,answer will never true 
                }
            }
            if (qaLast.getTrueAns().containsAll(userAnss) && qaLast.getTrueAns().size() == userAnss.size()) {
                entity.sjfTx.sendQuote(gme, "回答正确");
                qaLast.incTrueTimes();
                save();
            } else {
                entity.sjfTx.sendQuote(gme, "回答错误");
            }
            onGoingQA.remove(qqId);
            return true;
        }
        if (msg.equalsIgnoreCase("-qa")) {
            int randomInt = ThreadLocalRandom.current().nextInt(qaList.size());
            QA qaNow = qaList.get(randomInt);
            qaNow.incShowTimes();
            StringBuilder sb = new StringBuilder().append("\n题目ID:").append(randomInt).append("\n");
            sb.append("难度:");
            switch (qaNow.getDifficulty()) {
                case 0:
                    sb.append("easy");
                    break;
                case 1:
                    sb.append("normal");
                    break;
                case 2:
                    sb.append("hard");
                    break;
                case 3:
                    sb.append("lunatic");
                    break;
                case 4:
                    sb.append("overdrive");
                    break;
                case 5:
                    sb.append("kidding");
            }
            sb.append("\n分类:");
            switch (qaNow.getType()) {
                case 0:
                    sb.append("未定义");
                    break;
                case touhouBase:
                    sb.append("东方project基础");
                    break;
                case _2unDanmakuIntNew:
                    sb.append("新作整数作");
                    break;
                case _2unDanmakuAll:
                    sb.append("官方弹幕作");
                    break;
                case _2unNotDanmaku:
                    sb.append("官方非弹幕");
                    break;
                case _2unAll:
                    sb.append("官方所有");
                    break;
                case otherDanmaku:
                    sb.append("同人弹幕");
                    break;
                case luastg:
                    sb.append("LuaSTG");
                    break;
                default:
                    sb.append("未知");
            }   
            sb.append("\n");
            if (qaNow.getShowTimes() > 0) {
                sb.append(String.format("正确率:%.2f%%", ((float)qaNow.getTrueTimes()) / qaNow.getShowTimes()));
                sb.append("\n");
            }
            if (qaNow.q.contains("(image)")) {
                sb.append(qaNow.q.replace("(image)", entity.image(new File(imagePath + qaNow.getId() + ".jpg"), groupId).toMiraiCode()));
            } else {
                sb.append(qaNow.q);
            }
            sb.append("\n");
            qaNow.shuffleAnswer();
            save();
            onGoingQA.put(qqId, qaNow);
            int i = 0;
            for (String s:qaNow.a) {
                if (s.equals("")) {
                    continue;
                }
                sb.append(i++).append(": ").append(s).append("\n");
            }
            sb.append("回答序号即可");
            if (qaNow.getTrueAns().size() > 1) {
                sb.append(",本题有多个选项");
            }
            entity.sjfTx.sendQuote(gme, MiraiCode.parseMiraiCode(sb.toString()));
            return true;
        }
        return false;
    }

    private boolean processQar(GroupMessageEvent gme) {
        long groupId = gme.getGroup().getId();
        if (!entity.configManager.getGroupConfig(groupId).isQAREnable()) {
            return false;
        }
        String msg = gme.getMessage().contentToString();
        long qqId = gme.getSender().getId();
        QA onGoing = onGoingQA.get(qqId);
        if (onGoing != null && msg.equalsIgnoreCase("-qar")) {
            entity.sjfTx.sendQuote(gme, "你还没有回答");
            return true;
        }
        if (onGoing != null) {
            int userAnser = -1;
            try {
                userAnser = Integer.parseInt(msg);
            } catch (NumberFormatException ignore) {
                //if not number,answer will never true
            }
            if (onGoing.getTrueAns().contains(userAnser)) {
                entity.sjfTx.sendQuote(gme, "回答正确");
            } else {
                entity.sjfTx.sendQuote(gme, "回答错误");
            }
            onGoingQA.remove(qqId);
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
            onGoingQA.put(qqId, qar);
            entity.sjfTx.sendQuote(gme, sb.toString());
            return true;
        }
        return false;
    }

    private QA createQA() {
        int diff = 1 << ThreadLocalRandom.current().nextInt(9);
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

    public void addQA(QA qa) {
        qa.setId(qaList.size());
        qaList.add(qa);
        save();
    }

    public void setQA(QA qa) {
        qaList.set(qa.getId(), qa);
        save();
    }

    private void save() {
        DataPersistenter.save(this);
    }

    public int getQaCount() {
        return qaList.size();
    }

    public List<QA> getQas() {
        return Collections.unmodifiableList(qaList);
    }

    @Override
    public String getModuleName() {
        return "问答(题库)";
    }

    public static class QA {
        private int flag = 0;
        //flag: id(16bit)                   type(8bit)      diffculty(8bit)
        //  0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0|0 0 0 0 0 0 0 0|0 0 0 0 0 0 0 0
        public int l = 0;//file length
        public String q;
        public ArrayList<String> a = new ArrayList<>();
        private int t;//trueAns
        public String r;
        private int showTimes;
        private int trueTimes;

        public void incShowTimes() {
            ++showTimes;
        }

        public void incTrueTimes() {
            ++trueTimes;
        }

        public int getShowTimes() {
            return showTimes;
        }

        public int getTrueTimes() {
            return trueTimes;
        }

        public void shuffleAnswer() {
            Random r = ThreadLocalRandom.current();
            int index1 = r.nextInt(a.size() - 1);
            int index2 = r.nextInt(a.size() - 1);
            boolean is1F = getBit(index1);
            setBit(index1, getBit(index2));
            setBit(index2, is1F);
            Collections.swap(a, index1, index2);
        }

        private boolean getBit(int shift) {
            return (t & (1 << shift)) != 0;
        }

        private void setBit(int shift, boolean v) {
            if (v) {
                t |= (1 << shift);
            } else {
                t &= ~(1 << shift);
            }
        }

        public void setTrueFlag(int flag) {
            t = flag;
        }

        public int getTrueAnsFlag() {
            return t;
        }

        public void setTrueAns(int... ts) {
            t = 0;
            for (int i:ts) {
                t |= (1 << i);
            }
        }

        public HashSet<Integer> getTrueAns() {
            HashSet<Integer> intList = new HashSet<>(32);
            for (int i = 0;i < 32;++i) {
                if ((t & (1 << i)) != 0) {
                    intList.add(i);
                }
            }
            return intList;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getFlag() {
            return flag;
        }

        public void setDifficulty(int d) {
            flag &= 0xffffff00;
            flag |= d;
        }

        public int getDifficulty() {
            return flag & 0xff;
        }

        public void setId(int id) {
            flag &= 0x0000ffff;
            flag |= (id << 16);
        }

        public int getId() {
            return (flag >> 16) & 0xff;
        }

        public void setType(int type) {
            flag &= 0xffff00ff;
            flag |= (type << 8);
        }

        public int getType() {
            return (flag >> 8) & 0xff;
        }
    }
}

