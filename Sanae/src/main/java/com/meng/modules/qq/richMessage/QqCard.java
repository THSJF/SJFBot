package com.meng.modules.qq.richMessage;

import com.meng.tools.JsonHelper;
import java.util.ArrayList;
import java.util.List;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.code.MiraiCode;

public class QqCard {
    public String app = "com.tencent.miniapp";
    public String desc = "";
    public String view = "notification";
    public String ver = "0.0.0.1";
    public String prompt;//外面看
    public Meta meta = new Meta();

    public QqCard(String title, long uid, String... kv) {
        prompt = title;
        meta.notification.appInfo.appName = title;
        meta.notification.emphasis_keyword = title;
        meta.notification.appInfo.iconUrl = "http:\\/\\/q1.qlogo.cn\\/g?b=qq&nk=" + uid + "&s=640";
        for (int i = 0;i < kv.length;i += 2) {
            meta.notification.data.add(meta.notification.new Data(kv[i], kv[i + 1]));
        }
    }

    public QqCard(String prompt, String title, String imgUrl, String... kv) {
        this.prompt = prompt;
        meta.notification.appInfo.appName = title;
        meta.notification.emphasis_keyword = title;
        meta.notification.appInfo.iconUrl = imgUrl;
        for (int i = 0;i < kv.length;i += 2) {
            meta.notification.data.add(meta.notification.new Data(kv[i], kv[i + 1]));
        }
    }
    public class Meta {
        public Notification notification = new Notification();

        public class Notification {
            public AppInfo appInfo = new AppInfo();
            public List<Data> data = new ArrayList<>();
            public String title = "";
            public String emphasis_keyword = "";

            public class AppInfo {
                public String appName; //标题
                public int appType = 4;
                public int appid = 1109659848;
                public String iconUrl;
            }

            public class Data {
                public String title;
                public String value;

                public Data(String title, String value) {
                    this.title = title;
                    this.value = value;
                }
            }
        }
    }

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public String toMiraiCode() {
        return  "[mirai:app:" + JsonHelper.toJson(this).replace(":", "\\:").replace("[", "\\[").replace("]", "\\]").replace(",", "\\,").replace("u003d", "=").replace("u0026", "&") + "]";
    }

    public Message toMiraiMessage() {
        return MiraiCode.deserializeMiraiCode(toMiraiCode());
    }
}
