package com.meng.tools;

import com.meng.*;
import java.io.*;
import java.util.concurrent.*;

/**
 * @author 司徒灵羽
 */

public class CleanRunnable {

	public CleanRunnable() {
		SJFExecutors.executeAtFixedRate(new Runnable(){

				@Override
				public void run() {
					File coolData=new File(new File(Autoreply.appDirectory).getParentFile().getParentFile().getParentFile().getParentFile() + "/data/");
					File[] images=new File(coolData.getAbsolutePath() + "/image/").listFiles();
					for (File f : images) {
						if (f.getName().endsWith(".cqimg") || f.getName().endsWith(".jpg") || f.getName().equals(".png")) {
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
				}
			}, 0, 1, TimeUnit.DAYS);
	}
}
