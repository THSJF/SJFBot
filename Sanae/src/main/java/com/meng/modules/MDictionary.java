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

public class MDictionary extends BaseGroupModule {

    @SanaeData("dictionary.json")
    private WordStock dictionary;

    public MDictionary(BotWrapperEntity bw) {
        super(bw);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        if (!entity.configManager.getGroupConfig(gme.getGroup().getId()).isGroupDicEnable()) {
            return false; 
        }
        String msg = gme.getMessage().contentToString();
        for (String k : dictionary.map.keySet()) {
            if (msg.contains(k)) {
                ArrayList<WordStock.Entry> list = dictionary.map.get(k);
                WordStock.Entry entry = list.get(ThreadLocalRandom.current().nextInt(list.size()));
                MessageChainBuilder mcb = new MessageChainBuilder();
                for (WordStock.Entry.Node node:entry.items) {
                    switch (node.type) {
                        case Type_text:
                            mcb.add(node.content);
                            break;
                        case Type_image:
                            try {
                                mcb.add(gme.getGroup().uploadImage(new File(BotWrapperEntity.appDirectory + node.content)));
                            } catch (Exception e) {
                                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                            }
                            break;
                        case Type_senderId:
                            mcb.add(String.valueOf(gme.getSender().getId()));
                            break;
                        case Type_senderName:
                            mcb.add(gme.getSenderName());
                            break;
                        case Type_fromGroupId:
                            mcb.add(String.valueOf(gme.getGroup().getId()));
                            break;
                        case Type_fromGroupName:
                            mcb.add(gme.getGroup().getName());
                            break;
                        case Type_at_sender:
                            mcb.add(new At(gme.getSender()));
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
    public MDictionary load() {
        DataPersistenter.read(this);
        return this;
    }

    public void save() {
        DataPersistenter.save(this);
    }

    @Override
    public String getModuleName() {
        return "词库";
    }

    enum ItemType {
        Type_text,
        Type_image,
        Type_senderId,
        Type_senderName,
        Type_fromGroupId,
        Type_fromGroupName,
        Type_at_sender,
    }

    public static class WordStock {
        public HashMap<String,ArrayList<Entry>> map = new HashMap<>();

        public static class Entry {
            public ArrayList<Node> items = new ArrayList<>();

            public static class Node {
                public String content = "";
                public ItemType type;
            }
        }  
    }
}
