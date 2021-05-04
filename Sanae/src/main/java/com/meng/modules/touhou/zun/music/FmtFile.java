package com.meng.modules.touhou.zun.music;

import com.meng.tools.FileTool;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class FmtFile {

    private int position = 0;
    private byte[] fileByte;
    public MusicInfo[] musicInfos;

    public FmtFile(File file) {
        if (!file.exists()) {
            throw new RuntimeException("file not found:" + file.getAbsolutePath());
        }
        fileByte = FileTool.readBytes(file);
        musicInfos = new MusicInfo[(int) (file.length() / 52)];
        for (int i = 0; i < musicInfos.length; ++i) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.name = readName();
            musicInfo.start = readInt();
            musicInfo.unknown1 = readInt();
            musicInfo.repeatStart = readInt();
            musicInfo.length = readInt();
            musicInfo.format = readShort();
            musicInfo.channels = readShort();
            musicInfo.rate = readInt();
            musicInfo.avgBytesPerSec = readInt();
            musicInfo.blockAlign = readShort();
            musicInfo.bitsPerSample = readShort();
            musicInfo.cbSize = readShort();
            musicInfo.pad = readShort();
            musicInfos[i] = musicInfo;
        }
    }

    private short readShort() {
        return (short) (fileByte[position++] & 0xff | (fileByte[position++] & 0xff) << 8);
    }

    private int readInt() {
        return (fileByte[position++] & 0xff) | (fileByte[position++] & 0xff) << 8 | (fileByte[position++] & 0xff) << 16 | (fileByte[position++] & 0xff) << 24;
    }

    private String readName() {
        byte[] ba = new byte[16];
        for (int i = 0; i < ba.length; ++i) {
            ba[i] = fileByte[position++];
        }
        String string = new String(ba, StandardCharsets.UTF_8);
        return string.substring(0, string.indexOf(0));
    }

    public class MusicInfo {
        public String name;
        public int start;
        public int unknown1;
        public int repeatStart;
        public int length;
        public short format;
        public short channels;
        public int rate;
        public int avgBytesPerSec;
        public short blockAlign;
        public short bitsPerSample;
        public short cbSize;
        public short pad;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(name).append(" ");
            sb.append(start).append(" ");
            sb.append(unknown1).append(" ");
            sb.append(repeatStart).append(" ");
            sb.append(length).append(" ");
            sb.append(format).append(" ");
            sb.append(channels).append(" ");
            sb.append(rate).append(" ");
            sb.append(avgBytesPerSec).append(" ");
            sb.append(blockAlign).append(" ");
            sb.append(bitsPerSample).append(" ");
            sb.append(cbSize).append(" ");
            sb.append(pad).append(" ");
            return sb.toString();
        }
        //    public int beanSize = 52;
	}
}
