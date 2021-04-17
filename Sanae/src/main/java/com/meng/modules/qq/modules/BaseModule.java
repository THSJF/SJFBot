package com.meng.modules.qq.modules;

import com.meng.config.DataPersistenter;
import com.meng.modules.qq.SBot;

/**
 * @author: 司徒灵羽
 **/
public abstract class BaseModule {

    public SBot entity;

    public BaseModule(SBot bw) {
        entity = bw;
    }

    public final void save(){
        DataPersistenter.save(this);  
    }

    public abstract BaseModule load();
    public abstract String getModuleName();
}
