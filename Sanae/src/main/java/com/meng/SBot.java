package com.meng;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.meng.config.ConfigManager;
import com.meng.handler.MessageManager;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.Tools;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.data.FriendInfo;
import net.mamoe.mirai.data.GroupActiveData;
import net.mamoe.mirai.data.GroupAnnouncement;
import net.mamoe.mirai.data.GroupAnnouncementList;
import net.mamoe.mirai.data.GroupHonorListData;
import net.mamoe.mirai.data.GroupHonorType;
import net.mamoe.mirai.data.MemberInfo;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.action.Nudge;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.OfflineMessageSource;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.network.LoginFailedException;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.Context;
import net.mamoe.mirai.utils.MiraiLogger;

/**
 * @author: 司徒灵羽
 **/
public class SBot extends Bot {
    private Bot bot;

    public static final String appDirectory = "C://Program Files/sanae_data/";
    public static final String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0";
    public static final long mainGroup = 807242547L;
    public static final long yysGroup = 617745343L;

    public static Gson gson;

    public ModuleManager moduleManager;
    public ConfigManager configManager;

    public boolean sleeping = false;

    static{
        GsonBuilder gb = new GsonBuilder();
        gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gson = gb.create();
    }

    public SBot(Bot b, BotConfiguration bc) {
        super(bc);
        bot = b;
    }

    public void init() {
        moduleManager = new ModuleManager(this);
        moduleManager.load();
        configManager = new ConfigManager(this);
        configManager.load();
    }

    public Member getGroupMemberInfo(long gid, long qq) {
        return bot.getGroup(gid).getOrNull(qq);
    }

    public int sendGroupMessage(long fromGroup, Message msg) {
        if (sleeping || !configManager.isFunctionEnabled(fromGroup, Functions.GroupMessageEvent)) {
            return -1;
        }
        return bot.getGroup(fromGroup).sendMessage(msg).getSource().getId();
    }

    public int sendGroupMessage(long fromGroup, String msg) {
        return sendGroupMessage(fromGroup, new PlainText(msg));
    }

    public int sendGroupMessage(long fromGroup, String[] msg) {
        return sendGroupMessage(fromGroup, Tools.ArrayTool.rfa(msg));
    }

    public int sendGroupMessage(long fromGroup, ArrayList<String> msg) {
        return sendGroupMessage(fromGroup, msg.toArray(new String[0]));
    }

    public int sendMessage(Group group, String msg) {
        return sendGroupMessage(group.getId(), msg);
    }

    public int sendMessage(Group group, Message msg) {
        return sendGroupMessage(group.getId(), msg);
    }

    public int sendMessage(Group group, String[] msg) {
        return sendGroupMessage(group.getId(), Tools.ArrayTool.rfa(msg));
    }

    public int sendMessage(Group group, ArrayList<String> msg) {
        return sendGroupMessage(group.getId(), msg.toArray(new String[0]));
    } 

    public int sendQuote(GroupMessageEvent gme, String msg) {
        return sendGroupMessage(gme.getGroup().getId(), new QuoteReply(gme.getSource()).plus(msg));
    }

