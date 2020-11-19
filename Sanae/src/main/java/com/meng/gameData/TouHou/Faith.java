package com.meng.gameData.TouHou;

import com.meng.SBot;
import com.meng.annotation.SanaeData;
import com.meng.config.DataPersistenter;
import com.meng.modules.BaseModule;
import java.util.HashMap;

/**
 * @author: 司徒灵羽
 **/
public class Faith extends BaseModule {

    @SanaeData("faith.json")
    private HashMap<Long, Integer> faithMap = new HashMap<>();

    public Faith(SBot sb){
        super(sb);
    }
    
    public Faith(){
        super(null);
    }
    
    @Override
    public String getModuleName() {
        return "faith";
    }

    public Faith load() {
        DataPersistenter.read(this);
        return this;
    }

    public void addFaith(long fromQQ, int faith) {
        if (faithMap.get(fromQQ) != null) {
            int qqFaith = faithMap.get(fromQQ);
            qqFaith += faith;
            faithMap.put(fromQQ, qqFaith);
        } else {
            faithMap.put(fromQQ, faith);
        }
        save();
    }

    public int getFaith(long fromQQ) {
        if (faithMap.get(fromQQ) == null) {
            return 0;
        }
        return faithMap.get(fromQQ);
    }
}

