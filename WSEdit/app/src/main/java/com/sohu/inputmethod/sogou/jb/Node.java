package com.sohu.inputmethod.sogou.jb;

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

    @Override
    public String toString() {
        return c;
    }
}
