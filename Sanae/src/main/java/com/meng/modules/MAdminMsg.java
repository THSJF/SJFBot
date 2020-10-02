package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.javabeans.GroupConfig;
import com.meng.config.javabeans.PersonInfo;
import com.meng.sjfmd.libs.GSON;
import com.meng.tools.SJFExecutors;
import com.meng.tools.Tools;
import java.util.HashSet;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Description: 管理员命令
 * @author: 司徒灵羽
 **/

public class MAdminMsg extends BaseGroupModule {

    private String[] cmdMsg;
    private int pos = 0;

    public MAdminMsg(BotWrapperEntity bw) {
        super(bw);
    }

	@Override
    public MAdminMsg load() {
		return this;
	}

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        long fromQQ = gme.getSender().getId();
        long fromGroup = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
        if (!entity.configManager.isAdminPermission(fromQQ) && entity.getGroupMemberInfo(fromGroup, fromQQ).getPermission().getLevel() < 1) {
            return false;
		}
        if (msg.charAt(0) != '.') {
            return false;
        }
        cmdMsg = msg.split(" ");
        pos = 0;
        if (pos < cmdMsg.length) {
            try {
                String first = next();
                switch (first) {
                    case ".findGroup":
                        Tools.CQ.findQQInAllGroup(entity, fromGroup, fromQQ, next());
                        return true;
                    case ".welcome":
                        entity.configManager.setWelcome(fromGroup, next());
                        entity.configManager.save();
                        return true;
                }
                if (!entity.configManager.isMaster(fromQQ) && entity.getGroupMemberInfo(fromGroup, fromQQ).getPermission().getLevel() < 2) {
                    return false;
                }
                switch (first) {
                    case ".broadcast":
                        String broadcast = msg.substring(11);
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
                        entity.sjfTx.sendGroupMessage(fromGroup, result.toString());
                        return true;
                    case ".stop":
                        entity.sjfTx.sendGroupMessage(fromGroup, gme, "disabled");
                        entity.sleeping = true;
                        return true;
                    case ".start":
                        entity.sleeping = false;
                        entity.sjfTx.sendGroupMessage(fromGroup, gme, "enabled");
                        return true;
                    case ".findConfig":
                        String name = next();
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
                        entity.sjfTx.sendGroupMessage(fromGroup, GSON.toJson(hashSet)); 
                        return true;
                    case ".thread":
                        String s = "taskCount：" + SJFExecutors.getTaskCount() + "\n" +
                            "completedTaskCount：" + SJFExecutors.getCompletedTaskCount() + "\n" +
                            "largestPoolSize：" + SJFExecutors.getLargestPoolSize() + "\n" +
                            "poolSize：" + SJFExecutors.getPoolSize() + "\n" +
                            "activeCount：" + SJFExecutors.getActiveCount();
                        entity.sjfTx.sendGroupMessage(fromGroup, s);
                        return true;
                    case ".gc":
                        System.gc();
                        entity.sjfTx.sendGroupMessage(fromGroup, "gc start");
                        return true;
                    case ".send":
                        if (cmdMsg.length == 2) {
                            entity.sjfTx.sendGroupMessage(fromGroup, next());
                        } else if (cmdMsg.length == 3) {
                            entity.sjfTx.sendGroupMessage(Long.parseLong(next()), next());
                        }
                        return true;
                    case ".groupTitle":
                        entity.setGroupSpecialTitle(fromGroup, Long.parseLong(next()), next());
                        return true;
                    case ".groupCard":
                        entity.setGroupCard(fromGroup, Long.parseLong(next()), next());
                        return true;
                    case ".block":
                        {
                            StringBuilder sb = new StringBuilder();
                            sb.append("屏蔽列表添加:");
                            String nextqq;
                            while ((nextqq = next()) != null) {
                                entity.configManager.addBlackQQ(Long.parseLong(nextqq));   
                                sb.append(nextqq).append(" ");
                                entity.configManager.save();
                            }
                            entity.sjfTx.sendGroupMessage(fromGroup, sb.toString());
                        }
                        return true;
                    case ".black":
                        { 
                            StringBuilder sb = new StringBuilder();
                            sb.append("屏蔽列表添加:");
                            String nextqq;
                            while ((nextqq = next()) != null) {
                                entity.configManager.addBlackQQ(Long.parseLong(nextqq));   
                                sb.append(nextqq).append(" ");
                            }
                            entity.configManager.save();
                            entity.sjfTx.sendGroupMessage(fromGroup, sb.toString());
                        }
                        return true;
                }
            } catch (Exception e) {
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
