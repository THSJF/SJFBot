package com.meng.SJFInterfaces;

import java.lang.reflect.*;
import com.meng.BotWrapper;

public interface IPersistentData {
    public BotWrapper getWrapper();
	public String getPersistentName();
	public Type getDataType();
	public Object getDataBean();
	public void setDataBean(Object o);
}
