package com.meng.modules.fantasyZone;
import com.meng.modules.fantasyZone.javabean.Fantasy;
import org.jsoup.Jsoup;
import com.meng.tools.JsonHelper;
import org.jsoup.Connection;
import java.io.IOException;

/*
 url:https://api.fantasyzone.cc/#/tu?id=httpsapifantasyzonecctu
 */
public class FantasyZoneApi {
    public static Fantasy getPicture() {
        return getPicture("pixiv", "json", 0);
    }
    /*
     _class:选择图片库，pc为横向动漫壁纸图片 ，m为纵向动漫壁纸图片，mc为FantasyZone Server截图，pixiv为pixiv库图片
     type:选择输出方式 ，url为重定向到目标图片，html为以img形式输出（方便浏览，刷新换图），json为输出json数据
     r18:class为pixiv也就是Pixiv库才需要选择，0为非限制级图片，1为R18限制级图片，2为混合模式
     */
    public static Fantasy getPicture(String _class, String type, int r18) {
        Fantasy apiReturn = new Fantasy();
        Connection connection = Jsoup.connect("https://api.fantasyzone.cc/tu");
        connection.method(Connection.Method.GET);
        connection.ignoreContentType(true);
        connection.data("class", _class);
        connection.data("type", type);
        connection.data("r18", String.valueOf(r18));
        Connection.Response response;
        try {
            response = connection.execute();
        } catch (IOException e) {
            apiReturn.code = -1;
            return apiReturn;
        }
        if (response.statusCode() != 200) {
            apiReturn.code = response.statusCode();
        } else {
            apiReturn = JsonHelper.fromJson(response.body(), Fantasy.class);
        }
        return apiReturn;
    }
}
