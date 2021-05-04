package com.meng.modules.touhou.zun.replay;

import com.meng.tools.BitConverter;

public class BinaryFile {

    private BitConverter converter;
    public byte[] file;
    public int Length;
    public int position = 0;
    
    public BinaryFile(byte[] bs) {
        file = bs;
        Length = bs.length;
        converter = BitConverter.getInstanceLittleEndian();
    }

    public void Close() {
    }

    public enum SeekOrigin {
        Current,
        Begin
        }

    public void Seek(long l, SeekOrigin origin) {
        if (origin == SeekOrigin.Current) {
            position += l;
        } else if (origin == SeekOrigin.Begin) {
            position = (int)l;
        }
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

    public byte ReadByte() {
        if (position == file.length) {
            return -1;
        }
        return file[position++];
    }
}
