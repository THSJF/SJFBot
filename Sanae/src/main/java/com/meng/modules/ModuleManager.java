package com.meng.modules;

import com.meng.SJFInterfaces.BaseModule;
import com.meng.SJFInterfaces.IDiscussMessage;
import com.meng.SJFInterfaces.IFriendEvent;
import com.meng.SJFInterfaces.IGroupEvent;
import com.meng.SJFInterfaces.IGroupMessage;
import com.meng.SJFInterfaces.IHelpMessage;
import com.meng.SJFInterfaces.IPrivateMessage;
import com.meng.adapter.BotWrapperEntity;
import com.meng.tip.MTimeTip;
import com.meng.tools.SJFExecutors;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.lang.reflect.Constructor;

/**
 * @author 司徒灵羽
 */

public class ModuleManager extends BaseModule implements IGroupMessage, IPrivateMessage, IDiscussMessage, IGroupEvent, IFriendEvent {

    public BotWrapperEntity entity;

    public ModuleManager() {
        super(null);
    }

	private ArrayList<IGroupMessage> groupModules = new ArrayList<>();
	private ArrayList<IPrivateMessage> privateModules = new ArrayList<>();
	private ArrayList<IDiscussMessage> discussModules = new ArrayList<>();
	private ArrayList<IHelpMessage> helpModules = new ArrayList<>();
	private ArrayList<IGroupEvent> groupEventModules = new ArrayList<>();
	private ArrayList<IFriendEvent> friendEventModules = new ArrayList<>();
	private ArrayList<Object> all = new ArrayList<>();

    public void setBotWrapperEntity(BotWrapperEntity bwe) {
        entity = bwe;
    }

	@Override
	public ModuleManager load() {
		load(ReflectCommand.class);
		load(MAdminMsg.class);
		load(MGroupCounter.class);
		load(MessageRefuse.class);
		load(ModuleRepeater.class);
		load(ModuleReport.class);

		load(ModuleMorning.class);
		load(ModuleDiceCmd.class);
		load(ModuleFaith.class);


		load(MTimeTip.class);
		SJFExecutors.execute(getGroupModule(MTimeTip.class));
		return this;
	}

	public void load(Class<?> cls) {
		load(cls, true);
	}

