package com.meng.config;

import com.meng.help.Permission;
import com.meng.tools.JsonHelper;
import java.util.Objects;

public class Person {

    public String name;
    public long qq;
    public int bid = 0;
    public int bLiveRoom = 0;
    public Permission permission = Permission.Normal;
    
    public transient String liveUrl = "";
    public transient boolean lastStatus = false;
    public transient boolean needTip = false;

    private transient int hash = 0;

    public Person() {

    }

    public Person(String name, long qq, int bid) {
        this.name = name;
        this.qq = qq;
        this.bid = bid;
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
        return qq == p.qq && bid == p.bid && bLiveRoom == p.bLiveRoom && name.equals(p.name);
    }

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public static class Config {

        private static final int qa = 1 << 0;
        private static final int botOn = 1 << 1;
        private static final int jrrpStyle = 1 << 2;

        private int flag = -1;

        {
            setJrrpNewStyle(false);
        }

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
    }

}
