package com.meng.modules.touhou;
import com.meng.tools.TextLexer;

public class THMusic {
    public String name;
    public THGameData game;

    public THMusic(String name, THGameData game) {
        this.name = name;
        this.game = game;
    }

    public String getNameCN() {
        String[] parts = name.split(" ~ ");
        if (parts.length < 2) {
            return TextLexer.isAlpha(parts[0].charAt(0)) ? null : name;
        }
        return parts[TextLexer.isAlpha(parts[0].charAt(0)) ? 1 : 0];
    }

    public String getNameEng() {
        String[] parts = name.split(" ~ ");
        if (parts.length < 2) {
            return TextLexer.isAlpha(parts[0].charAt(0)) ? name: null;
        }
        return parts[TextLexer.isAlpha(parts[0].charAt(0)) ? 0 : 1];
    }
}
