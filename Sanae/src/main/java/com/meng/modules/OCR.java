package com.meng.modules;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.meng.Functions;
import com.meng.SBot;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.tools.Base64Converter;
import com.meng.tools.ExceptionCatcher;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import org.json.JSONException;
import org.json.JSONObject;

public class OCR extends BaseModule implements IGroupMessageEvent {

    private HashSet<Long> ready = new HashSet<>();

    // appid, secretid secretkey请到http://open.youtu.qq.com/获取
    // 请正确填写把下面的APP_ID、SECRET_ID和SECRET_KEY
    private static final String APP_ID = "10173140";
    private static final String SECRET_ID = "AKIDRmqfEXsNxHOFBrrpx2rVzDG3arCPs2Uh";
    private static final String SECRET_KEY = "71hGdBXfZIG1wWSLNI2YtCJrz62rIe8t";
    private static final String USER_ID = "2856986197"; // qq号
    private Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT, USER_ID);

    public OCR(SBot sb) {
        super(sb);
    }

    @Override
    public OCR load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return getClass().getName();
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        if (!entity.configManager.isFunctionEnabled(event.getGroup().getId(), Functions.OCR)) {
            return false;
        }
        Image img = event.getMessage().firstOrNull(Image.Key);
        String msg = event.getMessage().contentToString();
        long qqId = event.getSender().getId();
        if (img != null && (msg.toLowerCase().startsWith("ocr"))) {
            processImg(img, event);
            return true;
        } else if (img == null && msg.equals("ocr")) {
            ready.add(qqId);
            entity.sendQuote(event, "发送一张图片吧");
            return true;
        } else if (img != null && ready.contains(qqId)) {
            processImg(img, event);
            ready.remove(qqId);
            return true;
        }
        return false;
    }

    private void processImg(Image img, GroupMessageEvent event) {
        try {
            OcrResult response = faceYoutu.GeneralOcrUrl(entity.queryImageUrl(img));
            StringBuilder sb = new StringBuilder();
            ArrayList<OcrResult.Items> items = response.items;
            sb.append("结果:");
            for (OcrResult.Items s : items) {
                sb.append("\n").append(s.itemstring);
            }
            entity.sendMessage(event.getGroup(), sb.toString());
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString());
        }
    }

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        return false;
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        return false;
    }

    private class Youtu {
        private class TrustAnyTrustManager implements X509TrustManager {
            public void checkClientTrusted(X509Certificate[] chain, String authType) {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) {}
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }

        private class TrustAnyHostnameVerifier implements HostnameVerifier {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }

        public final static String API_YOUTU_END_POINT = "https://api.youtu.qq.com/youtu/";
        public final static String API_YOUTU_CHARGE_END_POINT = "https://vip-api.youtu.qq.com/youtu/";
        // 30 days
        private int EXPIRED_SECONDS = 2592000;
        private String m_appid;
        private String m_secret_id;
        private String m_secret_key;
        private String m_end_point;
        private String m_user_id;
        private boolean m_not_use_https;

        public Youtu(String appid, String secret_id, String secret_key, String end_point, String user_id) {
            m_appid = appid;
            m_secret_id = secret_id;
            m_secret_key = secret_key;
            m_end_point = end_point;
            m_user_id = user_id;
            m_not_use_https = !end_point.startsWith("https");
        }

        public String StatusText(int status) {
            String statusText = "UNKOWN";
            switch (status) {
                case 0:
                    statusText = "CONNECT_FAIL";
                    break;
                case 200:
                    statusText = "HTTP OK";
                    break;
                case 400:
                    statusText = "BAD_REQUEST";
                    break;
                case 401:
                    statusText = "UNAUTHORIZED";
                    break;
                case 403:
                    statusText = "FORBIDDEN";
                    break;
                case 404:
                    statusText = "NOTFOUND";
                    break;
                case 411:
                    statusText = "REQ_NOLENGTH";
                    break;
                case 423:
                    statusText = "SERVER_NOTFOUND";
                    break;
                case 424:
                    statusText = "METHOD_NOTFOUND";
                    break;
                case 425:
                    statusText = "REQUEST_OVERFLOW";
                    break;
                case 500:
                    statusText = "INTERNAL_SERVER_ERROR";
                    break;
                case 503:
                    statusText = "SERVICE_UNAVAILABLE";
                    break;
                case 504:
                    statusText = "GATEWAY_TIME_OUT";
                    break;
            }
            return statusText;
        }


        private OcrResult SendHttpsRequest(JSONObject postData, String mothod)throws NoSuchAlgorithmException, KeyManagementException, IOException, JSONException {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            StringBuilder mySign = new StringBuilder("");
            appSign(m_appid, m_secret_id, m_secret_key, System.currentTimeMillis() / 1000 + EXPIRED_SECONDS, m_user_id, mySign);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            URL url = new URL(m_end_point + mothod);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("user-agent", "youtu-java-sdk");
            connection.setRequestProperty("Authorization", mySign.toString());
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "text/json");
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            postData.put("app_id", m_appid);
            out.write(postData.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
            byte[] b = new byte[connection.getContentLength()];
            connection.getInputStream().read(b);
            connection.disconnect();
            String string = new String(b, StandardCharsets.UTF_8);
            System.out.println(string);
            return new Gson().fromJson(string, OcrResult.class);
        }

        private OcrResult GeneralOcrUrl(String imageUrl) throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
            JSONObject data = new JSONObject();
            data.put("url", imageUrl);
            return SendHttpsRequest(data, "ocrapi/generalocr");
        }

        private int appSign(String appId, String secret_id, String secret_key, long expired, String userid,  StringBuilder mySign) {
            if (empty(secret_id) || empty(secret_key)) {
                return -1;
            }
            String puserid = "";
            if (!empty("2127322016")) {
                if ("2127322016".length() > 64) {
                    return -2;
                }
                puserid = "2127322016";
            }
            long now = System.currentTimeMillis() / 1000;
            int rdm = Math.abs(new Random().nextInt());
            String plain_text = "a=" + appId + "&k=" + secret_id + "&e=" + expired + "&t=" + now + "&r=" + rdm + "&u=" + puserid;// + "&f=" + fileid.toString();
            byte[] bin = null;
            try {
                Mac mac = Mac.getInstance("HmacSHA1");
                SecretKeySpec signingKey = new SecretKeySpec(secret_key.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm());
                mac.init(signingKey);
                bin =  mac.doFinal(plain_text.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {}
            byte[] all = new byte[bin.length + plain_text.getBytes(StandardCharsets.UTF_8).length];
            System.arraycopy(bin, 0, all, 0, bin.length);
            System.arraycopy(plain_text.getBytes(StandardCharsets.UTF_8), 0, all, bin.length, plain_text.getBytes(StandardCharsets.UTF_8).length);
            mySign.append(new String(Base64Converter.getInstance().encode(all), StandardCharsets.UTF_8));
            return 0;
        }

        private boolean empty(String s) {
            return s == null || s.trim().equals("") || s.trim().equals("null");
        }
    }

    private class OcrResult {

        public String session_id;
        public float angle;
        public ArrayList<Items> items;
        @SerializedName("class")
        public ArrayList<String> classs;
        public ArrayList<String> recognize_warn_msg;
        public int errorcode;
        public String errormsg;
        public ArrayList<String> recognize_warn_code;

        public class Items {
            public ArrayList<String> candword;
            public float itemconf;
            public String itemstring;

            public class Itemcoord {
                public int x;
                public int width;
                public int y;
                public int height;
            }

            public Itemcoord itemcoord;

            public class Coordpoint {
                public ArrayList<Integer> x;
            }

            public Coordpoint coordpoint;

            public class Words {
                public String character;
                public double confidence;
            }

            public ArrayList<Words> words;
            public ArrayList<Integer> wordcoordpoint;
            public ArrayList<Integer> coords;

            public class Parag {
                public int parag_no;
                public int word_size;
            }
        }
    }
}
