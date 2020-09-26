package com.meng.SJFInterfaces;

import com.meng.adapter.BotWrapperEntity;

public abstract class BaseGroupModule extends BaseModule implements IGroupMessage {

    public BaseGroupModule(BotWrapperEntity bw){
        super(bw);
    }
    
	@Override
	public abstract boolean onGroupMessage(long fromGroup, long fromQQ, String msg, int msgId);
}
