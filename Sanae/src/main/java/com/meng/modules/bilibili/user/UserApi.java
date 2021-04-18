package com.meng.modules.bilibili.user;

import com.google.gson.JsonParser;
import com.meng.modules.bilibili.self.javabean.Relation;
import com.meng.modules.bilibili.user.javabean.UidToRoom;
import com.meng.modules.bilibili.user.javabean.Upstat;
import com.meng.modules.bilibili.user.javabean.UserInfo;
import com.meng.tools.GSON;
import com.meng.tools.Network;
import java.util.Random;

import static com.meng.modules.bilibili.BilibiliBotMain.getCsrf;
import static com.meng.modules.bilibili.BilibiliBotMain.REFERER;

public class UserApi {
    
    public static UidToRoom getUidToRoom(long uid) {
        return GSON.fromJson(Network.httpGet("https://api.live.bilibili.com/room/v1/Room/getRoomInfoOld?mid=" + uid, null, "https://live.bilibili.com/"), UidToRoom.class);
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

    public static UserInfo getUserInfo(long id, String cookie) {
        return GSON.fromJson(Network.httpGet("https://api.bilibili.com/x/space/acc/info?mid=" + id + "&jsonp=jsonp", cookie), UserInfo.class);
    }

    public static String getUserDynamicList(long uid, int offsetDynamicID) {
        return Network.httpGet("https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?offset_dynamic_id=" + offsetDynamicID  + "&host_uid=" + uid);
    }

    public static String followUser(String cookie, long UID) {
        String firstStep = Network.bilibiliMainPost("https://api.bilibili.com/x/relation/modify?cross_domain=true", cookie, REFERER, "https://www.bilibili.com/video/av" + new Random().nextInt(47957369), "fid", UID, "act", 1, "re_src", 122, "csrf", getCsrf(cookie));
        if (new JsonParser().parse(firstStep).getAsJsonObject().get("code").getAsInt() != 0) {
            return "关注失败";
        }
        return Network.bilibiliMainPost("https://api.bilibili.com/x/relation/tags/addUsers?cross_domain=true", cookie, REFERER, "https://www.bilibili.com/video/av" + new Random().nextInt(47957369), "fids", UID, "tagids", 0, "csrf", getCsrf(cookie));
    }
}
