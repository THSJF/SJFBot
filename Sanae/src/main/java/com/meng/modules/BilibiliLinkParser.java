package com.meng.modules;
import com.meng.Functions;
import com.meng.SBot;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.sjfmd.result.VideoInfo;
import com.meng.tools.AvBvConverter;
import com.meng.tools.Bilibili;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class BilibiliLinkParser extends BaseModule implements IGroupMessageEvent {

    public static final Pattern patternAv = Pattern.compile(".{0,}[Aa][Vv](\\d{1,})\\D{0,}");
    public static final Pattern patternBv = Pattern.compile(".{0,}([Bb][Vv]1.{2}4.{1}1.{1}7.{2}).{0,}");

    public BilibiliLinkParser(SBot bot) {
        super(bot);
    }

    @Override
    public BilibiliLinkParser load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return getClass().getName();
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        if (!entity.configManager.isFunctionEnabled(event.getGroup().getId(), Functions.BilibiliVideo)) {
            return false; 
        }
        String msg = event.getMessage().contentToString();
        long avId = getAvId(msg);
        if (avId == -1) {
            return false;
        }
        VideoInfo info = Bilibili.getVideoInfo(avId);
        Image image = null;
        try {
            byte[] imgBytes = Jsoup.connect(info.data.pic).method(Connection.Method.GET).ignoreContentType(true).userAgent(SBot.userAgent).execute().bodyAsBytes();
            File folder = new File(SBot.appDirectory + "images/bilibili/");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(SBot.appDirectory + "images/bilibili/" + info.data.aid);
            FileTool.saveFile(file, imgBytes);
            image = event.getGroup().uploadImage(file);
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        if (image == null) {
            messageChainBuilder.add("封面图获取失败");
        } else {
            messageChainBuilder.add(image);
        }
        messageChainBuilder.add(info.toString());
        entity.sendGroupMessage(event.getGroup().getId(), info.toString());
        return false;
    }

    private long getAvId(String link) {
        Matcher mav = patternAv.matcher(link);
        if (mav.find()) {
            return Long.parseLong(mav.group(1));
        }
        Matcher mbv = patternBv.matcher(link);  
        if (mbv.find()) {
            return AvBvConverter.getInstance().decode(mbv.group(1));
        }
        String realUrl = null;
        try {
            realUrl = Network.getRealUrl(link);
        } catch (Exception e) {
            return -1;
        }
        Matcher mav2 = patternAv.matcher(realUrl);
        if (mav2.find()) {
            return Long.parseLong(mav2.group(1));
        }
        Matcher mbv2 = patternBv.matcher(realUrl);  
        if (mbv2.find()) {
            return AvBvConverter.getInstance().decode(mbv2.group(1));
        }
        return -1;
	}

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        return false;
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        return false;
    }

}
