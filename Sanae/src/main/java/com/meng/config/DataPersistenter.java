package com.meng.config;

import com.meng.SJFInterfaces.IPersistentData;
import com.meng.sjfmd.libs.FileTool;
import com.meng.sjfmd.libs.GSON;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Description: 将数据保存到磁盘
 * @author: 司徒灵羽
 **/

public class DataPersistenter {

	private DataPersistenter() {

	}

	public static boolean save(IPersistentData pb) {
		try {
            File file = new File(pb.getWrapper().appDirectory + "/persistent/" + pb.getPersistentName());
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.write(GSON.toJson(pb.getDataBean()));
            writer.flush();
            fos.close();
			return true;
        } catch (Exception e) {
            e.printStackTrace();
			return false;
        }
	}

	public static boolean read(IPersistentData pb) {    
        try {
            File f = new File(pb.getWrapper().appDirectory + "/persistent/" + pb.getPersistentName());
			if (!f.exists()) {
                save(pb);
            }
            String readString = FileTool.readString(f);
			pb.setDataBean(GSON.fromJson(readString, pb.getDataType()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
