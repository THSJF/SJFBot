package com.meng.modules.qq.modules;

import com.meng.Functions;
import com.meng.config.ConfigManager;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.TextLexer;
import java.util.ArrayList;
import java.util.Iterator;
import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @author: 司徒灵羽
 **/
public class NumberProcess extends BaseModule implements IGroupMessageEvent {

    public NumberProcess(SBot bwe) {
        super(bwe);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        long groupId = gme.getGroup().getId();
        if (!ConfigManager.getInstance().isFunctionEnabled(gme.getGroup(), Functions.NumberProcess)) {
            return false;
        }
        String msg = gme.getMessage().contentToString();
        if (msg.charAt(0) != '.') {
            return false;
        }
        ArrayList<String> list = TextLexer.analyze(msg);
        Iterator<String> iter = list.iterator();
        iter.next();//.
        if (iter.next().equals("int")) {
            try {
                String firstArg = iter.next();
                if (firstArg.equals("~")) {
                    entity.sendGroupMessage(groupId, "result:" + (~Integer.parseInt(iter.next()))); 
                    return true;
                }
                int a1 = Integer.parseInt(firstArg);
                String op = iter.next();
                int a2 = Integer.parseInt(iter.next());
                String resu = "failed";
                switch (op) {
                    case "+":
                        resu = "result:" + (a1 + a2);
                        break;
                    case "-":
                        resu = "result:" + (a1 - a2);
                        break;
                    case "*":
                        resu = "result:" + (a1 * a2);
                        break;
                    case "/":
                        resu = "result:" + (a1 / a2);
                        break;
                    case ">>":
                        resu = "result:" + (a1 >> a2);
                        break;
                    case ">>>":
                        resu = "result:" + (a1 >>> a2);
                        break;
                    case "<<":
                        resu = "result:" + (a1 << a2);
                        break;
                    case "^":
                        resu = "result:" + (a1 ^ a2);
                        break;
                    case "%":
                        resu = "result:" + (a1 % a2);
                        break;
                    case "|":
                        resu = "result:" + (a1 | a2);
                        break;
                    case "&":
                        resu = "result:" + (a1 & a2);
                        break;
                }
                entity.sendGroupMessage(groupId, resu);
            } catch (Exception e) {
                entity.sendGroupMessage(groupId, e.toString());
            }
            return true;
        }
        return false;
    }
    
    @Override
    public NumberProcess load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return Functions.NumberProcess.toString();
    }
}
