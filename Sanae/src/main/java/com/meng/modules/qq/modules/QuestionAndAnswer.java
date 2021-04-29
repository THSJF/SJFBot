package com.meng.modules.qq.modules;

import com.meng.Functions;
import com.meng.config.ConfigManager;
import com.meng.config.DataPersistenter;
import com.meng.config.SanaeData;
import com.meng.gameData.TouHou.SpellCard;
import com.meng.gameData.TouHou.UserInfo;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;

/**
 * @author: 司徒灵羽
 **/
public class QuestionAndAnswer extends BaseModule implements IGroupMessageEvent {

    @SanaeData("qa.json")
    private ArrayList<QABean> qaList = new ArrayList<>();

    public HashMap<Long,QABean> onGoingQA = new HashMap<>();
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

    public QuestionAndAnswer(SBot bwe) {
        super(bwe);
        imagePath = bwe.appDirectory + "/qaImages";
    }

    @Override
    public QuestionAndAnswer load() {
        DataPersistenter.read(this);
        return this;
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        if (!ConfigManager.getInstance().isFunctionEnabled(gme.getGroup(), Functions.QuestionAndAnswer)) {
            return false;
        }
        if (processQa(gme)) {
            return true;
        }
        if (processQar(gme)) {
            return true;
        }
        return false;
    }

    private boolean processQa(GroupMessageEvent gme) {
        long qqId = gme.getSender().getId();
        QABean qaLast = onGoingQA.get(qqId);
        if (qaLast != null && qaLast.fromQar) {
            return false;
        }
        long groupId = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
        if (qaLast != null && msg.equalsIgnoreCase("-qa")) {
            sendQuote(gme, "你还没有回答");
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
                sendQuote(gme, "回答正确");
                qaLast.incTrueTimes();
                UserInfo info = entity.moduleManager.getModule(UserInfo.class);
                info.getUserData(gme).qaRight++;
                info.save();
                save();
            } else {
                sendQuote(gme, "回答错误");
            }
            onGoingQA.remove(qqId);
            return true;
        }
        if (msg.equalsIgnoreCase("-qa")) {
            int randomInt = ThreadLocalRandom.current().nextInt(qaList.size());
            QABean qaNow = qaList.get(randomInt);
            qaNow.incShowTimes();
            UserInfo module = entity.moduleManager.getModule(UserInfo.class);
            module.getUserData(gme).qaCount++;
            module.save();
            StringBuilder sb = new StringBuilder().append("\n题目ID:").append(randomInt).append("\n");
            if (qaNow.getShowTimes() > 0) {
                sb.append(String.format("正确率:%.2f%%", ((float)qaNow.getTrueTimes()) / qaNow.getShowTimes() * 100));
                sb.append("\n");
            }
            if (qaNow.q.contains("(image)")) {
                sb.append(qaNow.q.replace("(image)", entity.toImage(new File(imagePath + qaNow.getId() + ".jpg"), gme.getGroup()).serializeToMiraiCode()));
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
            sendQuote(gme, MiraiCode.deserializeMiraiCode(sb.toString()));
            return true;
        }
        return false;
    }

    private boolean processQar(GroupMessageEvent gme) {
        String msg = gme.getMessage().contentToString();
        long qqId = gme.getSender().getId();
        QABean onGoing = onGoingQA.get(qqId);
        if (onGoing != null && msg.equalsIgnoreCase("-qar")) {
            sendQuote(gme, "你还没有回答");
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
                sendQuote(gme, "回答正确");
            } else {
                sendQuote(gme, "回答错误");
            }
            onGoingQA.remove(qqId);
            return true;
        }
        if (msg.equalsIgnoreCase("-qar")) {
            QABean qar = createQA();
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
            sendQuote(gme, sb.toString());
            return true;
        }
        return false;
    }

    private QABean createQA() {
        int diff = 1 << ThreadLocalRandom.current().nextInt(9);
        SpellCard spellCard = entity.moduleManager.getModule(Dice.class).thData.getSpellFromDiff(diff);
        SpellCard[] sps = entity.moduleManager.getModule(Dice.class).thData.getSpellFromNotDiff(3, diff);
        QABean qa = new QABean();
        qa.fromQar = true;
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

    public void addQA(QABean qa) {
        qa.setId(qaList.size());
        qaList.add(qa);
        save();
    }

    public void setQA(QABean qa) {
        qaList.set(qa.getId(), qa);
        save();
    }

    public int getQaCount() {
        return qaList.size();
    }

    public List<QABean> getQas() {
        return Collections.unmodifiableList(qaList);
    }

    @Override
    public String getModuleName() {
        return "qa";
    }

    public static class QABean {
        private int flag = 0;
        //flag: id(16bit)                      
        //  0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 | 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        public int l = 0;//file length
        public String q;
        public ArrayList<String> a = new ArrayList<>();
        private int t;//trueAns
        public String r;
        private int showTimes;
        private int trueTimes;
        private transient boolean fromQar;

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

        public void setId(int id) {
            flag &= 0x0000ffff;
            flag |= (id << 16);
        }

        public int getId() {
            return (flag >> 16) & 0xff;
        }
    }
}

