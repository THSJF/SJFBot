package com.sohu.inputmethod.sogou.jb;

import java.util.ArrayList;

public class Entry {
    public ArrayList<Node> e = new ArrayList<>();

    public void add(Node node) {
        e.add(node);
    }

    @Override
    public String toString() {
        return e.toString();
    }
}
