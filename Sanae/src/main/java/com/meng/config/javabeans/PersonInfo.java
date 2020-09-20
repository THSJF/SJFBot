package com.meng.config.javabeans;

import java.util.*;

public class PersonInfo extends Object {
    public String name = "";
    public long qq = 0;
    public int bid = 0;
    public int bliveRoom = 0;

    @Override
    public int hashCode() {
        return name.hashCode() + (int)qq;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PersonInfo)) {
            return false;
        }
        PersonInfo p = (PersonInfo) obj;
        return name.equals(p.name) && qq == p.qq && bid == p.bid && bliveRoom == p.bliveRoom;
    }
}
