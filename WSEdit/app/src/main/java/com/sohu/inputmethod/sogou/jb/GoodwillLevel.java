package com.sohu.inputmethod.sogou.jb;

public enum GoodwillLevel {
    ALL("十六夜"),
    EASY("新月级"),
    NORMAL("三日月级"),
    HARD("半月级"),
    LUNATIC("满月级"),
    EXTRA("暗月级");

    private String note = null;

    private GoodwillLevel(String s){
        note = s;   
    }

    public static GoodwillLevel getLevel(int i){
        if(i == -1){
            return ALL;
        }
        if(i >= 0 && i < 1000){
            return EASY;
        }
        if(i >= 1000 && i < 3000){
            return NORMAL;
        }
        if(i >= 3000 && i < 6000){
            return HARD;
        }
        if(i >= 6000 && i < 9961){
            return LUNATIC;
        }
        if(i >= 9961){
            return EXTRA;
        }
        throw new IllegalArgumentException();
    }
}
