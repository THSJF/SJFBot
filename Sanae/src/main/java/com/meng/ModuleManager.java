package com.meng;

import com.meng.config.ConfigManager;
import com.meng.config.javabeans.PersonConfig;
import com.meng.gameData.TouHou.Faith;
import com.meng.handler.friend.IFriendChangeEvent;
import com.meng.handler.friend.IFriendEvent;
import com.meng.handler.friend.IFriendMessageEvent;
import com.meng.handler.group.IGroupBotEvent;
import com.meng.handler.group.IGroupEvent;
import com.meng.handler.group.IGroupMemberEvent;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.handler.group.IGroupSettingEvent;
import com.meng.modules.BaseModule;
import com.meng.modules.DynamicWordStock;
import com.meng.modules.MAdminMsg;
import com.meng.modules.MAimMessage;
import com.meng.modules.MGroupCounterChart;
import com.meng.modules.MNumberProcess;
import com.meng.modules.MTimeTask;
import com.meng.modules.MemberChange;
import com.meng.modules.MessageRefuse;
import com.meng.modules.ModuleDiceCmd;
import com.meng.modules.ModuleMorning;
import com.meng.modules.ModuleQA;
import com.meng.modules.ModuleRepeater;
import com.meng.modules.ModuleReport;
import com.meng.modules.Modules;
import com.meng.modules.MtestMsg;
import com.meng.modules.ReflectCommand;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;
import net.mamoe.mirai.event.events.BotMuteEvent;
import net.mamoe.mirai.event.events.BotUnmuteEvent;
import net.mamoe.mirai.event.events.FriendAddEvent;
import net.mamoe.mirai.event.events.FriendDeleteEvent;
import net.mamoe.mirai.event.events.GroupAllowAnonymousChatEvent;
import net.mamoe.mirai.event.events.GroupAllowConfessTalkEvent;
import net.mamoe.mirai.event.events.GroupAllowMemberInviteEvent;
import net.mamoe.mirai.event.events.GroupEntranceAnnouncementChangeEvent;
import net.mamoe.mirai.event.events.GroupMuteAllEvent;
import net.mamoe.mirai.event.events.GroupNameChangeEvent;
import net.mamoe.mirai.event.events.GroupSettingChangeEvent;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MemberMuteEvent;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;
import net.mamoe.mirai.event.events.MemberSpecialTitleChangeEvent;
import net.mamoe.mirai.event.events.MemberUnmuteEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import com.meng.gameData.TouHou.Goodwill;

/**
 * @Description: 模块管理器
 * @author: 司徒灵羽
 **/

public class ModuleManager extends BaseModule implements IGroupEvent, IFriendEvent {

    private List<IGroupBotEvent> groupBotHandlers = new ArrayList<>();
    private List<IGroupMessageEvent> groupMsgHandlers = new ArrayList<>();
    private List<IGroupSettingEvent> groupSettingsHandlers = new ArrayList<>();
    private List<IGroupMemberEvent> groupMemberHandlers = new ArrayList<>();
    private List<IFriendMessageEvent> friendMsgHandlers = new ArrayList<>();
    private List<IFriendChangeEvent> friendChangeHandlers = new ArrayList<>();

    private List<Object> all = new ArrayList<>();

    public ModuleManager(SBot bwe) {
        super(bwe);
    }

    @Override
    public String getModuleName() {
        return "moudlemanager";
    }

    public ModuleManager load() {
        load(ReflectCommand.class);
        load(MtestMsg.class);
        load(MAdminMsg.class);

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
        load(Goodwill.class);
        return this;
    }

    public void load(Class<?> cls) {
        load(cls, true);
    }

    public void load(Class<?> cls, boolean needLoad) {
        Object o = null;
        try {
            Constructor cos = cls.getConstructor(SBot.class);
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
            entity.sendGroupMessage(SBot.yysGroup, "加载失败:" + cls.getName());
        }
        loadModules(o);
    }

    public void load(String className, boolean needLoad) {
        try {
            load(Class.forName(className), needLoad);
        } catch (ClassNotFoundException e) {
            entity.sendGroupMessage(SBot.yysGroup, "加载失败:" + className);
        }
    }

