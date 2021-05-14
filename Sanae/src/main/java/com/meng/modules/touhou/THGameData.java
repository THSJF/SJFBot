package com.meng.modules.touhou;
import com.meng.tools.Hash;
import com.meng.tools.SJFRandom;
import com.meng.tools.Tools;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Consumer;

public abstract class THGameData {


    protected THCharacter[] charas;
    protected THMusic[] music;
    protected THSpell[] spells;
    protected THPlayer[] players;

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

    public THSpell getSpellFromDiff(int diffFlag) {
        THSpell splc = null;
        while (true) {
            splc = SJFRandom.randomSelect(spells);
            if ((splc.difficult & diffFlag) != 0) {
                return splc;
            }
        }
    }

    public THSpell[] getSpellFromNotDiff(int count, int diffFlag) {
        THSpell[] spshs = new THSpell[count];
        for (int i = 0;i < count;++i) {
            THSpell splc;
            while (true) {
                splc = SJFRandom.randomSelect(spells);
                if ((splc.difficult & diffFlag) == 0) {
                    spshs[i] = splc;
                    splc = null;
                    break;
                }
            }
        }
        return spshs;
    }

    public THSpell getTHSpell(String spellName) {
        for (THSpell sc : spells) {
            if (sc.cnName.contains(spellName)) {
                return sc;
            }
        }
        return null;
    }

    public THSpell getTHSpell(String spellName, int diff) {
        for (THSpell sc : spells) {
            if (sc.cnName.contains(spellName) && sc.difficult == diff) {
                return sc;
            }
        }
        return null;
    }

    public HashSet<THSpell> getCharaTHSpell(String name) {
        HashSet<THSpell> hscs = new HashSet<>();
        for (THSpell sc : spells) {
            if (sc.master.equals(name)) {
                hscs.add(sc);
            }
        }
        return hscs;
    }

    public HashSet<THSpell> getCharaTHSpell(String name, int diff) {
        HashSet<THSpell> hscs = new HashSet<>();
        for (THSpell sc : spells) {
            if (sc.master.equals(name) && sc.difficult == diff) {
                hscs.add(sc);
            }
        }
        return hscs;
    }

    public HashSet<THSpell> getCharaTHSpell(String name, String... spellExcept) {
        HashSet<THSpell> hscs = new HashSet<>();
        for (THSpell sc : spells) {
            if (sc.master.equals(name)) {
                for (String necx:spellExcept) {
                    if (!sc.cnName.equals(necx)) {
                        hscs.add(sc);
                    }
                }
            }
        }
        return hscs;
    }

    public THSpell randomSpell() {
        return SJFRandom.randomSelect(spells);
    }

    public THMusic randomMusic(){
        return SJFRandom.randomSelect(music);
    }
    
    public THSpell hashRandomSpell(long fromQQ) {
        return SJFRandom.hashSelect(fromQQ, spells);
    }

    public THCharacter hashRandomCharacter(long fromQQ) {
        return SJFRandom.hashSelect(fromQQ, charas);
    }

    public THMusic hashRandomMusic(long fromQQ) {
        return SJFRandom.hashSelect(fromQQ, music);
    }

    public void forEachCharacter(Consumer<THCharacter> consumer) {
        for (THCharacter chara : charas) {
            consumer.accept(chara);
        }
    }

    public void forEachSpell(Consumer<THSpell> consumer) {
        for (THSpell spell : spells) {
            consumer.accept(spell);
        }
    }

    public void forEachMusic(Consumer<THMusic> consumer) {
        for (THMusic music : music) {
            consumer.accept(music);
        }
    }

    public abstract String getNameCN();

    public abstract String getNameEng();

    public abstract String getNameAbbr();

}
