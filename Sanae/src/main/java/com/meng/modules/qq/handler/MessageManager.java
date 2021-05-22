package com.meng.modules.qq.handler;

import com.meng.modules.qq.SBot;
import com.meng.tools.SJFExecutors;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.OnlineMessageSource;

public class MessageManager {
    private static MessageManager instance = null;
    private LinkedList<MessageEvent> msgList = new LinkedList<>();

    private MessageManager() {
        SJFExecutors.executeAtFixedRate(new Runnable(){

                @Override
                public void run() {

                    Iterator<MessageEvent> iterator = msgList.iterator();
                    while (iterator.hasNext()) {
                        if ((int)(System.currentTimeMillis() / 1000) - iterator.next().getTime() > 1800) {
                            iterator.remove();
                        }
                    }
                }
            }, 2, 2, TimeUnit.MINUTES);
    }

    public static void put(MessageEvent msg) {
        instance.msgList.add(msg);
        if (instance.msgList.size() > 1000) {
            instance.msgList.removeFirst();
        }
    }

    public static MessageEvent get(MessageRecallEvent event) {
        return get(event.getMessageIds());
    }

    public static MessageEvent get(int[] ids) {
        for (MessageEvent msg :instance.msgList) {
            if (arrayEquals(msg.getSource().getIds(), ids)) {
                return msg;  
            }
        }
        return null;
    }

    public static MessageEvent get(MessageSource source) {
        for (MessageEvent msg :instance.msgList) {
            if (arrayEquals(msg.getSource().getIds(), source.getIds())) {
                return msg;  
            }
        }
        return null;
    }

    public static void autoRecall(SBot sb, int[] msgIds, int second) {
        MessageSource.recallIn(get(msgIds).getSource(), second);
    }

    public static void autoRecall(SBot sb, int[] msgIds) {
        MessageSource.recallIn(get(msgIds).getSource(), 60);
	}

    private static boolean arrayEquals(int[] a1, int[] a2) {
        if (a1.length != a2.length) {
            return false;
        }
        for (int i = 0;i < a1.length;++i) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void init() {
        if (instance == null) {
            instance = new MessageManager();
        } else {
            throw new IllegalStateException();
        }
    }
}
