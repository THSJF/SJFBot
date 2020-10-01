package com.meng.modules;
import com.meng.SJFInterfaces.BaseGroupModule;
import net.mamoe.mirai.message.GroupMessageEvent;
import com.meng.SJFInterfaces.BaseModule;
import com.meng.adapter.BotWrapperEntity;

public class MtestMsg extends BaseGroupModule {
    public MtestMsg(BotWrapperEntity bw) {
        super(bw);
    }
    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        if (gme.getMessage().contentToString().equals(".atme2")) {
            gme.getGroup().sendMessage(entity.at(gme.getGroup().getId(), gme.getSender().getId()));
        }
        return false;
    }

    @Override
    public BaseModule load() {
        return this;
    }

}
