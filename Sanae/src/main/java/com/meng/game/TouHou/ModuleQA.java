package com.meng.game.TouHou;

import com.google.gson.*;
import com.google.gson.reflect.*;
import com.meng.*;
import com.meng.config.*;
import com.meng.groupMsgProcess.*;
import com.meng.tools.*;
import java.io.*;
import java.lang.reflect.*;
import java.nio.charset.*;
import java.util.*;

public class ModuleQA extends BaseModule {

	public HashMap<Long,QA> qaMap=new HashMap<>();
	public String imagePath;
	public ArrayList<QA> qaList=new ArrayList<>();
	private File qafile;

	public static final int easy=0;
	public static final int normal=1;
	public static final int hard=2;
	public static final int lunatic=3;

	public static final int touhouBase=1;
	public static final int _2unDanmakuIntNew=2;
	public static final int _2unDanmakuAll=3;
	public static final int _2unNotDanmaku=4;
	public static final int _2unAll=5;
	public static final int otherDanmaku=6;
	public static final int luastg=7;


	@Override
	public BaseModule load() {
		qafile = new File(Autoreply.appDirectory + "/qa.json");
        if (!qafile.exists()) {
            saveData();
        }
        Type type = new TypeToken<ArrayList<QA>>() {
        }.getType();
        qaList = Autoreply.gson.fromJson(Tools.FileTool.readString(qafile), type);
		imagePath = Autoreply.appDirectory + "/qaImages/";
		File imageFolder=new File(imagePath);
		if (!imageFolder.exists()) {
			imageFolder.mkdirs();
		}
		enable = true;
		return this;
	}

	@Override
	public boolean processMsg(long fromGroup, long fromQQ, String msg, int msgId) {
		if (msg.equals("-qa 设置抢答")) {
			PersonConfig pcfg=ConfigManager.instence.getPersonCfg(fromQQ);
			pcfg.setQaAllowOther(pcfg.isQaAllowOther() ?false: true);
			Autoreply.sendMessage(fromGroup, 0, "允许抢答:" + pcfg.isQaAllowOther());
			ConfigManager.instence.saveSanaeConfig();
			return true;
		}
		if (msg.startsWith("抢答[")) {
			long target=Autoreply.CC.getAt(msg);
			if (ConfigManager.instence.getPersonCfg(target).isQaAllowOther()) {
				QA qa = qaMap.get(fromQQ);
				if (qa != null) {
					HashSet<Integer> userAnss = new HashSet<>();
					String[] usAnsStrs = msg.split(" ");
					for (String s : usAnsStrs) {
						try {
							userAnss.add(Integer.parseInt(s));
						} catch (NumberFormatException e) {}
					}
					if (qa.getTrueAns().containsAll(userAnss) && qa.getTrueAns().size() == userAnss.size()) {
						Autoreply.sendMessage(fromGroup, 0, Autoreply.CC.at(fromQQ) + "回答正确");
					} else {
						Autoreply.sendMessage(fromGroup, 0, String.format("%s回答错误", Autoreply.CC.at(fromQQ)));
					}
					qaMap.remove(fromQQ);
					return true;
				}
			} else {
				Autoreply.sendMessage(fromGroup, 0, "该用户不允许别人抢答");
			}
			return true;
		}
		QA qa = qaMap.get(fromQQ);
		if (qa != null && msg.equalsIgnoreCase("-qa")) {
			Autoreply.sendMessage(fromGroup, 0, Autoreply.CC.at(fromQQ) + "你还没有回答");
			return true;
		}
		if (qa != null) {
			HashSet<Integer> userAnss = new HashSet<>();
			String[] usAnsStrs = msg.split(" ");
			for (String s : usAnsStrs) {
				try {
					userAnss.add(Integer.parseInt(s));
				} catch (NumberFormatException e) {}
			}
			if (qa.getTrueAns().containsAll(userAnss) && qa.getTrueAns().size() == userAnss.size()) {
				Autoreply.sendMessage(fromGroup, 0, Autoreply.CC.at(fromQQ) + "回答正确");
			} else {
				Autoreply.sendMessage(fromGroup, 0, String.format("%s回答错误", Autoreply.CC.at(fromQQ)));
			}
			qaMap.remove(fromQQ);
			return true;
		}
		if (msg.equalsIgnoreCase("-qa")) {
			int ran=Autoreply.ins.random.nextQA();
			QA qa2=qaList.get(ran);
			StringBuilder sb=new StringBuilder(Autoreply.CC.at(fromQQ)).append("\n题目ID:").append(ran).append("\n");
			sb.append("难度:");
			switch (qa2.getDifficulty()) {
				case 0:
					sb.append("easy");
					break;
				case 1:
					sb.append("normal");
					break;
				case 2:
					sb.append("hard");
					break;
				case 3:
					sb.append("lunatic");
					break;
				case 4:
					sb.append("overdrive");
					break;
				case 5:
					sb.append("kidding");
			}
			sb.append("\n分类:");
			switch (qa2.getType()) {
				case 0:
					sb.append("未定义");
					break;
				case touhouBase:
					sb.append("东方project基础");
					break;
				case _2unDanmakuIntNew:
					sb.append("新作整数作");
					break;
				case _2unDanmakuAll:
					sb.append("官方弹幕作");
					break;
				case _2unNotDanmaku:
					sb.append("官方非弹幕");
					break;
				case _2unAll:
					sb.append("官方所有");
					break;
				case otherDanmaku:
					sb.append("同人弹幕");
					break;
				case luastg:
					sb.append("LuaSTG");
					break;
				default:
					sb.append("未知");
			}	
			sb.append("\n");
			if (qa2.q.contains("(image)")) {
				try {
					sb.append(qa2.q.replace("(image)", Autoreply.CC.image(new File(imagePath + qa2.getId() + ".jpg"))));
				} catch (IOException e) {
					e.printStackTrace();
					sb.append(qa2.q.replace("(image)", "(图片出现错误)"));
				}
			} else {
				sb.append(qa2.q);
			}
			sb.append("\n");
			qa2.exangeAnswer();
			saveData();
			qaMap.put(fromQQ, qa2);
			int i=0;
			for (String s:qa2.a) {
				if (s.equals("")) {
					continue;
				}
				sb.append(i++).append(": ").append(s).append("\n");
			}
			sb.append("回答序号即可");
			if (qa2.getTrueAns().size() > 1) {
				sb.append(",本题有多个选项");
			}
			Autoreply.sendMessage(fromGroup, 0, sb.toString());
			return true;
		}
		return false;
	}

	public void addQA(QA qa) {
		qa.setId(qaList.size());
		qaList.add(qa);
		saveData();
	}

	public void setQA(QA qa) {
		qaList.set(qa.getId(), qa);
		saveData();
	}

	private void saveData() {
		try {
			FileOutputStream fos = new FileOutputStream(qafile);
			OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			writer.write(new Gson().toJson(qaList));
			writer.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
