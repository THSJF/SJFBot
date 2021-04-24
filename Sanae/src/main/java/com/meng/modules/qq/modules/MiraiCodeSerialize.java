package com.meng.modules.qq.modules;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import java.util.HashSet;
import net.mamoe.mirai.message.code.MiraiCode;

public class MiraiCodeSerialize extends BaseModule implements IGroupMessageEvent {

    private HashSet<Long> ready = new HashSet<>();

    public MiraiCodeSerialize(SBot s) {
        super(s);
    }
    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        String msg = event.getMessage().toString();
        long qq = event.getSender().getId();
        if (msg.equalsIgnoreCase("rcode")) {
            ready.add(qq);
        } else if (ready.contains(qq)) {
            entity.sendMessage(event.getGroup(), event.getMessage().serializeToMiraiCode());
        } else if (msg.startsWith("tcode ")) {
            entity.sendMessage(event.getGroup(), MiraiCode.deserializeMiraiCode(msg.substring(6)));
        }
        return false;
    }

    @Override
    public MiraiCodeSerialize load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "MiraiCodeSerialize";
    }

}
