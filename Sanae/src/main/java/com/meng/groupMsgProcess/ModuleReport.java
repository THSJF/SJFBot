package com.meng.groupMsgProcess;

import com.meng.*;
import com.meng.config.*;
import com.meng.groupMsgProcess.*;
import com.meng.tools.*;

public class ModuleReport extends BaseModule {

	@Override
	public BaseModule load() {
		enable = true;
		return this;
	}

	@Override
	protected boolean processMsg(long fromGroup, long fromQQ, String msg, int msgId) {
		if (ConfigManager.instence.isMaster(fromQQ)) {
			if (msg.equals("-留言查看")) {
				SanaeConfigJavaBean.ReportBean rb=ConfigManager.instence.getReport();
				Autoreply.sendMessage(fromGroup, fromQQ, rb == null ?"无留言": rb.toString());
				return true;
			}
			if (msg.startsWith("-留言查看 t ")) {
				SanaeConfigJavaBean.ReportBean rb = ConfigManager.instence.getReport();
				ModuleManager.instence.getModule(ModuleFaith.class).addFaith(rb.q, 5);
				ConfigManager.instence.removeReport();
				if (rb.g == -5) {

				} else {
					ModuleManager.instence.getModule(ModuleMsgDelaySend.class).addTip(rb.q, String.format("%d在%s的留言「%s」已经处理,获得5信仰奖励,附加消息:%s", rb.q, Tools.CQ.getTime(rb.t), rb.c, msg.substring(msg.indexOf("t") + 1)));
				}
				Autoreply.sendMessage(fromGroup, 0, "处理成功");
				return true;
			}
			if (msg.startsWith("-留言查看 f ")) {
				SanaeConfigJavaBean.ReportBean rb = ConfigManager.instence.removeReport();
				Autoreply.sendMessage(fromGroup, 0, "处理成功");
				ModuleManager.instence.getModule(ModuleMsgDelaySend.class).addTip(rb.q, String.format("%d在%s的留言「%s」已经处理:%s", rb.q, Tools.CQ.getTime(rb.t), rb.c, msg.substring(msg.indexOf("f") + 1)));
				return true;
			}
			if (msg.equalsIgnoreCase("-留言查看 w")) {
				SanaeConfigJavaBean.ReportBean rb = ConfigManager.instence.getReport();
				if (rb.g == -5) {

				} else {
					ModuleManager.instence.getModule(ModuleMsgDelaySend.class).addTip(rb.q, String.format("%d在%s的留言「%s」已经处理,开发者认为目前还不是处理此留言的时候", rb.q, Tools.CQ.getTime(rb.t), rb.c));
				}
				ConfigManager.instence.reportToLast();
				Autoreply.sendMessage(fromGroup, 0, "处理成功");
				return true;
			}
			if (msg.equals("-反馈查看")) {
				SanaeConfigJavaBean.BugReportBean brb=ConfigManager.instence.getBugReport();
				Autoreply.sendMessage(fromGroup, fromQQ, brb == null ?"无反馈": brb.toString());
				return true;
			}
			if (msg.startsWith("-反馈查看 t ")) {
				SanaeConfigJavaBean.BugReportBean brb = ConfigManager.instence.getBugReport();
				ModuleManager.instence.getModule(ModuleFaith.class).addFaith(brb.q, 10);
				ConfigManager.instence.removeBugReport();
				ModuleManager.instence.getModule(ModuleMsgDelaySend.class).addTip(brb.q, String.format("%d在%s的反馈「%s」已经处理,获得10信仰奖励,附加消息:%s", brb.q, Tools.CQ.getTime(brb.t), brb.c, msg.substring(msg.indexOf("t") + 1)));
				Autoreply.sendMessage(fromGroup, 0, "处理成功");
				return true;
			}
			if (msg.startsWith("-反馈查看 f ")) {
				SanaeConfigJavaBean.BugReportBean brb = ConfigManager.instence.removeBugReport();
				Autoreply.sendMessage(fromGroup, 0, "处理成功");
				ModuleManager.instence.getModule(ModuleMsgDelaySend.class).addTip(brb.q, String.format("%d在%s的留言「%s」已经处理:%s", brb.q, Tools.CQ.getTime(brb.t), brb.c, msg.substring(msg.indexOf("f") + 1)));
				return true;
			}
			if (msg.equalsIgnoreCase("-反馈查看 w")) {
				SanaeConfigJavaBean.BugReportBean brb = ConfigManager.instence.getBugReport();
				ModuleManager.instence.getModule(ModuleMsgDelaySend.class).addTip(brb.q, String.format("%d在%s的反馈「%s」已经处理,开发者认为暂时不需要处理此问题", brb.q, Tools.CQ.getTime(brb.t), brb.c));
				ConfigManager.instence.bugReportToLast();
				Autoreply.sendMessage(fromGroup, 0, "处理成功");
				return true;
			}
		} 
		if (msg.startsWith("-留言 ")) {
			ConfigManager.instence.addReport(fromGroup, fromQQ, msg);
			Autoreply.sendMessage(fromGroup, fromQQ, "留言成功");
			return true;
		}
		if (msg.startsWith("-问题反馈 ")) {
			ConfigManager.instence.addBugReport(fromGroup, fromQQ, msg);
			Autoreply.sendMessage(fromGroup, fromQQ, "反馈成功");
			return true;
		}
		return false;
	}
}
