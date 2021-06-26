package com.meng.modules.mhy.honkai.third;

public enum CharaName {
    KianaKaslana("琪亚娜·卡斯兰娜"),
    RaidenMei("雷电芽衣"),
    BronyaZaychik("布洛妮娅·扎伊切克"),
    MurataHimeko("无量塔姬子"),
    TheresaApocalypse("德丽莎·阿波卡利斯"),
    FuHua("符华"),
    RitaRossweisse("丽塔·洛丝薇瑟"),
    YaeSakura("八重樱"),
    KallenKaslana("卡莲·卡斯兰娜"),
    RozaliyaOlenyeva("萝莎莉娅·阿琳"),
    LiliyaOlenyeva("莉莉娅·阿琳"),
    SeeleVollerei("希儿·芙乐艾"),
    Durandal("幽兰黛尔"),
    Asuka("明日香"),
    Fischl("菲谢尔");
    
    private CharaName(String name){
        this.name = name;  
    }
    
    private String name;
    
    public String value(){
        return name;
    }
}
