package com.meng.modules;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meng.Functions;
import com.meng.SBot;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.sjfmd.result.UidToRoom;
import com.meng.sjfmd.result.VideoInfo;
import com.meng.tools.AvBvConverter;
import com.meng.tools.Bilibili;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.Network;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class BilibiliLinkParser extends BaseModule implements IGroupMessageEvent {

    public static final Pattern patternAv = Pattern.compile(".{0,}[Aa][Vv](\\d{1,})\\D{0,}");
    public static final Pattern patternBv = Pattern.compile(".{0,}([Bb][Vv]1.{2}4.{1}1.{1}7.{2}).{0,}");
    public static final Pattern patternLive = Pattern.compile(".{0,}live\\D{0,}(\\d{1,})\\D{0,}");
    public static final Pattern patternCv = Pattern.compile(".{0,}[Cc][Vv](\\d{1,})");
    public static final Pattern patternUid = Pattern.compile(".{0,}space\\D{0,}(\\d{1,})");



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
        long groupId = event.getGroup().getId();
        long id;
        id = getLiveId(msg);
        if (id != -1) {
            processLive(id, groupId);
            return true;
        }
        id = getAvId(msg);
        if (id != -1) {
            processVideo(id, event);
            return true;
        }
        return false;
    }

    private void processLive(long id, long groupId) {
        JsonObject liveToMainInfo = null;
        try {
            liveToMainInfo = new JsonParser().parse(Network.httpGet("https://api.live.bilibili.com/live_user/v1/UserInfo/get_anchor_in_room?roomid=" + id)).getAsJsonObject().get("data").getAsJsonObject().get("info").getAsJsonObject();
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return;
        }
        long uid = liveToMainInfo.get("uid").getAsLong();
        String uname = liveToMainInfo.get("uname").getAsString();
        UidToRoom utr = Bilibili.getUidToRoom(uid);
        MessageChainBuilder builder = new MessageChainBuilder();
        if (utr.data.liveStatus != 1) {
            builder.add("主播:");
            builder.add(uname);
            builder.add("\n未直播");
        } else {
            builder.add("主播:");
            builder.add(uname);
            builder.add("\n标题:");
            builder.add(utr.data.title);
        }
        String html = Network.httpGet("https://live.bilibili.com/" + id);
        String jsonInHtml = html.substring(html.indexOf("{\"roomInitRes\":"), html.lastIndexOf("}") + 1);
        JsonObject data = new JsonParser().parse(jsonInHtml).getAsJsonObject().get("baseInfoRes").getAsJsonObject().get("data").getAsJsonObject();
        builder.add("\n分区:");
        builder.add(data.get("parent_area_name").getAsString());
        builder.add("-");
        builder.add(data.get("area_name").getAsString());
        builder.add("\n标签:");
        builder.add(data.get("tags").getAsString()); 
        entity.sendGroupMessage(groupId, builder.asMessageChain());
    }

    private void processVideo(long avId, GroupMessageEvent event) {
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
        entity.sendGroupMessage(event.getGroup().getId(), messageChainBuilder.asMessageChain());
    }

    private long getLiveId(String link) {
        Matcher matcher = patternLive.matcher(link);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return -1;
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
        if (!link.contains("https://b23.tv")) {
            return -1;
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
