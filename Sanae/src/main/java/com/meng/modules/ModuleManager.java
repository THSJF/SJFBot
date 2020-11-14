package com.meng.modules;

import com.meng.SJFInterfaces.IFriendEvent;
import com.meng.SJFInterfaces.IFriendMessage;
import com.meng.SJFInterfaces.IGroupEvent;
import com.meng.SJFInterfaces.IGroupMessage;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.ConfigManager;
import com.meng.config.javabeans.PersonConfig;
import com.meng.gameData.TouHou.Faith;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Description: 模块管理器
 * @author: 司徒灵羽
 **/

public class ModuleManager implements IGroupMessage, IFriendMessage, IGroupEvent, IFriendEvent {

    public BotWrapperEntity entity;

	private List<IGroupMessage> groupModules = new ArrayList<>();
	private List<IFriendMessage> friendModules = new ArrayList<>();
	private List<IGroupEvent> groupEventModules = new ArrayList<>();
	private List<IFriendEvent> friendEventModules = new ArrayList<>();
	private List<Object> all = new ArrayList<>();

    public void setBotWrapperEntity(BotWrapperEntity bwe) {
        entity = bwe;
    }

	public ModuleManager load() {
		load(ReflectCommand.class);
        load(MtestMsg.class);
		load(MAdminMsg.class);
        load(Recall.class);
        load(MainSwitch.class);
        load(MGroupCounterChart.class);
		load(MessageRefuse.class);
		load(ModuleRepeater.class);
		load(ModuleReport.class);

        load(MNumberProcess.class);
		load(ModuleDiceCmd.class);
		load(Faith.class);
        load(MTimeTask.class);
        load(MAimMessage.class);
        load(MemberChange.class, false);
        load(ModuleQA.class);
        load(DynamicWordStock.class);
        load(ModuleMorning.class);
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
			try {
                o = cls.newInstance();
                if (needLoad) {
                    Method m = o.getClass().getMethod("load");
                    m.invoke(o);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
		if (module instanceof IFriendMessage) {
			friendModules.add((IFriendMessage)module);
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
        long qqId = gme.getSender().getId();
        long groupId = gme.getGroup().getId();
//        if (groupId != BotWrapperEntity.yysGroup) {
//            return true;
//        }
        String msg = gme.getMessage().contentToString();
        if (msg.startsWith(".bot")) {
            ConfigManager cm = entity.configManager;
            if (cm.isAdminPermission(qqId) || entity.getGroupMemberInfo(groupId, qqId).getPermission().getLevel() > 0) {
                if (msg.equals(".bot on")) {
                    cm.setFunctionDisable(groupId, MainSwitch.class);
                    entity.sjfTx.sendQuote(gme, "已启用本群响应");
                    cm.save();
                    return true;
                } else if (msg.equals(".bot off")) {
                    entity.sjfTx.sendQuote(gme, "已停用本群响应");
                    cm.setFunctionDisable(groupId, MainSwitch.class);
                    cm.save();
                    return true;
                } 
            } else {
                if (msg.equals(".bot on")) {
                    PersonConfig pc = entity.configManager.getPersonConfig(qqId);
                    pc.setBotOn(true);
                    entity.sjfTx.sendQuote(gme, "已启用对你的响应");
                    return true;
                } else if (msg.equals(".bot off")) {
                    PersonConfig pc = entity.configManager.getPersonConfig(qqId);
                    pc.setBotOn(false);
                    entity.sjfTx.sendQuote(gme, "已停用对你的响应");
                    return true;
                }
                entity.configManager.save(); 
                return true;
            }  
        }
        if (!entity.configManager.isFunctionEnbled(groupId, MainSwitch.class)) {
            return false; 
        }
		for (IGroupMessage m : groupModules) {
			if (m.onGroupMessage(gme)) {
				return true;
			}
		}
		return false;
	}

    @Override
    public boolean onFriendMessage(FriendMessageEvent event) {
		for (IFriendMessage m : friendModules) {
			if (m.onFriendMessage(event)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onGroupFileUpload(int sendTime, long fromGroup, long fromQQ, String file) {
		if (!entity.configManager.isFunctionEnbled(fromGroup, MainSwitch.class)) {
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
		if (!entity.configManager.isFunctionEnbled(fromGroup, MainSwitch.class)) {
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
		if (!entity.configManager.isFunctionEnbled(fromGroup, MainSwitch.class)) {
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
		if (!entity.configManager.isFunctionEnbled(event.getGroup().getId(), MainSwitch.class)) {
            return true;
        }
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

    public List<Object> getAllModules() {
        return all;
    }

	public <T> T getModule(Class<T> t) {
        if (t == ModuleManager.class) {
            return (T)this;
        }
        if (t == ConfigManager.class) {
            return (T)entity.configManager;
        }
        if (t == BotWrapperEntity.class) {
            return (T)entity;
        }
		for (Object m :all) {
			if (m.getClass() == t) {
				return (T)m;
			}
		}
		return null;
	}
}

