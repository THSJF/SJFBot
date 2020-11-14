package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.annotation.SanaeData;
import com.meng.config.DataPersistenter;
import java.util.ArrayList;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Description: 早安
 * @author: 司徒灵羽
 **/

public class ModuleMorning extends BaseGroupModule {

    @SanaeData("getUp.json")
	private ArrayList<GetUpBean> getUp = new ArrayList<>();

    public ModuleMorning(BotWrapperEntity bw) {
        super(bw);
    }

    @Override
    public String getModuleName() {
        return "morning";
    }

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long qqId = gme.getSender().getId();
        long groupId = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
		if (msg.equals("早上好")) {
			for (GetUpBean qif:getUp) {
				if (qif.qq == qqId) {
                    entity.sjfTx.sendGroupMessage(groupId, "你今天已经起床了.jpg");
					return false;
				}
			}
			GetUpBean qi = new GetUpBean();
			qi.qq = qqId;
            qi.isBoy = false;
            qi.getUptimeStamp = System.currentTimeMillis();
			getUp.add(qi);
            entity.sjfTx.sendGroupMessage(groupId, String.format("你是今天第%d位起床的少女哦", getUp.size()));
			save();
		} else if (msg.equals("晚安")) {
			for (GetUpBean qif:getUp) {
				if (qif.qq == qqId) {
					if (qif.getUptimeStamp == 0 || qif.isSleep) {
						return false;
					} else {
						entity.sjfTx.sendGroupMessage(groupId, "你今天清醒了" + secondToTime((System.currentTimeMillis() - qif.getUptimeStamp) / 1000));
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
        MTimeTask.TaskBean mt = new MTimeTask.TaskBean(0, 0, new Runnable(){

                @Override
                public void run() {
                    getUp.clear();
                }
            });
        entity.moduleManager.getModule(MTimeTask.class).addTask(mt);
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

