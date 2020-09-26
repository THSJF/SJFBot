package com.meng.modules;

import com.google.gson.reflect.TypeToken;
import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.SJFInterfaces.IPersistentData;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.DataPersistenter;
import com.meng.tools.Tools;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author 司徒灵羽
 */

public class ModuleReport extends BaseGroupModule implements IPersistentData {

	public ArrayList<ReportBean> reportList=new ArrayList<>();

    public ModuleReport(BotWrapperEntity bw) {
        super(bw);
    }

	@Override
	public ModuleReport load() {
		DataPersistenter.read(this);
		return this;
	}

	@Override
	public boolean onGroupMessage(long fromGroup, long fromQQ, String msg, int msgId) {
		if (entity.configManager.isMaster(fromQQ)) {
			if (msg.equals("-留言查看")) {
				ReportBean rb = getReport();
				entity.sendGroupMessage(fromGroup, rb == null ?"无留言": rb.toString());
				return true;
			}
			if (msg.startsWith("-留言查看 t ")) {
				ReportBean rb = getReport();
				entity.moduleManager.getModule(ModuleFaith.class).addFaith(rb.qq, 5);
				removeReport();
				if (rb.group == -5) {

				} else {
					entity.moduleManager.getModule(ModuleMsgDelaySend.class).addTip(rb.qq, String.format("%d在%s的留言「%s」已经处理,获得5信仰奖励,附加消息:%s", rb.qq, Tools.CQ.getTime(rb.time), rb.content, msg.substring(msg.indexOf("t") + 1)));
				}
				entity.sendGroupMessage(fromGroup, "处理成功");
				return true;
			}
			if (msg.startsWith("-留言查看 f ")) {
				ReportBean rb = removeReport();
				entity.sendGroupMessage(fromGroup, "处理成功");
				entity.moduleManager.getModule(ModuleMsgDelaySend.class).addTip(rb.qq, String.format("%d在%s的留言「%s」已经处理:%s", rb.qq, Tools.CQ.getTime(rb.time), rb.content, msg.substring(msg.indexOf("f") + 1)));
				return true;
			}
			if (msg.equalsIgnoreCase("-留言查看 w")) {
				ReportBean rb = getReport();
				if (rb.group == -5) {

				} else {
					entity.moduleManager.getModule(ModuleMsgDelaySend.class).addTip(rb.qq, String.format("%d在%s的留言「%s」已经处理,开发者认为目前还不是处理此留言的时候", rb.qq, Tools.CQ.getTime(rb.time), rb.content));
				}
				reportToLast();
				entity.sendGroupMessage(fromGroup, "处理成功");
				return true;
			}
		} 
		if (msg.startsWith("-留言 ")) {
			addReport(fromGroup, fromQQ, msg);
			entity.sendGroupMessage(fromGroup, "留言成功");
			return true;
		}
		return false;
	}

	public void addReport(long fromGroup, long fromQQ, String content) {
		ReportBean report=new ReportBean();
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

	public void save() {
		DataPersistenter.save(this);
	}

	public ReportBean getReport() {
		if (reportList.size() == 0) {
			return null;
		}
		return reportList.get(0);
	}

	@Override
	public String getPersistentName() {
		return "report.json";
	}

	@Override
	public Type getDataType() {
		return new TypeToken<ArrayList<ReportBean>>(){}.getType();
	}

	@Override
	public Object getDataBean() {
		return reportList;
	}

    @Override
    public BotWrapperEntity getWrapper() {
        return entity;
    }

	@Override
	public void setDataBean(Object o) {
		reportList = (ArrayList<ModuleReport.ReportBean>) o;
	}

	public static class ReportBean {
		public long time;
		public long group;
		public long qq;
		public String content;

		@Override
		public String toString() {
			return String.format("时间:%s,群:%d,用户:%d\n内容:%s", Tools.CQ.getTime(time), group, qq, content);
		}
	}
}

