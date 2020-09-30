package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.javabeans.GroupConfig;
import com.meng.config.javabeans.PersonInfo;
import com.meng.sjfmd.libs.GSON;
import com.meng.tools.SJFExecutors;
import com.meng.tools.Tools;
import java.io.File;
import java.util.HashSet;
import java.util.List;
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
        long fromQQ = gme.getSender().getId();
        long fromGroup = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
		if (!entity.configManager.isAdminPermission(fromQQ) && entity.getGroupMemberInfo(fromGroup, fromQQ).getPermission().getLevel() < 1) {
			return false;
		} 
		if (msg.equals(".bot on")) {
            GroupConfig groupConfig = entity.configManager.getGroupConfig(fromGroup);
            if (groupConfig == null) {
                entity.sjfTx.sendGroupMessage(fromGroup, "本群没有默认配置");
                return true;
            }
            entity.configManager.getGroupConfig(fromGroup).setMainSwitchEnable(true);
            entity.sjfTx.sendGroupMessage(fromGroup, "已启用");
            entity.configManager.save();
			return true;
		}
        if (msg.equals(".bot off")) {
			entity.configManager.getGroupConfig(fromGroup).setMainSwitchEnable(false);
			entity.sjfTx.sendGroupMessage(fromGroup,  "已停用");
            return true;
        }
		if (msg.startsWith("findInAll:")) {
			Tools.CQ.findQQInAllGroup(entity, fromGroup, fromQQ, msg);
			return true;
		}
        if (!entity.configManager.isMaster(fromQQ) && entity.getGroupMemberInfo(fromGroup, fromQQ).getPermission().getLevel() < 2) {
			return false;
		}
		if (msg.startsWith("群广播:")) {
			if (msg.contains("~") || msg.contains("～")) {
				entity.sjfTx.sendGroupMessage(fromGroup,  "包含屏蔽的字符");
				return true;
			}
			String broadcast=msg.substring(4);
			HashSet<Group> hs=new HashSet<>();
			ContactList<Group> glist=entity.getGroupList();
			for (Group g:glist) {
				GroupConfig gc=entity.configManager.getGroupConfig(g.getId());
				if (!entity.configManager.getGroupConfig(fromGroup).isMainSwitchEnable()) {
					continue;
				}
				entity.sjfTx.sendGroupMessage(gc.n, broadcast);
				hs.add(g);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
			}
			String result="在以下群发送了广播:";
			for (Group g:hs) {
				result += "\n";
				result += g.getId();
				result += ":";
				result += g.getName();
			}
			entity.sjfTx.sendGroupMessage(fromGroup,  result);
			return true;
		}
		if (msg.equals(".stop")) {
			entity.sjfTx.sendGroupMessage(fromGroup,  "disabled");
			entity.sleeping = true;
			return true;
		}
		if (msg.equals(".start")) {
			entity.sleeping = false;
			entity.sjfTx.sendGroupMessage(fromGroup,  "enabled");
			return true;
		}
		if (msg.startsWith("block[CQ:at")) {
			StringBuilder sb = new StringBuilder();
			List<Long> qqs = entity.getAts(msg);
			sb.append("屏蔽列表添加:");
			for (int i = 0, qqsSize = qqs.size(); i < qqsSize; i++) {
				long qq = qqs.get(i);
				entity.configManager.addBlockQQ(qq);
				sb.append(qq).append(" ");
			}
			entity.configManager.save();
			entity.sjfTx.sendGroupMessage(fromGroup, sb.toString());
			return true;
		}
		if (msg.startsWith("black[CQ:at")) {
			StringBuilder sb = new StringBuilder();
			List<Long> qqs = entity.getAts(msg);
			sb.append("黑名单添加:");
			for (int i = 0, qqsSize = qqs.size(); i < qqsSize; i++) {
				long qq = qqs.get(i);
				entity.configManager.addBlackQQ(qq);
				sb.append(qq).append(" ");
			}
			entity.configManager.save();
			entity.sjfTx.sendGroupMessage(fromGroup, sb.toString());
			return true;
		}
		if (msg.startsWith("find:")) {
			String name = msg.substring(5);
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
		}
		if (msg.equals("线程数")) {
			String s = "taskCount：" + SJFExecutors.getTaskCount() + "\n" +
				"completedTaskCount：" + SJFExecutors.getCompletedTaskCount() + "\n" +
				"largestPoolSize：" + SJFExecutors.getLargestPoolSize() + "\n" +
				"poolSize：" + SJFExecutors.getPoolSize() + "\n" +
				"activeCount：" + SJFExecutors.getActiveCount();
			entity.sjfTx.sendGroupMessage(fromGroup, s);
			return true;
		}
		if (msg.equalsIgnoreCase("System.gc();")) {
			System.gc();
			entity.sjfTx.sendGroupMessage(fromGroup, "gc start");
			return true;
		}
		if (msg.equals("精神支柱")) {
			entity.sjfTx.sendGroupMessage(fromGroup,  entity.image(new File(entity.appDirectory + "pic\\alice.png"), fromGroup));
			return true;
		}
		if (msg.equals("生成位置")) {
			entity.sjfTx.sendGroupMessage(fromGroup,  entity.location(35.594993, 118.869838, 15, "守矢神社", "此生无悔入东方 来世愿生幻想乡"));
		}
		if (msg.startsWith("生成位置")) {
			String[] args = msg.split(",");
			if (args.length == 6) {
				try {
					entity.sjfTx.sendGroupMessage(fromGroup, 
                                                  entity.location(
                                                      Double.parseDouble(args[2]),
                                                      Double.parseDouble(args[1]),
                                                      Integer.parseInt(args[3]),
                                                      args[4],
                                                      args[5]));
					return true;
				} catch (Exception e) {
					entity.sjfTx.sendGroupMessage(fromGroup, "参数错误,生成位置.经度double.纬度double.倍数int.名称string.描述string");
					return true;
				}
			}
		}

		String[] strings = msg.split("\\.", 3);
		if (strings[0].equals("send")) {
			if (msg.contains("~") || msg.contains("～")) {
				entity.sjfTx.sendGroupMessage(fromGroup,  "包含屏蔽的字符");
				return true;
			}
            entity.sjfTx.sendGroupMessage(Long.parseLong(strings[1]), strings[2]);
			return true;
		}
		if (msg.startsWith("设置群头衔[CQ:at")) {
			String title = msg.substring(msg.indexOf("]") + 1);
			System.out.println(entity.setGroupSpecialTitle(fromGroup, entity.getAt(msg), title));
			return true;
		}
		if (msg.startsWith("设置群名片[CQ:at")) {
			String title = msg.substring(msg.indexOf("]") + 1);
			System.out.println(entity.setGroupCard(fromGroup, entity.getAt(msg), title));
			return true;
		}
        return false;
	}

}
