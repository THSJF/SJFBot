package com.meng.modules.bilibili.user.javabean;

import com.meng.tools.JsonHelper;

public class RoomInfoByUid {
	public int code;
    public String msg;
    public String message;
    public Data data;

    public class Data {
        public int roomStatus;
        public int roundStatus;
        public int liveStatus;
        public String url;
        public String title;
        public String cover;
        public int online;
        public int roomid;
        public int broadcast_type;
    }
	
	@Override
	public String toString() {
		return JsonHelper.toJson(this);
	}
}
