package com.meng.gameData.TouHou;

import com.meng.modules.ModuleQA.QA;
import com.meng.SanaeDataPack;
import com.meng.adapter.BotWrapperEntity;
import com.meng.modules.ModuleQA;
import java.io.File;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class QuestionServer extends WebSocketServer {

    private BotWrapperEntity entity;

	public QuestionServer(int port, BotWrapperEntity bwe) {
		super(new InetSocketAddress(port));
        entity = bwe;
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println("websocket连接");
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.out.println("websocket断开");
	}

	@Override
	public void onMessage(WebSocket conn, String message) {

	}
	@Override
	public void onMessage(WebSocket conn, ByteBuffer message) {
		SanaeDataPack dataRec = SanaeDataPack.decode(message.array());
		SanaeDataPack sdp = null;
		switch (dataRec.getVersion()) {
			case 1:
			case 2:
			case 3:
				sdp = SanaeDataPack.encode(SanaeDataPack.opNotification, dataRec);
				sdp.write("旧版本已弃用");
				break;
			case 4:
				switch (dataRec.getOpCode()) {
					case SanaeDataPack.opAddQuestion:
						QA qa40= new QA();
						qa40.setFlag(dataRec.readInt());
						qa40.q = dataRec.readString();
						int ans40=dataRec.readInt();
						qa40.setTrueFlag(dataRec.readInt());
						for (int i=0;i < ans40;++i) {
							qa40.a.add(dataRec.readString());
						}
						qa40.r = dataRec.readString();
						if (qa40.r.equals("")) {
							qa40.r = null;
						}
						if (dataRec.hasNext()) {
							ModuleQA module = entity.moduleManager.getModule(ModuleQA.class);
                            qa40.l = (int)dataRec.readFile(new File(module.imagePath + module.qaList.size() + ".jpg")).length();
						}
						entity.moduleManager.getModule(ModuleQA.class).addQA(qa40);
						sdp = SanaeDataPack.encode(SanaeDataPack.opNotification, dataRec);
						sdp.write("添加成功");
						break;
					case SanaeDataPack.opAllQuestion:
						sdp = writeQA(entity.moduleManager.getModule(ModuleQA.class).qaList);
						break;
					case SanaeDataPack.opSetQuestion:
						QA qa43= new QA();
						qa43.setFlag(dataRec.readInt());
						qa43.q = dataRec.readString();
						int ans43=dataRec.readInt();
						qa43.setTrueFlag(dataRec.readInt());
						for (int i=0;i < ans43;++i) {
							qa43.a.add(dataRec.readString());
						}
						qa43.r = dataRec.readString();
						if (qa43.r.equals("")) {
							qa43.r = null;
						}
						if (dataRec.hasNext()) {
							qa43.l = (int)dataRec.readFile(new File(entity.moduleManager.getModule(ModuleQA.class).imagePath + qa43.getId() + ".jpg")).length();
						}
						entity.moduleManager.getModule(ModuleQA.class).setQA(qa43);
						sdp = SanaeDataPack.encode(SanaeDataPack.opNotification, dataRec);
						sdp.write("修改成功");
						break;
					case SanaeDataPack.opQuestionPic:
						sdp = SanaeDataPack.encode(SanaeDataPack.opQuestionPic, dataRec);
						int id = dataRec.readInt();
						File img = new File(entity.moduleManager.getModule(ModuleQA.class).imagePath + id + ".jpg");
						sdp.write(id);
						sdp.write(img);
						break;
				}
				break;
		}
		if (sdp != null) {
			conn.send(sdp.getData());
		}
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace();
		if (conn != null) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}

	@Override
	public void onStart() {
		System.out.println("quesServer started!");
		setConnectionLostTimeout(100);
	}
	private SanaeDataPack writeQA(ArrayList<ModuleQA.QA> qas) {
		SanaeDataPack sdp=SanaeDataPack.encode(SanaeDataPack.opAllQuestion);
		for (QA qa:qas) {
			sdp.write(qa.getFlag());//flag
			sdp.write(qa.l);
			sdp.write(qa.q);//ques
			sdp.write(qa.a.size());//ansCount
			sdp.write(qa.getTrueAnsFlag());
			for (String s:qa.a) {
				sdp.write(s);
			}
			sdp.write(qa.r == null ?"": qa.r);
		}
		return sdp;
	}
}
	