	public void load(Class<?> cls, boolean needLoad) {
		Object o = null;
		try {
            Constructor cos = cls.getConstructor(BotWrapperEntity.class);
			o = cos.newInstance(entity);
			if (needLoad) {
				Method m = o.getClass().getMethod("load");
				m.invoke(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (o == null) {
			entity.sendGroupMessage(BotWrapperEntity.yysGroup, "加载失败:" + cls.getName());
		}
		loadModules(o);
	}

	public void load(String className, boolean needLoad) {
		try {
			load(Class.forName(className), needLoad);
		} catch (ClassNotFoundException e) {
			entity.sendGroupMessage(BotWrapperEntity.yysGroup, "加载失败:" + className);
		}
	}

	public void loadModules(Object module) {
		all.add(module);
		if (module instanceof IGroupMessage) {
			groupModules.add((IGroupMessage)module);
		}
		if (module instanceof IPrivateMessage) {
			privateModules.add((IPrivateMessage)module);
		}
		if (module instanceof IDiscussMessage) {
			discussModules.add((IDiscussMessage)module);
		}
		if (module instanceof IHelpMessage) {
			helpModules.add((IHelpMessage)module);
		}
		if (module instanceof IGroupEvent) {
			groupEventModules.add((IGroupEvent)module);
		}
		if (module instanceof IFriendEvent) {
			friendEventModules.add((IFriendEvent)module);
		}
	}

	@Override
	public boolean onGroupMessage(long fromGroup, long fromQQ, String msg, int msgId) {
		if (!entity.configManager.getGroupConfig(fromGroup).isMainSwitchEnable()) {
			return true;
		}
		for (IGroupMessage m : groupModules) {
			System.out.println(m.getClass().getName());
			if (m.onGroupMessage(fromGroup, fromQQ, msg, msgId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onPrivateMsg(long fromQQ, String msg, int msgId) {
		for (IPrivateMessage m : privateModules) {
			if (m.onPrivateMsg(fromQQ, msg, msgId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onDiscussMessage(long fromDiscuss, long fromQQ, String msg, int msgId) {
		for (IDiscussMessage m : discussModules) {
			if (m.onDiscussMessage(fromDiscuss, fromQQ, msg, msgId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onGroupFileUpload(int sendTime, long fromGroup, long fromQQ, String file) {
		if (!entity.configManager.getGroupConfig(fromGroup).isMainSwitchEnable()) {
            return true;
        }
		for (IGroupEvent e : groupEventModules) {
			if (e.onGroupFileUpload(sendTime, fromGroup, fromQQ, file)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onGroupAdminChange(int subtype, int sendTime, long fromGroup, long beingOperateQQ) {
		if (!entity.configManager.getGroupConfig(fromGroup).isMainSwitchEnable()) {
            return true;
        }
		for (IGroupEvent e : groupEventModules) {
			if (e.onGroupAdminChange(subtype, sendTime, fromGroup, beingOperateQQ)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onGroupMemberDecrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
		if (!entity.configManager.getGroupConfig(fromGroup).isMainSwitchEnable()) {
            return true;
        }
		for (IGroupEvent e : groupEventModules) {
			if (e.onGroupMemberDecrease(subtype, sendTime, fromGroup, fromQQ, beingOperateQQ)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onGroupMemberIncrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
		if (!entity.configManager.getGroupConfig(fromGroup).isMainSwitchEnable()) {
            return true;
        }
		if (beingOperateQQ == entity.getLoginQQ()) {
            return true;
        }
		for (IGroupEvent e : groupEventModules) {
			if (e.onGroupMemberIncrease(subtype, sendTime, fromGroup, fromQQ, beingOperateQQ)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onRequestAddGroup(int subtype, int sendTime, long fromGroup, long fromQQ, String msg, String responseFlag) {
		/*if (entity.configManager.isBlockQQ(fromQQ)) {
         wrapper.getCQ().setFriendAddRequest(responseFlag, Autoreply.REQUEST_REFUSE, "");
         entity.sendPrivateMessage(2856986197L, "拒绝了" + fromQQ + "加为好友");
         return true;
         }
         for (IGroupEvent e : groupEventModules) {
         if (e.onRequestAddGroup(subtype, sendTime, fromGroup, fromQQ, msg, responseFlag)) {
         return true;
         }
         }
         /*
         * REQUEST_ADOPT 通过 REQUEST_REFUSE 拒绝
         */
        //    QQInfo qInfo = CQ.getStrangerInfo(fromQQ);
        //    CQ.setFriendAddRequest(responseFlag, REQUEST_ADOPT, qInfo.getNick()); //
        // sendMessage(0, fromQQ, "本体2856986197");
        /*sendMessage(0, 2856986197L, fromQQ + "把我加为好友");*/
        // 同意好友添加请求
		return true;
	}

	@Override
	public boolean onFriendAdd(int sendTime, long fromQQ) {
		for (IFriendEvent e : friendEventModules) {
			if (e.onFriendAdd(sendTime, fromQQ)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onRequestAddFriend(int sendTime, long fromQQ, String msg, String responseFlag) {
		for (IFriendEvent e : friendEventModules) {
			if (e.onRequestAddFriend(sendTime, fromQQ, msg, responseFlag)) {
				return true;
			}
		}
		return false;
	}

	public <T> T getModule(Class<T> t) {
		for (Object m :all) {
			if (m.getClass() == t) {
				return (T)m;
			}
		}
		return null;
	}

	public <T extends IGroupMessage> T getGroupModule(Class<T> t) {
		for (IGroupMessage m :groupModules) {
			if (m.getClass() == t) {
				return (T)m;
			}
		}
		return null;
	}

	public <T extends IPrivateMessage> T getPrivateModule(Class<T> t) {
		for (IPrivateMessage m : privateModules) {
			if (m.getClass() == t) {
				return (T)m;
			}
		}
		return null;
	}

	public <T extends IDiscussMessage> T getDiscussModule(Class<T> t) {
		for (IDiscussMessage m : discussModules) {
			if (m.getClass() == t) {
				return (T)m;
			}
		}
		return null;
	}

	public <T extends IHelpMessage> T getHelpModule(Class<T> t) {
		for (IHelpMessage m : helpModules) {
			if (m.getClass() == t) {
				return (T)m;
			}
		}
		return null;
	}

	public <T extends IGroupEvent> T getGroupEventModule(Class<T> t) {
		for (IGroupEvent m : groupEventModules) {
			if (m.getClass() == t) {
				return (T)m;
			}
		}
		return null;
	}

	public <T extends IFriendEvent> T getFriendEventModule(Class<T> t) {
		for (IFriendEvent m : friendEventModules) {
			if (m.getClass() == t) {
				return (T)m;
			}
		}
		return null;
	}
}

