package com.meng.groupMsgProcess;

import com.meng.*;
import com.meng.game.TouHou.*;
import java.util.*;

public class ModuleManager extends BaseModule {
	public static ModuleManager instence;
	private ArrayList<BaseModule> modules = new ArrayList<>();

	@Override
	public BaseModule load() {
		modules.add(new ModuleAdminMsg().load());
		modules.add(new ModuleMorning().load());
		modules.add(new ModuleGroupCounter().load());
		modules.add(new ModuleMsgRefuse().load());
		modules.add(new ModuleReport().load());
		modules.add(new ModuleMsgDelaySend().load());
		modules.add(new ModuleDiceCmd().load());
		modules.add(new ModuleFaith().load());
		modules.add(new ModuleMsgAt().load());
		modules.add(new ModuleTHData().load());
		modules.add(new ModuleSpellCollect().load());
		modules.add(new ModuleQA().load());
		modules.add(new ModuleRepeater().load());
		modules.add(new ModuleCQCode().load());
		modules.add(new ModuleVirus().load());
		modules.add(new ModuleDiceImitate().load());
		modules.add(new ModuleSeq());
		modules.add(new ModuleGroupDic().load());
		modules.add(new ModuleQAR().load());
		instence = this;
		enable = true;
		return this;
	}

	@Override
	protected boolean processMsg(long fromGroup, long fromQQ, String msg, int msgId) {
		if (msg.equals("查看活跃数据")) {
			Autoreply.sendMessage(fromGroup, fromQQ, "https://qqweb.qq.com/m/qun/activedata/active.html?gc=" + fromGroup);
			return true;
		}
		for (int i=0;i < modules.size();++i) {
			if (modules.get(i).onMsg(fromGroup, fromQQ, msg, msgId)) {
				return true;
			}
		}
		return false;
	}

	public <T extends BaseModule> T getModule(Class<T> t) {
		for (int i=0;i < modules.size();++i) {
			BaseModule bm=modules.get(i);
			if (bm.getClass().getSimpleName().equals(t.getSimpleName())) {
				return (T)bm;
			}
		}
		return null;
	}
}
