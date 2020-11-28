package com.meng.handler;
/**
 * @author: 司徒灵羽
 **/
import com.meng.SBot;
import com.meng.tools.SJFExecutors;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;

public class MessageManager {
    private static MessageManager instance = null;

    private LinkedList<MessageEvent> msgList = new LinkedList<>();

    private MessageManager() {
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
            }, 2, 2, TimeUnit.MINUTES);
    }

    public static void put(MessageEvent msg) {
        instance.msgList.add(msg);
    }

    public static MessageEvent get(MessageRecallEvent event) {
        return get(event.getMessageId());
    }
    public static MessageEvent get(int id) {
        for (MessageEvent msg :instance.msgList) {
            if (msg.getSource().getId() == id) {
                return msg;   
            }
        }
        return null;
    }

    public static void autoRecall(final SBot sb, final int msgId, int second) {
        SJFExecutors.executeAfterTime(new Runnable(){
                @Override
                public void run() {
                    sb.recall(get(msgId).getSource());
                }
            }, second, TimeUnit.SECONDS);
    }

    public static void autoRecall(final SBot sb, final int msgId) {
        SJFExecutors.executeAfterTime(new Runnable(){
                @Override
                public void run() {
                    sb.recall(get(msgId).getSource());
                }
            }, 1, TimeUnit.MINUTES);
	}

    public static void init() {
        if (instance == null) {
            instance = new MessageManager();
        } else {
            throw new IllegalStateException();
        }
    }
}
