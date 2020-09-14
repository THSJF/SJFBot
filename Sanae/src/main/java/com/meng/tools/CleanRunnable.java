package com.meng.tools;

import com.meng.*;
import java.io.*;

public class CleanRunnable implements Runnable {
    @Override
    public void run() {
        while (true) {
			File coolData=new File(new File(Autoreply.appDirectory).getParentFile().getParentFile().getParentFile().getParentFile() + "/data/");
			File[] images=new File(coolData.getAbsolutePath() + "/image/").listFiles();
			for (File f : images) {
				if (f.getName().endsWith(".cqimg")) {
					f.delete();
				}
            }
			File[] records=new File(coolData.getAbsolutePath() + "/record/").listFiles();
			for (File f : records) {
				if (f.getName().endsWith(".amr") || f.getName().endsWith(".silk") || f.getName().endsWith(".cqrec")) {
					f.delete();
				}
            }
            File[] tmpFiles = new File(Autoreply.appDirectory + "downloadImages/").listFiles();
            for (File f : tmpFiles) {
                f.delete();
            }
			try {
                Thread.sleep(86400000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
    }
}
