package com.sohu.inputmethod.sogou.jb;
import java.util.NoSuchElementException;

public enum ItemType {
    TXT(1),
        IMG(2),
        QID(3),
        QNAME(4),
        GID(5),
        GNAME(6),
        RAN_INT(7),
        RAN_FLOAT(8),
        HASH_RAN_INT(9),
        HASH_RAN_FLOAT(10),
        IMG_FOLDER(11);

    public static ItemType valueOf(int v){
        for(ItemType it : values()){
            if(it.v == v){
                return it;
            }
        } 
      throw new NoSuchElementException();
    }
    
    private ItemType(int v){
        this.v = v;
    }

    private int v;

    public int value(){
        return v;
    }
}
