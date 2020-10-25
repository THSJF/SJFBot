package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import net.mamoe.mirai.message.GroupMessageEvent;
import com.meng.SJFInterfaces.BaseModule;
import com.meng.adapter.BotWrapperEntity;
import java.util.ArrayList;
import com.meng.tools.TextLexer;
import java.util.Iterator;
import com.meng.SJFInterfaces.IHelpMessage;

public class Help extends BaseGroupModule {

    public Help(BotWrapperEntity bwe) {
        super(bwe);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        String msg = gme.getMessage().contentToString();
        long group = gme.getGroup().getId();
        if (!msg.startsWith(".help")) {
            return false;
        }
        if (msg.equals(".help")) {
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<Object> all = entity.moduleManager.getAllModules();
            for (Object o:all) {
                if (o instanceof IHelpMessage) {
                    stringBuilder.append(((IHelpMessage)o).getHelp(null, Object.class)).append("\n");
                }
            }
            entity.sjfTx.sendGroupMessage(group, stringBuilder.toString());
            return true;  
        }
        ArrayList<String> arrayList = TextLexer.analyze(msg);
        if (arrayList.size() != 3) {
            entity.sjfTx.sendGroupMessage(group, "参数错误");
            return true;
        }
        Iterator<String> iter = arrayList.iterator();
        iter.next();//.
        iter.next();//help
        ArrayList<Object> all = entity.moduleManager.getAllModules();
        for (Object o:all) {
            if (o instanceof BaseGroupModule) {
                BaseGroupModule bgm = (BaseGroupModule) o;
                if (bgm.getModuleName().equals(iter.next())) {
                    if (bgm instanceof IHelpMessage) {
                        entity.sjfTx.sendGroupMessage(group, ((IHelpMessage) bgm).getHelp(null, bgm.getClass()));
                        return true;
                    } else {
                        entity.sjfTx.sendGroupMessage(group, "无帮助信息");
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Help load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "Help";
    }
}
