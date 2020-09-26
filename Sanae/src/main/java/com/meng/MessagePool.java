package com.meng;

import com.meng.tools.SJFExecutors;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.message.MessageEvent;

public class MessagePool {

    private static LinkedList<MessageEvent> msgList = new LinkedList<>();
    private static boolean first = true;

    public static void put(MessageEvent msg) {
        msgList.add(msg);
    }

    public static MessageEvent get(int id) {
        for (MessageEvent msg :msgList) {
            if (msg.getSource().getId() == id) {
                return msg;   
            }
        }
        return null;
    }

    public static void start() {
        if (first) {
            SJFExecutors.executeAtFixedRate(new Runnable(){

                    @Override
                    public void run() {
                        Iterator<MessageEvent> iterator = msgList.iterator();
                        while (iterator.hasNext()) {
                            if ((int)(System.currentTimeMillis() / 1000) - iterator.next().getTime() > 120) {
                                iterator.remove();
                            }
                        }
                    }
                }, 0, 2, TimeUnit.MINUTES);
            first = false;
        }
    }
}
