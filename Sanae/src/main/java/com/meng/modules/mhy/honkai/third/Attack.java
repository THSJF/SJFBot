package com.meng.modules.mhy.honkai.third;

public enum Attack {
    Fire("火焰元素"),
    Ice("冰冻元素"),
    Thunder("雷电元素"),
    Physics("物理");
    
    private Attack(String name){
        this.name = name;  
    }

    private String name;

    public String value(){
        return name;
    }
}
