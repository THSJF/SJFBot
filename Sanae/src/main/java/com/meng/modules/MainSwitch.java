package com.meng.modules;

import com.meng.SJFInterfaces.BaseModule;
import com.meng.adapter.BotWrapperEntity;

public class MainSwitch extends BaseModule {

    public MainSwitch(BotWrapperEntity bwe) {  
        super(bwe);
    }

    @Override
    public MainSwitch load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "mainswitch";
    }  
}
