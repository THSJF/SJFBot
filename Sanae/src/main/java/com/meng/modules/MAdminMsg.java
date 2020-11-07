package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.javabeans.GroupConfig;
import com.meng.config.javabeans.PersonInfo;
import com.meng.tools.GSON;
import com.meng.tools.SJFExecutors;
import com.meng.tools.TextLexer;
import com.meng.tools.Tools;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Description: 管理员命令
 * @author: 司徒灵羽
 **/

public class MAdminMsg extends BaseGroupModule {

    public MAdminMsg(BotWrapperEntity bw) {
        super(bw);
    }

	@Override
    public MAdminMsg load() {
		return this;
	}

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        long qqId = gme.getSender().getId();
        long groupId = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
        if (!entity.configManager.isAdminPermission(qqId) && entity.getGroupMemberInfo(groupId, qqId).getPermission().getLevel() > 0) {
            return false;
		}
        if (msg.charAt(0) != '.') {
            return false;
        }
        ArrayList<String> list = TextLexer.analyze(msg);
        Iterator<String> iter = list.iterator();
        iter.next();//.
        try {
            String first = iter.next();
            switch (first) {
                case "findGroup":
                    Tools.CQ.findQQInAllGroup(entity, groupId, qqId, iter.next());
                    return true;
                case "welcome":
                    String wel = null;
                    if (iter.hasNext()) {
                        wel = iter.next();
                    }
                    entity.configManager.setWelcome(groupId, wel);
                    entity.configManager.save();
                    entity.sjfTx.sendGroupMessage(groupId, "已设置为:" + wel);
                    return true;
            }
            if (!entity.configManager.isMaster(qqId) && entity.getGroupMemberInfo(groupId, qqId).getPermission().getLevel() > 1) {
                return false;
            }
            switch (first) {
                case "groupCard":
                    if (list.size() == 3) {
                        entity.setGroupCard(groupId, entity.getLoginQQ(), iter.next());
                    } else if (list.size() == 4) {
                        entity.setGroupCard(groupId, Long.parseLong(iter.next()), iter.next());
                    }
                    return true;
                case "switch":
                    GroupConfig cfg = entity.configManager.getGroupConfig(gme.getGroup().getId());
                    boolean re = false;
                    switch (iter.next().toLowerCase()) {
                        case "recall":
                            cfg.setRecallEnable(re = !cfg.isRecallEnable());
                            break;
                        case "dice":
                            cfg.setDiceEnable(re = !cfg.isDiceEnable());
                            break;
                        case "qa":
                            cfg.setQAEnable(re = !cfg.isQAEnable());
                            break;
                        case "qar":
                            cfg.setQAREnable(re = !cfg.isQAREnable());
                            break;
                        case "welcome":
                            cfg.setMemberIncEnable(re = !cfg.isMemberIncEnable());
                            break;
                        case "repeater":
                            cfg.setRepeaterEnable(re = !cfg.isRepeaterEnable());
                            break;
                        case "groupcount":
                            cfg.setGroupCounterEnable(re = !cfg.isGroupCountEnable());
                            break;
                        case "groupcountchart":
                            cfg.setGroupCountChartEnable(re = !cfg.isGroupCountChartEnable());
                            break;  
                        default:
                            return true;
                    }
                    entity.configManager.save();
                    entity.sjfTx.sendGroupMessage(gme.getGroup().getId(), "已设置为" + (re ?"启用": "禁用"));
                    return true;
            }
            if (!entity.configManager.isMaster(qqId)) {
                return false;
            }
            switch (first) {
                case "broadcast":
                    String broadcast = iter.next();
                    HashSet<Group> hs = new HashSet<>();
                    ContactList<Group> glist = entity.getGroupList();
                    for (Group g:glist) {
                        GroupConfig gc = entity.configManager.getGroupConfig(g.getId());
                        if (!gc.isMainSwitchEnable()) {
                            continue;
                        }
                        entity.sjfTx.sendGroupMessage(gc.n, broadcast);
                        hs.add(g);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {}
                    }
                    StringBuilder result = new StringBuilder("在以下群发送了广播:");
                    for (Group g:hs) {
                        result.append("\n").append(g.getId()).append(":").append(g.getName());
                    }
                    entity.sjfTx.sendGroupMessage(groupId, result.toString());
                    return true;
                case "stop":
                    entity.sjfTx.sendQuote(gme, "disabled");
                    entity.sleeping = true;
                    return true;
                case "start":
                    entity.sleeping = false;
                    entity.sjfTx.sendQuote(gme, "enabled");
                    return true;
                case "findConfig":
                    String name = iter.next();
                    HashSet<PersonInfo> hashSet = new HashSet<>();
                    for (PersonInfo personInfo : entity.configManager.getPersonInfo()) {
                        if (personInfo.name.contains(name)) {
                            hashSet.add(personInfo);
                        }
                        if (personInfo.qq != 0 && String.valueOf(personInfo.qq).contains(name)) {
                            hashSet.add(personInfo);
                        }
                        if (personInfo.bid != 0 && String.valueOf(personInfo.bid).contains(name)) {
                            hashSet.add(personInfo);
                        }
                        if (personInfo.bliveRoom != 0 && String.valueOf(personInfo.bliveRoom).contains(name)) {
                            hashSet.add(personInfo);
                        }
                    }
                    entity.sjfTx.sendGroupMessage(groupId, GSON.toJson(hashSet)); 
                    return true;
                case "thread":
                    String s = "taskCount：" + SJFExecutors.getTaskCount() + "\n" +
                        "completedTaskCount：" + SJFExecutors.getCompletedTaskCount() + "\n" +
                        "largestPoolSize：" + SJFExecutors.getLargestPoolSize() + "\n" +
                        "poolSize：" + SJFExecutors.getPoolSize() + "\n" +
                        "activeCount：" + SJFExecutors.getActiveCount();
                    entity.sjfTx.sendGroupMessage(groupId, s);
                    return true;
                case "gc":
                    System.gc();
                    entity.sjfTx.sendGroupMessage(groupId, "gc start");
                    return true;
                case "send":
                    if (list.size() == 3) {
                        entity.sjfTx.sendGroupMessage(groupId, iter.next());
                    } else if (list.size() == 4) {
                        entity.sjfTx.sendGroupMessage(Long.parseLong(iter.next()), iter.next());
                    }
                    return true;
                case "groupTitle":
                    entity.setGroupSpecialTitle(groupId, Long.parseLong(iter.next()), iter.next());
                    return true;
                case "block":
                    {
                        StringBuilder sb = new StringBuilder();
                        sb.append("屏蔽列表添加:");
                        String nextqq;
                        while ((nextqq = iter.next()) != null) {
                            entity.configManager.addBlackQQ(Long.parseLong(nextqq));   
                            sb.append(nextqq).append(" ");
                            entity.configManager.save();
                        }
                        entity.sjfTx.sendGroupMessage(groupId, sb.toString());
                    }
                    return true;
                case "black":
                    { 
                        StringBuilder sb = new StringBuilder();
                        sb.append("屏蔽列表添加:");
                        String nextqq;
                        while ((nextqq = iter.next()) != null) {
                            entity.configManager.addBlackQQ(Long.parseLong(nextqq));   
                            sb.append(nextqq).append(" ");
                        }
                        entity.configManager.save();
                        entity.sjfTx.sendGroupMessage(groupId, sb.toString());
                    }
                    return true;
            }
        } catch (Exception e) {
            entity.sjfTx.sendGroupMessage(groupId, "参数错误:" + e.toString());
        }
        return false;
    }

    @Override
    public String getModuleName() {
        return "admin消息处理";
    }
}
