package com.meng.SJFInterfaces;
import com.meng.BotWrapper;

public abstract class BaseGroupModule extends BaseModule implements IGroupMessage {

    public BotWrapper wrapper;
    
    public BaseGroupModule(BotWrapper bw){
        wrapper = bw;
    }
    
	@Override
	public abstract boolean onGroupMessage(long fromGroup, long fromQQ, String msg, int msgId);
}
