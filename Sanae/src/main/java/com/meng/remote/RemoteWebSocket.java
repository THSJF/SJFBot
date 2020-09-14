package com.meng.remote;
import com.meng.*;
import com.meng.config.*;
import com.meng.game.TouHou.*;
import com.meng.groupMsgProcess.*;
import com.meng.remote.softinfo.*;
import com.meng.tools.*;
import com.sobte.cqp.jcq.entity.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;
import org.java_websocket.*;
import org.java_websocket.handshake.*;
import org.java_websocket.server.*;

public class RemoteWebSocket extends WebSocketServer {
	BotDataPack msgPack;
	public static BotMsgInfo botInfoBean=new BotMsgInfo();
	public RemoteWebSocket() {
		super(new InetSocketAddress(7777));
		Autoreply.ins.threadPool.execute(new Runnable(){

				@Override
				public void run() {
					while (true) {
						msgPack = BotDataPack.encode(BotDataPack.onGroupMsg);
						try {
							Thread.sleep(1000);
							broadcast(msgPack.getData());
							BotDataPack bbbbb=BotDataPack.encode(BotDataPack.onPerSecMsgInfo);
							bbbbb.write(botInfoBean.sendTo).write(botInfoBean.recFrom).write(botInfoBean.msgPerSec).write(botInfoBean.msgCmdPerSec).write(botInfoBean.msgSendPerSec);
							broadcast(bbbbb.getData());
							botInfoBean.reset();
						} catch (Exception e) {}
					}
				}
			});
	}
	@Override
	public void onOpen(WebSocket p1, ClientHandshake p2) {
		System.out.println("remote connect");
	}

	@Override
	public void onClose(WebSocket p1, int p2, String p3, boolean p4) {
		System.out.println("remote disconnect");
	}

	@Override
	public void onMessage(WebSocket p1, String p2) {
		// TODO: Implement this method
	}

