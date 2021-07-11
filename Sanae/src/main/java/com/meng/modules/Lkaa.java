package com.meng.modules;

import com.meng.tools.FileTool;
import com.meng.tools.Hash;
import com.meng.tools.Network;
import com.meng.tools.SJFPathTool;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class Lkaa {
    public static File generalVoice(String text) {
        File voiceFile = SJFPathTool.getTTSPath(Hash.getMd5Instance().calculate(text.getBytes(StandardCharsets.UTF_8)) + ".mp3");
        if (!voiceFile.exists()) {
            byte[] voice = Network.httpGetRaw(Network.httpGet("http://lkaa.top/API/yuyin/api.php?msg=" + text + "&type=text"));
            FileTool.saveFile(voiceFile, voice);             
        }
        return voiceFile;
    }

    public static String generalTranslate(String text) {
        return Network.httpGet("http://lkaa.top/API/qqfy/api.php?msg=" + text + "&type=male");
    }
}
