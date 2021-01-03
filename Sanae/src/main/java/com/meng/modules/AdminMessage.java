package com.meng.modules;

import com.meng.Functions;
import com.meng.SBot;
import com.meng.config.javabeans.PersonInfo;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.tools.GSON;
import com.meng.tools.SJFExecutors;
import com.meng.tools.TextLexer;
import com.meng.tools.Tools;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.PttMessage;

/**
 * @Description: 管理员命令
 * @author: 司徒灵羽
 **/
public class AdminMessage extends BaseModule implements IGroupMessageEvent {

    public AdminMessage(SBot bw) {
        super(bw);
    }

	@Override
    public AdminMessage load() {
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
                    entity.sendGroupMessage(groupId, "已设置为:" + wel);
                    return true;
            }
            if (!entity.configManager.isMaster(qqId) && entity.getGroupMemberInfo(groupId, qqId).getPermission().getLevel() > 1) {
                return false;
            }
            switch (first) {
                case "groupCard":
                    if (list.size() == 3) {
                        entity.setGroupCard(groupId, entity.getId(), iter.next());
                    } else if (list.size() == 4) {
                        entity.setGroupCard(groupId, Long.parseLong(iter.next()), iter.next());
                    }
                    return true;
                case "switch":
                    if (list.size() == 2) {
                        StringBuilder sb = new StringBuilder("当前有:\n");
                        for (Functions function:Functions.values()) {
                            sb.append(function.getName()).append("\n");
                        }
                        sb.setLength(sb.length() - 1);
                        entity.sendGroupMessage(gme.getGroup().getId(), sb.toString());
                    } else if (list.size() == 3) {
                        Functions function = Functions.get(iter.next());
                        if (function != null) {
                            entity.sendQuote(gme, (function.change(entity.configManager, groupId) ?"已启用" : "已停用") + function.toString());
                        } else {
                            entity.sendQuote(gme, "无此开关");
                        }
                    }
                    return true;
            }
            if (!entity.configManager.isMaster(qqId)) {
                return false;
            }
            switch (first) {
                case "broadcast":
                    String broadcast = iter.next();
                    HashSet<Group> hs = new HashSet<>();
                    Collection<Group> glist = entity.getGroups();
                    for (Group g:glist) {
                        if (!entity.configManager.isFunctionEnabled(groupId, Functions.GroupMessageEvent)) {
                            continue;
                        }
                        entity.sendGroupMessage(g.getId(), broadcast);
                        hs.add(g);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {}
                    }
                    StringBuilder result = new StringBuilder("在以下群发送了广播:");
                    for (Group g:hs) {
                        result.append("\n").append(g.getId()).append(":").append(g.getName());
                    }
                    entity.sendGroupMessage(groupId, result.toString());
                    return true;
                case "stop":
                    entity.sendQuote(gme, "disabled");
                    entity.sleeping = true;
                    return true;
                case "start":
                    entity.sleeping = false;
                    entity.sendQuote(gme, "enabled");
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
                    entity.sendGroupMessage(groupId, GSON.toJson(hashSet)); 
                    return true;
                case "thread":
                    String s = "taskCount：" + SJFExecutors.getTaskCount() + "\n" +
                        "completedTaskCount：" + SJFExecutors.getCompletedTaskCount() + "\n" +
                        "largestPoolSize：" + SJFExecutors.getLargestPoolSize() + "\n" +
                        "poolSize：" + SJFExecutors.getPoolSize() + "\n" +
                        "activeCount：" + SJFExecutors.getActiveCount();
                    entity.sendGroupMessage(groupId, s);
                    return true;
                case "gc":
                    System.gc();
                    entity.sendGroupMessage(groupId, "gc start");
                    return true;
                case "send":
                    if (list.size() == 3) {
                        entity.sendGroupMessage(groupId, iter.next());
                    } else if (list.size() == 4) {
                        String next = iter.next();
                        if (next.equals("晚上好啊老婆们")) {
                            File fileMp3 = new File(SBot.appDirectory + "/tts/晚上好啊老婆们.wav");
                            PttMessage ptt = gme.getGroup().uploadVoice(new FileInputStream(fileMp3));
                            if (ptt == null) {
                                entity.sendQuote(gme, "生成失败");
                            }
                            entity.sendQuote(gme, ptt);
                        } else {
                            entity.sendGroupMessage(Long.parseLong(iter.next()), next);
                        }
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
                        entity.sendGroupMessage(groupId, sb.toString());
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
                        entity.sendGroupMessage(groupId, sb.toString());
                    }
                    return true;
            }
            if (qqId != 2856986197L) {
                return false;
            }
            switch (first) {
                case "kick":
                    {
                        At at = gme.getMessage().firstOrNull(At.Key);
                        long target;
                        if (at == null) {
                            target = Long.parseLong(iter.next());
                        } else {
                            target = at.getTarget();
                        }
                        Member targetMember = entity.getGroupMemberInfo(groupId, target);
                        if (targetMember != null) {
                            if (iter.hasNext()) {
                                targetMember.kick(iter.next());
                            } else {
                                targetMember.kick();
                            }
                        } else {
                            entity.sendGroupMessage(groupId, "未找到该成员:" + target);
                        }
                    }
                    return true;
                case "mute":
                    {
                        At at = gme.getMessage().firstOrNull(At.Key);
                        long target;
                        if (at == null) {
                            target = Long.parseLong(iter.next());
                        } else {
                            target = at.getTarget();
                        }
                        Member targetMember = entity.getGroupMemberInfo(groupId, target);
                        if (targetMember != null) {
                            targetMember.mute(Integer.parseInt(iter.next()));
                        } else {
                            entity.sendGroupMessage(groupId, "未找到该成员:" + target);
                        }
                    }
                    return true;
                case "exit":
                    gme.getGroup().quit();
                    return true;
                case "openAll":
                    Functions function = Functions.get(iter.next());
                    if (function != null) {
                        for (Group group : entity.getGroups()) {
                            entity.configManager.setFunctionEnabled(group.getId(), function, true);
                        }
                        entity.sendQuote(gme, function + "已启用");
                    } else {
                        entity.sendQuote(gme, "无此开关");
                    }
            }
        } catch (Exception e) {
            entity.sendGroupMessage(groupId, "参数错误:" + e.toString().replace("java", "jvav"));
        }
        return false;
    }

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            entity.sendGroupMessage(event.getGroup().getId(), "你群日常乱戳");
        }
        return false;
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        return false;
    }

    @Override
    public String getModuleName() {
        return "AdminMessage";
    }
}
