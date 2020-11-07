package com.meng.gameData.TouHou;

import com.meng.annotation.SanaeData;
import com.meng.config.DataPersistenter;
import java.util.HashMap;

/**
 * @Description: 信仰
 * @author: 司徒灵羽
 **/

public class Faith {

    @SanaeData("faith.json")
    private HashMap<Long, Integer> faithMap = new HashMap<>();

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

    private void save() {
        DataPersistenter.save(this);
    }
}

