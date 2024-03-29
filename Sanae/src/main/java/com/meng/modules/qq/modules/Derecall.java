package com.meng.modules.qq.modules;

import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.MessageManager;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.qq.handler.group.IGroupRecallEvent;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.Hash;
import com.meng.tools.Network;
import com.meng.tools.SJFPathTool;
import java.io.File;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

public class Derecall extends BaseModule implements IGroupMessageEvent,IGroupRecallEvent {

    public Derecall(SBot bot) {
        super(bot);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        return false;
    }

    @Override
    public boolean onGroupRecall(MessageRecallEvent.GroupRecall event) {
        sendGroupMessage(event.getGroup().getId(), new PlainText(String.valueOf(event.getOperator().getId())).plus("撤回了:"));
        sendGroupMessage(event.getGroup().getId(), MessageManager.get(event).getOriginalMessage());
        MessageChain chain = MessageManager.get(event.getMessageIds()).getOriginalMessage();
        Image img = chain.get(Image.Key);
        if (img != null) {
            String url = entity.getUrl(img);
            try {
                byte[] fileBytes = Network.httpGetRaw(url);
                File file = SJFPathTool.getRecallPath(Hash.getMd5Instance().calculate(fileBytes) + "." + FileFormat.getFileType(fileBytes));
                FileTool.saveFile(file, fileBytes);
            } catch (Exception e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
        }
        FlashImage fi = chain.get(FlashImage.Key);
        if (fi != null) {
            String url = entity.getUrl(fi.getImage());
            try {
                byte[] fileBytes = Network.httpGetRaw(url);
                File file =SJFPathTool.getFlashImagePath(Hash.getMd5Instance().calculate(fileBytes) + "." + FileFormat.getFileType(fileBytes));
                FileTool.saveFile(file, fileBytes);
            } catch (Exception e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
        }
        return false;
    }

}
