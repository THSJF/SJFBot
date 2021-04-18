package com.meng.modules.bilibili.live.javabean;

import com.meng.tools.GSON;
import java.util.List;

public class GiftBag {
    public int code;
    public String message;
    public int ttl;
    public Data data;

    public class Data {
        public List<Item> list;
        public long time;
    }

    public class Item {
        public int bag_id;
        public int gift_id;
        public String gift_name;
        public int gift_num;
        public int gift_type;
        public long expire_at;
        public String corner_mark;
        public List<CountMap> count_map;
        public int type;
        public String card_image;
        public String card_gif;
        public int card_id;
        public int card_record_id;
        public boolean is_show_send;
    }

    public class CountMap {
        public long num;
        public String text;
    }

	@Override
	public String toString() {
		return GSON.toJson(this);
	}

	public int getStripCount() {
		int i = 0;
		for (Item item:data.list) {
			if (item.gift_name.equals("辣条")) {
				i += item.gift_num;
			}
		}
		return i;
	}
}
