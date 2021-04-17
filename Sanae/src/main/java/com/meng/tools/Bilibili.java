package com.meng.tools;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meng.gameData.TouHou.UserInfo;
import com.meng.modules.bilibili.javabean.CvInfo;
import com.meng.modules.bilibili.javabean.DynamicWithPictureResult;
import com.meng.modules.bilibili.javabean.FollowingLiving;
import com.meng.modules.bilibili.javabean.GiftBag;
import com.meng.modules.bilibili.javabean.LivePart;
import com.meng.modules.bilibili.javabean.LiveStream;
import com.meng.modules.bilibili.javabean.Medals;
import com.meng.modules.bilibili.javabean.MyInfo;
import com.meng.modules.bilibili.javabean.Relation;
import com.meng.modules.bilibili.javabean.RoomToUid;
import com.meng.modules.bilibili.javabean.StartLive;
import com.meng.modules.bilibili.javabean.StopLive;
import com.meng.modules.bilibili.javabean.UidToRoom;
import com.meng.modules.bilibili.javabean.UploadPicResult;
import com.meng.modules.bilibili.javabean.Upstat;
import com.meng.modules.bilibili.javabean.VideoInfo;
import com.meng.modules.bilibili.javabean.VideoReply;
import com.meng.modules.qq.SBot;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class Bilibili {

    public static String REFERER = "Referer";

    public static Map<String, String> liveHead = new HashMap<String,String>(){
        {
            put("Host", "api.live.bilibili.com");
            put("Accept", "application/json, text/javascript, */*; q=0.01");
            put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            put("Connection", "keep-alive");
            put("Origin", "https://live.bilibili.com");
        }
    };
    public static Map<String, String> mainHead = new HashMap<String,String>(){
        {
            put("Host", "api.bilibili.com");
            put("Accept", "application/json, text/javascript, */*; q=0.01");
            put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            put("Connection", "keep-alive");
            put("Origin", "https://www.bilibili.com");
        }
    };

    public static CvInfo getCvInfo(long cvId) {
        return GSON.fromJson(Network.httpGet("http://api.bilibili.com/x/article/viewinfo?id=" + cvId + "&mobi_app=pc&jsonp=jsonp"), CvInfo.class);
    }

    public static VideoInfo getVideoInfo(long aid) {
        return GSON.fromJson(Network.httpGet("http://api.bilibili.com/x/web-interface/view?aid=" + aid) , VideoInfo.class);
    }

    public static VideoReply getVideoJudge(long aid) {
        return GSON.fromJson(Network.httpGet("https://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn=1&type=1&sort=1&oid=" + aid), VideoReply.class);
    }

    public static VideoReply getVideoJudge(long aid, long root) {
        return GSON.fromJson(Network.httpGet("https://api.bilibili.com/x/v2/reply/reply?jsonp=jsonp&pn=1&type=1&sort=1&oid=" + aid + "&ps=10&root=" + root + "&_=" + System.currentTimeMillis()), VideoReply.class);
    }

    public static MyInfo getMyInfo(String cookie) {
        return GSON.fromJson(Network.httpGet("http://api.bilibili.com/x/space/myinfo?jsonp=jsonp", cookie), MyInfo.class);
    }

    public static UserInfo getUserInfo(long id, String cookie) {
        return GSON.fromJson(Network.httpGet("https://api.bilibili.com/x/space/acc/info?mid=" + id + "&jsonp=jsonp", cookie), UserInfo.class);
    }

    public static UidToRoom getUidToRoom(long uid) {
        return GSON.fromJson(Network.httpGet("https://api.live.bilibili.com/room/v1/Room/getRoomInfoOld?mid=" + uid, null, "https://live.bilibili.com/"), UidToRoom.class);
    }

    public static RoomToUid getRoomToUid(long roomId) {
        return GSON.fromJson(Network.httpGet("https://api.live.bilibili.com/live_user/v1/UserInfo/get_anchor_in_room?roomid=" + roomId), RoomToUid.class);
    }

    public static LivePart getLivePart() {
        return GSON.fromJson(Network.httpGet("https://api.live.bilibili.com/room/v1/Area/getList"), LivePart.class);
    }

    public static String getRoomInfo(long roomId) {
        return Network.httpGet("https://api.live.bilibili.com/xlive/web-room/v1/index/getInfoByRoom?room_id=" + roomId);
    }

    public static Relation getRelation(long uid) {
        return GSON.fromJson(Network.httpGet("https://api.bilibili.com/x/relation/stat?vmid=" + uid + "&jsonp=jsonp"), Relation.class);
    }

    public static Upstat getUpstat(long uid) {
        return GSON.fromJson(Network.httpGet("https://api.bilibili.com/x/space/upstat?mid=" + uid + "&jsonp=jsonp"), Upstat.class);
    }

    public static String getFollowing(String cookie, long uid, int page, int pageSize) {
        return Network.httpGet("https://api.bilibili.com/x/relation/followings?vmid=" + uid + "&pn=1&ps=" + pageSize + "&order=desc&jsonp=jsonp", cookie);
    }

    public static FollowingLiving getFollowingLiving(String cookie, int page, int pageSize) {
        return GSON.fromJson(Network.httpGet("https://api.live.bilibili.com/relation/v1/feed/feed_list?page=" + page + "&pagesize=" + pageSize, cookie), FollowingLiving.class);
    }

    public static Medals getMedal(String cookie) {
        Medals mb = GSON.fromJson(Network.httpGet("https://api.live.bilibili.com/i/api/medal?page=1&pagesize=10", cookie), Medals.class);
        for (int i = mb.data.pageinfo.curPage + 1;i <= mb.data.pageinfo.totalpages;++i) {
            Medals tm = GSON.fromJson(Network.httpGet("https://api.live.bilibili.com/i/api/medal?page=" + i + "&pagesize=10", cookie), Medals.class);
            mb.data.fansMedalList.addAll(tm.data.fansMedalList);
        }
        return mb;
    }

    public static GiftBag getGiftBag(String cookie) {
        return GSON.fromJson(Network.httpGet("https://api.live.bilibili.com/xlive/web-room/v1/gift/bag_list?t=" + System.currentTimeMillis(), cookie), GiftBag.class);
    }

    public static String getMedalRank(String cookie, long uid, long roomId) {
        return Network.httpGet("https://api.live.bilibili.com/rankdb/v1/RoomRank/webMedalRank?roomid=" + roomId + "&ruid=" + uid, cookie);
    }

    public static String allSearch(String keyword, int page) {
        return Network.httpGet("https://api.bilibili.com/x/web-interface/search/all/v2?__refresh__=true&highlight=1&single_column=0&jsonp=jsonp&keyword=" + keyword + "&page=" + page);
    }

    public static String userSearch(String keyword, int page) {
        return Network.httpGet("https://api.bilibili.com/x/web-interface/search/type?search_type=bili_user&changing=mid&__refresh__=true&highlight=1&single_column=0&jsonp=jsonp&keyword=" + keyword + "&page=" + page);
    }

    public static String videoSearch(String keyword, int page) {
        return Network.httpGet("https://api.bilibili.com/x/web-interface/search/type?search_type=video&__refresh__=true&highlight=1&single_column=0&jsonp=jsonp&keyword=" + keyword + "&page=" + page);
    }

    public static String photoSearch(String keyword, int page) {
        return Network.httpGet("https://api.bilibili.com/x/web-interface/search/type?search_type=photo&__refresh__=true&highlight=1&single_column=0&jsonp=jsonp&keyword=" + keyword + "&page=" + page);
    }

    public static String getUserDynamicList(long uid, int offsetDynamicID) {
        return Network.httpGet("https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?offset_dynamic_id=" + offsetDynamicID  + "&host_uid=" + uid);
    }

    public static String sendDynamic(String content, String cookie) {
        return Network.bilibiliPost("https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/create", cookie, "dynamic_id", 0, "type", 4, "rid", 0, "content", content, "extension", "{\"from\":{\"emoji_type\":1}}", "at_uids", "", "ctrl", "[]", "csrf_token", getCsrf(cookie));
    }

    public static DynamicWithPictureResult sendDynamic(String content, String cookie, ArrayList<File> pics) {
        HashSet<UploadPicResult> bset=new HashSet<>(); 
        try {
            for (File pic:pics) {
                Connection.Response response = Jsoup.connect("https://api.vc.bilibili.com/api/v1/drawImage/upload").timeout(60000).method(Connection.Method.POST).userAgent(SBot.userAgent).ignoreContentType(true).cookies(Network.cookieToMap(cookie)).data("file_up", pic.getName(), new FileInputStream(pic)).data("biz", "draw").data("category", "daily").execute();
                if (response.statusCode() != 200) {
                    return null;
                } 
                JsonObject jo=new JsonParser().parse(response.body()).getAsJsonObject();
                if (jo.get("code").getAsInt() == 0) {
                    JsonObject jobj=jo.get("data").getAsJsonObject();
                    UploadPicResult upr=new UploadPicResult();
                    upr.img_src = jobj.get("image_url").getAsString();
                    upr.img_width = jobj.get("image_width").getAsInt();
                    upr.img_height = jobj.get("image_height").getAsInt();
                    upr.img_size = pic.length() / 1024.0f;
                    bset.add(upr);
                } else {
                    return null;
                }
            }
            String result=Network.bilibiliPost("https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/create_draw", cookie, "biz", 3, "category", 3, "type", 0, "pictures", GSON.toJson(bset), "title", "", "tags", "", "description", "", "content", content, "setting", "{\"copy_forbidden\":0,\"cachedTime\":0}", "from", "create.dynamic.web", "extension", "{\"from\":{\"emoji_type\":1}}", "at_uids", "", "at_control", "[]", "csrf_token", getCsrf(cookie));
            pics.clear();
            return GSON.fromJson(result, DynamicWithPictureResult.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static String sendArticalJudge(long cvId, String msg, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/v2/reply/add", cookie, "Referer", "https://www.bilibili.com/", "type", 12, "message", msg, "plat", 1, "jsonp", "jsonp", "csrf", getCsrf(cookie));
    }

    public static String setMyInfo(String cookie, String newName, String newSign, String newSex, String newBirthday) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/member/web/update", cookie, "uname", newName, "usersign", newSign, "sex", newSex, "birthday", newBirthday, "csrf", getCsrf(cookie));
    }

    public static StartLive startLive(long roomID, String partID, String cookie) {
        if (partID == null) {
            partID = "235";
            System.out.println("没有发现这个分区，已自动选择\"单机-其他分区\"");
        }
        String csrf = getCsrf(cookie);
        return GSON.fromJson(Network.bilibiliLivePost("https://api.live.bilibili.com/room/v1/Room/startLive", cookie, "Referer", "https://link.bilibili.com/p/center/index", "room_id", roomID, "platform", "pc", "area_v2", partID, "csrf_token", csrf, "csrf", csrf), StartLive.class);
    }

    public static StopLive stopLive(int roomID, String cookie) {
        String csrf = getCsrf(cookie);
        return GSON.fromJson(Network.bilibiliLivePost("https://api.live.bilibili.com/room/v1/Room/stopLive", cookie, "Referer", "https://link.bilibili.com/p/center/index", "room_id", roomID, "csrf_token", csrf, "csrf", csrf), StopLive.class);
    }

    public static String renameLive(int roomID, String newName, String cookie) {
        String csrf = getCsrf(cookie);
        return Network.bilibiliLivePost("https://api.live.bilibili.com/room/v1/Room/update", cookie, "Referer", "https://link.bilibili.com/p/center/index", "room_id", roomID, "title", newName, "csrf_token", csrf, "csrf", csrf);
    }

    public static LiveStream getLiveStream(long roomid, String cookie) {
        return GSON.fromJson(Network.httpGet("https://api.live.bilibili.com/live_stream/v1/StreamList/get_stream_by_roomId?room_id=" + roomid, cookie, "https://link.bilibili.com/p/center/index"), LiveStream.class);
    }

    public static String sendLiveSign(String cookie) {
        return Network.httpGet("https://api.live.bilibili.com/sign/doSign", cookie, "https://live.bilibili.com/" + new Random().nextInt(10_000_000));
    }

    public static String sendHotStrip(long myUid, long roomMasterUid, long roomID, int count, String cookie) {
        String csrf = getCsrf(cookie);
        return Network.bilibiliLivePost("http://api.live.bilibili.com/gift/v2/gift/send", cookie, "Referer" , "https://live.bilibili.com/" + roomID, "uid", myUid, "gift_id", 1, "ruid", roomMasterUid, "gift_num", count, "coin_type", "silver", "bag_id", 0, "platform", "pc", "biz_code", "live", "biz_id", roomID, "rnd", System.currentTimeMillis() / 1000, "metadata", "", "price", 0, "csrf_token", csrf, "csrf", csrf, "visit_id", "");
    }

    public static String followUser(String cookie, long UID) {
        String firstStep=Network.bilibiliMainPost("https://api.bilibili.com/x/relation/modify?cross_domain=true", cookie, REFERER, "https://www.bilibili.com/video/av" + new Random().nextInt(47957369), "fid", UID, "act", 1, "re_src", 122, "csrf", getCsrf(cookie));
        if (new JsonParser().parse(firstStep).getAsJsonObject().get("code").getAsInt() != 0) {
            return "关注失败";
        }
        return Network.bilibiliMainPost("https://api.bilibili.com/x/relation/tags/addUsers?cross_domain=true", cookie, REFERER, "https://www.bilibili.com/video/av" + new Random().nextInt(47957369), "fids", UID, "tagids", 0, "csrf", getCsrf(cookie));
    }

    public static String getCsrf(String cookie) {
        return Network.cookieToMap(cookie).get("bili_jct");
    }

    public static String sendCvCoin(int count, long CvId, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/web-interface/coin/add", cookie, REFERER, "https://www.bilibili.com/read/cv" + CvId, "aid", CvId, "multiply", count, "upid", String.valueOf(Bilibili.getCvInfo(CvId).data.mid), "avtype", 2, "csrf", getCsrf(cookie));
    }

    public static String sendAvCoin(int count, long AID, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/web-interface/coin/add", cookie, REFERER, "https://www.bilibili.com/video/av" + AID, "aid", AID, "multiply", count, "select_like", 0, "cross_domain", "true", "csrf", getCsrf(cookie));
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

    public static String sendCvLike(long cvID, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/article/like", cookie, REFERER, "https://www.bilibili.com/read/cv" + cvID, "id", cvID, "type", 1, "jsonp", "jsonp", "csrf", getCsrf(cookie));
    }

    public static String sendAvLike(long AID, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/web-interface/archive/like", cookie, REFERER, "https://www.bilibili.com/video/av" + AID, "aid", AID, "like", 1, "csrf", getCsrf(cookie));
    }

    public static String sendLiveDanmaku(String msg, String cookie, long roomId) {
        String csrf = getCsrf(cookie);
        return Network.bilibiliLivePost("http://api.live.bilibili.com/msg/send", cookie, REFERER, "https://live.bilibili.com/" + roomId, "color", 0xffffff, "fontsize", 25, "mode", 1, "msg", msg, "rnd", System.currentTimeMillis() / 1000, "roomid", roomId, "bubble", 0, "csrf_token", csrf, "csrf", csrf);
    }
}
