package com.meng.modules.bilibili.live.javabean;

import com.meng.tools.JsonHelper;
import java.util.List;

public class LivePart {
    public int code;
    public String message;
    public String msg;
    public List<GroupData> data;

    public class GroupData {
        public long id;
        public String name;
        public List<PartData> list;
    }

	public class PartData {
		public String id;
		public String parent_id;
		public String old_area_id;
		public String name;
		public String act_id;
		public String pk_status;
		public int hot_status;
		public String lock_status;
		public String pic;
		public String parent_name;
		public int area_type;
	}

	@Override
	public String toString() {
		return JsonHelper.toJson(this);
	}
}
