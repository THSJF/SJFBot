package com.meng.modules.qq.modules;
import com.meng.config.Person;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import com.meng.config.ConfigManager;

public class PersonalConfig extends BaseModule implements IGroupMessageEvent {

    public PersonalConfig(SBot bot) {
        super(bot);
    }

    @Override
    public PersonalConfig load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        long qq = event.getSender().getId();
        String msg = event.getMessage().contentToString();
        ConfigManager cm = ConfigManager.getInstance();
        Person pc = cm.getPersonFromQQ(qq);
        switch (msg) {
            case ".bot on":
                pc.setBotOn(true);
                entity.sendQuote(event, "已启用对你的响应");
                break;
            case ".bot off":
                pc.setBotOn(false);
                entity.sendQuote(event, "已停用对你的响应");
                break;
            case ".switch jrrp":
                pc.setJrrpNewStyle(pc.isJrrpNewStyle() ? false: true);
                sendQuote(event, "已切换风格");
                break;
            default:
                return false;
        }
        cm.save();
        return true;
    }
}
