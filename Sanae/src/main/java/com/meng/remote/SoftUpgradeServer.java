package com.meng.remote;

import com.meng.Autoreply;
import com.meng.BotWrapper;
import com.meng.config.ConfigManager;
import com.meng.groupMsgProcess.ModuleManager;
import com.meng.groupMsgProcess.ModuleMsgDelaySend;
import com.meng.remote.softinfo.EachSoftInfo;
import com.meng.remote.softinfo.SoftInfo;
import com.meng.remote.softinfo.SoftInfoBean;
import com.meng.tools.Tools;
import java.io.File;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Iterator;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class SoftUpgradeServer extends WebSocketServer {

	private BotWrapper wrapper;
    public SoftUpgradeServer(InetSocketAddress in, BotWrapper bw) {
		super(in);
        wrapper = bw;
	}

	@Override
	public void onOpen(WebSocket p1, ClientHandshake p2) {
		// TODO: Implement this method
	}

	@Override
	public void onClose(WebSocket p1, int p2, String p3, boolean p4) {
		// TODO: Implement this method
	}

	@Override
	public void onMessage(WebSocket p1, String p2) {
		System.out.println(p2);
		CheckNewBean cnb=Autoreply.gson.fromJson(p2, CheckNewBean.class);
		EachSoftInfo esi=readJson().infos.get(cnb.packageName);
		if (esi == null || cnb.nowVersionCode >= esi.lastestVersionCode) {
			p1.send("");
			return;
		}
		File app=new File(wrapper.getCQ().getAppDirectory() + "/software/" + cnb.packageName + "-" + esi.lastestVersionCode + ".apk");
		esi.lastestSize = (int)app.length();
		Iterator<SoftInfo> iterator=esi.infoList.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().versionCode > cnb.nowVersionCode) {
				break;
			}
			iterator.remove();
		}
		p1.send(Autoreply.gson.toJson(esi));
	}

	@Override
	public void onMessage(WebSocket conn, ByteBuffer message) {
		BotDataPack rec=BotDataPack.decode(message.array());
		switch (rec.getOpCode()) {
			case BotDataPack.opGetApp:
				String packageName=rec.readString();
				EachSoftInfo esi=readJson().infos.get(packageName);
				File app=new File(wrapper.getCQ().getAppDirectory() + "/software/" + packageName + "-" + esi.lastestVersionCode + ".apk");
				BotDataPack toSend=BotDataPack.encode(BotDataPack.opGetApp);
				toSend.write(app);
				conn.send(toSend.getData());
				break;
			case BotDataPack.opCrashLog:
				File fc=new File(wrapper.getCQ().getAppDirectory() + "/softlog/" + rec.readString() + "-" + rec.readInt() + ".log");
				rec.readFile(fc);
				conn.send(BotDataPack.encode(BotDataPack.opTextNotify).write("发送成功").getData());
				break;
			case BotDataPack.sendToMaster:
				ConfigManager.instence.addReport(-5, -5, rec.readString());
				ModuleManager.instence.getModule(ModuleMsgDelaySend.class).addTip(Autoreply.mainGroup, 2856986197L, "有新的用户反馈");
				break;
		}
		super.onMessage(conn, message);
	}

	@Override
	public void onError(WebSocket p1, Exception p2) {
		p2.printStackTrace();
	}

	@Override
	public void onStart() {
		// TODO: Implement this method
	}

	public class CheckNewBean {
		public String packageName;
		public int nowVersionCode;
	}

	private SoftInfoBean readJson() {
		return Autoreply.gson.fromJson(Tools.FileTool.readString(wrapper.getCQ().getAppDirectory() + "/software/info.json"), SoftInfoBean.class);
	}

}
