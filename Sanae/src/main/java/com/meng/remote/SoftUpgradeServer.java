package com.meng.remote;
import com.meng.*;
import com.meng.config.*;
import com.meng.groupMsgProcess.*;
import com.meng.remote.softinfo.*;
import com.meng.tools.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;
import org.java_websocket.*;
import org.java_websocket.handshake.*;
import org.java_websocket.server.*;

public class SoftUpgradeServer extends WebSocketServer {

	private File hashFile=new File("C://hash.dat");
	public SoftUpgradeServer(InetSocketAddress in) {
		super(in);
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
		File app=new File(Autoreply.appDirectory + "/software/" + cnb.packageName + "-" + esi.lastestVersionCode + ".apk");
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
				File app=new File(Autoreply.appDirectory + "/software/" + packageName + "-" + esi.lastestVersionCode + ".apk");
				BotDataPack toSend=BotDataPack.encode(BotDataPack.opGetApp);
				toSend.write(app);
				conn.send(toSend.getData());
				break;
			case BotDataPack.opCrashLog:
				File fc=new File(Autoreply.appDirectory + "/softlog/" + rec.readString() + "-" + rec.readInt() + ".log");
				rec.readFile(fc);
				conn.send(BotDataPack.encode(BotDataPack.opTextNotify).write("发送成功").getData());
				break;
			case BotDataPack.sendToMaster:
				ConfigManager.instence.addReport(-5, -5, rec.readString());
				ModuleManager.instence.getModule(ModuleMsgDelaySend.class).addTip(Autoreply.mainGroup, 2856986197L, "有新的用户反馈");
				break;
			case BotDataPack.getIdFromHash:
				byte[] fileBytes;
				int loopIndex;
				toSend = BotDataPack.encode(BotDataPack.getIdFromHash);
				ArrayList<Integer> intValues=new ArrayList<>();
				while (rec.hasNext()) {
					intValues.add(rec.readInt());
				}
				int tasks=intValues.size();
				byte[] didFlag=new byte[intValues.size()];
				byte[][] hashs=new byte[intValues.size()][4];
				for (int i=0;i < intValues.size();++i) {
					byte[] tmp=Tools.BitConverter.getBytes(intValues.get(i));
					hashs[i][0] = tmp[0];
					hashs[i][1] = tmp[1];
					hashs[i][2] = tmp[2];
					hashs[i][3] = tmp[3];
				}
				try {
					for (long loopFlag = 0;loopFlag < hashFile.length();loopFlag += 1024 * 1024 * 20) {
						fileBytes = readFile(loopFlag);
						for (int i=0;i < hashs.length;++i) {
							if (didFlag[i] == 1) {
								continue;
							}
							byte[] hashBytes = hashs[i];
							if ((loopIndex = getIndexOf(fileBytes, hashBytes)) != -1) {
								toSend.write(Tools.BitConverter.toInt(hashBytes));
								toSend.write((int)(loopFlag + loopIndex) / 4);
								System.out.println((int)(loopFlag + loopIndex) / 4);
								didFlag[i] = 1;
								tasks--;
							}
						}
						if (tasks == 0) {
							break;
						}
					}
				} catch (EOFException e) {
					System.out.println("eof");
				} catch (Exception e) {
					e.printStackTrace();
				}
				conn.send(toSend.getData());
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
		return Autoreply.gson.fromJson(Tools.FileTool.readString(Autoreply.appDirectory + "/software/info.json"), SoftInfoBean.class);
	}

	public int getIndexOf(byte[] bigArray, byte[] smallArray) {
		try {
			if (bigArray == null || bigArray == null || bigArray.length == 0 || smallArray.length == 0) return -1;
			int i, j;
			for (i = 0;i < bigArray.length;i++) {
				if (bigArray[i] == smallArray[0]) {
					for (j = 1;j < smallArray.length;j++) {
						if (bigArray[i + j] != smallArray[j]) break;
					}
					if (j == smallArray.length) return i;
				}
			}
			return -1;
		} catch (Exception e) {
			return -1;
		}
	}

	public byte[] readFile(long offset) throws EOFException, FileNotFoundException, IOException {
        RandomAccessFile randomAccessFile;
		byte[] data=new byte[1024 * 1024 * 20];
		randomAccessFile = new RandomAccessFile(hashFile.getAbsolutePath(), "r");
		randomAccessFile.seek(offset);
		randomAccessFile.readFully(data);
		randomAccessFile.close();
        return data;
    }

}
