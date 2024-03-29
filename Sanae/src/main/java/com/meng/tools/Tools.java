package com.meng.tools;

import com.meng.modules.qq.SBot;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;

public class Tools {

	public static Map<String, String> liveHead = new HashMap<>();
    public static Map<String, String> mainHead = new HashMap<>();

	public static final String DEFAULT_ENCODING = "UTF-8";

	static{
		liveHead.put("Host", "api.live.bilibili.com");
        liveHead.put("Accept", "application/json, text/javascript, */*; q = 0.01");
        liveHead.put("Content-Type", "application/x-www-form-urlencoded; charset = UTF-8");
        liveHead.put("Connection", "keep-alive");
        liveHead.put("Origin", "https://live.bilibili.com");

        mainHead.put("Host", "api.bilibili.com");
        mainHead.put("Accept", "application/json, text/javascript, */*; q = 0.01");
        mainHead.put("Content-Type", "application/x-www-form-urlencoded; charset = UTF-8");
        mainHead.put("Connection", "keep-alive");
        mainHead.put("Origin", "https://www.bilibili.com");
	}

	public static class CQ {

		public static void findQQInAllGroup(SBot bw, long fromGroup, long fromQQ, String msg) {
			long findqq;
			try {
				findqq = Long.parseLong(msg);
			} catch (Exception e) {
                return;
			}
			if (findqq <= 0) {
				bw.sendGroupMessage(fromGroup, "账号错误");
				return;
			}
			bw.sendGroupMessage(fromGroup, "running");
			HashSet<Group> hashSet = findQQInAllGroup(bw, findqq);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(findqq).append("在这些群中出现");
			for (Group l : hashSet) {
				stringBuilder.append("\n").append(l.getId()).append(l.getName());
			}
			bw.sendGroupMessage(fromGroup, stringBuilder.toString());
		}

		public static HashSet<Group> findQQInAllGroup(SBot bw, long findQQ) {
			ContactList<Group> groups = bw.getGroups();
			HashSet<Group> hashSet = new HashSet<>();
			for (Group group : groups) {
				if (group.getId() == 959615179L || group.getId() == 666247478L) {
					continue;
				}
                if (group.contains(findQQ)) {
                    hashSet.add(group);
                }
			}
			return hashSet;
		}
	}

	public static class ArrayTool {

		public static byte[] mergeArray(byte[]... arrays) {
			int allLen = 0;
			for (byte[] bs:arrays) {
				allLen += bs.length;
			}
			byte[] finalArray = new byte[allLen];
			int flag = 0;
			for (byte[] byteArray:arrays) {
				for (int i = 0;i < byteArray.length;++flag,++i) {
					finalArray[flag] = byteArray[i];
				}
			}
			return finalArray;
		}
	}
}
