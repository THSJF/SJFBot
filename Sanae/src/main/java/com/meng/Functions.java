package com.meng;

import com.google.gson.annotations.SerializedName;
import com.meng.config.ConfigManager;

public enum Functions {
    @SerializedName("a") GroupMessageEvent,
    @SerializedName("b") FriendMessageEvent,
    @SerializedName("c") MessageRecallEvent_GroupRecall,
    @SerializedName("d") BotLeaveEvent,
    @SerializedName("e") BotMuteEvent,
    @SerializedName("f") MemberJoinEvent,
    @SerializedName("g") MemberLeaveEvent,
    @SerializedName("h") Dice,
    @SerializedName("i") DynamicWordStock,
    @SerializedName("j") GroupCounterChart,
    @SerializedName("k") MemberChange,
    @SerializedName("l") Morning,
    @SerializedName("m") NumberProcess,
    @SerializedName("n") QuestionAndAnswer,
    @SerializedName("o") Repeater,
    @SerializedName("p") Report;

    public static Functions get(String s){
        for(Functions mds : values()){
            if(mds.toString().equals(s)){
                return mds;
            }
        }
        return null;
    }

    public boolean change(ConfigManager configManager, long groupId) {
        return configManager.setFunctionEnabled(groupId, this, !configManager.isFunctionEnabled(groupId,this));
    }
}
