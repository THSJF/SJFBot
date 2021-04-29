package com.meng.modules.sauceNao;
import com.meng.modules.sauceNao.javabean.SauceNaoResult;
import com.meng.tools.ExceptionCatcher;
import java.io.File;
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;

public class SauceNaoApi {

    public static SauceNaoResult getSauce(int database, String url) throws IOException {
        Connection.Response response = Jsoup.connect("https://saucenao.com/search.php?db=" + database).timeout(60000).data("url", url).method(Connection.Method.POST).execute();
        if (response.statusCode() != 200) {
            return null;
        }
        return new SauceNaoResult(Jsoup.parse(response.body()));
    }

    public static SauceNaoResult getSauce(int database, InputStream img) throws IOException {
        Connection.Response response = Jsoup.connect("https://saucenao.com/search.php?db=" + database).timeout(60000).data("file", "image.png", img).method(Connection.Method.POST).execute();
        if (response.statusCode() != 200) {
            return null;
        }
        return new SauceNaoResult(Jsoup.parse(response.body()));
    }

    public static SauceNaoResult getSauce(int database, File img) throws IOException {
        return getSauce(database, new FileInputStream(img));
    }

    public static SauceNaoResult getSauce(int database, byte[] img) throws IOException {
        return getSauce(database, new ByteArrayInputStream(img));
    }

    public static SauceNaoResult getSauce(File img) throws IOException {
        return getSauce(999, new FileInputStream(img));
    }

    public static SauceNaoResult getSauce(String url) throws IOException {
        return getSauce(999, url);
    }

    public static SauceNaoResult getSauce(byte[] img) throws IOException {
        return getSauce(999, img);
    }

    public static SauceNaoResult getSauce(InputStream img) throws IOException {
        return getSauce(999, img);
    }
}
