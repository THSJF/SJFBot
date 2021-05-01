package com.meng.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;

public class FileFormat {
    private static HashSet<Content> hashset = new HashSet<>();

    public static Content getFileType(File file) throws IOException {
        try(FileInputStream fis = new FileInputStream(file)) {
            byte[] bs = new byte[16];
            fis.read(bs, 0, bs.length);
            return getFileType(bs);
        }
    }

    public static Content getFileType(byte[] file) {
        for (Content content : hashset) {
            if (file.length < content.magic.length) {
                continue;
            }
            if (arrayContains(file, content.magic)) {
                return content;
            }
        }
        return null;
    }

    public static File checkFormat(File file) throws IOException {
        File ret;
        String type = getFileType(file).extendName;
        String fullName = file.getName();
        String parent = file.getParent() + File.separator;
        int dividerIndex = fullName.lastIndexOf(".");
        if (dividerIndex == -1) {
            ret = new File(parent + fullName + "." + type);
            if (ret.exists()) {
                ret.delete();
            }
            file.renameTo(ret);
        } else {
            String fName = fullName.substring(0, dividerIndex - 1);
            ret = new File(parent + fName + "." + type);
            if (ret.exists()) {
                ret.delete();
            }
            file.renameTo(ret); 
        }
        return ret;
    }

    private static boolean arrayContains(byte[] big, byte[] small) {
        for (int i = 0;i < small.length;++i) {
            if (big[i] == 0) {
                continue;
            }
            if (small[i] != big[i]) {
                return false;
            }
        }
        return true;
    }

    private static void add(String bytes, String extendName) {
        hashset.add(new Content(bytes, extendName));
    }

    private static void add(String bytes, String extendName, String describe) {
        hashset.add(new Content(bytes, extendName, describe));
    }

    public static void init() {
        add("FF D8 FF E0 00 10 4A 46 49 46", "jpg", "jpg(JFIF)");
        add("FF D8 FF E1", "jpg", "jpg(Exif)");

        add("89 50 4E 47 0D 0A 1A 0A", "png");
        add("47 49 46 38 37 61", "gif", "gif87a");
        add("47 49 46 38 39 61", "gif", "gif89a");
        add("49 49 2A 00 22 71 05 00 80 37", "tif");
        add("42 4D 38 60", "bmp", "16色");
        add("42 4D 22 8C 01", "bmp", "16色");
        add("42 4D 82 40 09", "bmp", "24色");
        add("42 4D 8E 1B 03", "bmp", "256色");


        add("FF FE", "txt", "Unicode(little endian)");
        add("FE FF", "txt", "Unicode(big endian)");
        add("EB BB BF", "txt", "UTF-8");


        add("41 43 31 30", "dwg", "CAD");
        add("25 50 44 46 2D 31 2E", "pdf");
        add("50 4B 03 04", "zip");
        add("52 61 72 21", "rar");
        add("57 41 56 45", "wav");
        add("52 49 46 46", "wav");
        add("41 56 49 20", "avi");
        add("2E 52 4D 46", "rmvb");
        add("6D 6F 6F 76", "mov");
        add("1F 8B 08", "gz");
        add("7F 45 4C 46", "elf");
        add("4D 54 68 64", "mid");
        add("0E E0 00 00", "rpy", "thsss replay");
        add("78 6F 66 20", "x", "DirectX data");

        add("4D 5A 90 00", "exe", "maybe dll");

        add("64 65 78 0A 30 33 35", "Android dex file");
        add("CA FE BA BE", "Java class file");
        add("53 43 50 54", "ecl");
        add("08 00 00 00", "anm");

    }

    private static byte[] getByteArray(String hex) {
        String[] hexByte = hex.split(" ");
        byte[] bs = new byte[hexByte.length];
        for (int i = 0;i < hexByte.length;++i) {
            bs[i] = (byte) Integer.parseInt(hexByte[i], 16);
        }
        return bs;
    }

    public static class Content {
        public byte[] magic;
        public String extendName;
        public String describe;

        public Content(String magic, String extendName) {
            this.magic = getByteArray(magic);
            this.extendName = extendName;
        }

        public Content(String magic, String extendName, String describe) {
            this(magic, extendName);
            this.describe = describe;
        }

        @Override
        public String toString() {
            return extendName;
        }
    }
}
