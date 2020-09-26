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
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
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
        List<Long> list = getAts(msg);
        long me = bot.getSelfQQ().getId();
        for (long l : list) {
            if (l == me) {
                return true;
            }
        }
        return false;
    }

    public String at(long qqId) {
        return String.format("[mirai:at:%d,%d]", qqId, qqId);
    }

    public String at(long groupId, long qqId) {
        return String.format("[mirai:at:%d,%d]", qqId, bot.getGroup(groupId).get(qqId).getNameCard());
    }

    public String at(long qqId, String name) {
        return String.format("[mirai:at:%d,%d]", qqId, name);
    }

    public String image(File f, long groupId) {
        return bot.getGroup(groupId).uploadImage(f).toString();
    }

    public long getAt(String msg) {
        int index = msg.indexOf("[mirai:at:");
        if (index == -1) return -1000;
        int index2 = msg.indexOf(",", index);
        if (index == -2) return -1000;
        try {
            return Long.parseLong(msg.substring(index + 10, index2));
        } catch (NumberFormatException e) {
            return -1000;
        }
    }

    public List<Long> getAts(String msg) {
        ArrayList<Long> al = new ArrayList<>();
        int pointer = 0;
        while (true) {
            int index = msg.indexOf("[mirai:at:", pointer);
            if (index == -1) break;
            int index2 = msg.indexOf(",", index);
            if (index == -2) break;
            try {
                al.add(Long.parseLong(msg.substring(index + 10, index2)));
            } catch (NumberFormatException e) {

            }
            pointer = index2;
        }
        return al;
    }

    public int sendPrivateMsg(long qqId, String msg) {
        try {
            return bot.getFriend(qqId).sendMessage(msg).getSource().getId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int sendGroupMsg(long groupId, Message msg) {
        try {
            return bot.getGroup(groupId).sendMessage(msg).getSource().getId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int sendGroupMsg(long groupId, String msg) {
        try {
            return bot.getGroup(groupId).sendMessage(msg).getSource().getId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
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
        bot.recall(MessagePool.get(id).getMessage());
        return 0;
    }

    public String getRecord(MessageChain mc) {
        throw new UnsupportedOperationException();
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
