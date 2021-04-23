package com.meng.modules.qq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.meng.Functions;
import com.meng.config.ConfigManager;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.Tools;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.sequences.Sequence;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.OtherClient;
import net.mamoe.mirai.contact.Stranger;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.action.BotNudge;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.message.data.Voice;
import net.mamoe.mirai.network.LoginFailedException;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;

/**
 * @author: 司徒灵羽
 **/
public class SBot implements Bot {

    public static final String appDirectory = "C://Program Files/sanae_data/";
    public static final String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0";
    public static final long mainGroup = 807242547L;
    public static final long yysGroup = 617745343L;

    public static Gson gson;

    private Bot bot;

    public ModuleManager moduleManager;
    
    public boolean sleeping = false;

    static{
        GsonBuilder gb = new GsonBuilder();
        gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gson = gb.create();
    }

    public SBot(Bot b) {
        bot = b;
    }

    public static List<Bot> getInstances() {
        return Bot.Companion.getInstances();
    }

    public static Sequence<Bot> getInstancesSequence() {
        return Bot.Companion.getInstancesSequence();
    }

    public static Bot getInstance(long qq) throws NoSuchElementException {
        return Bot.Companion.getInstance(qq);
    }

    public static Bot getInstanceOrNull(long qq) {
        return Bot.Companion.getInstanceOrNull(qq);
    }

    public static Bot findInstance(long qq) {
        return Bot.Companion.findInstance(qq);
    }

    public void init() {
        moduleManager = new ModuleManager(this);
        moduleManager.load();
    }

    public String getUrl(Image image) {
        return Mirai.getInstance().queryImageUrl(bot, image);
    }

    public Image toImage(File file, Contact contact) {
        return ExternalResource.Companion.uploadAsImage(file, contact);
    }

    public Image toImage(URL url, Contact contact) {
        try {
            return ExternalResource.Companion.uploadAsImage(url.openStream(), contact);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image toImage(InputStream inputStream, Contact contact) {
        return ExternalResource.Companion.uploadAsImage(inputStream, contact);
    }

    public Voice toVoice(File file, Contact contact) {
        return ExternalResource.Companion.uploadAsVoice(ExternalResource.Companion.create(file), contact);
    }

    public Voice toVoice(InputStream inputStream, Contact contact) {
        try {
            return ExternalResource.Companion.uploadAsVoice(ExternalResource.Companion.create(inputStream), contact);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void join() {
        bot.join();
    }

    public NormalMember getGroupMemberInfo(long gid, long qq) {
        return bot.getGroup(gid).getOrFail(qq);
    }

    public int[] sendGroupMessage(long fromGroup, Message msg) {
        if (sleeping || !ConfigManager.getInstance().isFunctionEnabled(fromGroup, Functions.GroupMessageEvent)) {
            return new int[]{-1};
        }
        return bot.getGroup(fromGroup).sendMessage(msg).getSource().getIds();
    }

    public int[] sendGroupMessage(long fromGroup, String msg) {
        return sendGroupMessage(fromGroup, new PlainText(msg));
    }

    public int[] sendGroupMessage(long fromGroup, String[] msg) {
        return sendGroupMessage(fromGroup, Tools.ArrayTool.rfa(msg));
    }

    public int[] sendGroupMessage(long fromGroup, ArrayList<String> msg) {
        return sendGroupMessage(fromGroup, msg.toArray(new String[0]));
    }

    public int[] sendMessage(Group group, String msg) {
        return sendGroupMessage(group.getId(), msg);
    }

    public int[] sendMessage(Group group, Message msg) {
        return sendGroupMessage(group.getId(), msg);
    }

    public int[] sendMessage(Group group, String[] msg) {
        return sendGroupMessage(group.getId(), Tools.ArrayTool.rfa(msg));
    }

    public int[] sendMessage(Group group, ArrayList<String> msg) {
        return sendGroupMessage(group.getId(), msg.toArray(new String[0]));
    } 

    public int[] sendQuote(GroupMessageEvent gme, String msg) {
        return sendGroupMessage(gme.getGroup().getId(), new QuoteReply(gme.getSource()).plus(msg));
    }

    public int[] sendQuote(GroupMessageEvent gme, Message msg) {
        return sendGroupMessage(gme.getGroup().getId(), new QuoteReply(gme.getSource()).plus(msg));
    }

    public boolean isAtme(String msg) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(msg);
        List<Long> list = getAts(messageChainBuilder.asMessageChain());
        long me = bot.getId();
        for (long l : list) {
            if (l == me) {
                return true;
            }
        }
        return false;
    }

    public long getAt(MessageChain msg) {
        return ((At)msg.get(At.Key)).getTarget();
    }

    public List<Long> getAts(MessageChain msg) {
        ArrayList<Long> al = new ArrayList<>();
        for (SingleMessage p1 : msg) {
            if (p1 instanceof At) {
                al.add(((At)p1).getTarget()); 
            } 
        }
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

    @Override
    public BotConfiguration getConfiguration() {
        return bot.getConfiguration();
    }

    @Override
    public Bot getBot() {
        return bot.getBot();
    }

    @Override
    public EventChannel<BotEvent> getEventChannel() {
        return bot.getEventChannel();
    }

    @Override
    public ContactList<OtherClient> getOtherClients() {
        return bot.getOtherClients();
    }

    @Override
    public Friend getAsFriend() {
        return bot.getAsFriend();
    }

    @Override
    public Stranger getAsStranger() {
        return bot.getAsStranger();
    }

    @Override
    public ContactList<Stranger> getStrangers() {
        return bot.getStrangers();
    }

    @Override
    public Stranger getStranger(long id) {
        return bot.getStranger(id);
    }

    @Override
    public Stranger getStrangerOrFail(long id) {
        return bot.getStrangerOrFail(id);
    }

    @Override
    public Friend getFriend(long id) {
        return bot.getFriend(id);
    }

    @Override
    public Friend getFriendOrFail(long id) {
        return bot.getFriendOrFail(id);
    }

    @Override
    public Group getGroup(long id) {
        return bot.getGroup(id);
    }

    @Override
    public Group getGroupOrFail(long id) {
        return bot.getGroupOrFail(id);
    }

    @Override
    public Object login(Continuation<? super Unit> p1) {
        return bot.login(p1);
    }

    @Override
    public BotNudge nudge() {
        return bot.nudge();
    }

    @Override
    public Object join(Continuation<? super Unit> $completion) {
        return bot.join($completion);
    }

    @Override
    public Object closeAndJoin(Throwable cause, Continuation<? super Unit> $completion) {
        return bot.closeAndJoin(cause, $completion);
    }

    @Override
    public void closeAndJoin(Throwable p1) {
    }

    @Override
    public String getAvatarUrl() {
        return bot.getAvatarUrl();
    }

    @Override
    public CoroutineContext getCoroutineContext() {
        return bot.getCoroutineContext();
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
    public ContactList<Friend> getFriends() {
        return bot.getFriends();
    }

    @Override
    public ContactList<Group> getGroups() {
        return bot.getGroups();
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
    public void close(Throwable p1) {
        ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), p1);
    }

}
