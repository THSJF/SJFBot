package com.sohu.inputmethod.sogou.jb;

import android.text.SpannableStringBuilder;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Entry {
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
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (Node n:entryList) {
            ssb.append(n.getCharSequence());
        }
        return ssb;
    }
}
