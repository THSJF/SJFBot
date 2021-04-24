package com.meng.modules.bilibili.article;

import com.meng.modules.bilibili.article.javabean.ArticleInfo;
import com.meng.tools.JsonHelper;
import com.meng.tools.Network;

import static com.meng.modules.bilibili.BilibiliBotMain.getCsrf;
import static com.meng.modules.bilibili.BilibiliBotMain.REFERER;

public class ArticleApi {

    public static ArticleInfo getArticleInfo(long cvId) {
        return JsonHelper.fromJson(Network.httpGet("http://api.bilibili.com/x/article/viewinfo?id=" + cvId + "&mobi_app=pc&jsonp=jsonp"), ArticleInfo.class);
    } 

    public static String sendArticalJudge(long cvId, String msg, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/v2/reply/add", cookie, "Referer", "https://www.bilibili.com/", "type", 12, "message", msg, "plat", 1, "jsonp", "jsonp", "csrf", getCsrf(cookie));
    }

    public static String sendArticleCoin(int count, long CvId, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/web-interface/coin/add", cookie, REFERER, "https://www.bilibili.com/read/cv" + CvId, "aid", CvId, "multiply", count, "upid", String.valueOf(getArticleInfo(CvId).data.mid), "avtype", 2, "csrf", getCsrf(cookie));
    }

    public static String sendArticleLike(long cvID, String cookie) {
        return Network.bilibiliMainPost("https://api.bilibili.com/x/article/like", cookie, REFERER, "https://www.bilibili.com/read/cv" + cvID, "id", cvID, "type", 1, "jsonp", "jsonp", "csrf", getCsrf(cookie));
    }

}
