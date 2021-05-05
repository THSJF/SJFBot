package com.meng.modules;

import com.meng.gameData.TouHou.zun.TH06GameData;
import com.meng.gameData.TouHou.zun.TH07GameData;
import com.meng.gameData.TouHou.zun.TH08GameData;
import com.meng.gameData.TouHou.zun.TH10GameData;
import com.meng.gameData.TouHou.zun.TH11GameData;
import com.meng.gameData.TouHou.zun.TH12GameData;
import com.meng.gameData.TouHou.zun.TH13GameData;
import com.meng.gameData.TouHou.zun.TH14GameData;
import com.meng.gameData.TouHou.zun.TH15GameData;
import com.meng.gameData.TouHou.zun.TH16GameData;
import com.meng.gameData.TouHou.zun.TH17GameData;
import com.meng.modules.qq.SBot;
import com.meng.tools.FileTool;
import com.meng.tools.Hash;
import com.meng.tools.Network;
import java.io.File;
import java.nio.charset.StandardCharsets;
import com.meng.gameData.TouHou.TouhouCharacter;
import com.meng.gameData.TouHou.THDataHolder;

public class Lkaa {
    public static File generalVoice(String text) {
        File voiceFile = new File(SBot.appDirectory + "tts/" + Hash.getMd5Instance().calculate(text.getBytes(StandardCharsets.UTF_8)) + ".mp3");
        if (!voiceFile.exists()) {
            byte[] voice = Network.httpGetRaw(Network.httpGet("http://lkaa.top/API/yuyin/api.php?msg=" + text + "&type=text"));
            FileTool.saveFile(voiceFile, voice);             
        }
        return voiceFile;
    }

    public static String generalTranslate(String text) {
        if (text.equals(TH06GameData.gameNameCN) || text.equals(TH06GameData.gameNameAbbr)) {
            return TH06GameData.gameName;
        }
        if (text.equals(TH07GameData.gameNameCN) || text.equals(TH07GameData.gameNameAbbr)) {
            return TH07GameData.gameName;
        }
        if (text.equals(TH08GameData.gameNameCN) || text.equals(TH08GameData.gameNameAbbr)) {
            return TH08GameData.gameName;
        }
        if (text.equals(TH10GameData.gameNameCN) || text.equals(TH10GameData.gameNameAbbr)) {
            return TH10GameData.gameName;
        }
        if (text.equals(TH11GameData.gameNameCN) || text.equals(TH11GameData.gameNameAbbr)) {
            return TH11GameData.gameName;
        }
        if (text.equals(TH12GameData.gameNameCN) || text.equals(TH12GameData.gameNameAbbr)) {
            return TH12GameData.gameName;
        }
        if (text.equals(TH13GameData.gameNameCN) || text.equals(TH13GameData.gameNameAbbr)) {
            return TH13GameData.gameName;
        }
        if (text.equals(TH14GameData.gameNameCN) || text.equals(TH14GameData.gameNameAbbr)) {
            return TH14GameData.gameName;
        }
        if (text.equals(TH15GameData.gameNameCN) || text.equals(TH15GameData.gameNameAbbr)) {
            return TH15GameData.gameName;
        }
        if (text.equals(TH16GameData.gameNameCN) || text.equals(TH16GameData.gameNameAbbr)) {
            return TH16GameData.gameName;
        }
        if (text.equals(TH17GameData.gameNameCN) || text.equals(TH17GameData.gameNameAbbr)) {
            return TH17GameData.gameName;
        }
        for (TouhouCharacter[] tcs : THDataHolder.name) {
            for (TouhouCharacter tc : tcs) {
                if (text.equals(tc.nick)) {
                    return tc.charaName;
                }
            }
        }
        return Network.httpGet("http://lkaa.top/API/qqfy/api.php?msg=" + text + "&type=male");
    }
}
