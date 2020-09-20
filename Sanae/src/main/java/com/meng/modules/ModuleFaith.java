package com.meng.modules;

import com.google.gson.reflect.*;
import com.meng.*;
import com.meng.SJFInterfaces.*;
import com.meng.config.*;
import com.meng.tools.override.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author 司徒灵羽
 */

public class ModuleFaith extends BaseGroupModule implements IPersistentData {

	private HashMap<Long, Integer> faithMap = new HashMap<>();
	
    public ModuleFaith(BotWrapper bw){
        super(bw);
    }
    
	@Override
	public ModuleFaith load() {
		DataPersistenter.read(this);
		return this;
	}

	@Override
	public boolean onGroupMessage(long fromGroup, long fromQQ, String msg, int msgId) {
		
		return false;
	}

	public void addFaith(long fromQQ, int faith) {
		if (faith < 0) {
			return;
		}
		if (faithMap.get(fromQQ) != null) {
			int qqFaith=faithMap.get(fromQQ);
			qqFaith += faith;
			faithMap.put(fromQQ, qqFaith);
		} else {
			faithMap.put(fromQQ, faith);
		}
		saveData();
	}

	public boolean subFaith(long fromQQ, int faith) {
		if (faith < 0) {
			return false;
		}
		if (faithMap.get(fromQQ) != null) {
			int qqFaith=faithMap.get(fromQQ);
			if (qqFaith < faith) {
				return false;
			}
			qqFaith -= faith;
			faithMap.put(fromQQ, qqFaith);
			saveData();
			return true;
		}
		return false;
	}

	public int getFaith(long fromQQ) {
		if (faithMap.get(fromQQ) == null) {
			return 0;
		}
		return faithMap.get(fromQQ);
	}

	private void saveData() {
		DataPersistenter.save(this);
	}
	
	@Override
	public String getPersistentName() {
		return "faith.json";
	}

	@Override
	public Type getDataType() {
		return new TypeToken<HashMap<Long, Integer>>(){}.getType();
	}

	@Override
	public Object getDataBean() {
		return faithMap;
	}

    @Override
    public BotWrapper getWrapper() {
        return wrapper;
    }

	@Override
	public void setDataBean(Object o) {
		faithMap = (HashMap) o;
	}
}