	@Override
	public void onMessage(WebSocket conn, ByteBuffer message) {
		BotDataPack rec=BotDataPack.decode(message.array());
		BotDataPack toSend=null;
		switch (rec.getOpCode()) {
			case BotDataPack.opLoginQQ:
				toSend = BotDataPack.encode(rec.getOpCode());
				toSend.write(Autoreply.CQ.getLoginQQ());
				break;
			case BotDataPack.opLoginNick:
				toSend = BotDataPack.encode(rec.getOpCode());
				toSend.write(Autoreply.CQ.getLoginNick());
				break;
			case BotDataPack.opPrivateMsg:
				toSend = BotDataPack.encode(rec.getOpCode());
				toSend.write(Autoreply.sendMessage(0, rec.readLong(), rec.readString()));
				break;
			case BotDataPack.opGroupMsg:
				toSend = BotDataPack.encode(rec.getOpCode());
				toSend.write(Autoreply.sendMessage(rec.readLong(), 0, rec.readString()));
				break;
			case BotDataPack.opDiscussMsg:
				//toSend = BotDataPack.encode(botDataPack.getOpCode());
				break;
			case BotDataPack.opDeleteMsg:
				Autoreply.CQ.deleteMsg(rec.readInt());
				break;
			case BotDataPack.opSendLike:
				Autoreply.CQ.sendLikeV2(rec.readLong(), rec.readInt());
				break;
			case BotDataPack.opCookies:
				toSend = BotDataPack.encode(rec.getOpCode());
				toSend .write(Autoreply.CQ.getCookies());
				break;
			case BotDataPack.opCsrfToken:
				toSend = BotDataPack.encode(rec.getOpCode());
				toSend.write(Autoreply.CQ.getCsrfToken());
				break;
			case BotDataPack.opRecord:
				//	toSend = BotDataPack.encode(rec.getOpCode());
				break;
			case BotDataPack.opGroupKick:
				Autoreply.CQ.setGroupKick(rec.readLong(), rec.readLong(), rec.readBoolean());
				break;
			case BotDataPack.opGroupBan:
				Autoreply.CQ.setGroupBan(rec.readLong(), rec.readLong(), rec.readLong());
				break;
			case BotDataPack.opGroupAdmin:
				Autoreply.CQ.setGroupAdmin(rec.readLong(), rec.readLong(), rec.readBoolean());
				break;
			case BotDataPack.opGroupWholeBan:
				Autoreply.CQ.setGroupWholeBan(rec.readLong(), rec.readBoolean());
				break;
			case BotDataPack.opGroupAnonymousBan:
				Autoreply.CQ.setGroupAnonymousBan(rec.readLong(), rec.readString(), rec.readLong());
				break;
			case BotDataPack.opGroupAnonymous:
				Autoreply.CQ.setGroupAnonymous(rec.readLong(), rec.readBoolean());
				break;
			case BotDataPack.opGroupCard:
				Autoreply.CQ.setGroupCard(rec.readLong(), rec.readLong(), rec.readString());
				break;
			case BotDataPack.opGroupLeave:
				Autoreply.CQ.setGroupLeave(rec.readLong(), rec.readBoolean());
				break;
			case BotDataPack.opGroupSpecialTitle:
				Autoreply.CQ.setGroupSpecialTitle(rec.readLong(), rec.readLong(), rec.readString(), rec.readLong());
				break;
			case BotDataPack.opGroupMemberInfo:
				toSend = BotDataPack.encode(rec.getOpCode());
				Member m=Autoreply.CQ.getGroupMemberInfo(rec.readLong(), rec.readLong());
				toSend.
					write(m.getGroupId()).
					write(m.getQqId()).
					write(m.getNick()).
					write(m.getCard()).
					write(m.getGender()).
					write(m.getAge()).
					write(m.getArea()).
					write(m.getAddTime().getTime()).
					write(m.getLastTime().getTime()).
					write(m.getLevelName()).
					write(m.getAuthority()).
					write(m.getTitle()).
					write(m.getTitleExpire().getTime()).
					write(m.isBad()).
					write(m.isModifyCard());
				break;
			case BotDataPack.opDiscussLeave:
				toSend = BotDataPack.encode(rec.getOpCode());
				break;
			case BotDataPack.opFriendAddRequest:
				toSend = BotDataPack.encode(rec.getOpCode());
				Autoreply.CQ.setFriendAddRequest(rec.readString(), rec.readInt(), rec.readString());
				break;
			case BotDataPack.opGroupMemberList:
				toSend = BotDataPack.encode(rec.getOpCode());
				List<Member> ml=Autoreply.CQ.getGroupMemberList(rec.readLong());
				for (Member mlm:ml) {
					toSend.
						write(mlm.getGroupId()).
						write(mlm.getQqId()).
						write(mlm.getNick()).
						write(mlm.getCard()).
						write(mlm.getGender()).
						write(mlm.getAge()).
						write(mlm.getArea()).
						write(mlm.getAddTime().getTime()).
						write(mlm.getLastTime().getTime()).
						write(mlm.getLevelName()).
						write(mlm.getAuthority()).
						write(mlm.getTitle()).
						write(mlm.getTitleExpire().getTime()).
						write(mlm.isBad()).
						write(mlm.isModifyCard());
				}
				break;
			case BotDataPack.opGroupList:
				toSend = BotDataPack.encode(rec.getOpCode());
				List<Group> gl=Autoreply.CQ.getGroupList();
				for (Group g:gl) {
					toSend.write(g.getId()).write(g.getName());
				}
				break;
			case BotDataPack.getConfig:
				toSend = BotDataPack.encode(rec.getOpCode());
				toSend.write(Autoreply.gson.toJson(ConfigManager.instence.RanConfig));
				break;
			case BotDataPack.opAddQuestion:
				QA qa40= new QA();
				qa40.setFlag(rec.readInt());
				qa40.q = rec.readString();
				int ans40=rec.readInt();
				qa40.setTrueAnsFlag(rec.readInt());
				for (int i=0;i < ans40;++i) {
					qa40.a.add(rec.readString());
				}
				qa40.r = rec.readString();
				if (qa40.r.equals("")) {
					qa40.r = null;
				}
				if (rec.hasNext()) {
					qa40.l = (int)rec.readFile(new File(ModuleManager.instence.getModule(ModuleQA.class).imagePath + ModuleManager.instence.getModule(ModuleQA.class).qaList.size() + ".jpg")).length();
				}
				ModuleManager.instence.getModule(ModuleQA.class).addQA(qa40);
				toSend = BotDataPack.encode(BotDataPack.opTextNotify);
				toSend.write("添加成功");
				break;
			case BotDataPack.opAllQuestion:
				toSend = writeQA(ModuleManager.instence.getModule(ModuleQA.class).qaList);
				break;
			case BotDataPack.opSetQuestion:
				QA qa43= new QA();
				qa43.setFlag(rec.readInt());
				qa43.q = rec.readString();
				int ans43=rec.readInt();
				qa43.setTrueAnsFlag(rec.readInt());
				for (int i=0;i < ans43;++i) {
					qa43.a.add(rec.readString());
				}
				qa43.r = rec.readString();
				if (qa43.r.equals("")) {
					qa43.r = null;
				}
				if (rec.hasNext()) {
					qa43.l = (int)rec.readFile(new File(ModuleManager.instence.getModule(ModuleQA.class).imagePath + qa43.getId() + ".jpg")).length();
				}
				ModuleManager.instence.getModule(ModuleQA.class).setQA(qa43);
				toSend = BotDataPack.encode(BotDataPack.opTextNotify);
				toSend.write("修改成功");
				break;
			case BotDataPack.opQuestionPic:
				toSend = BotDataPack.encode(BotDataPack.opQuestionPic);
				int id = rec.readInt();
				File img = new File(ModuleManager.instence.getModule(ModuleQA.class).imagePath + id + ".jpg");
				toSend.write(id);
				toSend.write(img);
				break;
			case BotDataPack.opUploadApk:
				String packageName2=rec.readString();
				int versionCode2=rec.readInt();
				String versionName2=rec.readString();
				String describe2=rec.readString();
				File apk=new File(Autoreply.appDirectory + "/software/" + packageName2 + "-" + versionCode2 + ".apk");
				rec.readFile(apk);
				SoftInfo si=new SoftInfo();
				si.versionCode = versionCode2;
				si.versionName = versionName2;
				si.versionDescribe = describe2;
				SoftInfoBean sib=readJson();
				EachSoftInfo esi2=sib.infos.get(packageName2);
				if (esi2 == null) {
					esi2 = new EachSoftInfo();
					sib.infos.put(packageName2, esi2);
				}
				esi2.lastestVersionCode = si.versionCode;
				esi2.lastestVersionName = si.versionName;
				esi2.infoList.add(si);
				saveJson(sib);
				conn.send(BotDataPack.encode(BotDataPack.opUploadApk).getData());
				break;
		}
		if (toSend != null) {
			conn.send(toSend.getData());
		}
	}

