package com.meng.config;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import com.meng.modules.qq.SBot;

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
        private String identification;
        public ConnectionThread(Socket s) {
            try {
                socket = s;
                in = new DataInputStream(s.getInputStream());
                out = new DataOutputStream(s.getOutputStream());
                identification = in.readLine();
                if (identification.startsWith("ljyys")) {
                    connections.put(identification, this);
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
                out.writeChars(s);
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
                    onReceive(in.readLine());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            connections.remove(identification);
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
