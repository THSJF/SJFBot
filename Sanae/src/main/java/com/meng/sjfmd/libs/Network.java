package com.meng.sjfmd.libs;

import com.meng.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;
import java.util.regex.*;
import org.jsoup.*;
import com.meng.adapter.BotWrapperEntity;

/**
 * @author 司徒灵羽
 */

public class Network {

	public static Map<String, String> cookieToMap(String value) {
		Map<String, String> map = new HashMap<>();
		String[] values = value.split("; ");
		for (String val : values) {
			String[] vals = val.split("=");
			if (vals.length == 2) {
				map.put(vals[0], vals[1]);
			} else if (vals.length == 1) {
				map.put(vals[0], "");
			}
		}
		return map;
	}

	public static String getRealUrl(String surl) throws Exception {
		URL url = new URL(surl);
		URLConnection conn = url.openConnection();
		conn.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
		String nurl = conn.getURL().toString();
		in.close();
		return nurl;
	}

	public static String httpGet(String url) {
		return httpGet(url, null, null);
	}

	public static String httpGet(String url, String cookie) {
		return httpGet(url, cookie, null);
	}

	public static String httpGet(String url, String cookie, String refer) {
		Connection.Response response = null;
		Connection connection;
		try {
			connection = Jsoup.connect(url);
			if (cookie != null) {
				connection.cookies(cookieToMap(cookie));
			}
			if (refer != null) {
				connection.referrer(refer);
			}
			connection.userAgent(BotWrapperEntity.userAgent);
			connection.ignoreContentType(true).method(Connection.Method.GET);
			response = connection.execute();
			if (response.statusCode() != 200) {
				System.out.println(String.valueOf(response.statusCode()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.body();
	}

	public static String formatJson(String content) {
		if (content == null) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		int index = 0;
		int count = 0;
		while (index < content.length()) {
			char ch = content.charAt(index);
			if (ch == '{' || ch == '[') {
				sb.append(ch);
				sb.append('\n');
				count++;
				for (int i = 0; i < count; i++) {                   
					sb.append('\t');
				}
			} else if (ch == '}' || ch == ']') {
				sb.append('\n');
				count--;
				for (int i = 0; i < count; i++) {                   
					sb.append('\t');
				}
				sb.append(ch);
			} else if (ch == ',') {
				sb.append(ch);
				sb.append('\n');
				for (int i = 0; i < count; i++) {                   
					sb.append('\t');
				}
			} else {
				sb.append(ch);              
			}
			index++;
		}
		return sb.toString();
	}
	/**
	 * 把格式化的json紧凑
	 * @param content
	 * @return
	 */
	public static String compactJson(String content) {
		String regEx="[\t\n]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(content);
		return m.replaceAll("").trim();
	}
}
