package com.meng.gameData.TouHou;

import com.meng.NetDataPackage;
import com.meng.NetOperatType;
import com.meng.SBot;
import com.meng.modules.ModuleQA;
import com.meng.modules.ModuleQA.QA;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.GSON;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class QuestionServer extends WebSocketServer {

    private SBot entity;

	public QuestionServer(int port, SBot bwe) {
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
		NetDataPackage dataRec = NetDataPackage.decode(message.array());
		NetDataPackage sdp = null;
		switch (dataRec.getVersion()) {
			case 1:
                switch (NetOperatType.valueOf(dataRec.getOpCode())) {
					case ADD_QA:
						QA qa40 = GSON.fromJson(dataRec.readString(), QA.class);
                        entity.moduleManager.getModule(ModuleQA.class).addQA(qa40);
						sdp = NetDataPackage.SUCCESS;
						break;
					case GET_QA:
						sdp = NetDataPackage.encode(NetOperatType.GET_QA.value()).writeString(GSON.toJson(entity.moduleManager.getModule(ModuleQA.class).getQas()));
						break;
					case SET_QA:
						QA qa43 = GSON.fromJson(dataRec.readString(), QA.class);
						entity.moduleManager.getModule(ModuleQA.class).setQA(qa43);
						sdp = NetDataPackage.SUCCESS;
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
		ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), ex);
		if (conn != null) {
			// some errors like port binding failed may not be assignable to a specific websocket
            conn.close();
        }
	}

	@Override
	public void onStart() {
		System.out.println("quesServer started!");
		setConnectionLostTimeout(100);
	}
}
	
