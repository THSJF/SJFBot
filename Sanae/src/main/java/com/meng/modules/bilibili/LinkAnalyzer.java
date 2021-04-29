package com.meng.modules.bilibili;

import com.meng.modules.bilibili.article.ArticleApi;
import com.meng.modules.bilibili.article.javabean.ArticleInfo;
import com.meng.modules.bilibili.live.LiveApi;
import com.meng.modules.bilibili.live.javabean.RoomInfo;
import com.meng.modules.bilibili.video.VideoApi;
import com.meng.modules.bilibili.video.javabean.VideoInfo;
import com.meng.tools.AvBvConverter;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.Network;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkAnalyzer {

    public static final Pattern patternAv = Pattern.compile(".{0,}[Aa][Vv](\\d{1,})\\D{0,}");
    public static final Pattern patternBv = Pattern.compile(".{0,}([Bb][Vv]1.{2}4.{1}1.{1}7.{2}).{0,}");

    public static final Pattern patternCv = Pattern.compile(".{0,}[Cc][Vv](\\d{1,})");
    public static final Pattern patternLive = Pattern.compile(".{0,}live\\D{0,}(\\d{1,})\\D{0,}");

    public static final Pattern patternUid = Pattern.compile(".{0,}space\\D{0,}(\\d{1,})");

    public BilibiliPair parse(String text) {
        BilibiliPair bp = getInfo(text);
        if (bp != null) {
            return bp;
        }
        if (!text.contains("https://b23.tv")) {
            return null;
        }
        try {
            String realUrl = Network.getRealUrl(text);
            if (realUrl == null) {
                return null;
            }
            return getInfo(realUrl);
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        return null;
    }

    private BilibiliPair getInfo(String text) {
        long id;
        id = getLiveId(text);
        if (id != -1) {
            return getLiveInfo(id);
        }
        id = getAvId(text);
        if (id != -1) {
            return getVideoInfo(id);
        }
        id = getCvId(text);
        if (id != -1) {
            return getArticleInfo(id);
        }
        return null;
    }

    private BilibiliPair getLiveInfo(long id) {
        RoomInfo roomInfo = LiveApi.getRoomInfo(id);
        return new BilibiliPair(roomInfo.toString(), roomInfo.data.room_info.keyframe);
    }

    private BilibiliPair getVideoInfo(long aid) {
        VideoInfo info = VideoApi.getVideoInfo(aid);
        return new BilibiliPair(info.toString(), info.data.pic);
    }

    private BilibiliPair getArticleInfo(long aid) {
        ArticleInfo info = ArticleApi.getArticleInfo(aid);
        return new BilibiliPair(info.toString(), info.data.origin_image_urls.get(0));
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
        return -1;
    }

    private long getCvId(String link) {
        Matcher matcher = patternCv.matcher(link);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return -1;
    }
}
