package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.annotation.SanaeData;
import com.meng.config.DataPersistenter;
import com.meng.tools.ExceptionCatcher;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.QuoteReply;

public class DynamicWordStock extends BaseGroupModule {

    @SanaeData("dynamic_word_stock.json")
    private WordStock dictionary;

    public DynamicWordStock(BotWrapperEntity bw) {
        super(bw);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        if (!entity.configManager.getGroupConfig(gme.getGroup().getId()).isGroupDicEnable()) {
            return false; 
        }
        String msg = gme.getMessage().contentToString();
        for (String k : dictionary.w.keySet()) {
            if (msg.contains(k)) {
                ArrayList<WordStock.Entry> list = dictionary.w.get(k);
                WordStock.Entry entry = list.get(ThreadLocalRandom.current().nextInt(list.size()));
                MessageChainBuilder mcb = new MessageChainBuilder();
                for (WordStock.Entry.Node node:entry.e) {
                    switch (node.t) {
                        case TXT:
                            mcb.add(node.c);
                            break;
                        case IMG:
                            try {
                                mcb.add(gme.getGroup().uploadImage(new File(BotWrapperEntity.appDirectory + node.c)));
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
                            } catch (NumberFormatException ignore) { }
                            mcb.add(String.valueOf(b == -1 ? ThreadLocalRandom.current().nextInt(): ThreadLocalRandom.current().nextInt(b)));
                            break;
                        case RAN_FLOAT:
                            float scale = Float.NaN;
                            try {
                                scale = Float.parseFloat(node.c);
                            } catch (NumberFormatException ignore) {}
                            if (Float.isNaN(scale)) {
                                scale = 1f;
                            }
                            mcb.add(String.valueOf(ThreadLocalRandom.current().nextFloat() * scale));
                            break;
                        case HASH_RAN_INT:
                            mcb.add(String.valueOf(entity.moduleManager.getModule(ModuleDiceCmd.class).thData.hashRandomInt(gme.getSender().getId())));
                            break;
                        case HASH_RAN_FLOAT:
                            float rscale = Float.NaN;
                            try {
                                rscale = Float.parseFloat(node.c);
                            } catch (NumberFormatException ignore) {}
                            if (Float.isNaN(rscale)) {
                                scale = 1f;
                            }
                            mcb.add(String.valueOf(entity.moduleManager.getModule(ModuleDiceCmd.class).thData.hashRandomFloat(gme.getSender().getId()) * rscale));
                            break;
                    }
                }
                entity.sjfTx.sendGroupMessage(gme.getGroup().getId(), mcb.asMessageChain());
                return true;
            }
        }
        return false;
    }

    @Override
    public DynamicWordStock load() {
        DataPersistenter.read(this);
        return this;
    }

    public void save() {
        DataPersistenter.save(this);
    }

    @Override
    public String getModuleName() {
        return "动态词库";
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
        HASH_RAN_FLOAT
        }

    public class WordStock {

        public HashMap<String,ArrayList<Entry>> w = new HashMap<>(); 

        public class Entry {
            public ArrayList<Node> e = new ArrayList<>();

            public void add(Node node) {
                e.add(node);
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
    }
}
