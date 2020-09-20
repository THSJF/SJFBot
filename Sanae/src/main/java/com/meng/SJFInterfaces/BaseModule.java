package com.meng.SJFInterfaces;

public abstract class BaseModule {
	
	private String moduleName = null;

	public final String getModuleName() {
		if (moduleName == null) {
			moduleName = getClass().getName();
		}
		return moduleName;
	}
	public abstract BaseModule load();
}
