package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.tools.TextLexer;
import java.util.ArrayList;
import java.util.Iterator;
import net.mamoe.mirai.message.GroupMessageEvent;

public class MNumberProcess extends BaseGroupModule {

    public MNumberProcess(BotWrapperEntity bwe) {
        super(bwe);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        long groupId = gme.getGroup().getId();
        if (!entity.configManager.getGroupConfig(gme.getGroup().getId()).isNumberProcessEnable()) {
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
                    entity.sjfTx.sendGroupMessage(groupId, "result:" + (~Integer.parseInt(iter.next()))); 
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
                entity.sjfTx.sendGroupMessage(groupId, resu);
            } catch (Exception e) {
                entity.sjfTx.sendGroupMessage(groupId, e.toString());
            }
            return true;
        }
        return false;
    }

    @Override
    public MNumberProcess load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "数字运算";
    }
}
