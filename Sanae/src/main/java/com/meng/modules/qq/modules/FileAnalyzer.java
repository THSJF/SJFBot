package com.meng.modules.qq.modules;

import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.modules.touhou.thsss.replay.ThsssReplay;
import com.meng.modules.touhou.thsss.replay.ThsssReplayAnalyzer;
import com.meng.modules.touhou.zun.replay.ReplayDecoder;
import com.meng.modules.touhou.zun.replay.ZunReplay;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
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
            File touhouReplay = new File(SBot.appDirectory + "/touhou/replay/" + System.currentTimeMillis() + rfile.getDownloadInfo().getFilename());
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
                ReplayDecoder decoder = new ReplayDecoder(touhouReplay);
                ZunReplay rpy = new ZunReplay();
                switch (fc.describe) {
                    case rpy_th06_replay:
                        decoder.readTh06(rpy);
                        break;
                    case rpy_th07_replay:
                        decoder.readTh07(rpy);
                        break;
                    case rpy_th08_replay:
                        decoder.readTh08(rpy);
                        break;
                    case rpy_th09_replay:
                        decoder.readTh09(rpy);
                        break;
                    case rpy_th09_5_replay:
                        decoder.readTh095(rpy);
                        break;
                    case rpy_th10_replay:
                        decoder.readTh10(rpy);
                        break;
                    case rpy_th11_replay:
                        decoder.readTh11(rpy);
                        break;
                    case rpy_th12_replay:
                        decoder.readTh12(rpy);
                        break;
                    case rpy_th12_5_replay:
                        decoder.readTh125(rpy);
                        break;
                    case rpy_th12_8_replay:
                        decoder.readTh128(rpy);
                        break;
                    case rpy_th13_th14_replay:
                        decoder.readTh13(rpy);
                        break;
                    case rpy_th14_3_replay:
                        decoder.readTh143(rpy);
                        break;
                    case rpy_th15_replay:
                        decoder.readTh15(rpy);
                        break;
                    case rpy_th16_replay:
                        decoder.readTh16(rpy);
                        break;
                    case rpy_th16_5_replay:
                        decoder.readTh165(rpy);
                        break;
                    case rpy_th17_replay:
                        decoder.readTh17(rpy);
                        break;
                    case rpy_th18_replay:
                        decoder.readTh18(rpy);
                        break;
                }
                sendMessage(group, rpy.toString());
            }
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
    }
}
