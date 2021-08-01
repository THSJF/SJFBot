package com.meng.modules.touhou.zun.replay;

import com.meng.tools.BitConverter;

public class ReplayBinaryFile {

    private BitConverter converter;
    public byte[] file;
    public int Length;
    public int position = 0;

    public ReplayBinaryFile(byte[] bs) {
        file = bs;
        Length = bs.length;
        converter = BitConverter.getInstanceLittleEndian();
    }

    public enum SeekOrigin {
        Current,
        Begin
        }

    public ReplayBinaryFile seek(long l, SeekOrigin origin) {
        if (origin == SeekOrigin.Current) {
            position += l;
        } else if (origin == SeekOrigin.Begin) {
            position = (int)l;
        }
        return this;
    }

    public String readString() {
        StringBuilder builder = new StringBuilder();
        int[] buf = new int[3];
        buf[0] = readByte();
        buf[1] = readByte();
        if (buf[0] != 13 && buf[1] != 10) {
            buf[2] = readByte();
            do {
                builder.append((char)buf[0]);
                buf[0] = buf[1];
                buf[1] = buf[2];
                buf[2] = readByte();
            } while (buf[0] != 13 && buf[1] != 10);
        }
        seek(-1, ReplayBinaryFile.SeekOrigin.Current);
        return builder.toString();
    }

    public int readUShort() {
        if (position == file.length) {
            return -1;
        }
        return 0xFFFF & readShort();
    }

    public short readShort() {
        if (position == file.length) {
            return -1;
        }
        short s = converter.toShort(file, position);
        position += 2;
        return s;
    }

    public int readInt() {
        if (position == file.length) {
            return -1;
        }
        int i = converter.toInt(file, position);
        position += 4;
        return i;
    }

    public long readUInt() {
        if (position == file.length) {
            return -1;
        }
        return 0xFFFFFFFFL & readInt();
    }

    public byte readByte() {
        if (position == file.length) {
            return -1;
        }
        return file[position++];
    }
}
