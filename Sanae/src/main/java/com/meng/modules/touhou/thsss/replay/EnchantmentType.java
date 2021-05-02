package com.meng.modules.touhou.thsss.replay;

public enum EnchantmentType {
    None(0),
    Red(1),
    Blue(2),
    Green(3);

    private int value = -1;

    private EnchantmentType(int v){
        value = v;
    }

    public static EnchantmentType valueOf(int i){
        switch(i){
            case 0:
                return None;
            case 1:
                return Red;
            case 2:
                return Blue;
            case 3:
                return Green;
            default:
                return null;
        }
    }
}
