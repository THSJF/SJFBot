package com.meng.modules.qq.handler;

import com.meng.modules.qq.SBot;
import com.meng.tools.SJFExecutors;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.MessageReceipt;

public class MessageManager {
    private static MessageManager instance = null;
    private LinkedList<MessageSource> sourceList = new LinkedList<>();
    private LinkedList<MessageEvent> msgEvents = new LinkedList<>();

    private MessageManager() {
        SJFExecutors.executeAtFixedRate(new Runnable(){

                @Override
                public void run() {
                    Iterator<MessageSource> iterator = sourceList.iterator();
                    while (iterator.hasNext()) {
                        if ((int)(System.currentTimeMillis() / 1000) - iterator.next().getTime() > 1800) {
                            iterator.remove();
                        }
                    }
                    Iterator<MessageEvent> iterator2 = msgEvents.iterator();
                    while (iterator2.hasNext()) {
                        if ((int)(System.currentTimeMillis() / 1000) - iterator2.next().getTime() > 1800) {
                            iterator2.remove();
                        }
                    }
                }
            }, 2, 2, TimeUnit.MINUTES);
    }

    public static void put(MessageReceipt msgEvent) {
        instance.sourceList.add(msgEvent.getSource());
        if (instance.sourceList.size() > 1000) {
            instance.sourceList.removeFirst();
        }
    }

    public static void put(MessageEvent msgEvent) {
        instance.msgEvents.add(msgEvent);
        if (instance.msgEvents.size() > 1000) {
            instance.msgEvents.removeFirst();
        }
        instance.sourceList.add(msgEvent.getSource());
        if (instance.sourceList.size() > 1000) {
            instance.sourceList.removeFirst();
        }
    }

    public static MessageSource get(MessageRecallEvent event) {
        return get(event.getMessageIds());
    }

    public static MessageSource get(int[] ids) {
        for (MessageSource source :instance.sourceList) {
            if (arrayEquals(source.getIds(), ids)) {
                return source;  
            }
        }
        return null;
    }

    public static MessageSource get(MessageSource source) {
        return get(source.getIds());
    }

    public static MessageEvent getEvent(MessageRecallEvent event) {
        return getEvent(event.getMessageIds());
    }

    public static MessageEvent getEvent(int[] ids) {
        for (MessageEvent event :instance.msgEvents) {
            if (arrayEquals(event.getSource().getIds(), ids)) {
                return event;  
            }
        }
        return null;
    }

    public static MessageEvent getEvent(MessageSource source) {
        return getEvent(source.getIds());
    }

    public static void autoRecall(SBot sb, MessageSource source, int second) {
        MessageSource.recallIn(get(source), second * 1000);
    }

    public static void autoRecall(SBot sb, MessageSource source) {
        MessageSource.recallIn(get(source), 60000);
	}

    public static void autoRecall(SBot sb, int[] msgIds, int second) {
        MessageSource.recallIn(get(msgIds), second * 1000);
    }

    public static void autoRecall(SBot sb, int[] msgIds) {
        MessageSource.recallIn(get(msgIds), 60000);
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
