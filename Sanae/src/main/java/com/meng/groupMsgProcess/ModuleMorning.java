package com.meng.groupMsgProcess;
import com.google.gson.reflect.*;
import com.meng.*;
import com.meng.config.*;
import com.meng.tools.*;
import com.sobte.cqp.jcq.entity.*;
import java.io.*;
import java.lang.reflect.*;
import java.nio.charset.*;
import java.util.*;

public class ModuleMorning extends BaseModule {

	private ArrayList<GetUpBean> getUp;

	@Override
	public BaseModule load() {
		Type type = new TypeToken<ArrayList<GetUpBean>>() {
		}.getType();
		File gotUpF = new File(Autoreply.appDirectory + "/getUp.json");
		if (!gotUpF.exists()) {
			getUp = new ArrayList<>();
			saveConfig();
		}
        getUp = Autoreply.gson.fromJson(Tools.FileTool.readString(gotUpF), type);
		enable = true;
		return this;
	}

	@Override
	protected boolean processMsg(long fromGroup, long fromQQ, String msg, int msgId) {
		if (msg.equals("早上好")) {
			for (GetUpBean qif:getUp) {
				if (qif.qq == fromQQ) {
					return false;
				}
			}
			GetUpBean qi=new GetUpBean();
			qi.qq = fromQQ;
			QQInfo qif=Autoreply.CQ.getStrangerInfo(fromQQ);
			if (qif == null) {
				qi.isBoy = false;
			} else {
				qi.isBoy = qif.getGender() == 0;
			}
			qi.getUptimeStamp = System.currentTimeMillis();
			getUp.add(qi);
			String ni="bba";
			if (qi.isBoy) {
				if (qif.getAge() > 18) {
					ni = "老大爷";
				} else {
					ni = "少年";
				}
			} else if ((qif.getAge() > 16 || Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 6) && !ConfigManager.instence.isAdmin(fromQQ)) {
				ni = "bba";
			} else {
				ni = "少女";
			}
			Autoreply.CQ.sendGroupMsg(fromGroup, String.format("你是今天第%d位起床的%s哦", getUp.size(), ni));
			saveConfig();
		} else if (msg.equals("晚安")) {
			for (GetUpBean qif:getUp) {
				if (qif.qq == fromQQ) {
					if (qif.getUptimeStamp == 0 || qif.isSleep) {
						return false;
					} else {
						Autoreply.CQ.sendGroupMsg(fromGroup, "你今天清醒了" + secondToTime((System.currentTimeMillis() - qif.getUptimeStamp) / 1000));
						qif.isSleep = true;
						saveConfig();
					}
				}
			}
		}
		return false;
	}

	private void saveConfig() {
		try {
			FileOutputStream fos = new FileOutputStream(new File(Autoreply.appDirectory + "/getUp.json"));
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.write(Autoreply.gson.toJson(getUp));
            writer.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void reset() {
		getUp.clear();
		saveConfig();
	}

	public String secondToTime(long second) {
		long hours = second / 3600;            //转换小时
		second = second % 3600;                //剩余秒数
		long minutes = second / 60;            //转换分钟
		second = second % 60;                //剩余秒数
		return hours + "小时" + minutes + "分" + second + "秒";
	}
}
