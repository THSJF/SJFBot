package com.meng.config;

import com.meng.SJFInterfaces.IPersistentData;
import com.meng.tools.FileTool;
import com.meng.tools.GSON;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import com.meng.tools.Tools;
import com.meng.tools.Network;
import com.meng.tools.ExceptionCatcher;

/**
 * @Description: 将数据保存到磁盘
 * @author: 司徒灵羽
 **/

public class DataPersistenter {

	private DataPersistenter() {
        throw new AssertionError();
	}

	public static boolean save(IPersistentData pb) {
		try {
            File file = new File(pb.getWrapper().appDirectory + "/persistent/" + pb.getPersistentName());
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.write(Network.formatJson(GSON.toJson(pb.getDataBean())));
            writer.flush();
            fos.close();
			return true;
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
			return false;
        }
	}

	public static boolean read(IPersistentData pb) {    
        try {
            File f = new File(pb.getWrapper().appDirectory + "/persistent/" + pb.getPersistentName());
			if (!f.exists()) {
                pb.setDataBean(GSON.fromJson("{}", pb.getDataType()));
                return true;
            }
            String readString = FileTool.readString(f);
			pb.setDataBean(GSON.fromJson((readString == null || readString.equals("null")) ?"{}": readString, pb.getDataType()));
			return true;
		} catch (Exception e) {
			ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
			return false;
		}
	}
}
