package com.meng.modules.bilibili.video;

import com.meng.modules.bilibili.video.javabean.VideoInfo;
import com.meng.modules.bilibili.video.javabean.VideoReply;
import com.meng.tools.JsonHelper;
import com.meng.tools.Network;

import static com.meng.modules.bilibili.BilibiliBotMain.getCsrf;
import static com.meng.modules.bilibili.BilibiliBotMain.REFERER;

public class VideoApi {
    
    public static VideoInfo getVideoInfo(long aid) {
        return JsonHelper.fromJson(Network.httpGet("http://api.bilibili.com/x/web-interface/view?aid=" + aid) , VideoInfo.class);
    }

    public static VideoReply getVideoJudge(long aid) {
        return JsonHelper.fromJson(Network.httpGet("https://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn=1&type=1&sort=1&oid=" + aid), VideoReply.class);
    }

    public static VideoReply getVideoJudge(long aid, long root) {
        return JsonHelper.fromJson(Network.httpGet("https://api.bilibili.com/x/v2/reply/reply?jsonp=jsonp&pn=1&type=1&sort=1&oid=" + aid + "&ps=10&root=" + root + "&_=" + System.currentTimeMillis()), VideoReply.class);
    }

    public static String sendAvCoin(int count, long AID, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/web-interface/coin/add", cookie, REFERER, "https://www.bilibili.com/video/av" + AID, "aid", AID, "multiply", count, "select_like", 0, "cross_domain", "true", "csrf", getCsrf(cookie));
    }

    public static String sendAvLike(long AID, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/web-interface/archive/like", cookie, REFERER, "https://www.bilibili.com/video/av" + AID, "aid", AID, "like", 1, "csrf", getCsrf(cookie));
    }

    public static String sendVideoJudge(String content, long AID, String cookie) {      
        return Network.bilibiliMainPost("https://api.bilibili.com/x/v2/reply/add", cookie, REFERER, "https://www.bilibili.com/video/av" + AID, "oid", AID, "type", 1, "message", content, "jsonp", "jsonp", "csrf", getCsrf(cookie));
    }

    public static String sendVideoJudge(String content, long AID, long rootId, long parentId, String cookie) {      
        return Network.bilibiliMainPost("https://api.bilibili.com/x/v2/reply/add", cookie, REFERER, "https://www.bilibili.com/video/av" + AID, "oid", AID, "type", 1, "root", rootId, "parent", parentId, "message", content, "jsonp", "jsonp", "csrf", getCsrf(cookie));
    }

    public static String sendLikeReply(long AID, long rpid, boolean deLike, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/v2/reply/action", cookie, REFERER, "https://www.bilibili.com/video/av" + AID, "oid", AID, "type", 1, "rpid", rpid, "action", deLike ?0: 1, "jsonp", "jsonp", "csrf", getCsrf(cookie));
    }

    public static String sendDisikeReply(long AID, long rpid, boolean deDislike, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/v2/reply/hate", cookie, REFERER, "https://www.bilibili.com/video/av" + AID, "oid", AID, "type", 1, "rpid", rpid, "action", deDislike ?0: 1, "jsonp", "jsonp", "csrf", getCsrf(cookie));
    }

    public static String deleteReply(long AID, long rpid, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/v2/reply/del", cookie, REFERER, "https://www.bilibili.com/video/av" + AID, "oid", AID, "type", 1, "rpid", rpid, "jsonp", "jsonp", "csrf", getCsrf(cookie));
    }


}
