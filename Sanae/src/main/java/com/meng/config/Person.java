package com.meng.config;

import java.util.ArrayList;
import java.util.Objects;

public class Person {

    public static final int qa = 1 << 0;
    public static final int botOn = 1 << 1;
    public static final int jrrpStyle = 1 << 2;

    public String name;
    public ArrayList<Long> qq = new ArrayList<>();
    public int bid = 0;
    public int bLiveRoom = 0;

    public transient String liveUrl = "";
    public transient boolean lastStatus = false;
    public transient boolean needTip = false;

    private transient int hash = 0;

    private int flag = -1;

    public boolean isQaAllowOther() {
        return (flag & qa) != 0;
    }

    public void setQaAllowOther(boolean b) {
        if (b) {
            flag |= qa;
        } else {
            flag &= ~qa;
        }
    }

    public boolean isBotOn() {
        return (flag & botOn) != 0;
    }

    public void setBotOn(boolean b) {
        if (b) {
            flag |= botOn;
        } else {
            flag &= ~botOn;
        }
    }
    
    public boolean isJrrpNewStyle() {
        return (flag & jrrpStyle) != 0;
    }

    public void setJrrpNewStyle(boolean b) {
        if (b) {
            flag |= jrrpStyle;
        } else {
            flag &= ~jrrpStyle;
        }
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = Objects.hash(name, qq, bid);
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Person.class) {
            return false;
        }
        if (obj.hashCode() != hashCode()) {
            return false;
        }
        Person p = (Person) obj;
        return qq.equals(p.qq) && bid == p.bid && bLiveRoom == p.bLiveRoom && name.equals(p.name);
    } 
}
