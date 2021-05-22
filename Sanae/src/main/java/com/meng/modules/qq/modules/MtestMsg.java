package com.meng.modules.qq.modules;

import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.touhou.THGameDataManager;
import com.meng.tools.ExceptionCatcher;
import java.io.File;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.Voice;
import com.meng.modules.CmdExecuter;

/**
 * @author: 司徒灵羽
 **/

public class MtestMsg extends BaseModule implements IGroupMessageEvent {

    private CmdExecuter ce;
    int step=0;
    @Override
    public String getModuleName() {
        return "测试模块";
    }

    public MtestMsg(SBot bw) {
        super(bw);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        if(gme.getMessage().contentToString().equals("threp")){
            ce = CmdExecuter.execute("from threp import THReplay", new CmdExecuter.OnOutputListener(){

                    @Override
                    public void onOutput(String output) {
                        System.out.println(output);
                        step++;
                        switch(step){
                            case 1:
                                ce.write("tr=THReplay('th7_03.rpy')");
                                break;
                                case 2:
                               ce.write("print(tr.getBaseInfo())");
                               break;
                        }
                    }
            });
         }
        return false;
    }

    private void processText(GroupMessageEvent event) {
        try {
            File fileMp3 = new File(SBot.appDirectory + "/tts/此生无悔入东方_来世愿生幻想乡.mp3");
            Voice ptt = entity.toVoice(fileMp3, event.getGroup());
            if (ptt == null) {
                sendQuote(event, "生成失败");
            }
            sendQuote(event, ptt);
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            sendQuote(event, e.toString());
        }
    }

    @Override
    public BaseModule load() {
        return this;
    }
}
