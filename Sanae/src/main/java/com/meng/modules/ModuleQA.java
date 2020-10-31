package com.meng.modules;

import com.google.gson.reflect.TypeToken;
import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.SJFInterfaces.IPersistentData;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.DataPersistenter;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;

public class ModuleQA extends BaseGroupModule implements IPersistentData {

    public HashMap<Long,QA> qaMap = new HashMap<>();
    public ArrayList<QA> qaList = new ArrayList<>();
    public String imagePath;
    public static final int easy=0;
    public static final int normal=1;
    public static final int hard=2;
    public static final int lunatic=3;

    public static final int touhouBase=1;
    public static final int _2unDanmakuIntNew=2;
    public static final int _2unDanmakuAll=3;
    public static final int _2unNotDanmaku=4;
    public static final int _2unAll=5;
    public static final int otherDanmaku=6;
    public static final int luastg=7;

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
        long fromGroup = gme.getGroup().getId();
        if (!entity.configManager.getGroupConfig(fromGroup).isQAEnable()) {
            return false;
        }
        String msg = gme.getMessage().contentToString();
        long fromQQ = gme.getSender().getId();
        QA qa = qaMap.get(fromQQ);
        if (qa != null && msg.equalsIgnoreCase("-qa")) {
            entity.sjfTx.sendQuote(gme, "你还没有回答");
            return true;
        }
        if (qa != null) {
            HashSet<Integer> userAnss = new HashSet<>();
            String[] usAnsStrs = msg.split(" ");
            for (String s : usAnsStrs) {
                try {
                    userAnss.add(Integer.parseInt(s));
                } catch (NumberFormatException e) {}
            }
            if (qa.getTrueAns().containsAll(userAnss) && qa.getTrueAns().size() == userAnss.size()) {
                entity.sjfTx.sendQuote(gme, "回答正确");
            } else {
                entity.sjfTx.sendQuote(gme, "回答错误");
            }
            qaMap.remove(fromQQ);
            return true;
        }
        if (msg.equalsIgnoreCase("-qa")) {
            int ran = new Random().nextInt(qaList.size());
            QA qa2 = qaList.get(ran);
            StringBuilder sb=new StringBuilder().append("\n题目ID:").append(ran).append("\n");
            sb.append("难度:");
            switch (qa2.getDifficulty()) {
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
            switch (qa2.getType()) {
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
            if (qa2.q.contains("(image)")) {
                sb.append(qa2.q.replace("(image)", entity.image(new File(imagePath + qa2.getId() + ".jpg"), fromGroup).toMiraiCode()));
            } else {
                sb.append(qa2.q);
            }
            sb.append("\n");
            qa2.shuffleAnswer();
            saveData();
            qaMap.put(fromQQ, qa2);
            int i=0;
            for (String s:qa2.a) {
                if (s.equals("")) {
                    continue;
                }
                sb.append(i++).append(": ").append(s).append("\n");
            }
            sb.append("回答序号即可");
            if (qa2.getTrueAns().size() > 1) {
                sb.append(",本题有多个选项");
            }
            entity.sjfTx.sendQuote(gme, MiraiCode.parseMiraiCode(sb.toString()));
            return true;
        }
        return false;
    }

    public void addQA(QA qa) {
        qa.setId(qaList.size());
        qaList.add(qa);
        saveData();
    }

    public void setQA(QA qa) {
        qaList.set(qa.getId(), qa);
        saveData();
    }

    private void saveData() {
        DataPersistenter.save(this);
    }

    @Override
    public BotWrapperEntity getWrapper() {
        return entity;
    }

    @Override
    public String getPersistentName() {
        return "qa.json";
    }

    @Override
    public Type getDataType() {
        return new TypeToken<ArrayList<QA>>() {}.getType();
    }

    @Override
    public Object getDataBean() {
        return qaList;
    }

    @Override
    public void setDataBean(Object o) {
        qaList = (ArrayList) o;
    }

    @Override
    public String getModuleName() {
        return "问答(题库)";
    }

    public static class QA {
        private int flag=0;
        //flag: id(16bit)                   type(8bit)      diffculty(8bit)
        //  0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0|0 0 0 0 0 0 0 0|0 0 0 0 0 0 0 0
        public int l = 0;//file length
        public String q;
        public ArrayList<String> a = new ArrayList<>();
        private int t;//trueAns
        public String r;

        public void shuffleAnswer() {
            Random r = new Random();
            int index1 = r.nextInt(a.size() - 1);
            int index2 = r.nextInt(a.size() - 1);
            boolean is1F = getBit(index1);
            setBit(index1, getBit(index2));
            setBit(index2, is1F);
            String ele1 = a.get(index1);
            a.set(index1, a.get(index2));
            a.set(index2, ele1);
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

