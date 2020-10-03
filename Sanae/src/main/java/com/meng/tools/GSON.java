package com.meng.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import java.lang.reflect.Type;

public class GSON {
	private static Gson gson;

	static{
		GsonBuilder gb = new GsonBuilder();
		gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		gson = gb.create();
	}

	public static <T> T fromJson(String json, Class<T> clz) {
		return (T)gson.fromJson(json, clz);
	}

	public static <T> T fromJson(String json, Type t) {
		return (T)gson.fromJson(json, t);
	}

	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}
}
