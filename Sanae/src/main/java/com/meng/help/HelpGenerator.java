package com.meng.help;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpGenerator {
    private static HelpGenerator instance = new HelpGenerator();

    public static HelpGenerator getInstance() {
        return instance;
    }

    private Map<String,Item> items = new HashMap<>();

    public Item newItem(Permission pms, String className) {
        Item m = new Item(pms, className, 0, 0, className.length() + 2);
        items.put(className, m);
        return m;
    }

    public Map<String,Item> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public static class Item {
        private List<Item> args = null;

        private String cmd;
        private int grandLeng = 0;
        private int superLeng = 0;
        private int allLeng = 0;

        private Permission pms = Permission.Normal;

        public Item(Item father, String cmd) {
            this.pms = father.pms;
            this.cmd = cmd;
            this.superLeng = father.getLeng();
            this.grandLeng = father.superLeng;
            this.allLeng = father.allLeng + cmd.length() + 2;
            father.args.add(this);
        }

        public Item(Item father, String cmd, Permission pms) {
            this(father, cmd);
            this.pms = pms;
        }

        public Item(Permission pms, String cmd, int superLeng, int grandLeng, int allLeng) {
            this.pms = pms;
            this.cmd = cmd;
            this.superLeng = superLeng;
            this.grandLeng = grandLeng;
            this.allLeng = allLeng;
        }

        public Item arg(String cmd) {
            Item child = new Item(
                pms,
                cmd,
                cmd.length() + 2,
                superLeng, grandLeng + superLeng + this.cmd.length() + 2 + cmd.length() + 2);
            if (args == null) {
                args = new ArrayList<>();
            }
            args.add(child);
            return child;
        }

        public Item permission(Permission pms) {
            this.pms = pms;
            return this;
        }

        @Override
        public String toString() {
            if (args == null || args.size() == 0) {
                return cmd + "\n";
            }
            StringBuilder builder = new StringBuilder();
            builder.append("[").append(cmd).append("]-");
            builder.append(args.get(0));
            for (int i = 1;i < args.size();++i) {
                Item item = args.get(i);
                builder.append(format(item.grandLeng, item.allLeng - item.getLeng())).append(item);
            }
            return builder.toString();
        }

        private String format(int length, int length2) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0;i < length + 3;++i) {
                builder.append(" ");
            }
            builder.append("┗");
            for (int i = length + 4;i < length2;++i) {
                builder.append("-");
            }
            return builder.toString();
        }

        private int getLeng() {
            return cmd.length() + 2;
        }
    }
}
