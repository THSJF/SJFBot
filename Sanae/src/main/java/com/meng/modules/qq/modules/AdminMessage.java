package com.meng.modules.qq.modules;

import com.meng.bot.Functions;
import com.meng.config.CommandDescribe;
import com.meng.config.ConfigManager;
import com.meng.config.Person;
import com.meng.help.HelpGenerator;
import com.meng.help.Permission;
import com.meng.modules.Baidu;
import com.meng.modules.Lkaa;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.qq.handler.group.INudgeEvent;
import com.meng.modules.qq.hotfix.HotfixClassLoader;
import com.meng.modules.qq.hotfix.SJFCompiler;
import com.meng.modules.touhou.THGameDataManager;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.Hash;
import com.meng.tools.JsonHelper;
import com.meng.tools.Network;
import com.meng.tools.SJFExecutors;
import com.meng.tools.SJFPathTool;
import com.meng.tools.TextLexer;
import com.meng.tools.Tools;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.NudgeEvent;
import net.mamoe.mirai.message.data.Voice;

/**
 * @Description: 管理员命令
 * @author: 司徒灵羽
 **/
public class AdminMessage extends BaseModule implements IGroupMessageEvent ,INudgeEvent {

    public AdminMessage(SBot bw) {
        super(bw);
    }

    @Override
    public String getModuleName() {
        return "管理指令";
    }

    @Override
    public BaseModule load() {
        super.load();
        HelpGenerator.Item mainMenu = HelpGenerator.getInstance().newItem(Permission.Owner, getModuleName());
        mainMenu.arg(".tr").arg("文本").arg("翻译").permission(Permission.Normal);
        new HelpGenerator.Item(mainMenu, ".tts", Permission.Normal){{
                arg("文本").arg("文字转语音");
                arg("群号").arg("文本").arg("文字转语音发送至指定群");
            }};
        mainMenu.arg(".findGroup").arg("账号").arg("在bot所在群中查找账号").permission(Permission.Admin);
        mainMenu.arg(".welcome").arg("文本").arg("设置入群欢迎词").permission(Permission.Admin);
        new HelpGenerator.Item(mainMenu, ".groupCard", Permission.Master){{
                arg("账号").arg("文本").arg("设置用户群昵称").permission(Permission.Master);
                arg("群号").arg("账号").arg("文本").arg("设置用户群昵称").permission(Permission.Master);
            }};
        new HelpGenerator.Item(mainMenu, ".switch", Permission.Master){{
                arg("查看本群可用功能开关");
                arg("(开关名)").arg("控制开关");
            }};
        mainMenu.arg(".broadcast").arg("文本").arg("在bot所在群中广播");
        mainMenu.arg(".stop").arg("关闭总开关");
        mainMenu.arg(".start").arg("打开总开关");
        mainMenu.arg(".findConfig").arg("文本/数字").arg("在配置文件中查找");
        mainMenu.arg(".thread").arg("线程池信息");
        mainMenu.arg(".gc").arg("触发一次显式GC");
        new HelpGenerator.Item(mainMenu, ".send"){{
                arg("文本").arg("发送至本群");
                arg("群号").arg("文本").arg("发送至指定群");
            }};
        mainMenu.arg(".groupTitle").arg("群号").arg("文本").arg("设置群头衔");
        mainMenu.arg(".black").arg("账号").arg("加入黑名单");
        mainMenu.arg(".block").arg("账号").arg("加入屏蔽列表");
        mainMenu.arg(".kick").arg("账号").arg("踢出本群");
        mainMenu.arg(".mute").arg("账号").arg("秒").arg("禁言指定群员");
        return this;
    }

