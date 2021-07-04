package com.meng.tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import com.meng.tools.FileWatcher.FileInfo;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.ILoad;
import com.meng.tools.FileWatcher.OnChangeListener;

public class FileWatcher implements Runnable {

    private static FileWatcher instance;
    private boolean running = false;
    public static FileWatcher getInstance() {
        if (instance == null) {
            instance = new FileWatcher();
        }
        return instance;
    }

    public void init() {
        if (running) {
            return;
        }
        SJFExecutors.executeWithFixedDelay(this, 2, TimeUnit.SECONDS);
    }

    private FileWatcher() {

    }

    private Map<File,FileInfo> fileInfo = new ConcurrentHashMap<>();
    private List<Runnable> listeners = new ArrayList<>();

    public void addOnFileChangeListener(File file, Runnable listener) {
        if (file.isDirectory()) {
            throw new UnsupportedOperationException("unsupport directory");
        }
        fileInfo.put(file, new FileInfo(file.lastModified(), false));
        listeners.add(listener);
    }

    public boolean registerSelf(File file) {
        FileWatcher.FileInfo ff = fileInfo.get(file);
        if (ff == null) {
            return false;
        }
        ff.bySelf = true;
        return true;
    }

    @Override
    public void run() {
        for (Map.Entry<File,FileInfo> entry : fileInfo.entrySet()) {
            long thisModify = entry.getKey().lastModified();
            FileWatcher.FileInfo value = entry.getValue();
            if (thisModify != value.lastModify) {
//                System.out.println("发现文件变化:" + entry.getKey());
                if (value.bySelf) {
//                    System.out.println("自身修改");
                    value.bySelf = false;
                    value.lastModify = thisModify;
                    continue;
                }
                for (int i = 0;i < listeners.size();++i) {
                    Runnable get = listeners.get(i);
                    if (get instanceof OnChangeListener) {
                        ((OnChangeListener)get).onChange(entry.getKey(), value.lastModify, thisModify);
                    }
                    get.run();
                }
                value.lastModify = thisModify;
            }
        }
    }

    public static abstract class OnChangeListener implements Runnable {
        public abstract void onChange(File file, long lastModify, long thisModify);

        @Override
        public void run() {
        }
    }

    public static class FileInfo {
        public long lastModify;
        public boolean bySelf;

        public FileInfo(long lastModify, boolean bySelf) {
            this.lastModify = lastModify;
            this.bySelf = bySelf;
        }
    }
}
