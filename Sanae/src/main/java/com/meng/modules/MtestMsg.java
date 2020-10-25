package com.meng.modules;
import com.meng.SJFInterfaces.BaseGroupModule;
import net.mamoe.mirai.message.GroupMessageEvent;
import com.meng.SJFInterfaces.BaseModule;
import com.meng.adapter.BotWrapperEntity;

public class MtestMsg extends BaseGroupModule {

    @Override
    public String getModuleName() {
        return "测试模块";
    }

    public MtestMsg(BotWrapperEntity bw) {
        super(bw);
    }
    
    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        if (gme.getMessage().contentToString().equals("emoji")) {
            gme.getGroup().sendMessage("🐴");
        }
        return false;
    }

    @Override
    public BaseModule load() {
        return this;
    }

}
