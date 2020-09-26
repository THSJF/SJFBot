package com.meng.modules;

import com.google.gson.reflect.*;
import com.meng.*;
import com.meng.SJFInterfaces.*;
import com.meng.config.*;
import java.lang.reflect.*;
import java.util.*;
import com.meng.adapter.BotWrapperEntity;

public class ModuleMsgDelaySend extends BaseGroupModule implements IPersistentData {

	private ArrayList<MessageWait> delayMsg = new ArrayList<>();

    public ModuleMsgDelaySend(BotWrapperEntity bw){
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
	public boolean onGroupMessage(long fromGroup, long fromQQ, String msg, int msgId) {
		if (delayMsg.size() == 0) {
			return false;
		}
		Iterator<MessageWait> iter = delayMsg.iterator();
		while (iter.hasNext()) {
			MessageWait mw=iter.next();
			if (mw.qq == fromQQ) {
				if (mw.group == -1) {
					entity.sendGroupMessage(fromGroup,  mw.content);
					iter.remove();
				} else if (mw.group == fromGroup) {
					entity.sendGroupMessage(fromGroup,  mw.content);
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

