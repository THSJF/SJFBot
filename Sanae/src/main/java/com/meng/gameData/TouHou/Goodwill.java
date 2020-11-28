package com.meng.gameData.TouHou;

import com.google.gson.annotations.SerializedName;
import com.meng.SBot;
import com.meng.annotation.SanaeData;
import com.meng.config.DataPersistenter;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.modules.BaseModule;
import java.util.HashMap;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;

public class Goodwill extends BaseModule {

    @SanaeData("goodwill.json")
    private HashMap<Long,UserData> values = new HashMap<>();

    public Goodwill(SBot sb) {
        super(sb);
    }

    @Override
    public Goodwill load() {
        DataPersistenter.read(this);
        return this;
    }

    @Override
    public String getModuleName() {
        return "userdata";
    }

    public UserData getUsetData(Member user) {
        if (values.containsKey(user.getId())) {
            return values.get(user.getId());
        }
        UserData ud = new UserData();
        ud.firstMeet = System.currentTimeMillis();
        values.put(user.getId(), ud);
        save();
        return ud;
    }

    public String onSign(Member user) {
        long qq = user.getId();
        UserData ud = values.get(qq);
        if (ud.todaySigned) {
            return "你今天已经签到过了";
        }
        ud.signedDays++;
        ud.todaySigned = true;
        if (ud.todaySigned && ud.yesterdaySigned) {
            ud.continuousSignedDays++;
        }
        save();
        return String.format("签到成功,累计%d天,连续签到%d天", ud.signedDays, ud.continuousSignedDays);
    }

    public void onNewDay() {
        for (UserData ud : values.values()) {
            ud.yesterdaySigned = ud.todaySigned;
            if (ud.todaySigned) {
                ud.todaySigned = false;
            } else {
                ud.continuousSignedDays = 1;
            }
        }
        save();
    }

    public static class UserData {
        @SerializedName("a")
        public int goodwill;
        @SerializedName("b")
        public int faith;
        @SerializedName("c")
        public int signedDays;
        @SerializedName("d")
        public boolean yesterdaySigned;
        @SerializedName("e")
        public boolean todaySigned;
        @SerializedName("f")
        public int continuousSignedDays = 1;
        @SerializedName("g")
        public long firstMeet;
    }
}
