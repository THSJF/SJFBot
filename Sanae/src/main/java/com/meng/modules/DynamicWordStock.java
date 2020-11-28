package com.meng.modules;

import com.google.gson.annotations.SerializedName;
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
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import org.jsoup.nodes.Node;
import java.util.Map;
import com.meng.modules.DynamicWordStock.GoodwillLevel;

/**
 * @author: 司徒灵羽
 **/
public class DynamicWordStock extends BaseModule implements IGroupMessageEvent {

    @SanaeData("dynamic_word_stock.json")
    private WordStock dictionary;

    private HashMap<String,Pattern> regexMap = new HashMap<>();
    public DynamicWordStock(SBot bw) {
        super(bw);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
//        if (!entity.configManager.isFunctionEnbled(gme.getGroup().getId(), Modules.WORDSTOCK)) {
//            return false; 
//        }
        String msg = gme.getMessage().contentToString();
        Goodwill.UserData userData = entity.moduleManager.getModule(Goodwill.class).getUsetData(gme.getSender());
        for (Map.Entry<String,Pattern> mapEntry : regexMap.entrySet()) {
            if (mapEntry.getValue().matcher(msg).find()) {
                ArrayList<Entry> list = dictionary.words.get(mapEntry.getKey());
                Entry entry = null;
                GoodwillLevel gl = GoodwillLevel.getLevel(userData == null ? 0 : userData.goodwill);
                for (int i = 0;i < 10000;i++) {
                    entry = list.get(ThreadLocalRandom.current().nextInt(list.size()));
                    if (goodwillMatch(entry, gl)) {
                        break;
                    }
                    if (i == 9999) {
                        throw new NoSuchElementException();
                    }
                }
                MessageChainBuilder mcb = new MessageChainBuilder();
                if (entry.isFlag(Entry.QUOTE)) {
                    mcb.add(new QuoteReply(gme.getSource()));
                } else if (entry.isFlag(Entry.AT)) {
                    mcb.add(new At(gme.getSender()));
                }
                for (Node node:entry.entryList) {
                    try {
                        switch (NodeType.valueOf(node.type)) {
                            case TXT:
                                mcb.add(node.content);
                                break;
                            case IMG:
                                try {
                                    mcb.add(gme.getGroup().uploadImage(new File(SBot.appDirectory + node.content)));
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
                            case RAN_INT:
                                int b = -1;
                                try {
                                    b = Integer.parseInt(node.content);  
                                } catch (NumberFormatException ignore) {}
                                ThreadLocalRandom current = ThreadLocalRandom.current();
                                mcb.add(String.valueOf(b == -1 ? current.nextInt(): current.nextInt(b)));
                                break;
                            case RAN_FLOAT:
                                float scale = 1f;
                                try {
                                    scale = Float.parseFloat(node.content);
                                } catch (NumberFormatException ignore) {}
                                mcb.add(String.valueOf(ThreadLocalRandom.current().nextFloat() * scale));
                                break;
                            case HASH_RAN_INT:
                                int hi = -1;
                                try {
                                    hi = Integer.parseInt(node.content);  
                                } catch (NumberFormatException ignore) {} 
                                mcb.add(String.valueOf(entity.moduleManager.getModule(ModuleDiceCmd.class).thData.hashRandomInt(gme.getSender().getId(), hi)));
                                break;
                            case HASH_RAN_FLOAT:
                                float rscale = 1f;
                                try {
                                    rscale = Float.parseFloat(node.content);
                                } catch (NumberFormatException ignore) {}
                                mcb.add(String.valueOf(entity.moduleManager.getModule(ModuleDiceCmd.class).thData.hashRandomFloat(gme.getSender().getId()) * rscale));
                                break;
                            case IMG_FOLDER:
                                mcb.add(gme.getGroup().uploadImage(Tools.ArrayTool.rfa(new File(SBot.appDirectory + node.content).listFiles())));
                                break;
                        }    
                    } catch (Exception e) {
                        ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                    }
                }
                int id = entity.sendGroupMessage(gme.getGroup().getId(), mcb.asMessageChain());
                if (entry.isFlag(Entry.AUTO_RECALL)) {
                    MessageManager.autoRecall(entity, id);  
                }
                return true;
            }
        }
        return false;
    }

    private boolean goodwillMatch(Entry entry, GoodwillLevel gl) {
        if (entry.isFlag(Entry.E) && gl == GoodwillLevel.EASY) {
            return true;
        } 
        if (entry.isFlag(Entry.N) && gl == GoodwillLevel.NORMAL) {
            return true;
        }
        if (entry.isFlag(Entry.H) && gl == GoodwillLevel.HARD) {
            return true;
        }
        if (entry.isFlag(Entry.L) && gl == GoodwillLevel.LUNATIC) {
            return true;
        }
        if (entry.isFlag(Entry.X) && gl == GoodwillLevel.EXTRA) {
            return true;
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
        for (String key : dictionary.words.keySet()) {
            regexMap.put(key, Pattern.compile(key));
        }
        return this;
    }

    @Override
    public String getModuleName() {
        return "wordstock";
    }

    public enum NodeType {
        TXT(1),
        IMG(2),
        QID(3),
        QNAME(4),
        GID(5),
        GNAME(6),
        RAN_INT(9),
        RAN_FLOAT(10),
        HASH_RAN_INT(11),
        HASH_RAN_FLOAT(12),
        IMG_FOLDER(13);

        public static NodeType valueOf(int v){
            for(NodeType it : values()){
                if(it.v == v){
                    return it;
                }
            } 
            throw new NoSuchElementException();
        }

        private NodeType(int v){
            this.v = v;
        }

        private int v;

        public int value(){
            return v;
        }
    }

    public enum GoodwillLevel {
        ALL("十六夜"),
        EASY("新月级"),
        NORMAL("三日月级"),
        HARD("半月级"),
        LUNATIC("满月级"),
        EXTRA("暗月级");

        private String note = null;

        private GoodwillLevel(String s){
            note = s;   
        }

        public static GoodwillLevel getLevel(int i){
            if(i == -1){
                return ALL;
            }
            if(i >= 0 && i < 1000){
                return EASY;
            }
            if(i >= 1000 && i < 3000){
                return NORMAL;
            }
            if(i >= 3000 && i < 6000){
                return HARD;
            }
            if(i >= 6000 && i < 9961){
                return LUNATIC;
            }
            if(i >= 9961){
                return EXTRA;
            }
            throw new IllegalArgumentException();
        }
    }

    private static class WordStock {
        @SerializedName("w")
        public HashMap<String,ArrayList<Entry>> words = new HashMap<>();
    }

    private static class Entry {
        public static final int E = 1 << 8;
        public static final int N = 1 << 7;
        public static final int H = 1 << 6;
        public static final int L = 1 << 5;
        public static final int X = 1 << 4;

        public static final int AUTO_RECALL = 1 << 3;

        public static final int NORMAL = 1 << 2;
        public static final int AT = 1 << 1;
        public static final int QUOTE = 1 << 0;

        //                                                 E N H L X  auto recall normal at quote
        // 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 | 0 0 0 0 0 |     0     |   0    0   0
        @SerializedName("f") public int flag = 500; // E | N | H | L | X | normal
        @SerializedName("e") public ArrayList<Node> entryList = new ArrayList<>();

        public void add(Node node) {
            entryList.add(node);
        }

        public void setFlagBit(int index) {
            flag |= index;
        }

        public void clearFlagBit(int index) {
            flag &= ~ index;
        }

        public boolean isFlag(int index) {
            return (flag & index) != 0;
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException();
        } 

        public CharSequence getCharSequence() {
            StringBuilder ssb = new StringBuilder();
            for (Node n:entryList) {
                ssb.append(n.getCharSequence());
            }
            return ssb;
        }
    }

    private static class Node {

        @SerializedName("t") public int type;
        @SerializedName("c") public String content = "";

        public Node() { }

        public Node(NodeType type) {
            this.type = type.value();
        }

        public Node(String content) {
            if (content != null) {
                this.content = content;
            }
            this.type = NodeType.TXT.value();
        }

        public Node(String content, NodeType type) {
            if (content != null) {
                this.content = content;
            }
            this.type = type.value();
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException();
        }  

        public CharSequence getCharSequence() {
            switch (NodeType.valueOf(type)) {
                case TXT:
                    return content;
                case IMG:
                    return "[image:" + content + "]";
                case QID:
                    return "[sender qq]";
                case QNAME:
                    return "[sender name]";
                case GID:
                    return "[from group]";
                case GNAME:
                    return "[group name]";
                case RAN_INT:
                    return "[random int,max = " + content + "]";
                case RAN_FLOAT:
                    return "[random float,max = " + content + "]";
                case HASH_RAN_INT:
                    return "[int by hash,max = " + content + "]";
                case HASH_RAN_FLOAT:
                    return "[float by hash,max = " + content + "]";
                case IMG_FOLDER:
                    return "[image folder:" + content + "]";
            }
            throw new IllegalArgumentException();
        }
    }
}
