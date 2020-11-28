package com.sohu.inputmethod.sogou.jb;
import java.util.NoSuchElementException;

public enum NodeType {
    TXT(1),
    IMG(2),
    QID(3),
    QNAME(4),
    GID(5),
    GNAME(6),
    RAN_INT(9),
    RAN_FLOAT(10),
    HASH_RAN_INT(11),
    HASH_RAN_FLOAT(12),
    IMG_FOLDER(13);

    public static NodeType valueOf(int v){
        for(NodeType it : values()){
            if(it.v == v){
                return it;
            }
        } 
      throw new NoSuchElementException();
    }
    
    private NodeType(int v){
        this.v = v;
    }

    private int v;

    public int value(){
        return v;
    }
}
