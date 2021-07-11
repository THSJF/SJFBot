package com.meng.modules.qq.modules;

import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.Hash;
import com.meng.tools.Network;
import com.meng.tools.SJFPathTool;
import java.io.File;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;

public class MessageSaver extends BaseModule implements IGroupMessageEvent {

    public MessageSaver(SBot bot) {
        super(bot);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        FlashImage fi = event.getMessage().get(FlashImage.Key);
        if (fi != null) {
            String url = entity.getUrl(fi.getImage());
            try {
                byte[] fileBytes = Network.httpGetRaw(url);
                File file = SJFPathTool.getFlashImagePath(Hash.getMd5Instance().calculate(fileBytes) + "." + FileFormat.getFileType(fileBytes));
                FileTool.saveFile(file, fileBytes);
            } catch (Exception e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
        }
        return false;
    }
}