    @Override
    @CommandDescribe(cmd = "-", note = "主要给管理员用的指令")
    public boolean onGroupMessage(GroupMessageEvent gme) {
        ConfigManager configManager = ConfigManager.getInstance();
        long qqId = gme.getSender().getId();
        long groupId = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
        if (msg.charAt(0) != '.') {
            return false;
        }
        ArrayList<String> list = TextLexer.analyze(msg);
        Iterator<String> iter = list.iterator();
        iter.next();//.
        try {
            String first = iter.next();
            switch (first) {
                case "tts":
                    if (list.size() == 3) {
                        sendGroupMessage(groupId, entity.toVoice(Lkaa.generalVoice(iter.next()), gme.getGroup()));
                    } else if (list.size() == 4) {
                        sendGroupMessage(Long.parseLong(iter.next()), entity.toVoice(Lkaa.generalVoice(iter.next()), gme.getGroup()));
                    }  
                    return true;
                case "tr":
                    String next = iter.next();
                    String translate = THGameDataManager.generalTranslate(next);
                    sendGroupMessage(groupId, translate == null ? Baidu.getInstance().generalTranslate(next) : translate);
                    return true;
            }
            if (!configManager.isAdminPermission(qqId) && entity.getGroupMemberInfo(groupId, qqId).getPermission().getLevel() < 1) {
                return false;
            }
            switch (first) {
                case "findGroup":
                    Tools.CQ.findQQInAllGroup(entity, groupId, qqId, iter.next());
                    return true;
                case "welcome":
                    String wel = null;
                    if (iter.hasNext()) {
                        wel = iter.next();
                    }
                    configManager.setWelcome(groupId, wel);
                    configManager.save();
                    sendGroupMessage(groupId, "已设置为:" + wel);
                    return true;
            }
            if (!configManager.isMaster(qqId) && entity.getGroupMemberInfo(groupId, qqId).getPermission().getLevel() > 1) {
                return false;
            }
            switch (first) {
                case "groupCard":
                    if (list.size() == 3) {
                        entity.setGroupCard(groupId, entity.getId(), iter.next());
                    } else if (list.size() == 4) {
                        entity.setGroupCard(groupId, Long.parseLong(iter.next()), iter.next());
                    }
                    return true;
                case "switch":
                    if (list.size() == 2) {
                        StringBuilder sb = new StringBuilder("当前有:\n");
                        for (Functions function:Functions.values()) {
                            sb.append(function.getName()).append("\n");
                        }
                        sb.setLength(sb.length() - 1);
                        sendGroupMessage(gme.getGroup().getId(), sb.toString());
                    } else if (list.size() == 3) {
                        Functions function = Functions.get(iter.next());
                        if (function != null) {
                            sendQuote(gme, (function.change(configManager, groupId) ?"已启用" : "已停用") + function.toString());
                        } else {
                            sendQuote(gme, "无此开关");
                        }
                    }
                    return true;
            }
            if (!configManager.isMaster(qqId)) {
                return false;
            }
            switch (first) {
                case "broadcast":
                    String broadcast = iter.next();
                    HashSet<Group> hs = new HashSet<>();
                    Collection<Group> glist = entity.getGroups();
                    for (Group g:glist) {
                        if (!configManager.isFunctionEnabled(groupId, Functions.GroupMessageEvent)) {
                            continue;
                        }
                        sendGroupMessage(g.getId(), broadcast);
                        hs.add(g);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {}
                    }
                    StringBuilder result = new StringBuilder("在以下群发送了广播:");
                    for (Group g:hs) {
                        result.append("\n").append(g.getId()).append(":").append(g.getName());
                    }
                    sendGroupMessage(groupId, result.toString());
                    return true;
                case "stop":
                    sendQuote(gme, "disabled");
                    entity.sleeping = true;
                    return true;
                case "start":
                    entity.sleeping = false;
                    sendQuote(gme, "enabled");
                    return true;
                case "findConfig":
                    String name = iter.next();
                    HashSet<Person> hashSet = new HashSet<>();
                    for (Person personInfo : configManager.getPerson()) {
                        if (personInfo.name.contains(name)) {
                            hashSet.add(personInfo);
                        }
                        if (personInfo.qq != 0 && Long.toString(personInfo.qq).contains(name)) {
                            hashSet.add(personInfo);
                        }
                        if (personInfo.bid != 0 && String.valueOf(personInfo.bid).contains(name)) {
                            hashSet.add(personInfo);
                        }
                        if (personInfo.bLiveRoom != 0 && String.valueOf(personInfo.bLiveRoom).contains(name)) {
                            hashSet.add(personInfo);
                        }
                    }
                    sendGroupMessage(groupId, JsonHelper.toJson(hashSet)); 
                    return true;
                case "thread":
                    String s = "taskCount：" + SJFExecutors.getTaskCount() + "\n" +
                        "completedTaskCount：" + SJFExecutors.getCompletedTaskCount() + "\n" +
                        "largestPoolSize：" + SJFExecutors.getLargestPoolSize() + "\n" +
                        "poolSize：" + SJFExecutors.getPoolSize() + "\n" +
                        "activeCount：" + SJFExecutors.getActiveCount();
                    sendGroupMessage(groupId, s);
                    return true;
                case "gc":
                    System.gc();
                    sendGroupMessage(groupId, "gc start");
                    return true;
                case "send":
                    if (list.size() == 3) {
                        sendGroupMessage(groupId, iter.next());
                    } else if (list.size() == 4) {
                        String next = iter.next();
                        if (next.equals("晚上好啊老婆们")) {
                            Voice ptt = entity.toVoice(new FileInputStream(SJFPathTool.getTTSPath("晚上好啊老婆们.wav")), gme.getGroup());
                            sendQuote(gme, ptt);
                        } else {
                            sendGroupMessage(Long.parseLong(next), iter.next());
                        }
                    }
                    return true;
                case "groupTitle":
                    entity.setGroupSpecialTitle(groupId, Long.parseLong(iter.next()), iter.next());
                    return true;
                case "block":
                    {
                        StringBuilder sb = new StringBuilder("屏蔽列表添加:");
                        String nextqq;
                        while ((nextqq = iter.next()) != null) {
                            configManager.addBlackQQ(Long.parseLong(nextqq));   
                            sb.append(nextqq).append(" ");
                            configManager.save();
                        }
                        sendGroupMessage(groupId, sb.toString());
                    }
                    return true;
                case "black":
                    { 
                        StringBuilder sb = new StringBuilder("屏蔽列表添加:");
                        String nextqq;
                        while ((nextqq = iter.next()) != null) {
                            configManager.addBlackQQ(Long.parseLong(nextqq));   
                            sb.append(nextqq).append(" ");
                        }
                        configManager.save();
                        sendGroupMessage(groupId, sb.toString());
                    }
                    return true;
                case "kick":
                    {
                        long target = entity.getAt(gme.getMessage());
                        if (target == -1) {
                            target = Long.parseLong(iter.next());
                        }
                        NormalMember targetMember = entity.getGroupMemberInfo(groupId, target);
                        if (targetMember != null) {
                            if (iter.hasNext()) {
                                targetMember.kick(iter.next());
                            } else {
                                targetMember.kick("");
                            }
                        } else {
                            sendGroupMessage(groupId, "未找到该成员:" + target);
                        }
                    }
                    return true;
                case "mute":
                    {
                        long target = entity.getAt(gme.getMessage());
                        if (target == -1) {
                            target = Long.parseLong(iter.next());
                        }
                        Member targetMember = entity.getGroupMemberInfo(groupId, target);
                        if (targetMember != null) {
                            targetMember.mute(Integer.parseInt(iter.next()));
                        } else {
                            sendGroupMessage(groupId, "未找到该成员:" + target);
                        }
                    }
                    return true;
            }
            if (qqId != 2856986197L) {
                return false;
            }
            switch (first) {
                case "hotfix":
                    {
                        String nane = iter.next();
                        String code = msg.substring(msg.indexOf(" ", 8));
                        HotfixClassLoader clsLd = new HotfixClassLoader(new HashMap<String,byte[]>());
                        SJFCompiler.generate(clsLd, nane, code);
                        Class<? extends Object> nClass = clsLd.loadClass(nane);
                        Constructor constructor = nClass.getDeclaredConstructor(entity.getClass());
                        Object module = constructor.newInstance(entity);
                        Method methodLoad = nClass.getMethod("load");
                        if (methodLoad != null) {
                            methodLoad.invoke(module);
                        }
                        entity.moduleManager.hotfix(module);
                        sendMessage(gme.getGroup(), nane + " loaded");
                    }
                    return true;
                case "hotfixcancel":
                    {
                        Object obj = entity.moduleManager.hotfixCancel(iter.next());
                        sendQuote(gme, obj != null ? "canceled" : "cancel failed");
                    }
                    return true;
                case "exit":
                    gme.getGroup().quit();
                    return true;
                case "openAll":
                    Functions function = Functions.get(iter.next());
                    if (function != null) {
                        for (Group group : entity.getGroups()) {
                            configManager.setFunctionEnabled(group.getId(), function, true);
                        }
                        sendQuote(gme, function + "已启用");
                    } else {
                        sendQuote(gme, "无此开关");
                    }
                    return true;
                case "save":
                    String arg = iter.next();
                    if (arg.equals("pixiv")) {
                        byte[] img = Network.httpGetRaw("https://www.pixiv.cat/" + iter.next() + ".png");
                        if (img.length < 1024) {
                            sendQuote(gme, "发生错误:" + new String(img));
                        } else {
                            File file = SJFPathTool.getR15Path(Hash.getMd5Instance().calculate(img).toUpperCase() + "." + FileFormat.getFileType(img));
                            FileTool.saveFile(file, img);
                            sendQuote(gme, "已保存" + file.getName());
                        }
                    }
                    return true;
            }
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            sendGroupMessage(groupId, "参数错误:" + e.toString());
        }
        return false;
    }

    @Override
    public boolean onNudge(NudgeEvent event) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return true;
        }
        if (event.getFrom().getId() == entity.getId()) {
            return false;
        }
        switch (entity.personality) {
            case White:
                if (ThreadLocalRandom.current().nextBoolean()) {
                    sendGroupMessage(event.getSubject().getId(), "你群日常乱戳");
                }
                break;
            case Mix:
                if (ThreadLocalRandom.current().nextBoolean()) {
                    event.getFrom().nudge().sendTo(event.getSubject());
                    return true;
                }
                break;
            case Black:
                if (ThreadLocalRandom.current().nextBoolean()) {
                    event.getFrom().nudge().sendTo(event.getSubject());
                    return true;
                }
                break;
        }
        return false;
    }
}
