package com.meng.modules.qq.modules;
import com.meng.config.CommandDescribe;
import com.meng.config.ConfigManager;
import com.meng.config.Person;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public class PersonalConfig extends BaseModule implements IGroupMessageEvent {

    public PersonalConfig(SBot bot) {
        super(bot);
    }

    @Override
    @CommandDescribe(cmd = "-", note = "个人设置")
    public boolean onGroupMessage(GroupMessageEvent event) {
        long qq = event.getSender().getId();
        String msg = event.getMessage().contentToString();
        ConfigManager cm = ConfigManager.getInstance();
        Person.Config pc = cm.getPersonConfig(qq);
        switch (msg) {
            case ".bot on":
                pc.setBotOn(true);
                entity.sendQuote(event, "已启用对你的响应");
                cm.save();
                break;
            case ".bot off":
                pc.setBotOn(false);
                entity.sendQuote(event, "已停用对你的响应");
                cm.save();
                break;
            case ".version jrrp":
                pc.setJrrpNewStyle(pc.isJrrpNewStyle() ? false: true);
                sendQuote(event, "已切换风格");
                cm.save();
                break;
            default:
                return false;
        }
        return true;
    }
}
