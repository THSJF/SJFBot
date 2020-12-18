package com.meng.modules;
import com.meng.Functions;
import com.meng.SBot;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.sjfmd.result.VideoInfo;
import com.meng.tools.AvBvConverter;
import com.meng.tools.Bilibili;
import com.meng.tools.ExceptionCatcher;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.jsoup.Jsoup;
import net.mamoe.mirai.message.data.Image;
import java.net.URL;
import net.mamoe.mirai.utils.OverFileSizeMaxException;
import java.net.MalformedURLException;
import net.mamoe.mirai.message.data.MessageChainBuilder;

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
            image = event.getGroup().uploadImage(new URL(info.data.pic));
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
