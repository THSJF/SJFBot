package com.meng.modules.touhou.thsss.replay;

public enum DifficultLevel {
    Easy(0),
    Normal(1),
    Hard(2),
    Lunatic(3),
    Ultra(4),
    Extra(5);

    private int value = -1;

    public DifficultLevel(int v){
        value = v;
    }

    public static DifficultLevel valueOf(int i){
        switch(i){
            case 0:
                return Easy;
            case 1:
                return Normal;
            case 2:
                return Hard;
            case 3:
                return Lunatic;
            case 4:
                return Ultra;
            case 5:
                return Extra;
            default:
                return null;
        }
    }
}
