package com.meng.modules.bilibili.user.javabean;

import com.meng.tools.JsonHelper;

public class Upstat {
	public int code;
	public String message;
	public int ttl;
	public Data data;

	public class Data {
		public Archive archive;
		public Article article;
	}

	public class Archive {
		public int view;
	}

	public class Article {
		public int view;
	}

	@Override
	public String toString() {
		return JsonHelper.toJson(this);
	}
}
