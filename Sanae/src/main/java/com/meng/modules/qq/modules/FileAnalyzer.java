package com.meng.modules.qq.modules;

import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.touhou.thsss.replay.ThsssReplay;
import com.meng.modules.touhou.thsss.replay.ThsssReplayAnalyzer;
import com.meng.modules.touhou.zun.replay.ReplayDecoder;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
import com.meng.tools.SJFPathTool;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.utils.RemoteFile;

public class FileAnalyzer extends BaseModule implements IGroupMessageEvent {
    public FileAnalyzer(SBot bot) {
        super(bot);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        FileMessage msg = event.getMessage().get(FileMessage.Key);
        if (msg == null) {
            return false;
        }
        RemoteFile rfile = msg.toRemoteFile(event.getGroup());
        if (rfile.getName().endsWith(".rpy")) {
            File touhouReplay = SJFPathTool.getReplayPath(System.currentTimeMillis() + rfile.getDownloadInfo().getFilename());
            byte[] fileBytes = Network.httpGetRaw(rfile.getDownloadInfo().getUrl());
            FileTool.saveFile(touhouReplay, fileBytes);
            onRpyFile(event.getGroup(), touhouReplay);
        }
        return false;
    }

    private void onRpyFile(Group group, File touhouReplay) {
        try {
            FileFormat.Content fc = FileFormat.getFileType(touhouReplay);
            if (fc.describe == FileFormat.FileType.rpy_thsss_replay) {
                ThsssReplay rpy = new ThsssReplay().load(touhouReplay);
                sendMessage(group, rpy.toString());
                sendMessage(group, entity.toGroupFile(group, ThsssReplayAnalyzer.analyze(rpy).toString().getBytes(StandardCharsets.UTF_8), "/thsss_replay_keys.txt"));
            } else {
                sendMessage(group, new ReplayDecoder().read(touhouReplay).toString());
            }
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
    }
}
