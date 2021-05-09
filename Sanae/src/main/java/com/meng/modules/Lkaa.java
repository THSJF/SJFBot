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
import com.meng.modules.touhou.THGameData;
import com.meng.modules.touhou.THMusic;
import com.meng.modules.touhou.THSpell;
import com.meng.modules.touhou.THCharacter;

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
        THGameData[] thGameData = THGameData.getThGameData();
        for (THGameData game : thGameData) {
            if (text.equals(game.getNameCN())) {
                return game.getNameEng(); 
            }
            if (text.equals(game.getNameEng())) {
                return game.getNameCN(); 
            }
            if (text.equals(game.getNameAbbr())) {
                return game.getNameFull(); 
            }
            for (THMusic mu : game.getMusics()) {
                String muCN = mu.getNameCN();
                String muEng = mu.getNameEng();
                if (muCN == null || muEng == null) {
                    continue;
                }
                if (text.equals(muCN)) {
                    return muEng;
                }
                if (text.equals(muEng)) {
                    return muCN;
                }
            }
            for(THSpell spell : game.getSpellCards()){
                if(text.equals(spell.cnName)){
                    return spell.jpName;
                }
                if(text.equals(spell.jpName)){
                    return spell.cnName;
                }
                for(String nick : spell.nick){
                    if(text.equals(nick)){
                        return spell.jpName;
                    }
                }
            }
            StringBuilder nickBuilder = new StringBuilder();
            for(THCharacter chara : game.getCharacters()){
                if(text.equals(chara.name)){
                    nickBuilder.append(chara.nick).append(" ");
                }
                if(text.equals(chara.nick)){
                    return chara.name;
                }
            }
            if(nickBuilder.length()>0){
                return nickBuilder.toString();
            }
        }
        return Network.httpGet("http://lkaa.top/API/qqfy/api.php?msg=" + text + "&type=male");
    }
}
