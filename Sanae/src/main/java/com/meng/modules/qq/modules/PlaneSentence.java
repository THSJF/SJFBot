package com.meng.modules.qq.modules;

import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.qq.handler.friend.IFriendMessageEvent;
import com.meng.modules.qq.SBot;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import java.util.Map;
import java.util.HashMap;
import com.meng.config.ConfigManager;
import com.meng.config.qq.GroupConfig;
import java.io.File;
import com.meng.tools.SJFPathTool;
import net.mamoe.mirai.message.data.Image;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
import com.meng.tools.SJFRandom;
import net.mamoe.mirai.message.data.Message;

public class PlaneSentence extends BaseModule implements IGroupMessageEvent {

    private Map<Long,Integer> steps = new HashMap<>();
    private Map<Long,String> name = new HashMap<>();
    public PlaneSentence(SBot b) {
        super(b);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        ConfigManager manager = ConfigManager.getInstance();
        GroupConfig config = manager.getGroupConfig(event.getGroup().getId());
        if (config == null || !config.planeSentence) {
            return false;  
        }
        String msg = event.getMessage().contentToString();
        if (!msg.startsWith("添加") && msg.endsWith("乐子图")) {
            String key = msg.substring(0, msg.length() - 3);
            File[] fs = SJFPathTool.getPlaneSentencePath(key).listFiles();
            sendMessage(event, entity.toImage(SJFRandom.randomSelect(fs), event.getGroup()));
            return true;
        }
        long qq = event.getSender().getId();
        if (!manager.isAdminPermission(qq)) {
            return false; 
        }
        if (msg.equals("添加乐子图")) {
            steps.put(qq, 0);
        } else if (msg.equals("取消添加")) {
            steps.remove(qq);
        }
        if (steps.get(qq) == null) {
            return false;
        }
        switch (steps.get(qq)) {
            case 0:
                sendQuote(event, "发送人名或\"其他\"以选择是谁的图");
                steps.put(qq, 1);
                File[] flist = SJFPathTool.getPlaneSentencePath("").listFiles();
                StringBuilder builder = new StringBuilder();
                for (File f : flist) {
                    builder.append(f.getName()).append(" ");
                }
                builder.setLength(builder.length() - 1);
                sendQuote(event, builder.toString());
                return true;
            case 1:
                String uname = event.getMessage().contentToString();
                sendQuote(event, "发送图片为" + uname + "添加图片,或发送取消添加以退出");
                steps.put(qq, 2);
                name.put(qq, uname);
                return true;
            case 2:
                int leng = 0;
                for (Message message : event.getMessage()) {
                    if (message instanceof Image) {
                        byte[] img = Network.httpGetRaw(entity.getUrl(((Image)message)));
                        FileTool.saveFile(SJFPathTool.getPlaneSentencePath(name.get(qq) + "/" + FileTool.getAutoFileName(img)), img);
                        leng += img.length;
                    }
                }
                name.remove(qq);
                steps.remove(qq);
                sendQuote(event, "保存完成(" + (leng / 1024) + "KB)");
                return true;
        }
        return false;
    }
}
