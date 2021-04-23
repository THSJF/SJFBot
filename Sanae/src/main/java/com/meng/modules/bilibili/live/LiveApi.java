package com.meng.modules.bilibili.live;

import com.meng.modules.bilibili.live.javabean.GiftBag;
import com.meng.modules.bilibili.live.javabean.LivePart;
import com.meng.modules.bilibili.live.javabean.LiveStream;
import com.meng.modules.bilibili.live.javabean.Medals;
import com.meng.modules.bilibili.live.javabean.RoomInfo;
import com.meng.modules.bilibili.live.javabean.RoomToUid;
import com.meng.modules.bilibili.live.javabean.StartLive;
import com.meng.modules.bilibili.live.javabean.StopLive;
import com.meng.tools.JsonHelper;
import com.meng.tools.Network;
import java.util.Random;

import static com.meng.modules.bilibili.BilibiliBotMain.getCsrf;
import static com.meng.modules.bilibili.BilibiliBotMain.REFERER;

public class LiveApi {

    public static RoomToUid getRoomToUid(long roomId) {
        return JsonHelper.fromJson(Network.httpGet("https://api.live.bilibili.com/live_user/v1/UserInfo/get_anchor_in_room?roomid=" + roomId), RoomToUid.class);
    }

    public static LivePart getLivePart() {
        return JsonHelper.fromJson(Network.httpGet("https://api.live.bilibili.com/room/v1/Area/getList"), LivePart.class);
    }

    public static RoomInfo getRoomInfo(long roomId) {
        return JsonHelper.fromJson(Network.httpGet("https://api.live.bilibili.com/xlive/web-room/v1/index/getInfoByRoom?room_id=" + roomId), RoomInfo.class);
    }

    public static Medals getMedal(String cookie) {
        Medals mb = JsonHelper.fromJson(Network.httpGet("https://api.live.bilibili.com/i/api/medal?page=1&pagesize=10", cookie), Medals.class);
        for (int i = mb.data.pageinfo.curPage + 1;i <= mb.data.pageinfo.totalpages;++i) {
            Medals tm = JsonHelper.fromJson(Network.httpGet("https://api.live.bilibili.com/i/api/medal?page=" + i + "&pagesize=10", cookie), Medals.class);
            mb.data.fansMedalList.addAll(tm.data.fansMedalList);
        }
        return mb;
    }

    public static GiftBag getGiftBag(String cookie) {
        return JsonHelper.fromJson(Network.httpGet("https://api.live.bilibili.com/xlive/web-room/v1/gift/bag_list?t=" + System.currentTimeMillis(), cookie), GiftBag.class);
    }

    public static String getMedalRank(String cookie, long uid, long roomId) {
        return Network.httpGet("https://api.live.bilibili.com/rankdb/v1/RoomRank/webMedalRank?roomid=" + roomId + "&ruid=" + uid, cookie);
    }

    public static StartLive startLive(long roomID, String partID, String cookie) {
        if (partID == null) {
            partID = "235";
            System.out.println("没有发现这个分区，已自动选择\"单机-其他分区\"");
        }
        String csrf = getCsrf(cookie);
        return JsonHelper.fromJson(Network.bilibiliLivePost("https://api.live.bilibili.com/room/v1/Room/startLive", cookie, "Referer", "https://link.bilibili.com/p/center/index", "room_id", roomID, "platform", "pc", "area_v2", partID, "csrf_token", csrf, "csrf", csrf), StartLive.class);
    }

    public static StopLive stopLive(int roomID, String cookie) {
        String csrf = getCsrf(cookie);
        return JsonHelper.fromJson(Network.bilibiliLivePost("https://api.live.bilibili.com/room/v1/Room/stopLive", cookie, "Referer", "https://link.bilibili.com/p/center/index", "room_id", roomID, "csrf_token", csrf, "csrf", csrf), StopLive.class);
    }

    public static String renameLive(int roomID, String newName, String cookie) {
        String csrf = getCsrf(cookie);
        return Network.bilibiliLivePost("https://api.live.bilibili.com/room/v1/Room/update", cookie, "Referer", "https://link.bilibili.com/p/center/index", "room_id", roomID, "title", newName, "csrf_token", csrf, "csrf", csrf);
    }

    public static LiveStream getLiveStream(long roomid, String cookie) {
        return JsonHelper.fromJson(Network.httpGet("https://api.live.bilibili.com/live_stream/v1/StreamList/get_stream_by_roomId?room_id=" + roomid, cookie, "https://link.bilibili.com/p/center/index"), LiveStream.class);
    }

    public static String sendLiveSign(String cookie) {
        return Network.httpGet("https://api.live.bilibili.com/sign/doSign", cookie, "https://live.bilibili.com/" + new Random().nextInt(10_000_000));
    }

    public static String sendHotStrip(long myUid, long roomMasterUid, long roomID, int count, String cookie) {
        String csrf = getCsrf(cookie);
        return Network.bilibiliLivePost("http://api.live.bilibili.com/gift/v2/gift/send", cookie, "Referer" , "https://live.bilibili.com/" + roomID, "uid", myUid, "gift_id", 1, "ruid", roomMasterUid, "gift_num", count, "coin_type", "silver", "bag_id", 0, "platform", "pc", "biz_code", "live", "biz_id", roomID, "rnd", System.currentTimeMillis() / 1000, "metadata", "", "price", 0, "csrf_token", csrf, "csrf", csrf, "visit_id", "");
    }

    public static String sendLiveDanmaku(String msg, String cookie, long roomId) {
        String csrf = getCsrf(cookie);
        return Network.bilibiliLivePost("http://api.live.bilibili.com/msg/send", cookie, REFERER, "https://live.bilibili.com/" + roomId, "color", 0xffffff, "fontsize", 25, "mode", 1, "msg", msg, "rnd", System.currentTimeMillis() / 1000, "roomid", roomId, "bubble", 0, "csrf_token", csrf, "csrf", csrf);
    }

}
