package com.meng.modules;

import com.meng.SJFInterfaces.BaseModule;
import com.meng.SJFInterfaces.IDiscussMessage;
import com.meng.SJFInterfaces.IFriendEvent;
import com.meng.SJFInterfaces.IGroupEvent;
import com.meng.SJFInterfaces.IGroupMessage;
import com.meng.SJFInterfaces.IHelpMessage;
import com.meng.SJFInterfaces.IPrivateMessage;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.javabeans.GroupConfig;
import com.meng.config.javabeans.PersonConfig;
import com.meng.tip.MTimeTip;
import com.meng.tools.SJFExecutors;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import com.meng.SJFpermission;

/**
 * @Description: 模块管理器
 * @author: 司徒灵羽
 **/

public class ModuleManager extends BaseModule implements IGroupMessage, IPrivateMessage, IDiscussMessage, IGroupEvent, IFriendEvent {

    public BotWrapperEntity entity;

	private ArrayList<IGroupMessage> groupModules = new ArrayList<>();
	private ArrayList<IPrivateMessage> privateModules = new ArrayList<>();
	private ArrayList<IDiscussMessage> discussModules = new ArrayList<>();
	private ArrayList<IHelpMessage> helpModules = new ArrayList<>();
	private ArrayList<IGroupEvent> groupEventModules = new ArrayList<>();
	private ArrayList<IFriendEvent> friendEventModules = new ArrayList<>();
	private ArrayList<Object> all = new ArrayList<>();

    public ModuleManager() {
        super(null);
    }
    
    public void setBotWrapperEntity(BotWrapperEntity bwe) {
        entity = bwe;
    }

	@Override
	public ModuleManager load() {
		load(ReflectCommand.class);
        load(MtestMsg.class);
		load(MAdminMsg.class);
		load(MGroupCounter.class);
        load(MGroupCounterChart.class);
		load(MessageRefuse.class);
		load(ModuleRepeater.class);
		load(ModuleReport.class);

        load(MNumberProcess.class);
		load(ModuleMorning.class);
		load(ModuleDiceCmd.class);
		load(ModuleFaith.class);

        load(ModuleMsgDelaySend.class);
		load(MTimeTip.class);
        load(MWelcome.class, false);
        load(ModuleQA.class);
        load(ModuleQAR.class);
		SJFExecutors.execute(getGroupModule(MTimeTip.class));
        all.add(this);
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
			entity.sjfTx.sendGroupMessage(BotWrapperEntity.yysGroup, "加载失败:" + cls.getName());
		}
		loadModules(o);
	}

	public void load(String className, boolean needLoad) {
		try {
			load(Class.forName(className), needLoad);
		} catch (ClassNotFoundException e) {
			entity.sjfTx.sendGroupMessage(BotWrapperEntity.yysGroup, "加载失败:" + className);
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
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long id = gme.getSender().getId();
        long group = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
        if (msg.startsWith(".bot")) {
            if (entity.configManager.isAdminPermission(id) || entity.getGroupMemberInfo(group, id).getPermission().getLevel() > 0) {
                GroupConfig groupConfig = entity.configManager.getGroupConfig(group);
                if (msg.equals(".bot on")) {
                    groupConfig.setMainSwitchEnable(true);
                    entity.sjfTx.sendGroupMessage(group, gme, "已启用本群响应");
                    entity.configManager.save();
                    return true;
                } else if (msg.equals(".bot off")) {
                    entity.sjfTx.sendGroupMessage(group, gme, "已停用本群响应");
                    groupConfig.setMainSwitchEnable(false);
                    entity.configManager.save();
                    return true;
                } 
            } else {
                if (msg.equals(".bot on")) {
                    PersonConfig pc = entity.configManager.getPersonConfig(id);
                    pc.setBotOn(true);
                    entity.sjfTx.sendGroupMessage(group, gme, "已启用对你的响应");
                    return true;
                } else if (msg.equals(".bot off")) {
                    PersonConfig pc = entity.configManager.getPersonConfig(id);
                    pc.setBotOn(false);
                    entity.sjfTx.sendGroupMessage(group, gme, "已停用对你的响应");
                    return true;
                }
                entity.configManager.save(); 
                return true;
            }  
        }
		if (!entity.configManager.getGroupConfig(gme.getGroup().getId()).isMainSwitchEnable()) {
			return true;
		}
		for (IGroupMessage m : groupModules) {
			if (m.onGroupMessage(gme)) {
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
	public boolean onGroupMemberDecrease(MemberLeaveEvent event) {
        long fromGroup = event.getGroup().getId();
		if (!entity.configManager.getGroupConfig(fromGroup).isMainSwitchEnable()) {
            return true;
        }
		for (IGroupEvent e : groupEventModules) {
			if (e.onGroupMemberDecrease(event)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onGroupMemberIncrease(MemberJoinEvent event) {
		for (IGroupEvent e : groupEventModules) {
			if (e.onGroupMemberIncrease(event)) {
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

    public ArrayList<Object> getAllModules() {
        return all;
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

    @Override
    public String getModuleName() {
        return "模块管理器";
    }
}

