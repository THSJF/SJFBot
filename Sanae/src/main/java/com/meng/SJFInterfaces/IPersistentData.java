package com.meng.SJFInterfaces;

import com.meng.adapter.BotWrapperEntity;
import java.lang.reflect.Type;

/**
 * @author: 司徒灵羽
 **/

public interface IPersistentData {
    public BotWrapperEntity getWrapper();
	public String getPersistentName();
	public Type getDataType();
	public Object getDataBean();
	public void setDataBean(Object o);
}
