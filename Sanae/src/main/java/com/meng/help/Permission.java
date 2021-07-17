package com.meng.help;

import com.google.gson.annotations.SerializedName;

public enum Permission {
    @SerializedName("n") Normal(0),
    @SerializedName("a") Admin(2),
    @SerializedName("m") Master(3),
    @SerializedName("o") Owner(4);

    private int v;

    private Permission(int i){
        v = i; 
    }

    public int value(){
        return v;
    }
}
