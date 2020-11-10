package com.meng;

public enum NetOperatType {
    ADD_QA(0),
    SET_QA(1),
    DELETE_QA(2),
    OPERATE_SUCCESS(3),
    OPERATE_FAILED(4),
    GET_QA(5);

    public static NetOperatType valueOf(int i){
        for(NetOperatType not:values()){
            if(not.value == i){
                return not;
            }
        }
        throw new EnumConstantNotPresentException(NetOperatType.class, "value:" + i);
    }

    private int value;

    public int value(){
        return value;
    }
    
    public NetOperatType(int i){
        value = i;
    }
}
