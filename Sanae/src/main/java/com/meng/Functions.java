package com.meng;

import com.google.gson.annotations.SerializedName;
import com.meng.config.qq.ConfigManager;

public enum Functions {
    @SerializedName("a") GroupMessageEvent("群消息"),
    @SerializedName("b") FriendMessageEvent("好友消息"),
    @SerializedName("c") MessageRecallEvent_GroupRecall("群撤回消息"),
    @SerializedName("d") BotLeaveEvent("bot退群"),
    @SerializedName("e") BotMuteEvent("bot被禁言"),
    @SerializedName("f") MemberJoinEvent("新群成员"),
    @SerializedName("g") MemberLeaveEvent("群员退群"),
    @SerializedName("h") Dice("骰子"),
    @SerializedName("i") DynamicWordStock("词库"),
    @SerializedName("j") GroupCounterChart("群统计"),
    @SerializedName("k") BilibiliVideo("哔哩哔哩视频"),
    @SerializedName("l") Morning("早"),
    @SerializedName("m") NumberProcess("数字计算"),
    @SerializedName("n") QuestionAndAnswer("问答"),
    @SerializedName("o") Repeater("复读机"),
    @SerializedName("p") Report("留言"),
    @SerializedName("q") Copper("铜"),
    @SerializedName("r") CopperFlash("闪照铜"),
    @SerializedName("s") PictureSearch("图片搜索"),
    @SerializedName("t") OCR("文字识别"),
    @SerializedName("u") Porn("色图等级评定"),
    @SerializedName("v") ImageTag("图片标签"),
    @SerializedName("w") TTS("音频合成"),
    @SerializedName("x") EuropeDogs("欧洲狗识别")
    ;

    private Functions(String s){
        this.s = s;
    }

    private String s;

    public String getName(){
        return s;
    }

    public static Functions get(String s){
        for(Functions mds : values()){
            if(mds.getName().equals(s) || mds.toString().equals(s)){
                return mds;
            }
        }
        return null;
    }

    public boolean change(ConfigManager configManager, long groupId) {
        return configManager.setFunctionEnabled(groupId, this, !configManager.isFunctionEnabled(groupId,this));
    }
}
