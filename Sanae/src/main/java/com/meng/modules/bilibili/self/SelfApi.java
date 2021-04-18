package com.meng.modules.bilibili.self;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meng.modules.bilibili.self.javabean.DynamicWithPictureResult;
import com.meng.modules.bilibili.self.javabean.FollowingLiving;
import com.meng.modules.bilibili.self.javabean.MyInfo;
import com.meng.modules.bilibili.self.javabean.UploadPicResult;
import com.meng.modules.qq.SBot;
import com.meng.tools.GSON;
import com.meng.tools.Network;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import static com.meng.modules.bilibili.BilibiliBotMain.getCsrf;

public class SelfApi {
    
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

    public static String setMyInfo(String cookie, String newName, String newSign, String newSex, String newBirthday) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/member/web/update", cookie, "uname", newName, "usersign", newSign, "sex", newSex, "birthday", newBirthday, "csrf", getCsrf(cookie));
    }

    public static FollowingLiving getFollowingLiving(String cookie, int page, int pageSize) {
        return GSON.fromJson(Network.httpGet("https://api.live.bilibili.com/relation/v1/feed/feed_list?page=" + page + "&pagesize=" + pageSize, cookie), FollowingLiving.class);
    }

    public static MyInfo getMyInfo(String cookie) {
        return GSON.fromJson(Network.httpGet("http://api.bilibili.com/x/space/myinfo?jsonp=jsonp", cookie), MyInfo.class);
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
                JsonObject jo = new JsonParser().parse(response.body()).getAsJsonObject();
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
            String result = Network.bilibiliPost("https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/create_draw", cookie, "biz", 3, "category", 3, "type", 0, "pictures", GSON.toJson(bset), "title", "", "tags", "", "description", "", "content", content, "setting", "{\"copy_forbidden\":0,\"cachedTime\":0}", "from", "create.dynamic.web", "extension", "{\"from\":{\"emoji_type\":1}}", "at_uids", "", "at_control", "[]", "csrf_token", getCsrf(cookie));
            pics.clear();
            return GSON.fromJson(result, DynamicWithPictureResult.class);
        } catch (Exception e) {
            return null;
        }
    }
}