	@Override
	public void onError(WebSocket p1, Exception p2) {
		// TODO: Implement this method
	}

	@Override
	public void onStart() {
		setConnectionLostTimeout(1800);
	}

	public void sendMsg(int type, long group, long qq, String msg, long msgId) {
		msgPack.write(type).write(group).write(qq).write(msg).write((int)msgId);
	}

	private BotDataPack writeQA(ArrayList<QA> qas) {
		BotDataPack sdp=BotDataPack.encode(BotDataPack.opAllQuestion);
		for (QA qa:qas) {
			sdp.write(qa.getFlag());
			sdp.write(qa.l);
			sdp.write(qa.q);
			sdp.write(qa.a.size());
			sdp.write(qa.getTrueAnsFlag());
			for (String s:qa.a) {
				sdp.write(s);
			}
			sdp.write(qa.r == null ?"": qa.r);
		}
		return sdp;
	}

	private SoftInfoBean readJson() {
		return Autoreply.gson.fromJson(Tools.FileTool.readString(Autoreply.appDirectory + "/software/info.json"), SoftInfoBean.class);
	}

	private void saveJson(SoftInfoBean sib) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(Autoreply.appDirectory + "/software/info.json"));
            OutputStreamWriter writer = new OutputStreamWriter(fos, "utf-8");
            writer.write(Autoreply.gson.toJson(sib));
            writer.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
