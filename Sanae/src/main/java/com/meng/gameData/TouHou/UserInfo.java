package com.meng.gameData.TouHou;

import com.google.gson.annotations.SerializedName;
import com.meng.annotation.BotData;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import java.util.HashMap;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public class UserInfo extends BaseModule {

    @BotData("userinfo.json")
    private HashMap<Long,UserData> values = new HashMap<>();

    public UserInfo(SBot sb) {
        super(sb);
    }

    public boolean addFaith(long qq, int v) {
        UserData ud = values.get(qq);
        if (ud == null) {
            return false;
        }
        ud.faith += v;
        save();
        return true;
    }

    public UserData getUserData(GroupMessageEvent event) {
        return getUserData(event.getGroup(), event.getSender());
    }

    public UserData getUserData(Group group, Member user) {
        if (values.containsKey(user.getId())) {
            return values.get(user.getId());
        }
        UserData ud = new UserData();
        ud.firstMeetTime = System.currentTimeMillis();
        ud.firstMeetGroup = group.getId();
        values.put(user.getId(), ud);
        save();
        return ud;
    }

    public boolean onSign(Group group, Member user) {
        UserData ud = getUserData(group, user);
        if (ud.todaySigned) {
            return false;
        }
        ud.signedDays++;
        ud.todaySigned = true;
        if (ud.todaySigned && ud.yesterdaySigned) {
            ud.continuousSignedDays++;
        }
        ud.faith += (10 + ud.continuousSignedDays);
        save();
        return true;
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
        public long firstMeetTime;
        @SerializedName("h")
        public long firstMeetGroup;
        @SerializedName("i")
        public int qaCount;
        @SerializedName("j")
        public int qaRight;
    }
}
