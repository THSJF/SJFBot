package com.meng.config;

import com.google.gson.reflect.*;
import com.meng.*;
import com.meng.remote.*;
import com.meng.tools.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.nio.charset.*;
import java.util.concurrent.*;

public class ConfigManager {
	public static ConfigManager instence;
    public RanConfigBean RanConfig = new RanConfigBean();
	public SanaeConfigJavaBean SanaeConfig=new SanaeConfigJavaBean();
	private File SanaeConfigFile;
	public NetConfig netConfig;

	private ConcurrentHashMap<Integer,TaskResult> resultMap=new ConcurrentHashMap<>();
	public ConfigManager() {
		Type type = new TypeToken<SanaeConfigJavaBean>() {
		}.getType();
		SanaeConfigFile = new File(Autoreply.appDirectory + "/SanaeConfig.json");
		if (!SanaeConfigFile.exists()) {
			saveSanaeConfig();
		}
        SanaeConfig = Autoreply.gson.fromJson(Tools.FileTool.readString(SanaeConfigFile), type);
		Autoreply.ins.threadPool.execute(new Runnable(){

				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(60000);
						} catch (InterruptedException e) {}
						saveSanaeConfig();
					}
				}
			});
	}

	public void load() {
		try {
			netConfig = new NetConfig(new URI("ws://123.207.65.93:9760"));
			netConfig.connect();
		} catch (URISyntaxException e) {}
		Autoreply.ins.threadPool.execute(new Runnable(){

				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {}
						if (netConfig != null && !netConfig.isClosed()) {
							netConfig.send("heart beat");
						}
					}
				}
			});
	}

	public String getOverSpell(long fromQQ) {
		send(SanaeDataPack.encode(SanaeDataPack.opGameOverSpell).write(fromQQ));
		return Tools.BitConverter.toString(getTaskResult(SanaeDataPack.opGameOverSpell).data);
	}

	public int getOverPersent(long fromQQ) {
		send(SanaeDataPack.encode(SanaeDataPack.opGameOverPersent).write(fromQQ));
		return Tools.BitConverter.toInt(getTaskResult(SanaeDataPack.opGameOverPersent).data);
	}

	public String getGrandma(long fromQQ) {
		send(SanaeDataPack.encode(SanaeDataPack.opGrandma).write(fromQQ));
		return Tools.BitConverter.toString(getTaskResult(SanaeDataPack.opGrandma).data);
	}

	public String getMusicName(long fromQQ) {
		send(SanaeDataPack.encode(SanaeDataPack.opMusicName).write(fromQQ));
		return Tools.BitConverter.toString(getTaskResult(SanaeDataPack.opMusicName).data);
	}

	public String getSpells(long fromQQ) {
		send(SanaeDataPack.encode(SanaeDataPack.opGotSpells).write(fromQQ));
		return Tools.BitConverter.toString(getTaskResult(SanaeDataPack.opGotSpells).data);
	}

	public String getNeta(long fromQQ) {
		send(SanaeDataPack.encode(SanaeDataPack.opNeta).write(fromQQ));
		return Tools.BitConverter.toString(getTaskResult(SanaeDataPack.opNeta).data);
	}

	public String getSeq() {
		send(SanaeDataPack.encode(SanaeDataPack.opSeqContent));
		return Tools.BitConverter.toString(getTaskResult(SanaeDataPack.opSeqContent).data);
	}

	public String getLiveList() {
		send(SanaeDataPack.encode(SanaeDataPack.opLiveList));
		return Tools.BitConverter.toString(getTaskResult(SanaeDataPack.opLiveList).data);
	}

	public void setWelcome(long group, String welcome) {
		SanaeConfig.welcomeMap.put(group, welcome);
		saveSanaeConfig();
	}

	public String getWelcome(long group) {
		if (SanaeConfig.welcomeMap.get(group) == null) {
			return null;
		}
		return SanaeConfig.welcomeMap.get(group);
	}

	private TaskResult getTaskResult(int opCode) {
		int time=5000;
		while (resultMap.get(opCode) == null && time-- > 0) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
		}
		TaskResult tr=resultMap.get(opCode);
		resultMap.remove(opCode);
		return tr;
	}

	public void setNickName(long qq, String nickname) {
		if (nickname != null) {
			RanConfig.nicknameMap.put(qq, nickname);
			send(SanaeDataPack.encode(SanaeDataPack.opSetNick).write(qq).write(nickname));
		} else {
			RanConfig.nicknameMap.remove(qq);
			send(SanaeDataPack.encode(SanaeDataPack.opSetNick).write(qq));
		}
	}

	public String getNickName(long qq) {
		String nick=null;
		nick = RanConfig.nicknameMap.get(qq);
		if (nick == null) {
			PersonInfo pi=getPersonInfoFromQQ(qq);
			if (pi == null) {
				nick = Autoreply.CQ.getStrangerInfo(qq).getNick();
			} else {
				nick = pi.name;
			}
		}
		return nick;
	}

	public String getNickName(long group, long qq) {
		String nick=null;
		nick = RanConfig.nicknameMap.get(qq);
		if (nick == null) {
			PersonInfo pi=getPersonInfoFromQQ(qq);
			if (pi == null) {
				nick = Autoreply.CQ.getGroupMemberInfo(group, qq).getNick();
			} else {
				nick = pi.name;
			}
		}
		return nick;
	}

    public boolean isMaster(long fromQQ) {
        return fromQQ == 1594703250L || fromQQ == 2856986197L || fromQQ == 8255053L || fromQQ == 1592608126L || fromQQ == 1620628713L || fromQQ == 2565128043L;
    }

    public boolean isAdmin(long fromQQ) {
        return RanConfig.adminList.contains(fromQQ) || isMaster(fromQQ);
    }

    public boolean isNotReplyQQ(long qq) {
        return RanConfig.QQNotReply.contains(qq) || RanConfig.blackListQQ.contains(qq);
    }

    public boolean isBlackQQ(long qq) {
        return RanConfig.blackListQQ.contains(qq);
    }

    public boolean isBlackGroup(long qq) {
        return RanConfig.blackListGroup.contains(qq);
    }

    public boolean isNotReplyWord(String word) {
        for (String nrw : RanConfig.wordNotReply) {
            if (word.contains(nrw)) {
                return true;
            }
        }
        return false;
    }

    public PersonInfo getPersonInfoFromQQ(long qq) {
        for (PersonInfo pi : RanConfig.personInfo) {
            if (pi.qq == qq) {
                return pi;
            }
        }
        return null;
    }

    public PersonInfo getPersonInfoFromName(String name) {
        for (PersonInfo pi : RanConfig.personInfo) {
            if (pi.name.equals(name)) {
                return pi;
            }
        }
        return null;
    }

    public PersonInfo getPersonInfoFromBid(long bid) {
        for (PersonInfo pi : RanConfig.personInfo) {
            if (pi.bid == bid) {
                return pi;
            }
        }
        return null;
    }

	public PersonInfo getPersonInfoFromLiveId(long lid) {
        for (PersonInfo pi : RanConfig.personInfo) {
            if (pi.bliveRoom == lid) {
                return pi;
			}
		}
        return null;
	}

	public PersonConfig getPersonCfg(long fromQQ) {
		PersonConfig pcfg=SanaeConfig.personCfg.get(fromQQ);
		if (pcfg == null) {
			pcfg = new PersonConfig();
			SanaeConfig.personCfg.put(fromQQ, pcfg);
		}
		return pcfg;
	}

    public void addBlack(long group, final long qq) {
        RanConfig.blackListQQ.add(qq);
        RanConfig.blackListGroup.add(group);
		send(SanaeDataPack.encode(SanaeDataPack.opAddBlack).write(group).write(qq));
		if (netConfig == null || netConfig.isClosed()) {
			Autoreply.sendMessage(Autoreply.mainGroup, 0, "连接出错,未能将用户" + qq + "加入黑名单");
			Autoreply.sendMessage(Autoreply.mainGroup, 0, "连接出错,未能将群" + group + "加入黑名单");

		} else {
			Autoreply.sendMessage(Autoreply.mainGroup, 0, "已将用户" + qq + "加入黑名单");
			Autoreply.sendMessage(Autoreply.mainGroup, 0, "已将群" + group + "加入黑名单");
		}
		Autoreply.CQ.setGroupLeave(group, false);
    }

	public void addReport(long fromGroup, long fromQQ, String content) {
		SanaeConfig.addReport(fromGroup, fromQQ, content.substring(4));
		saveSanaeConfig();
	}

	public void addBugReport(long fromGroup, long fromQQ, String content) {
		SanaeConfig.addBugReport(fromGroup, fromQQ, content.substring(6));
		saveSanaeConfig();
	}

	public SanaeConfigJavaBean.ReportBean getReport() {
		return SanaeConfig.getReport();
	}

	public SanaeConfigJavaBean.ReportBean removeReport() {
		return SanaeConfig.removeReport();
	}

	public void reportToLast() {
		SanaeConfig.reportToLast();
	}

	public SanaeConfigJavaBean.BugReportBean removeBugReport() {
		return SanaeConfig.removeBugReport();
	}

	public void bugReportToLast() {
		SanaeConfig.bugReportToLast();
	}

	public SanaeConfigJavaBean.BugReportBean getBugReport() {
		return SanaeConfig.getBugReport();
	}

	public void send(SanaeDataPack sdp) {
		if (netConfig == null || netConfig.isClosed()) {
			return;
		}
		netConfig.send(sdp.getData());
	}

	public void saveSanaeConfig() {
		BotDataPack toSend = BotDataPack.encode(BotDataPack.getConfig);
		toSend.write(Autoreply.gson.toJson(ConfigManager.instence.RanConfig));
		Autoreply.ins.remoteWebSocket.broadcast(toSend.getData());
        try {
            FileOutputStream fos = new FileOutputStream(SanaeConfigFile);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.write(Autoreply.gson.toJson(SanaeConfig));
            writer.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
