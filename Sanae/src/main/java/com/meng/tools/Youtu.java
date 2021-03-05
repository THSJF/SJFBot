package com.meng.tools;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONException;
import org.json.JSONObject;

public class Youtu {

    // appid, secretid secretkey请到http://open.youtu.qq.com/获取
    // 请正确填写把下面的APP_ID、SECRET_ID和SECRET_KEY
    private static final String APP_ID = "10173140";
    private static final String SECRET_ID = "AKIDRmqfEXsNxHOFBrrpx2rVzDG3arCPs2Uh";
    private static final String SECRET_KEY = "71hGdBXfZIG1wWSLNI2YtCJrz62rIe8t";
    private static final String USER_ID = "2856986197"; // qq号
    private static YoutuMain faceYoutu = new YoutuMain(APP_ID, SECRET_ID, SECRET_KEY, YoutuMain.API_YOUTU_END_POINT, USER_ID);

    public static YoutuMain getFaceYoutu() {
        return faceYoutu;
    }

    public static class YoutuMain {

        public final static String API_YOUTU_END_POINT = "https://api.youtu.qq.com/youtu/";
        public final static String API_YOUTU_CHARGE_END_POINT = "https://vip-api.youtu.qq.com/youtu/";
        // 30 days
        private int EXPIRED_SECONDS = 2592000;
        private String m_appid;
        private String m_secret_id;
        private String m_secret_key;
        private String m_end_point;
        private String m_user_id;

        public YoutuMain(String appid, String secret_id, String secret_key, String end_point, String user_id) {
            m_appid = appid;
            m_secret_id = secret_id;
            m_secret_key = secret_key;
            m_end_point = end_point;
            m_user_id = user_id;
        }

        public OcrResult doOcrWithUrl(String imageUrl) throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
            JSONObject data = new JSONObject();
            data.put("url", imageUrl);
            return new Gson().fromJson(sendHttpsRequest(data, "ocrapi/generalocr"), OcrResult.class);
        }

        public OcrResult doOcrWithFile(String image_path)throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
            JSONObject data = new JSONObject();
            StringBuffer image_data = new StringBuffer("");
            GetBase64FromFile(image_path, image_data);
            data.put("image", image_data);
            return new Gson().fromJson(sendHttpsRequest(data, "ocrapi/generalocr"), OcrResult.class);
        }

        private void GetBase64FromFile(String filePath, StringBuffer base64) throws IOException {
            File imageFile = new File(filePath);
            if (imageFile.exists()) {
                InputStream in = new FileInputStream(imageFile);
                byte data[] = new byte[(int) imageFile.length()];
                in.read(data);
                in.close();
                base64.append(new String(Base64Converter.getInstance().encode(data)));
            } else {
                throw new FileNotFoundException(filePath + " not exist");
            }
        }

        public PornResult doPornWithUrl(String imageUrl) throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
            JSONObject data = new JSONObject();
            data.put("url", imageUrl);
            return new Gson().fromJson(sendHttpsRequest(data, "imageapi/imageporn"), PornResult.class);
        }

        public TagResult doTagWithUrl(String imageUrl) throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
            JSONObject data = new JSONObject();
            data.put("url", imageUrl);
            return new Gson().fromJson(sendHttpsRequest(data, "imageapi/imagetag"), TagResult.class);
        }

        public TtsResult doTtsWithUrl(String text) throws IOException, JSONException, KeyManagementException, NoSuchAlgorithmException {
            JSONObject data = new JSONObject();
            data.put("text", text);
            data.put("model_type", 0);
            data.put("speed", 0);
            data.put("time_stamp", System.currentTimeMillis() / 1000);
            data.put("nonce_str", "fa577ce340859f9fe");
            return new Gson().fromJson(sendHttpsRequest(data, "ttsapi/text_to_audio"), TtsResult.class);
        }

        private String sendHttpsRequest(JSONObject postData, String method) throws NoSuchAlgorithmException, KeyManagementException, IOException, JSONException {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{
                        new X509TrustManager(){

                            @Override
                            public void checkClientTrusted(X509Certificate[] p1, String p2) {}

                            @Override
                            public void checkServerTrusted(X509Certificate[] p1, String p2) {}

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[]{};
                            }
                        }
                    }, new SecureRandom());
            StringBuilder mySign = new StringBuilder("");
            appSign(m_appid, m_secret_id, m_secret_key, System.currentTimeMillis() / 1000 + EXPIRED_SECONDS, m_user_id, mySign);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            URL url = new URL(m_end_point + method);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setHostnameVerifier(new HostnameVerifier(){

                    @Override
                    public boolean verify(String p1, SSLSession p2) {
                        return true;
                    }
                });
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
            StringBuilder buffer = new StringBuilder();  
            try {  
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));  
                String temp;  
                while ((temp = br.readLine()) != null) {  
                    buffer.append(temp);  
                    buffer.append("\n");  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            connection.disconnect();
            return buffer.toString();
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

    public class OcrResult {
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

    public class TagResult {
        public int errorcode;
        public String errormsg;
        public ArrayList<Tag> tags;

        public class Tag {
            public String tag_name;
            public int tag_confidence;
        }
    }

    public class TtsResult {
        public int errorcode;
        public String errormsg;
        public String voice;
    }

    public class PornResult {
        public int errorcode;
        public String errormsg;
        public ArrayList<Tag> tags;

        public class Tag {
            public String tag_name;
            public int tag_confidence;
            public float tag_confidence_f;
        }
    }
    /*    {
     "errorcode": 0,
     "errormsg": "OK",
     "tags": [{
     "tag_name": "normal",
     "tag_confidence": 95,
     "tag_confidence_f": 0.9591665863990784
     }, {
     "tag_name": "hot",
     "tag_confidence": 4,
     "tag_confidence_f": 0.04083340987563133
     }, {
     "tag_name": "porn",
     "tag_confidence": 0,
     "tag_confidence_f": 3.073516330331927e-9
     }, {
     "tag_name": "female-breast",
     "tag_confidence": 100,
     "tag_confidence_f": 1.0
     }, {
     "tag_name": "female-genital",
     "tag_confidence": 0,
     "tag_confidence_f": 0.0
     }, {
     "tag_name": "male-genital",
     "tag_confidence": 0,
     "tag_confidence_f": 0.0
     }, {
     "tag_name": "pubes",
     "tag_confidence": 0,
     "tag_confidence_f": 0.0
     }, {
     "tag_name": "anus",
     "tag_confidence": 0,
     "tag_confidence_f": 0.0
     }, {
     "tag_name": "sex",
     "tag_confidence": 0,
     "tag_confidence_f": 0.0
     }, {
     "tag_name": "normal_hot_porn",            #综合得分
     "tag_confidence": 0,
     "tag_confidence_f": 0.002473380882292986
     }],
     "faces": [],
     "feas": {
     "name": "global_pool",
     "feature": "AAAAAAAAAAAAAAAAa/rK"
     }*/
}
