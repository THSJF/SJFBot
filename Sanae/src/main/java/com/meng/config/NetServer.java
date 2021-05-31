package com.meng.config;

import com.meng.modules.qq.SBot;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class NetServer {

    private static NetServer instance = null;
    private Map<String, ConnectionThread> connections = new HashMap<>();

    public static NetServer getInstance() {
        if (instance == null) {
            instance = new NetServer();
        }
        return instance;
    }

    public void init() throws Exception {
        ServerSocket serverSocket = new ServerSocket(9961);
        while (true) {
            Socket socket = serverSocket.accept();
            ConnectionThread ct = new ConnectionThread(socket);
            ct.start();
        }
    }

    private class ConnectionThread extends Thread {

        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;
        private String id;
        public ConnectionThread(Socket s) {
            try {
                socket = s;
                in = new DataInputStream(s.getInputStream());
                out = new DataOutputStream(s.getOutputStream());
                id = in.readUTF();
                id = id.substring(0, id.length() - 1);
                if (id.startsWith("ljyys")) {
                    connections.put(id, this);
                } else {
                    s.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String s) {
            try {
                if (s.equals("down")) {
                    socket.close();
                    return;
                }
                out.writeUTF(s + "\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            System.out.println("rec start");
            try {
                while (!socket.isClosed()) {
                    System.out.println("read line:");
                    String line = in.readUTF();
                    onReceive(line.substring(0, line.length() - 1));
                }
            } catch (SocketException e) {
                System.out.println(String.format("socket %s closed", id));
            } catch (IOException e) {
                e.printStackTrace();
            }
            connections.remove(id);
        }

        private void onReceive(String content) {
            if (content == null) {
                System.out.println("content null");
                return;
            }
            System.out.println(content);
            send("received:" + content);
            if (content.startsWith("to yys")) {
                SBot.instance.sendGroupMessage(SBot.yysGroup, content.substring(6));
            }
        }
    }
}
