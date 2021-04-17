package com.meng.modules.bilibili.javabean;

import com.meng.tools.GSON;
import java.util.ArrayList;

public class VideoUrl {

    public int code;
    public String message;
    public int ttl;
    public Data data;

    public class Data {
        public String from;
        public String result;
        public String message;
        public int quality;
        public String format;
        public int timelength;
        public String accept_format;
        public ArrayList<String> accept_description;
        public ArrayList<Integer> accept_quality;
        public int video_codecid;
        public String seek_param;
        public String seek_type;
        public ArrayList<Durl> durl;
    }

    public class Durl {
        public int order;
        public long length;
        public long size;
        public String ahead;
        public String vhead;
        public String url;
        public ArrayList<String> backup_url;
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
