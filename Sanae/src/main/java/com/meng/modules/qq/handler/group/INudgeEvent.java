package com.meng.modules.qq.handler.group;
import net.mamoe.mirai.event.events.NudgeEvent;

public interface INudgeEvent {
    public boolean onNudge(NudgeEvent event);  
}
