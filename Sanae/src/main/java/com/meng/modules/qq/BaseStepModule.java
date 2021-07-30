package com.meng.modules.qq;

import com.meng.config.ConfigManager;
import com.meng.config.qq.GroupConfig;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
import com.meng.tools.SJFPathTool;
import com.meng.tools.SJFRandom;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import com.meng.modules.qq.BaseStepModule.StepBean;

public abstract class BaseStepModule extends BaseModule implements IGroupMessageEvent {

    protected Map<Long,StepBean> steps = new HashMap<>();

    public BaseStepModule(SBot b) {
        super(b);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        long qq = event.getSender().getId();
        BaseStepModule.StepBean bean = steps.get(qq);
        if (bean == null) {
            return false;
        }
        return onStep(event, bean, qq);
    }

    protected abstract boolean onStep(GroupMessageEvent event, StepBean bean, long qq);

    public static class StepBean<T> {
        public int step;
        public T extra;

        public StepBean(int step) {
            this.step = step;
        }

        public StepBean(int step, T extra) {
            this(step);
            this.extra = extra;
        }
    }
}
