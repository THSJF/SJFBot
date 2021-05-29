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

    public static boolean isFormat(File file, String extendName) {
        try {
            return extendName.equalsIgnoreCase(getFileType(file).extendName);
        } catch (IOException e) {
            return false;
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

    private static void add(String bytes, String extendName, FileType describe) {
        hashset.add(new Content(bytes, extendName, describe));
    }

    public static void init() {
        add("FF D8 FF E0 00 10 4A 46 49 46", "jpg", FileType.jpg_JFIF);
        add("FF D8 FF E1", "jpg", FileType.jpg_Exif);

        add("89 50 4E 47 0D 0A 1A 0A", "png", FileType.png);
        add("47 49 46 38 37 61", "gif", FileType.gif_87a);
        add("47 49 46 38 39 61", "gif", FileType.gif_89a);
        add("49 49 2A 00 22 71 05 00 80 37", "tif", FileType.tif);
        add("42 4D 38 60", "bmp", FileType.bmp_16colors);
        add("42 4D 22 8C 01", "bmp", FileType.bmp_16colors);
        add("42 4D 82 40 09", "bmp", FileType.bmp_24colors);
        add("42 4D 8E 1B 03", "bmp", FileType.bmp_256colors);


        add("FF FE", "txt", FileType.Unicode_LE);
        add("FE FF", "txt", FileType.Unicode_BE);
        add("EB BB BF", "txt", FileType.UTF_8);


        add("41 43 31 30", "dwg", FileType.cad);
        add("25 50 44 46 2D 31 2E", "pdf", FileType.pdf);
        add("50 4B 03 04", "zip", FileType.zip);
        add("52 61 72 21", "rar", FileType.rar);
        add("57 41 56 45", "wav", FileType.wav);
        add("52 49 46 46", "wav", FileType.wav);
        add("41 56 49 20", "avi", FileType.avi);
        add("2E 52 4D 46", "rmvb", FileType.rmvb);
        add("6D 6F 6F 76", "mov", FileType.mov);
        add("1F 8B 08", "gz", FileType.gzip);
        add("7F 45 4C 46", "elf", FileType.elf);
        add("4D 54 68 64", "mid", FileType.mid);
        add("0E E0 00 00", "rpy", FileType.rpy_thsss_replay);
        add("78 6F 66 20", "x", FileType.x_directx_data);

        add("4D 5A 90 00", "exe", FileType.exe_maybe_dll);

        add("64 65 78 0A 30 33 35", "Android dex file", FileType.dex_android_executeable);
        add("CA FE BA BE", "Java class file", FileType.class_jvm_executeable);
        add("53 43 50 54", "ecl", FileType.ecl_zun_danmaku);
        add("08 00 00 00", "anm", FileType.anm_zun_texture);

        add("54 36 52 50", "rpy", FileType.rpy_th06_replay);
        add("54 37 52 50", "rpy", FileType.rpy_th07_replay);
        add("54 38 52 50", "rpy", FileType.rpy_th08_replay);
        add("54 39 52 50", "rpy", FileType.rpy_th09_replay);
        add("74 39 35 72", "rpy", FileType.rpy_th09_5_replay);
        add("74 31 30 72", "rpy", FileType.rpy_th10_replay);
        add("74 31 31 72", "rpy", FileType.rpy_th11_replay);
        add("74 31 32 72", "rpy", FileType.rpy_th12_replay);
        add("31 32 38 72", "rpy", FileType.rpy_th12_8_replay);
        add("74 31 33 72", "rpy", FileType.rpy_th13_th14_replay);
        add("74 31 34 33", "rpy", FileType.rpy_th14_3_replay);
        add("74 31 35 72", "rpy", FileType.rpy_th15_replay);
        add("74 31 36 72", "rpy", FileType.rpy_th16_replay);
        add("74 31 35 36", "rpy", FileType.rpy_th16_5_replay);
        add("74 31 37 72", "rpy", FileType.rpy_th17_replay);
        add("74 31 38 72", "rpy", FileType.rpy_th18_replay);
        
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
        public FileType describe;

        public Content(String magic, String extendName, FileType describe) {
            this.magic = getByteArray(magic);
            this.extendName = extendName;
            this.describe = describe;
        }

        @Override
        public String toString() {
            return extendName;
        }
    }

    public enum FileType {
        jpg_JFIF,
        jpg_Exif,
        png,
        gif_87a,
        gif_89a,
        tif,
        bmp_16colors,
        bmp_24colors,
        bmp_256colors,
        Unicode_LE,
        Unicode_BE,
        UTF_8,
        cad,
        pdf,
        zip,
        rar,
        wav,
        avi,
        rmvb,
        mov,
        gzip,
        elf,
        mid,
        rpy_thsss_replay,
        x_directx_data,
        exe_maybe_dll,
        dex_android_executeable,
        class_jvm_executeable,
        ecl_zun_danmaku,
        anm_zun_texture,
        rpy_th06_replay,
        rpy_th07_replay,
        rpy_th08_replay,
        rpy_th09_replay,
        rpy_th09_5_replay,
        rpy_th10_replay,
        rpy_th11_replay,
        rpy_th12_replay,
        rpy_th12_5_replay,
        rpy_th12_8_replay,
        rpy_th13_th14_replay,
        rpy_th14_3_replay,
        rpy_th15_replay,
        rpy_th16_replay,
        rpy_th16_5_replay,
        rpy_th17_replay,
        rpy_th18_replay,
        }
}
