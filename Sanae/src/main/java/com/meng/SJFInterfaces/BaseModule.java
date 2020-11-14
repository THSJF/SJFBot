package com.meng.SJFInterfaces;

import com.meng.adapter.BotWrapperEntity;

/**
 * @author: 司徒灵羽
 **/

public abstract class BaseModule {

    public BotWrapperEntity entity;

    public BaseModule(BotWrapperEntity bw) {
        entity = bw;
    }

	public abstract BaseModule load();
    public abstract String getModuleName();
}
