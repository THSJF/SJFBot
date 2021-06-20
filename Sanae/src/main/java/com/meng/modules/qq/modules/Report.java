package com.meng.modules.qq.modules;

import com.meng.annotation.BotData;
import com.meng.config.CommandDescribe;
import com.meng.config.ConfigManager;
import com.meng.gameData.TouHou.UserInfo;
import com.meng.help.HelpGenerator;
import com.meng.help.Permission;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.qq.modules.AimMessage;
import com.meng.tools.TimeFormater;
import java.util.ArrayList;
import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @author 司徒灵羽
 */
public class Report extends BaseModule implements IGroupMessageEvent {

    @BotData("report.json")
	private ArrayList<ReportBean> reportList = new ArrayList<>();

    public Report(SBot bw) {
        super(bw);
    }

    @Override
    public String getModuleName() {
        return "内容留言";
    }

    @Override
    public BaseModule load() {
        HelpGenerator.Item mainMenu = HelpGenerator.getInstance().newItem(Permission.Normal, getModuleName());
        mainMenu.arg("-留言").arg("文本").arg("给开发者留言");
        return super.load();
    }

	@Override
    @CommandDescribe(cmd = "-留言 内容", note = "开发者可以查看留言内容")
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long qqId = gme.getSender().getId();
        long groupId = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
		if (ConfigManager.getInstance().isMaster(qqId)) {
			if (msg.equals("-留言查看")) {
				ReportBean rb = getReport();
				sendGroupMessage(groupId, rb == null ?"无留言": rb.toString());
				return true;
			}
			if (msg.startsWith("-留言查看 t ")) {
				ReportBean rb = getReport();
				entity.moduleManager.getModule(UserInfo.class).addFaith(rb.qq, 5);
				removeReport();
				if (rb.group == -5) {

				} else {
					entity.moduleManager.getModule(AimMessage.class).addTip(rb.qq, String.format("%d在%s的留言「%s」已经处理,获得5信仰奖励,附加消息:%s", rb.qq, TimeFormater.getTime(rb.time), rb.content, msg.substring(msg.indexOf("t") + 1)));
				}
				sendGroupMessage(groupId, "处理成功");
				return true;
			}
			if (msg.startsWith("-留言查看 f ")) {
				ReportBean rb = removeReport();
				sendGroupMessage(groupId, "处理成功");
				entity.moduleManager.getModule(AimMessage.class).addTip(rb.qq, String.format("%d在%s的留言「%s」已经处理:%s", rb.qq, TimeFormater.getTime(rb.time), rb.content, msg.substring(msg.indexOf("f") + 1)));
				return true;
			}
			if (msg.equalsIgnoreCase("-留言查看 w")) {
				ReportBean rb = getReport();
				if (rb.group == -5) {

				} else {
					entity.moduleManager.getModule(AimMessage.class).addTip(rb.qq, String.format("%d在%s的留言「%s」已经处理,开发者认为目前还不是处理此留言的时候", rb.qq, TimeFormater.getTime(rb.time), rb.content));
				}
				reportToLast();
				sendGroupMessage(groupId, "处理成功");
				return true;
			}
		} 
		if (msg.startsWith("-留言 ")) {
			addReport(groupId, qqId, msg.substring(4));
			sendGroupMessage(groupId, "留言成功");
			return true;
		}
		return false;
	}

	public void addReport(long fromGroup, long fromQQ, String content) {
		ReportBean report = new ReportBean();
		report.time = System.currentTimeMillis();
		report.group = fromGroup;
		report.qq = fromQQ;
		report.content = content;
		reportList.add(report);
	}

	public ReportBean removeReport() {
		if (reportList.size() == 0) {
			return null;
		}
		ReportBean rb = reportList.remove(0);
		save();
		return rb;
	}

	public void reportToLast() {
		if (reportList.size() == 0) {
			return;
		}
		reportList.add(reportList.remove(0));
		save();
	}

	public ReportBean getReport() {
		if (reportList.size() == 0) {
			return null;
		}
		return reportList.get(0);
	}

	public static class ReportBean {
		public long time;
		public long group;
		public long qq;
		public String content;

		@Override
		public String toString() {
			return String.format("时间:%s,群:%d,用户:%d\n内容:%s", TimeFormater.getTime(time), group, qq, content);
		}
	}
}

