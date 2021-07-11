package com.meng.modules.qq.modules;

import com.meng.bot.Functions;
import com.meng.config.CommandDescribe;
import com.meng.config.ConfigManager;
import com.meng.help.HelpGenerator;
import com.meng.help.Permission;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.TextLexer;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @author: 司徒灵羽
 **/
public class NumberProcess extends BaseModule implements IGroupMessageEvent {

    public NumberProcess(SBot bwe) {
        super(bwe);
    }

    @Override
    public String getModuleName() {
        return "数字计算";
    }

    @Override
    public BaseModule load() {
        HelpGenerator.Item mainMenu = HelpGenerator.getInstance().newItem(Permission.Normal, getModuleName());
        mainMenu.arg(".int").arg("int数字").arg("+/-/*///>>/>>>/<</^/%/|/&").arg("int数字").arg("计算结果");
        return super.load();
    }

    @Override
    @CommandDescribe(cmd = ".int 表达式", note = "int数字计算")
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
        String next = iter.next();
        if (next.equals("int")) {
            try {
                String firstArg = iter.next();
                if (firstArg.equals("~")) {
                    sendGroupMessage(groupId, "result:" + (~Integer.parseInt(iter.next()))); 
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
                sendGroupMessage(groupId, resu);
            } catch (Exception e) {
                sendGroupMessage(groupId, e.toString());
            }
            return true;
        } else if (next.equals("homo")) {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("javascript");
            try {
                FileReader fr = new FileReader(SBot.appDirectory + "/js/homo.js");
                engine.eval(fr);
                if (engine instanceof Invocable) {
                    Invocable in = (Invocable) engine;
                    System.out.println(in.invokeFunction("homo", Long.parseLong(iter.next())));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
