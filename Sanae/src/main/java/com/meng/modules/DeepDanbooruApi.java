package com.meng.modules;

import com.meng.tools.ExceptionCatcher;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.meng.tools.Network;
import com.meng.tools.FileTool;

public class DeepDanbooruApi {

    public static Map<String,Float> search(String url) {
        try {
            File file = File.createTempFile("a", "png");
            FileTool.saveFile(file, Network.httpGetRaw(url));
            return search(file);
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return null;
        }
    }

    public static Map<String,Float> search(File file) {
        Connection.Response response = null;
        try {
            response = Jsoup.connect("http://dev.kanotype.net:8003/deepdanbooru/upload")
                .timeout(60000)
                .data("network_type", "general")
                .data("file", "image.png", new FileInputStream(file))
                .method(Connection.Method.POST)
                .execute();
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return null;
        }
        if (response.statusCode() != 200) {
            System.out.println("连接失败,code:" + response.statusCode());
            return null;
        }
        return parseResult(response.body());
    }

    private static Map<String,Float> parseResult(String danbooruHtml) {
        Map<String,Float> result = new LinkedHashMap<>();
        Elements tables = Jsoup.parse(danbooruHtml).select("tbody");
        Elements trs = tables.get(0).select("tr");
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            result.put(tds.get(0).getElementsByTag("a").text(), Float.parseFloat(tds.get(1).text()));            
        }
        Elements tds = tables.get(1).select("tr").get(0).select("td");
        result.put(tds.get(0).getElementsByTag("a").text(), Float.parseFloat(tds.get(1).text()));            
        return result;
    }
}
