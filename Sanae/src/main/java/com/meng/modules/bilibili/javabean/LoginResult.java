package com.meng.modules.bilibili.javabean;

public class LoginResult {
	public long ts;
	public int code;
	public String message;
	public String url;
	public Data data;

	public class Data {
		public long mid;
		public String access_token;
		public String refresh_token;
		public int expires_in;
	}
}
