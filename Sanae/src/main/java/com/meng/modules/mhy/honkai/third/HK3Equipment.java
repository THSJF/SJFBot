package com.meng.modules.mhy.honkai.third;

public class HK3Equipment {

    private CharaName name;
    private String equipmentName;
    private String baseEquipment = null;
    private ArmType armType;
    private Attack attackType;

    private boolean isAwaken = false;//觉醒
    private boolean isShift = false;//增幅
    private boolean isSp = false;//sp

    public HK3Equipment(CharaName name, String equipmentName, ArmType equipmentType, Attack attackType) {
        this.name = name;
        this.equipmentName = equipmentName;
        this.armType = equipmentType;
        this.attackType = attackType;
    }

    public HK3Equipment(CharaName name, String equipmentName, String baseEquipment, ArmType equipmentType, Attack attackType) {
        this(name, equipmentName, equipmentType, attackType);
        this.baseEquipment = baseEquipment;
    }

    public CharaName getCharaName() {
        return name;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getBaseEquipment() {
        return baseEquipment;
    }

    public ArmType getArmType() {
        return armType;
    }

    public Attack getAttackType() {
        return attackType;
    }

    public boolean isAwaken() {
        return isAwaken;
    }

    public boolean isShift() {
        return isShift;
    }

    public boolean isSp() {
        return isSp;
    }

    public HK3Equipment setAwaken() {
        isAwaken = true;
        return this;
    }

    public HK3Equipment setShift() {
        isShift = true;
        return this;
    }

    public HK3Equipment setSp() {
        isSp = true;
        return this;
    }
}
