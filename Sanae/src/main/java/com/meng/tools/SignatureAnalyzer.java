package com.meng.tools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SignatureAnalyzer {

    public static JVMMethodSignature analyze(String signature) {
        String[] info = signature.split("[\\()]");
        if (info.length == 3) {
            return analyzeJvmSignature(info);
        } else if (info.length == 1) {
            return analyzeJavaType(signature);
        } else {
            throw new RuntimeException("not illegal signature");
        }
    }

    private static int handleSignaturePart(String signature, int i, Class<? extends Object>[] classHolder) {
        char c = signature.charAt(i);
        if (c == 'L') {
            StringBuilder builder = new StringBuilder();
            while ((c = signature.charAt(++i)) != ';') {
                builder.append(c);
            }
            classHolder[0] = handleType(builder.toString());
        } else if (c == '[') {
            c = signature.charAt(++i);
            if (c == 'L') {
                StringBuilder builder = new StringBuilder();
                while ((c = signature.charAt(++i)) != ';') {
                    builder.append(c);
                }
                classHolder[0] = Array.newInstance(handleType(builder.toString()), 0).getClass();
            } else {
                classHolder[0] = Array.newInstance(handleType(c), 0).getClass();
            }
        } else {
            classHolder[0] = handleType(c);
        }
        return i;
    }

    private static JVMMethodSignature analyzeJvmSignature(String[] info) {
        JVMMethodSignature jvmSignature = new JVMMethodSignature();
        jvmSignature.name = info[0];
        List<Class<? extends Object>> types = new ArrayList<>();
        Class<? extends Object>[] typeHolder = new Class<? extends Object>[1];
        String argTypes = info[1];
        for (int i = 0; i < argTypes.length(); ++i) {
            i = handleSignaturePart(argTypes, i, typeHolder);
            types.add(typeHolder[0]);
        }
        jvmSignature.argTypes = types.toArray(new Class<? extends Object>[0]); 
        String returnType = info[2];
        handleSignaturePart(returnType, 0, typeHolder);
        jvmSignature.returnType = typeHolder[0];
        return jvmSignature;
    }

    private static JVMMethodSignature analyzeJavaType(String signature) {
        JVMMethodSignature result = new JVMMethodSignature();
        Class<? extends Object>[] typeHolder = new Class<? extends Object>[1];
        handleSignaturePart(signature, 0, typeHolder);
        result.returnType = typeHolder[0];
        return result;
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

    public static class JVMMethodSignature {
        public String name;
        public Class<?>[] argTypes;
        public Class<?> returnType;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (Class<?> cls : argTypes) {
                String name = cls.isArray() ? cls.getComponentType().getName() + "[]" : cls.getName();
                builder.append("").append(name).append(", ");
            }
            builder.setLength(builder.length() - 2);
            return String.format("%s %s(%s);", returnType.getName(), name, builder.toString());
        }
    }
}
