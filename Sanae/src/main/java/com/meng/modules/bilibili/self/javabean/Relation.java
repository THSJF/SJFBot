package com.meng.modules.bilibili.self.javabean;

import com.meng.tools.JsonHelper;

	public class Relation {
	public int code;
	public String message;
	public int ttl;
	public Data data;

	public class Data {
		public int mid;
		public int following;
		public int whisper;
		public int follower;
		public int black;
	}
	
	@Override
	public String toString() {
		return JsonHelper.toJson(this);
	}
}
