package com.meng.modules.mhy.honkai.third;

public enum ArmType {
    Machin("机械"),
    Biology("生物"),
    Extra("异能"),
    Quantum("量子"),
    Imaginary("虚数");
    
    private ArmType(String name){
        this.name = name;  
    }

    private String name;

    public String value(){
        return name;
    }
}
