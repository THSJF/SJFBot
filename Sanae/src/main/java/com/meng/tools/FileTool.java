package com.meng.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileTool {

    public static String getAutoFileName(byte[] fileBytes) {
        return Hash.getMd5Instance().calculate(fileBytes).toUpperCase() + "." + FileFormat.getFileType(fileBytes);
    }

	public static void deleteFiles(File folder) {
		File[] fs = folder.listFiles();
		if (fs != null && fs.length > 0) {
			for (File f : fs) {
				if (f.isDirectory()) {
					deleteFiles(f);
					f.delete();
				} else {
					f.delete();
				}
			}
		}
	}

	public static void fileCopy(String src, String des) {
		try {
			BufferedInputStream bis = null;
			bis = new BufferedInputStream(new FileInputStream(src));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
			int i = -1;
			byte[] bt = new byte[1024];
			while ((i = bis.read(bt)) != -1) {
				bos.write(bt, 0, i);
			}
			bis.close();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readString(String fileName) {
		return readString(new File(fileName));
	}

	public static String readString(File f) {
		try {      
			long filelength = f.length();
			byte[] filecontent = new byte[(int) filelength];
			FileInputStream in = new FileInputStream(f);
			in.read(filecontent);
			in.close();
			return new String(filecontent, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readBytes(File f) {
		byte[] filecontent = null;
		try {
			long filelength = f.length();
			filecontent = new byte[(int) filelength];
			FileInputStream in = new FileInputStream(f);
			in.read(filecontent);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filecontent;
	}

	public static byte[] readBytes(String path) {
		return readBytes(new File(path));
	}

    public static File createFile(String path) throws IOException {
        return createFile(new File(path));
    }

    public static File createFile(File file) throws IOException {
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static void saveFile(File file, byte[] content) {
        try {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content);
            fos.close();
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
    }
}
