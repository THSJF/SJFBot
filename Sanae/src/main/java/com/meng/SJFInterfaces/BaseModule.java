package com.meng.SJFInterfaces;

import com.meng.adapter.BotWrapperEntity;

/**
 * @author: 司徒灵羽
 **/
 
public abstract class BaseModule {
	
	private String moduleName = null;
    public BotWrapperEntity entity;

    public BaseModule(BotWrapperEntity bw){
        entity = bw;
    }
    
	public final String getModuleName() {
		if (moduleName == null) {
			moduleName = getClass().getName();
		}
		return moduleName;
	}
	public abstract BaseModule load();
}