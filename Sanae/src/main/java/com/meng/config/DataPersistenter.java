package com.meng.config;

import com.meng.annotation.SanaeData;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileTool;
import com.meng.tools.GSON;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * @Description: 将数据保存到磁盘
 * @author: 司徒灵羽
 **/

public class DataPersistenter {

	private DataPersistenter() {

	}

	public static boolean save(Object module) {
        Class<?> moduleClass = module.getClass();
        //Field[] fields = classObj.getFields();        //只能获取public
        Field[] fields = moduleClass.getDeclaredFields();  //public和private
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(SanaeData.class)) {
                SanaeData annotationField = field.getAnnotation(SanaeData.class);
                try {
                    FileOutputStream fos = new FileOutputStream(new File("C://Program Files/sanae_data/persistent/" + annotationField.value()));
                    fos.write(GSON.toJson(field.get(module)).getBytes(StandardCharsets.UTF_8));
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } 
            }
        }
        return true;   
	}

	public static boolean read(Object module) {
        Class<?> moduleClass = module.getClass();
        //Field[] fields = classObj.getFields();        //只能获取public
        Field[] fields = moduleClass.getDeclaredFields();  //public和private
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(SanaeData.class)) {
                SanaeData annotationField = field.getAnnotation(SanaeData.class);
                try {
                    File file = new File("C://Program Files/sanae_data/persistent/" + annotationField.value());
                    if (!file.exists()) {
                        field.set(module, field.get(module).getClass().newInstance());
                        continue;
                    }
                    String json = FileTool.readString(file);
                    if (json == null || json.equals("null")) {  
                        field.set(module, field.get(module).getClass().newInstance());
                    } else {
                        field.set(module, GSON.fromJson(json, field.getGenericType()));
                    }
                } catch (Exception e) { 
                    ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                }  
            }
        }
        return true;   
    }
}
