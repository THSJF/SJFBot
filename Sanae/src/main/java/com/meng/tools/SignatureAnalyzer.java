package com.meng.tools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SignatureAnalyzer {

    public static Class<?>[] analyze(String signature) {
        List<Class<?>> types = new ArrayList<>();
        for (int i = 0; i < signature.length(); ++i) {
            char c = signature.charAt(i);
            if (c == 'L') {
                StringBuilder builder = new StringBuilder();
                while ((c = signature.charAt(++i)) != ';') {
                    builder.append(c);
                }
                types.add(handleType(builder.toString()));
            } else if (c == '[') {
                c = signature.charAt(++i);
                if (c == 'L') {
                    StringBuilder builder = new StringBuilder();
                    while ((c = signature.charAt(++i)) != ';') {
                        builder.append(c);
                    }
                    types.add(Array.newInstance(handleType(builder.toString()), 0).getClass());
                } else {
                    types.add(Array.newInstance(handleType(c), 0).getClass());
                }
            } else {
                types.add(handleType(c));
            }
        }
        return types.toArray(new Class<?>[0]);
    }

    private static Class<?> handleType(char c) {
        switch (c) {
            case 'Z' :
                return boolean.class;
            case 'B' :
                return byte.class;
            case 'C' :
                return char.class;
            case 'S' :
                return short.class;
            case 'I' :
                return int.class;
            case 'F' :
                return float.class;
            case 'J' :
                return long.class;
            case 'D' :
                return double.class;
            default:
                return null;
        }
    }

    private static Class<?> handleType(String s) {
        try {
            return Class.forName(s.replace("/", "."));
        } catch (ClassNotFoundException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return null;
        }
    }
}
