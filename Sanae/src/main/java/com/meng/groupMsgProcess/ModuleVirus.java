package com.meng.groupMsgProcess;
import com.google.gson.reflect.*;
import com.meng.*;
import com.meng.tools.*;
import java.util.*;

public class ModuleVirus extends BaseModule {

	private ArrayList<VirusBean> vb=new ArrayList<>();
	@Override
	public BaseModule load() {
		enable = true;
		Autoreply.ins.threadPool.execute(new Runnable(){

				@Override
				public void run() {
					while (true) {
						try {
							String s=Tools.Network.getSourceCode("https://3g.dxy.cn/newh5/view/pneumonia");
							vb = Autoreply.gson.fromJson(s.substring(s.indexOf("window.getAreaStat = ") + "window.getAreaStat = ".length(), s.indexOf("}catch(e)")), new TypeToken<ArrayList<VirusBean>>(){}.getType());
							Thread.sleep(600000);
						} catch (Exception e) {}
					}
				}
			});
		return this;
	}

	@Override
	protected boolean processMsg(long fromGroup, long fromQQ, String msg, int msgId) {
		if (msg.startsWith("-病毒 ")) {
			Autoreply.sendMessage(fromGroup, 0, getV(msg.substring(4)));
			return true;
		}
		return false;
	}

	public String getV(String placeName) {
		for (int i=0;i < vb.size();++i) {
			VirusBean v=vb.get(i);
			if (v.provinceName.equals(placeName)) {
				return v.toString();
			}
			if (v.provinceShortName.equals(placeName)) {
				return v.toString();
			}
			ArrayList<VirusBean.Cities> cities=v.cities;
			for (int j=0;j < cities.size();++j) {
				VirusBean.Cities vc=cities.get(j);
				if (vc.cityName.equals(placeName)) {
					return vc.toString();
				}
			}
		}
		return "无结果";
	}

	public class VirusBean {
		public String provinceName;//省名
		public String provinceShortName;//省短名
		public int confirmedCount;//确诊
		public int suspectedCount;//疑似
		public int curedCount;//治愈
		public int deadCount;//死亡
		public String comment;//备注

		public ArrayList<Cities> cities;

		public class Cities {
			public String cityName;//城市名
			public int confirmedCount;//确诊
			public int suspectedCount;//疑似
			public int curedCount;//治愈
			public int deadCount;//死亡

			@Override
			public String toString() {
				return String.format("%s:确诊%d例,疑似%d例,治愈%d例,死亡%d例", cityName, confirmedCount, suspectedCount, curedCount, deadCount);
			}
		}

		@Override
		public String toString() {
			StringBuilder sb=new StringBuilder(String.format("%s:确诊%d例,疑似%d,治愈%d例,死亡%d例,%s", provinceName, confirmedCount, suspectedCount, curedCount, deadCount, (comment.equals("") ?"": "备注:" + comment)));
			for (int i=0;i < cities.size();++i) {
				sb.append("\n").append(cities.get(i).toString());
			}
			return sb.toString();
		}
	}
}
