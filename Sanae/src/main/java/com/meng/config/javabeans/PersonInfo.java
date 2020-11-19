package com.meng.config.javabeans;

import java.util.Objects;

/**
 * @author: 司徒灵羽
 **/
public final class PersonInfo extends Object {
    public String name = "";
    public long qq = 0;
    public int bid = 0;
    public int bliveRoom = 0;
    private transient int hash = 0;

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = Objects.hash(name, qq, bid, bliveRoom);
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != PersonInfo.class) {
            return false;
        }
        if (obj.hashCode() != hashCode()) {
            return false;
        }
        PersonInfo p = (PersonInfo) obj;
        return qq == p.qq && bid == p.bid && bliveRoom == p.bliveRoom && name.equals(p.name);
    }
}
