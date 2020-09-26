package com.meng.modules;

import com.google.gson.reflect.TypeToken;
import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.SJFInterfaces.IPersistentData;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.DataPersistenter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author 司徒灵羽
 */

public class ModuleMorning extends BaseGroupModule implements IPersistentData {

	private ArrayList<GetUpBean> getUp = new ArrayList<>();

    public ModuleMorning(BotWrapperEntity bw) {
        super(bw);
    }

    @Override
    public BotWrapperEntity getWrapper() {
        return entity;
    }
    
	@Override
	public String getPersistentName() {
		return "getUp.json";
	}

	@Override
	public Type getDataType() {
		return new TypeToken<ArrayList<GetUpBean>>() {}.getType();
	}

	@Override
	public Object getDataBean() {
		return getUp;
	}

	@Override
	public void setDataBean(Object o) {
		getUp = (ArrayList) o;
	}

	@Override
	public boolean onGroupMessage(long fromGroup, long fromQQ, String msg, int msgId) {
		if (msg.equals("早上好")) {
			for (GetUpBean qif:getUp) {
				if (qif.qq == fromQQ) {
					return false;
				}
			}
			GetUpBean qi=new GetUpBean();
			qi.qq = fromQQ;
            qi.isBoy = false;
            qi.getUptimeStamp = System.currentTimeMillis();
			getUp.add(qi);
            entity.sendGroupMessage(fromGroup, String.format("你是今天第%d位起床的少女哦", getUp.size()));
			save();
		} else if (msg.equals("晚安")) {
			for (GetUpBean qif:getUp) {
				if (qif.qq == fromQQ) {
					if (qif.getUptimeStamp == 0 || qif.isSleep) {
						return false;
					} else {
						entity.sendGroupMessage(fromGroup, "你今天清醒了" + secondToTime((System.currentTimeMillis() - qif.getUptimeStamp) / 1000));
						qif.isSleep = true;
						save();
					}
				}
			}
		}
		return false;
	}

	@Override
	public ModuleMorning load() {
		DataPersistenter.read(this);
		return this;
	}

	private void save() {
		DataPersistenter.save(this);
    }

	public void reset() {
		getUp.clear();
		save();
	}

	private String secondToTime(long second) {
		long hours = second / 3600;            //转换小时
		second = second % 3600;                //剩余秒数
		long minutes = second / 60;            //转换分钟
		second = second % 60;                //剩余秒数
		return hours + "小时" + minutes + "分" + second + "秒";
	}

	private static class GetUpBean {
		public long qq;
		public long getUptimeStamp;
		public boolean isBoy;
		public boolean isSleep;
	}
}