    public void loadModules(Object module) {
        all.add(module);
        if (module instanceof IGroupBotEvent) {
            groupBotHandlers.add((IGroupBotEvent)module);
        }
        if (module instanceof IGroupMessageEvent) {
            groupMsgHandlers.add((IGroupMessageEvent)module);
        }
        if (module instanceof IGroupSettingEvent) {
            groupSettingsHandlers.add((IGroupSettingEvent)module);
        }
        if (module instanceof IGroupMemberEvent) {
            groupMemberHandlers.add((IGroupMemberEvent)module);
        }
        if (module instanceof IFriendMessageEvent) {
            friendMsgHandlers.add((IFriendMessageEvent)module);
        }
        if (module instanceof IFriendChangeEvent) {
            friendChangeHandlers.add((IFriendChangeEvent)module);
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
            if (cm.isAdminPermission(qqId) || entity.getGroup(groupId).get(qqId).getPermission().getLevel() > 0) {
                if (msg.equals(".bot on")) {
                    cm.setFunctionDisable(groupId, Modules.MAIN_SWITCH);
                    entity.sendQuote(gme, "已启用本群响应");
                    cm.save();
                    return true;
                } else if (msg.equals(".bot off")) {
                    entity.sendQuote(gme, "已停用本群响应");
                    cm.setFunctionDisable(groupId, Modules.MAIN_SWITCH);
                    cm.save();
                    return true;
                } 
            } else {
                if (msg.equals(".bot on")) {
                    PersonConfig pc = entity.configManager.getPersonConfig(qqId);
                    pc.setBotOn(true);
                    entity.sendQuote(gme, "已启用对你的响应");
                    return true;
                } else if (msg.equals(".bot off")) {
                    PersonConfig pc = entity.configManager.getPersonConfig(qqId);
                    pc.setBotOn(false);
                    entity.sendQuote(gme, "已停用对你的响应");
                    return true;
                }
                entity.configManager.save(); 
                return true;
            }  
        }
        if (!entity.configManager.isFunctionEnbled(groupId, Modules.MAIN_SWITCH)) {
            return false; 
        }
        for (IGroupMessageEvent m : groupMsgHandlers) {
            if (m.onGroupMessage(gme)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        if (!entity.configManager.isFunctionEnbled(event.getGroup().getId(), Modules.MAIN_SWITCH)) {
            return false; 
        }
        for (IGroupMessageEvent m : groupMsgHandlers) {
            if (m.onGroupMemberNudge(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        if (!entity.configManager.isFunctionEnbled(event.getGroup().getId(), Modules.RECALL)) {
            return false; 
        }
        for (IGroupMessageEvent m : groupMsgHandlers) {
            if (m.onGroupMessageRecall(event)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean onFriendMessage(FriendMessageEvent event) {
        for (IFriendMessageEvent m : friendMsgHandlers) {
            if (m.onFriendMessage(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onFriendAdd(FriendAddEvent event) {
        for (IFriendChangeEvent m : friendChangeHandlers) {
            if (m.onFriendAdd(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onRequestAddFriend(NewFriendRequestEvent event) {
        for (IFriendChangeEvent m : friendChangeHandlers) {
            if (m.onRequestAddFriend(event)) {
                return true;
            }
        } 
        return false;
    }

    @Override
    public boolean onFriendDelete(FriendDeleteEvent event) {
        for (IFriendChangeEvent m : friendChangeHandlers) {
            if (m.onFriendDelete(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onBotLeave(BotLeaveEvent event) {
        for (IGroupBotEvent m : groupBotHandlers) {
            if (m.onBotLeave(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onBotPermissionChange(BotGroupPermissionChangeEvent event) {
        for (IGroupBotEvent m : groupBotHandlers) {
            if (m.onBotPermissionChange(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onBotMute(BotMuteEvent event) {
        for (IGroupBotEvent m : groupBotHandlers) {
            if (m.onBotMute(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onBotUnmute(BotUnmuteEvent event) {
        for (IGroupBotEvent m : groupBotHandlers) {
            if (m.onBotUnmute(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onBotJoinGroup(BotJoinGroupEvent event) {
        for (IGroupBotEvent m : groupBotHandlers) {
            if (m.onBotJoinGroup(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onGroupSettingChange(GroupSettingChangeEvent event) {
        for (IGroupSettingEvent m : groupSettingsHandlers) {
            if (m.onGroupSettingChange(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onGroupNameChange(GroupNameChangeEvent event) {
        for (IGroupSettingEvent m : groupSettingsHandlers) {
            if (m.onGroupNameChange(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onGroupEntranceAnnouncementChange(GroupEntranceAnnouncementChangeEvent event) {
        for (IGroupSettingEvent m : groupSettingsHandlers) {
            if (m.onGroupEntranceAnnouncementChange(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onGroupMuteAll(GroupMuteAllEvent event) {
        for (IGroupSettingEvent m : groupSettingsHandlers) {
            if (m.onGroupMuteAll(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onGroupAllowAnonymousChange(GroupAllowAnonymousChatEvent event) {
        for (IGroupSettingEvent m : groupSettingsHandlers) {
            if (m.onGroupAllowAnonymousChange(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onGroupAllowConfessTalkChange(GroupAllowConfessTalkEvent event) {
        for (IGroupSettingEvent m : groupSettingsHandlers) {
            if (m.onGroupAllowConfessTalkChange(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onAllowInviteChange(GroupAllowMemberInviteEvent event) {
        for (IGroupSettingEvent m : groupSettingsHandlers) {
            if (m.onAllowInviteChange(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onMemberJoinRequest(MemberJoinRequestEvent event) {
        for (IGroupMemberEvent m : groupMemberHandlers) {
            if (m.onMemberJoinRequest(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onMemberJoin(MemberJoinEvent event) {
        for (IGroupMemberEvent m : groupMemberHandlers) {
            if (m.onMemberJoin(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onMemberLeave(MemberLeaveEvent event) {
        for (IGroupMemberEvent m : groupMemberHandlers) {
            if (m.onMemberLeave(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onInviteBot(BotInvitedJoinGroupRequestEvent event) {
        for (IGroupMemberEvent m : groupMemberHandlers) {
            if (m.onInviteBot(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCardChange(MemberCardChangeEvent event) {
        for (IGroupMemberEvent m : groupMemberHandlers) {
            if (m.onCardChange(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onTitleChange(MemberSpecialTitleChangeEvent event) {
        for (IGroupMemberEvent m : groupMemberHandlers) {
            if (m.onTitleChange(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onPermissionChange(MemberPermissionChangeEvent event) {
        for (IGroupMemberEvent m : groupMemberHandlers) {
            if (m.onPermissionChange(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onMemberMute(MemberMuteEvent event) {
        for (IGroupMemberEvent m : groupMemberHandlers) {
            if (m.onMemberMute(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onMemberUnmute(MemberUnmuteEvent event) {
        for (IGroupMemberEvent m : groupMemberHandlers) {
            if (m.onMemberUnmute(event)) {
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
        if (t == SBot.class) {
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
