package com.meng.config;

import com.meng.modules.qq.SBot;
import com.meng.modules.qq.modules.Sentence;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.JsonHelper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
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
                int available = 0;
                while ((available = in.available()) == 0) {
                    sleep(1);
                }
                byte[] bs = new byte[available];
                in.read(bs);
                id = new String(bs, StandardCharsets.UTF_8);
                if (id.startsWith("ljyys")) {
                    connections.put(id, this);
                } else {
                    s.close();
                }
            } catch (Exception e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
        }

        public void send(String s) {
            try {
                if (s.equals("down")) {
                    socket.close();
                    return;
                }
                send(s.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
        }

        public void send(byte[] array) {
            try {
                out.write(array);
            } catch (IOException e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
        }

        @Override
        public void run() {
            System.out.println("receive thread start");
            try {
                int available = 0;
                while (!socket.isClosed()) {
                    while ((available = in.available()) == 0) {
                        sleep(1);  
                    }
                    byte[] bs = new byte[available];
                    in.read(bs);
                    System.out.println("bs len:" + bs.length);
                    onReceive(bs);
                }
                connections.remove(this);
                System.out.println(this.toString() + " closed and removed");
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        private void onReceive(byte[] array) {
            onReceive(new String(array, StandardCharsets.UTF_8));
            DataPackage dp = DataPackage.decode(array);
            switch (dp.getOpCode()) {
                case DataPackage.typeString:
                    Sentence.Sentences sses = JsonHelper.fromJson(dp.readString(), Sentence.Sentences.class);
                    Sentence module = SBot.instance.moduleManager.getModule(Sentence.class);
                    module.sens.sentences.addAll(sses.sentences);
                    module.save();
                    module.reload();
                    break;
            }
        }

        private void onReceive(String content) {
            System.out.println(content);
            send("received:" + content);
            if (content.startsWith("to yys")) {
                SBot.instance.sendGroupMessage(SBot.yysGroup, content.substring(6));
            }
        }
    }
}
