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

public class ModuleQAR extends BaseModule {

	public HashMap<Long,QA> qaMap=new HashMap<>();

	@Override
	public BaseModule load() {
		enable = true;
		return this;
	}

	@Override
	public boolean processMsg(long fromGroup, long fromQQ, String msg, int msgId) {

		QA qar = qaMap.get(fromQQ);
		if (qar != null && msg.equalsIgnoreCase("-qar")) {
			Autoreply.sendMessage(fromGroup, 0, Autoreply.CC.at(fromQQ) + "你还没有回答");
			return true;
		}
		if (qar != null) {
			int userAnser=-1;
			try {
				userAnser = Integer.parseInt(msg);
			} catch (NumberFormatException e) {}
			if (qar.getTrueAns().contains(userAnser) && qar.getTrueAns().size() == 1) {
				Autoreply.sendMessage(fromGroup, 0, Autoreply.CC.at(fromQQ) + "回答正确");
			} else {
				Autoreply.sendMessage(fromGroup, 0, String.format("%s回答错误", Autoreply.CC.at(fromQQ)));
			}
			qaMap.remove(fromQQ);
			return true;
		}
		if (msg.equalsIgnoreCase("-qar")) {
			QA qa2=createQA();
			qa2.exangeAnswer();
			StringBuilder sb=new StringBuilder(Autoreply.CC.at(fromQQ)).append("\n");
			sb.append(qa2.q);
			int i=0;
			for (String s:qa2.a) {
				if (s.equals("")) {
					continue;
				}
				sb.append(i++).append(": ").append(s).append("\n");
			}
			sb.append("回答序号即可");
			qaMap.put(fromQQ, qa2);
			Autoreply.sendMessage(fromGroup, 0, sb.toString());
			return true;
		}
		return false;
    }

	private QA createQA() {
		int diff=1 << new Random().nextInt(9);
		SpellCard spellCard = ModuleManager.instence.getModule(ModuleDiceImitate.class).getSpellFromDiff(diff);
		SpellCard[] sps = ModuleManager.instence.getModule(ModuleDiceImitate.class).getSpellFromNotDiff(3, diff);
		QA qa=new QA();
		qa.a.add(spellCard.n);
		for (SpellCard spc:sps) {
			qa.a.add(spc.n);
		}
		qa.setTrueAns(0);
		qa.exangeAnswer();
		StringBuilder sb=new StringBuilder();
		sb.append("以下符卡在");
		switch (diff) {
			case SpellCard.E:
				sb.append("easy难度");
				break;
			case SpellCard.N:
				sb.append("normal难度");
				break;
			case SpellCard.H:
				sb.append("hard难度");
				break;
			case SpellCard.L:
				sb.append("lunatic难度");
				break;
			case SpellCard.O:
				sb.append("overdrive难度");
				break;
			case SpellCard.Ls:
				sb.append("last spell");
				break;
			case SpellCard.Lw:
				sb.append("lastword");
				break;
			case SpellCard.Ex:
				sb.append("extra关卡");
				break;
			case SpellCard.Ph:
				sb.append("phamtasm关卡");
				break;
			default:
				System.out.println(spellCard.n);
				System.out.println(diff);
		}
		sb.append("中出现的是:\n");
		qa.q = sb.toString();
		return qa;
	}
}
