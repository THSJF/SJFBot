package com.meng.SJFInterfaces;

import com.meng.adapter.BotWrapperEntity;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @author: 司徒灵羽
 **/
 
public abstract class BaseGroupModule extends BaseModule implements IGroupMessage {

    public BaseGroupModule(BotWrapperEntity bw){
        super(bw);
    }
    
	@Override
	public abstract boolean onGroupMessage(GroupMessageEvent gme);
}
