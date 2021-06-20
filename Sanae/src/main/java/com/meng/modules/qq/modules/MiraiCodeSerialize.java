package com.meng.modules.qq.modules;
import com.meng.config.CommandDescribe;
import com.meng.help.HelpGenerator;
import com.meng.help.Permission;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import java.util.HashSet;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;

public class MiraiCodeSerialize extends BaseModule implements IGroupMessageEvent {

    private HashSet<Long> ready = new HashSet<>();

    public MiraiCodeSerialize(SBot s) {
        super(s);
    }

    @Override
    public String getModuleName() {
        return "mirai码";
    }

    @Override
    public BaseModule load() {
        HelpGenerator.Item mainMenu = HelpGenerator.getInstance().newItem(Permission.Normal, getModuleName());
        mainMenu.arg("rcode").arg("QQ卡片").arg("获得mirai码");
        mainMenu.arg("tcode").arg("mirai码").arg("获得卡片");      
        return super.load();
    }
    
    @Override
    @CommandDescribe(cmd = "rcode/tcode mirai码", note = "收发mirai码")
    public boolean onGroupMessage(GroupMessageEvent event) {
        String msg = event.getMessage().contentToString();
        long qq = event.getSender().getId();
        if (msg.equalsIgnoreCase("rcode")) {
            ready.add(qq);
            sendQuote(event, "等待中……");
            return true;
        } else if (ready.contains(qq)) {
            sendMessage(event.getGroup(), event.getMessage().serializeToMiraiCode());
            ready.remove(qq);
            return true;
        } else if (msg.startsWith("tcode ")) {
            sendMessage(event.getGroup(), MiraiCode.deserializeMiraiCode(event.getMessage().get(1).toString().substring(6)));
            return true;
        }
        return false;
    }
}