    public int sendQuote(GroupMessageEvent gme, Message msg) {
        return sendGroupMessage(gme.getGroup().getId(), new QuoteReply(gme.getSource()).plus(msg));
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

    public static boolean messageEquals(MessageChain mc1, MessageChain mc2) {
        int len = mc1.size();
        if (len != mc2.size()) {
            return false;
        }
        for (int i = 1;i < len;++i) {
            if (!mc1.get(i).equals(mc2.get(i))) {
                return false; 
            }
        }
        return true;
    }

    public At at(long gid, long qq) {
        return new At(bot.getGroup(gid).get(qq));
    }

    public At at(Member m) {
        return new At(m);
    }

    @Override
    public Context getContext() {
        return bot.getContext();
    }

    @Override
    public long getId() {
        return bot.getId();
    }

    @Override
    public String getNick() {
        return bot.getNick();
    }

    @Override
    public MiraiLogger getLogger() {
        return bot.getLogger();
    }

    @Override
    public boolean isOnline() {
        return bot.isOnline();
    }

    @Override
    public Friend getSelfQQ() {
        return bot.getSelfQQ();
    }

    @Override
    public ContactList<Friend> getFriends() {
        return bot.getFriends();
    }

    @Override
    public ContactList<Group> getGroups() {
        return bot.getGroups();
    }

    @Override
    public OfflineMessageSource constructMessageSource(OfflineMessageSource.Kind p1, long p2, long p3, int p4, int p5, int p6, MessageChain p7) {
        return bot.constructMessageSource(p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public Object sendNudge(Nudge p1, Contact p2, Continuation<? super Boolean> p3) {
        return bot.sendNudge(p1, p2, p3);
    }

    public void setGroupCard(long gid, long qq, String nick) {
        bot.getGroup(gid).get(qq).setNameCard(nick);
    }

    public void setGroupSpecialTitle(long gid, long qq, String title) {
        bot.getGroup(gid).get(qq).setSpecialTitle(title);
    }

    @Override
    public void login() throws LoginFailedException {
        bot.login();
    }

    @Override
    public Future<Unit> loginAsync() {
        return bot.loginAsync();
    }

    @Override
    public String queryImageUrl(Image image) {
        return bot.queryImageUrl(image);
    }

    @Override
    public Future<String> queryImageUrlAsync(Image image) {
        return bot.queryImageUrlAsync(image);
    }

    @Override
    public void join() {
        super.join();
    }

    public void recall(int msgId) {
        bot.recall(MessageManager.get(msgId).getSource());
    }

    @Override
    public void recall(MessageSource source) {
        bot.recall(source);
    }

    @Override
    public void recall(MessageChain message) {
        bot.recall(message);
    }

    @Override
    public void recallIn(MessageSource source, long millis) {
        bot.recallIn(source, millis);
    }

    @Override
    public void recallIn(MessageChain source, long millis) {
        bot.recallIn(source, millis);
    }

    @Override
    public Future<Unit> recallAsync(MessageChain source) {
        return bot.recallAsync(source);
    }

    @Override
    public Future<Unit> recallAsync(MessageSource source) {
        return bot.recallAsync(source);
    }  

    @Override
    public void close(Throwable p1) {
        ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), p1);
    }

    @Override
    public Friend _lowLevelNewFriend(FriendInfo p1) {
        return bot._lowLevelNewFriend(p1);
    }

    @Override
    public Object _lowLevelQueryGroupList(Continuation<? super Sequence<Long>> p1) {
        return bot._lowLevelQueryGroupList(p1);
    }

    @Override
    public Object _lowLevelQueryGroupMemberList(long p1, long p2, long p3, Continuation<? super Sequence<? extends MemberInfo>> p4) {
        return bot._lowLevelQueryGroupMemberList(p1, p2, p3, p4);
    }

    @Override
    public Object _lowLevelGetAnnouncements(long p1, int p2, int p3, Continuation<? super GroupAnnouncementList> p4) {
        return bot._lowLevelGetAnnouncements(p1, p2, p3, p4);
    }

    @Override
    public Object _lowLevelSendAnnouncement(long p1, GroupAnnouncement p2, Continuation<? super String> p3) {
        return bot._lowLevelSendAnnouncement(p1, p2, p3);
    }

    @Override
    public Object _lowLevelDeleteAnnouncement(long p1, String p2, Continuation<? super Unit> p3) {
        return bot._lowLevelDeleteAnnouncement(p1, p2, p3);
    }

    @Override
    public Object _lowLevelGetAnnouncement(long p1, String p2, Continuation<? super GroupAnnouncement> p3) {
        return bot._lowLevelGetAnnouncement(p1, p2, p3);
    }

    @Override
    public Object _lowLevelGetGroupActiveData(long p1, int p2, Continuation<? super GroupActiveData> p3) {
        return bot._lowLevelGetGroupActiveData(p1, p2, p3);
    }

    @Override
    public Object _lowLevelGetGroupHonorListData(long p1, GroupHonorType p2, Continuation<? super GroupHonorListData> p3) {
        return bot._lowLevelGetGroupHonorListData(p1, p2, p3);
    }

    @Override
    public Object _lowLevelSolveNewFriendRequestEvent(long p1, long p2, String p3, boolean p4, boolean p5, Continuation<? super Unit> p6) {
        return bot._lowLevelSolveNewFriendRequestEvent(p1, p2, p3, p4, p5, p6);
    }

    @Override
    public Object _lowLevelSolveBotInvitedJoinGroupRequestEvent(long p1, long p2, long p3, boolean p4, Continuation<? super Unit> p5) {
        return bot._lowLevelSolveBotInvitedJoinGroupRequestEvent(p1, p2, p3, p4, p5);
    }

    @Override
    public Object _lowLevelSolveMemberJoinRequestEvent(long p1, long p2, String p3, long p4, Boolean p5, boolean p6, String p7, Continuation<? super Unit> p8) {
        return bot._lowLevelSolveMemberJoinRequestEvent(p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public Object _lowLevelQueryGroupVoiceDownloadUrl(byte[] p1, long p2, long p3, Continuation<? super String> p4) {
        return bot._lowLevelQueryGroupVoiceDownloadUrl(p1, p2, p3, p4);
    }

    @Override
    public Object _lowLevelUploadVoice(byte[] p1, long p2, Continuation<? super Unit> p3) {
        return bot._lowLevelUploadVoice(p1, p2, p3);
    }


}
