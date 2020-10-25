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
    
	public final String getModuleClassName() {
		if (moduleName == null) {
			moduleName = getClass().getName();
		}
		return moduleName;
	}
	public abstract BaseModule load();
    public abstract String getModuleName();
}
