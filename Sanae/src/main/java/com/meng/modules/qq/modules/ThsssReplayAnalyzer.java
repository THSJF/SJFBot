package com.meng.modules.qq.modules;

import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.utils.RemoteFile;
import com.meng.modules.touhou.thsss.replay.Replay;

public class ThsssReplayAnalyzer extends BaseModule implements IGroupMessageEvent {
    public ThsssReplayAnalyzer(SBot bot) {
        super(bot);
    }

    @Override
    public String getModuleName() {
        return getClass().getSimpleName();
    }

    @Override
    public ThsssReplayAnalyzer load() {
        return this;
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        FileMessage msg = event.getMessage().get(FileMessage.Key);
        if (msg == null) {
            return false;
        }
        RemoteFile rfile = msg.toRemoteFile(event.getGroup());
        if (rfile.getName().endsWith(".rpy")) {
            File replay = new File(SBot.appDirectory + "/thsss/replay/" + new Date().toString() + rfile.getDownloadInfo().getFilename());
            byte[] fileBytes = Network.httpGetRaw(rfile.getDownloadInfo().getUrl());
            FileTool.saveFile(replay, fileBytes);
            try {
                FileFormat.Content fc = FileFormat.getFileType(replay);
                if ("thsss replay".equals(fc.describe)) {
                    Replay rpy = new Replay(replay);
                    sendMessage(event, rpy.toString());
                    return true;
                }
            } catch (IOException e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
        }
        return false;
    }
}
