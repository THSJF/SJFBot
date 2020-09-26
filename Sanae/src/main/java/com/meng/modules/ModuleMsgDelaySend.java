package com.meng.modules;

import com.google.gson.reflect.TypeToken;
import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.SJFInterfaces.IPersistentData;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.DataPersistenter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Description: 消息延迟发送
 * @author: 司徒灵羽
 **/

public class ModuleMsgDelaySend extends BaseGroupModule implements IPersistentData {

	private ArrayList<MessageWait> delayMsg = new ArrayList<>();

    public ModuleMsgDelaySend(BotWrapperEntity bw) {
        super(bw);
    }

    @Override
    public BotWrapperEntity getWrapper() {
        return entity;
    }

	@Override
	public String getPersistentName() {
		return "delayMessage.json";
	}

	@Override
	public Type getDataType() {
		return new TypeToken<ArrayList<ModuleMsgDelaySend.MessageWait>>(){}.getType();
	}

	@Override
	public Object getDataBean() {
		return delayMsg;
	}

	@Override
	public void setDataBean(Object o) {
		delayMsg = (ArrayList) o;
	}

	@Override
	public ModuleMsgDelaySend load() {
		DataPersistenter.read(this);
		return this;
	}

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long fromQQ = gme.getSender().getId();
        long fromGroup = gme.getGroup().getId();
		if (delayMsg.size() == 0) {
			return false;
		}
		Iterator<MessageWait> iter = delayMsg.iterator();
		while (iter.hasNext()) {
			MessageWait mw=iter.next();
			if (mw.qq == fromQQ) {
				if (mw.group == -1) {
					entity.sjfTx.sendGroupMessage(fromGroup,  mw.content);
					iter.remove();
				} else if (mw.group == fromGroup) {
					entity.sjfTx.sendGroupMessage(fromGroup,  mw.content);
					iter.remove();
				}
			}
		}
		save();
		return false;
	}

	public void addTip(long InGroup, long toQQ, String msg) {
		delayMsg.add(new MessageWait(InGroup, toQQ, msg));
		save();
	}

	public void addTip(long toQQ, String msg) {
		addTip(-1, toQQ, msg);
	}

	public void save() {
		DataPersistenter.save(this);
	}

	public class MessageWait {
		public long group = 0;
		public long qq = 0;
		public String content = "";
		public MessageWait(long inGroup, long toQQ, String msg) {
			group = inGroup;
			qq = toQQ;
			content = msg;
		}
	}
}

