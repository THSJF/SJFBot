package com.meng.config;

import com.meng.modules.qq.SBot;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NetServer {

    private static NetServer instance = null;
    private ReceiveThread receiver;
    private PrintWriter writer;

    public static NetServer getInstance() {
        if (instance == null) {
            instance = new NetServer();
        }
        return instance;
    }

    public void init() throws Exception {
        ServerSocket serverSocket = new ServerSocket(9961);
        Socket socket = serverSocket.accept();
        receiver = new ReceiveThread(socket.getInputStream());
        receiver.start();
        writer = new PrintWriter(socket.getOutputStream());
    }

    private class ReceiveThread extends Thread {

        private BufferedReader reader; 

        public ReceiveThread(InputStream is) {
            reader = new BufferedReader(new InputStreamReader(is));
        }

        @Override
        public void run() {
            try {
                while (true) {
                    onReceive(reader.readLine());
                    sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void onReceive(String content) {
            if (content == null) {
                System.out.println("content null");
                return;
            }
            if (content.equals("ljyys")) {
                SBot.instance.sendGroupMessage(SBot.yysGroup, content);
            }
        }
    }

    public void send(String s) {
        writer.write(s);
    }
}
