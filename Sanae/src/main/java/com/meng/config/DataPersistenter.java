package com.meng.config;

import com.meng.SJFInterfaces.IPersistentData;
import com.meng.SjfPersistentData;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileTool;
import com.meng.tools.GSON;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * @Description: 将数据保存到磁盘
 * @author: 司徒灵羽
 **/

public class DataPersistenter {

    public static boolean useA = true;

	private DataPersistenter() {
        throw new AssertionError();
	}

	public static boolean save(IPersistentData pb) {
        if (useA) {
            Class<?> classObj = pb.getClass();
            //Field[] fields = classObj.getFields();        //只能获取public
            Field[] fields = classObj.getDeclaredFields();  //public和private
            for (Field field : fields) {
                if (field.isAnnotationPresent(SjfPersistentData.class)) {
                    SjfPersistentData per = field.getAnnotation(SjfPersistentData.class);
                    if (per != null) {
                        try {
                            FileOutputStream fos = new FileOutputStream(new File("C://Program Files/sanae_data/persistent/" + per.value()));
                            fos.write(GSON.toJson(field.get(pb)).getBytes(StandardCharsets.UTF_8));
                            fos.close();
                            continue;
                        } catch (Exception e) {
                            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                        } 
                    }
                }
            }
            return true;   
        } else {
            try {
                File file = new File(pb.getWrapper().appDirectory + "/persistent/" + pb.getPersistentName());
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                writer.write(GSON.toJson(pb.getDataBean()));
                writer.flush();
                fos.close();
                return true;
            } catch (Exception e) {
                ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                return false;
            }
        }
	}

	public static boolean read(IPersistentData pb) {
        if (useA) {
            Class<?> classObj = pb.getClass();
            //Field[] fields = classObj.getFields();        //只能获取public
            Field[] fields = classObj.getDeclaredFields();  //public和private
            for (Field field : fields) {
                if (field.isAnnotationPresent(SjfPersistentData.class)) {
                    SjfPersistentData per = field.getAnnotation(SjfPersistentData.class);
                    if (per != null) {
                        try {
                            File file = new File("C://Program Files/sanae_data/persistent/" + per.value());
                            if (!file.exists()) {
                                field.set(pb, GSON.fromJson("{}", classObj));
                                continue;
                            }
                            String readString = FileTool.readString(file);
                            field.set(pb, (GSON.fromJson((readString == null || readString.equals("null")) ?"{}": readString, classObj)));
                        } catch (Exception e) {
                            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                        }  
                    }
                }
            }
            return true;   
        } else {
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
}
