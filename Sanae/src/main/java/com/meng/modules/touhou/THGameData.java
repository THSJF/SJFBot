package com.meng.modules.touhou;
import com.meng.modules.touhou.gameData.TH06Data;
import com.meng.modules.touhou.gameData.TH07Data;
import com.meng.modules.touhou.gameData.TH08Data;
import com.meng.modules.touhou.gameData.TH10Data;
import com.meng.modules.touhou.gameData.TH11Data;
import com.meng.modules.touhou.gameData.TH12Data;
import com.meng.modules.touhou.gameData.TH13Data;
import com.meng.modules.touhou.gameData.TH14Data;
import com.meng.modules.touhou.gameData.TH15Data;
import com.meng.modules.touhou.gameData.TH16Data;
import com.meng.modules.touhou.gameData.TH17Data;
import com.meng.modules.touhou.gameData.TH18Data;

public abstract class THGameData {

    protected THCharacter[] charas;
    protected THMusic[] music;
    protected THSpell[] spells;
    protected THPlayer[] players;

    private static THGameData[] thGameData = null;
    
    public static THGameData[] getThGameData(){
        if(thGameData == null){
            thGameData = new THGameData[]{
                TH06Data.getInstance(),
                TH07Data.getInstance(),
                TH08Data.getInstance(),
                TH10Data.getInstance(),
                TH11Data.getInstance(),
                TH12Data.getInstance(),
                TH13Data.getInstance(),
                TH14Data.getInstance(),
                TH15Data.getInstance(),
                TH16Data.getInstance(),
                TH17Data.getInstance(),
                TH18Data.getInstance()
            };
        }
        return thGameData;
    }
    
    public THCharacter[] getCharacters() {
        return charas;
    }

    public THCharacter getCharacter(String name) {
        for (THCharacter t : charas) {
            if (t.name.equals(name)) {
                return t;
            }
        }
        return null;
    }

    public THMusic[] getMusics() {
        return music;
    }

    public THMusic getMusic(String name) {
        for (THMusic m : music) {
            if (m.name.equals(name)) {
                return m;
            }
        }
        return null;
    }

    public THSpell[] getSpellCards() {
        return spells;
    }

    public THSpell getSpellCard(String name) {
        for (THSpell s : spells) {
            if (s.cnName.equals(name) || s.jpName.equals(name)) {
                return s;
            }
        }
        return null;
    }

    public THPlayer[] getPlayers() {
        return players;
    }

    public THPlayer getPlayer(String name) {
        for (THPlayer p : players) {
            if (p.name.equals(name)) {
                return p;
            }
        }
        return null;
    }

    public String getNameFull() {
        return getNameCN() + " ~ " + getNameEng();
    }

    public abstract String getNameCN();

    public abstract String getNameEng();

    public abstract String getNameAbbr();

}
