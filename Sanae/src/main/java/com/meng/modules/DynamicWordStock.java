package com.meng.modules;

import com.meng.SBot;
import com.meng.annotation.SanaeData;
import com.meng.config.DataPersistenter;
import com.meng.gameData.TouHou.Goodwill;
import com.meng.handler.MessageManager;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.Tools;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

/**
 * @author: 司徒灵羽
 **/
public class DynamicWordStock extends BaseModule implements IGroupMessageEvent {

    @SanaeData("dynamic_word_stock.json")
    private WordStock dictionary;

    public DynamicWordStock(SBot bw) {
        super(bw);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        if (!entity.configManager.isFunctionEnbled(gme.getGroup().getId(), Modules.WORDSTOCK)) {
            return false; 
        }
        Goodwill.UserData gu = entity.moduleManager.getModule(Goodwill.class).getUsetData(gme.getSender().getId());
        String msg = gme.getMessage().contentToString();
        for (String k : dictionary.w.keySet()) {
            if (Pattern.matches(k, msg)) {
                ArrayList<Entry> list = dictionary.w.get(k);
                Entry entry = list.get(ThreadLocalRandom.current().nextInt(list.size()));
                if (entry.range != null) {
                    if (entry.range[1] == 0) {
                        entry.range[1] = Integer.MAX_VALUE;
                    }
                    int i = 0;
                    while (gu.goodwill < entry.range[0] || gu.goodwill > entry.range[1]) {
                        if (++i > 10000) {
                            throw new NoSuchElementException();
                        }
                        entry = list.get(ThreadLocalRandom.current().nextInt(list.size()));
                    }
                }
                MessageChainBuilder mcb = new MessageChainBuilder();
                boolean recall = false;
                for (Node node:entry.e) {
                    try {
                        switch (node.t) {
                            case TXT:
                                mcb.add(node.c);
                                break;
                            case IMG:
                                try {
                                    mcb.add(gme.getGroup().uploadImage(new File(SBot.appDirectory + node.c)));
                                } catch (Exception e) {
                                    ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                                }
                                break;
                            case QID:
                                mcb.add(String.valueOf(gme.getSender().getId()));
                                break;
                            case QNAME:
                                mcb.add(gme.getSenderName());
                                break;
                            case GID:
                                mcb.add(String.valueOf(gme.getGroup().getId()));
                                break;
                            case GNAME:
                                mcb.add(gme.getGroup().getName());
                                break;
                            case AT_QID:
                                mcb.add(new At(gme.getSender()));
                                break;
                            case QUOTE:
                                mcb.add(new QuoteReply(gme.getSource()));
                                break;
                            case RAN_INT:
                                int b = -1;
                                try {
                                    b = Integer.parseInt(node.c);  
                                } catch (NumberFormatException ignore) {}
                                ThreadLocalRandom current = ThreadLocalRandom.current();
                                mcb.add(String.valueOf(b == -1 ? current.nextInt(): current.nextInt(b)));
                                break;
                            case RAN_FLOAT:
                                float scale = 1f;
                                try {
                                    scale = Float.parseFloat(node.c);
                                } catch (NumberFormatException ignore) {}
                                mcb.add(String.valueOf(ThreadLocalRandom.current().nextFloat() * scale));
                                break;
                            case HASH_RAN_INT:
                                mcb.add(String.valueOf(entity.moduleManager.getModule(ModuleDiceCmd.class).thData.hashRandomInt(gme.getSender().getId())));
                                break;
                            case HASH_RAN_FLOAT:
                                float rscale = 1f;
                                try {
                                    rscale = Float.parseFloat(node.c);
                                } catch (NumberFormatException ignore) {}
                                mcb.add(String.valueOf(entity.moduleManager.getModule(ModuleDiceCmd.class).thData.hashRandomFloat(gme.getSender().getId()) * rscale));
                                break;
                            case IMG_FOLDER:
                                mcb.add(gme.getGroup().uploadImage(Tools.ArrayTool.rfa(new File(SBot.appDirectory + node.c).listFiles())));
                                break;
                            case AUTO_RECALL:
                                recall = true;
                                break;
                        }    
                    } catch (Exception e) {
                        ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                    }
                }
                int id = entity.sendGroupMessage(gme.getGroup().getId(), mcb.asMessageChain());
                if (recall) {
                    MessageManager.autoRecall(entity, id);  
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        return false;
    }

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        return false;
    }

    @Override
    public DynamicWordStock load() {
        DataPersistenter.read(this);
        return this;
    }

    @Override
    public String getModuleName() {
        return "wordstock";
    }

    public enum ItemType {
        TXT,
        IMG,
        QID,
        QNAME,
        GID,
        GNAME,
        AT_QID,
        QUOTE,
        RAN_INT,
        RAN_FLOAT,
        HASH_RAN_INT,
        HASH_RAN_FLOAT,
        IMG_FOLDER,
        AUTO_RECALL
        }

    public class WordStock {
        public HashMap<String,ArrayList<Entry>> w = new HashMap<>(); 
    }

    public class Entry {
        public ArrayList<Node> e = new ArrayList<>();
        public int[] range = new int[2];
        public void add(Node node) {
            e.add(node);
        }
    }

    public class Node {
        public String c = "";
        public ItemType t;

        public Node() { }
        public Node(String content, ItemType type) {
            this.c = content;
            this.t = type;
            if (c == null) {
                c = "";
            }
        }
    }
}
