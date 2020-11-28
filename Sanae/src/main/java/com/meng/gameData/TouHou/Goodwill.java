package com.meng.gameData.TouHou;

import com.meng.modules.BaseModule;
import java.util.HashMap;
import com.meng.annotation.SanaeData;
import com.meng.config.DataPersistenter;
import com.google.gson.annotations.SerializedName;
import com.meng.SBot;
import net.mamoe.mirai.contact.Member;

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
        return values.get(user.getId());
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
        public int continuousSignedDays;
        @SerializedName("g")
        public long firstMeet;
    }
}
