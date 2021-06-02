package com.meng.tools;

import java.util.ArrayList;
import java.util.List;

public class SJFCmdAnalyzer {
    public static List<String> analyze(String cmd) {    
        List<String> result = new ArrayList<>();
        boolean onString = false;
        boolean lastOnString = false;
        int length = cmd.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            char ch = cmd.charAt(i);
            lastOnString = onString;
            if (ch == '"') {
                onString = !onString;
                ch = cmd.charAt(++i);
            }
            if (onString) {
                builder.append(ch);
            } else if (!onString && lastOnString) {
                result.add(builder.toString());
                builder.setLength(0);
            } else if (ch != ' ') {
                builder.append(ch);
            } else {
                result.add(builder.toString());
                builder.setLength(0);
            }
        }
        result.add(builder.toString());
        return result;
    }
    
    public static boolean isAlpha(char c) {
        return ((c <= 'z') && (c >= 'a')) || ((c <= 'Z') && (c >= 'A')) || (c == '_');
    }

    public static boolean isNumber(char c) {
        return (c >= '0') && (c <= '9');
    }
}
