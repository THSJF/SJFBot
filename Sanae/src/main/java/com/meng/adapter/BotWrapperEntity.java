package com.meng.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.meng.MessagePool;
import com.meng.config.ConfigManager;
import com.meng.modules.ModuleManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.MessageSource;

/**
 * @Description: bot实体包装器
 * @author: 司徒灵羽
 **/

public class BotWrapperEntity {

    public static final String appDirectory = "C://Program Files/sanae_data/";
    public static final String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0";
    public static final long mainGroup = 807242547L;
    public static final long yysGroup = 617745343L;

    public static Gson gson;

    public final Bot bot;
    public final SJFTX sjfTx;
    public final SJFRX sjfRx;
    public final ModuleManager moduleManager;
    public final ConfigManager configManager;

    public boolean sleeping = false;

    static{
        GsonBuilder gb = new GsonBuilder();
        gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gson = gb.create();
    }

    public BotWrapperEntity(Bot b, SJFTX tx, SJFRX rx, ModuleManager mm, ConfigManager cm) {
        bot = b;
        sjfTx = tx;
        sjfRx = rx;
        moduleManager = mm;
        configManager = cm;
    }

    public long getLoginQQ() {
        return bot.getId();
    }

    public String getLoginNick() {
        return bot.getNick();
    }

    public boolean isAtme(String msg) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(msg);
        List<Long> list = getAts(messageChainBuilder.asMessageChain());
        long me = bot.getSelfQQ().getId();
        for (long l : list) {
            if (l == me) {
                return true;
            }
        }
        return false;
    }

    public String at(long groupId, long qqId) {
        return at(bot.getGroup(groupId).get(qqId));
    }

    public String at(Member m) {
        return new At(m).toMiraiCode();
    }

    public Image image(File f, long groupId) {
        return bot.getGroup(groupId).uploadImage(f);
    }

    public long getAt(MessageChain msg) {
        return msg.firstOrNull(At.Key).getTarget();
    }

    public List<Long> getAts(MessageChain msg) {
        final ArrayList<Long> al = new ArrayList<>();
        msg.forEachContent(new Function1<Message,Unit>(){

                @Override
                public Unit invoke(Message p1) {
                    if (p1 instanceof At) {
                        al.add(((At)p1).getTarget()); 
                    }
                    return null;
                }
            });
        return al;
    }

    public int deleteMsg(MessageChain mc) {
        bot.recall(mc);
        return 0;
    }

    public int deleteMsg(MessageSource ms) {
        bot.recall(ms);
        return 0;
    }

    public int deleteMsg(int id) {
        MessageEvent get = MessagePool.get(id);
        if (get == null) {
            return -1;
        }
        bot.recall(get.getMessage());
        return 0;
    }

    public String getRecord(MessageChain mc) {
        throw new UnsupportedOperationException();
    }

    public boolean messageEquals(MessageChain mc1, MessageChain mc2) {
        int len = mc1.size();
        if (len != mc2.size()) {
            return false;
        }
        for (int i=1;i < len;++i) {
            if (!mc1.get(i).equals(mc2.get(i))) {
                return false; 
            }
        }
        return true;
    }

    public void setGroupKick(long groupId, long qqId, boolean notBack) {
        try {
            bot.getGroup(groupId).get(qqId).kick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGroupBan(long groupId, long qqId, int banTime) {
        try {
            bot.getGroup(groupId).get(qqId).mute(banTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGroupWholeBan(long groupId, boolean isBan) {
        try {
            bot.getGroup(groupId).getSettings().setMuteAll(isBan);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String setGroupCard(long groupId, long qqId, String nick) {
        try {
            bot.getGroup(groupId).get(qqId).setNameCard(nick);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("群号:%d,账号:%d,名片:%s", groupId, qqId, nick);
    }

    public int setGroupLeave(long groupId, boolean isDisband) {
        return bot.getGroup(groupId).quit() ?0: -1;
    }

    public String setGroupSpecialTitle(long groupId, long qqId, String title) {
        try {
            bot.getGroup(groupId).get(qqId).setSpecialTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("群号:%d,账号:%d,头衔:%s", groupId, qqId, title);
    }

    public Member getGroupMemberInfo(long groupId, long qqId) {
        return bot.getGroup(groupId).get(qqId);
    }

    public ContactList<Member> getGroupMemberList(long groupId) {
        try {
            return bot.getGroup(groupId).getMembers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ContactList<Group> getGroupList() {
        try {
            return bot.getGroups();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    } 

    public String location(double p0, double p1, int p2, String p3, String p4) {
        throw new UnsupportedOperationException();
    }
}
