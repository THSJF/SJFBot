package com.meng.modules.qq.hotfix;

import java.util.Map;

public class HotfixClassLoader extends ClassLoader {
    private Map<String,byte[]> classes;

    public HotfixClassLoader(Map<String,byte[]> classes) {
        super(getSystemClassLoader());
        this.classes = classes;
    }

    public void put(String className, byte[] code) {
        classes.put(className, code);
    }

    public byte[] getCode(String name) {
        return classes.get(name);
    }
    
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        byte[] classBytes = classes.get(name);
        if (classBytes != null) {
            return defineClass(name, classBytes, 0, classBytes.length);
        }
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = classes.get(name);
        if (classBytes == null) {
            throw new ClassNotFoundException();
        }
        Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
        if (cl == null) {
            throw new ClassNotFoundException();
        }
        return cl;
    }
}
