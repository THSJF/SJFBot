package com.meng.modules;

import com.google.gson.reflect.TypeToken;
import com.meng.QA;
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
        String msg = gme.getMessage().contentToString();
        long fromQQ = gme.getSender().getId();
        long fromGroup = gme.getGroup().getId();

        QA qa = qaMap.get(fromQQ);
        if (qa != null && msg.equalsIgnoreCase("-qa")) {
            entity.sjfTx.sendGroupMessage(fromGroup, gme, "你还没有回答");
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
                entity.sjfTx.sendGroupMessage(fromGroup, gme, "回答正确");
            } else {
                entity.sjfTx.sendGroupMessage(fromGroup, gme, "回答错误");
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
            qa2.exangeAnswer();
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
            entity.sjfTx.sendGroupMessage(fromGroup, gme, MiraiCode.parseMiraiCode(sb.toString()));
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
}

