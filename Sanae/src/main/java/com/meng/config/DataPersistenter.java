package com.meng.config;

import com.meng.*;
import com.meng.SJFInterfaces.*;
import com.meng.sjfmd.libs.*;
import com.meng.tools.*;
import java.io.*;
import java.nio.charset.*;

/**
 * @author 司徒灵羽
 */

public class DataPersistenter {

	private DataPersistenter() {

	}

	public static boolean save(IPersistentData pb) {
		try {
            File file = new File(pb.getWrapper().getCQ().getAppDirectory() + "/persistent/" + pb.getPersistentName());
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.write(GSON.toJson(pb.getDataBean()));
            writer.flush();
            fos.close();
			return true;
        } catch (IOException e) {
            e.printStackTrace();
			return false;
        }
	}

	public static boolean read(IPersistentData pb) {    
        try {
			String readString = FileTool.readString(pb.getWrapper().getCQ().getAppDirectory() + pb.getPersistentName());
			pb.setDataBean(GSON.fromJson(readString == null ?"{}": readString, pb.getDataType()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
