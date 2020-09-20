package com.meng.config.ritsukage;

import com.meng.config.*;
import java.net.*;
import java.nio.*;
import org.java_websocket.*;
import org.java_websocket.handshake.*;
import org.java_websocket.server.*;

/**
 * @author 司徒灵羽
 */
 
public class RitsukageServer extends WebSocketServer {

	public RitsukageServer() {
		super(new InetSocketAddress(9961));
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		//	conn.send("Welcome to the server!"); //This method sends a message to the new client
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		//	broadcast(conn + " has left the room!");
	}

	@Override
	public void onMessage(WebSocket conn, String message) {

	}

	@Override
	public void onMessage(WebSocket conn, ByteBuffer message) {
		RitsukageDataPack dp=RitsukageDataPack.decode(message.array());
		if (dp.getTarget() == ConfigManager.getOgg()) {
			oggProcess(conn, dp);
		}
	}

	private void oggProcess(WebSocket ogg, RitsukageDataPack recievedDataPack) {

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
		System.out.println("Server started!");
		setConnectionLostTimeout(1000);
	}
}

