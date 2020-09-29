package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.javabeans.GroupConfig;
import com.meng.config.javabeans.PersonInfo;
import com.meng.sjfmd.libs.GSON;
import com.meng.tools.SJFExecutors;
import com.meng.tools.Tools;
import com.meng.tools.override.MyLinkedHashMap;
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

	private MyLinkedHashMap<String,String> masterPermission=new MyLinkedHashMap<>();
	private MyLinkedHashMap<String,String> adminPermission=new MyLinkedHashMap<>();
	public MyLinkedHashMap<String,String> userPermission=new MyLinkedHashMap<>();

    public MAdminMsg(BotWrapperEntity bw) {
        super(bw);
    }

	@Override
    public MAdminMsg load() {
		masterPermission.put("小律影专用指令:setconnect", "");
		masterPermission.put(".start|.stop", "总开关");
		masterPermission.put("find:[QQ号]", "在配置文件中查找此人");
		masterPermission.put("z.add[艾特至少一人]", "点赞列表");
		masterPermission.put("zan-now", "立即启动点赞线程,尽量不要用");
		masterPermission.put("block[艾特一人]", "屏蔽列表");
		masterPermission.put("black[艾特一人]", "黑名单");
		masterPermission.put("System.gc();", "System.gc();");
		masterPermission.put("-live.[start|stop]", "开关直播(hina)");
		masterPermission.put("-live.rename.[字符串]", "直播改名(hina)");
		masterPermission.put("blackgroup [群号]", "群加入黑名单,多群用空格隔开");
		masterPermission.put("av更新时间:[UID]", "用户最新后更新视频时间");
		masterPermission.put("avJson:[AV号]", "av信息");
		masterPermission.put("cv更新时间:[UID]", "用户最后更新文章时间");
		masterPermission.put("cvJson:[CV号]", "cv信息");
		masterPermission.put("直播状态lid:[直播间号]", "直播间状态");
		masterPermission.put("直播状态bid:[UID]", "从UID获取直播间状态");
		masterPermission.put("获取直播间:[UID]", "从UID获取直播间ID");
		masterPermission.put("直播时间统计", "统计的直播时间");
		masterPermission.put("群广播:[字符串]", "在所有回复的群里广播");
		masterPermission.put("nai.[称呼|直播间号].[内容]", "三月精账号发送弹幕");
		masterPermission.put("bav:[AV号]", "视频信息");
		masterPermission.put("bcv:[CV号]", "文章信息");
		masterPermission.put("blv:[直播间号]", "直播间信息");
		masterPermission.put("精神支柱[图片]|神触[图片]", "使用图片生成表情包");
		masterPermission.put("cookie.[称呼].[cookie字符串]", "设置cookie,可选值Sunny,Luna,Star,XingHuo,Hina,grzx");
		masterPermission.put("send.[群号].[内容]", "内容转发至指定群");
		masterPermission.put("mother.[字符串]", "直播间点歌问候");
		masterPermission.put("lban.[直播间号|直播间主人].[被禁言UID|被禁言者称呼].[时间]", "直播间禁言,单位为小时");
		masterPermission.put("移除成就 [成就名] [艾特一人]", "移除此人的该成就");

		adminPermission.put("findInAll:[QQ号]", "查找共同群");
		adminPermission.put("ban.[QQ号|艾特].[时间]|ban.[群号].[QQ号].[时间]", "禁言,单位为秒");
		adminPermission.put("加图指令懒得写了", "色图迫害图女装");
		adminPermission.put("蓝统计", "蓝发言统计");
		adminPermission.put("线程数", "线程池信息");
		adminPermission.put(".on|.off", "不修改配置文件的单群开关");
		adminPermission.put(".admin enable|.admin disable", "修改配置文件的单群开关");
		adminPermission.put(".live", "不管配置文件如何,都回复直播列表");

		userPermission.put(".live", "正在直播列表");
		userPermission.put(".nn [名字]", "设置蓝对你的称呼,如果不设置则恢复默认称呼");
		userPermission.put("-int [int] [+|-|*|/|<<|>>|>>>|%|^|&||] [int]", "int运算(溢出)");
		userPermission.put("-uint [int]", "int字节转uint(boom)");
		userPermission.put("抽卡", "抽卡");
		userPermission.put("给蓝master幻币转账", "抽卡，1币3卡");
		userPermission.put("查看成就", "查看成就列表");
		userPermission.put("查看符卡", "查看已获得的符卡,会刷屏，少用");
		userPermission.put("成就条件 [成就名]", "查看获得条件");
		userPermission.put("幻币兑换 [整数]", "本地幻币兑换至小律影");
		userPermission.put("~coins", "查看幻币数量");
		userPermission.put("幻币抽卡 [整数]", "使用本地幻币抽卡");
		userPermission.put("购买符卡 [符卡名]", "购买指定符卡,除lastword");
		userPermission.put("原曲认知 [E|N|H|L]", "原曲认知测试,只能回答自己的问题");

		masterPermission.putAll(adminPermission);
		masterPermission.putAll(userPermission);
		adminPermission.putAll(userPermission);
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
