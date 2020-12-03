package com.meng;

import com.google.gson.annotations.SerializedName;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.BotLeaveEvent;
import net.mamoe.mirai.event.events.BotMuteEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;

public enum EventReceivers {
    @SerializedName("a")
    GroupMessageEvent(GroupMessageEvent.class),
    @SerializedName("b")
    FriendMessageEvent(FriendMessageEvent.class),
    @SerializedName("c")
    MessageRecallEvent_GroupRecall(MessageRecallEvent.GroupRecall.class),
    @SerializedName("d")
    BotLeaveEvent(BotLeaveEvent.class),
    @SerializedName("e")
    BotMuteEvent(BotMuteEvent.class),
    @SerializedName("f")
    MemberJoinEvent(MemberJoinEvent.class),
    @SerializedName("g")
    MemberLeaveEvent(MemberLeaveEvent.class);

    private Class<? extends Event> cls;

    public Class<? extends Event> value(){
        return cls;
    }

    private EventReceivers(Class<? extends Event> cls){
        this.cls = cls;  
    }

    public static <T extends Event> EventReceivers get(Class<T> cls){
        for(EventReceivers er : values()){
            if(er.cls == cls){
                return er;
            }
        }
        return null;
    }

    public static EventReceivers get(String s){
        for(EventReceivers er : values()){
            if(er.toString().equals(s)){
                return er;
            }
        }
        return null;
    }
}
