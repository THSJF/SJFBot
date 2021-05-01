package com.meng.modules.touhou.thsss.replay;

public class KeyData {
    public byte[] fileByte;
    public int position = 0;

    public KeyData(byte[] bs) {
        fileByte = bs;
    }

    public int readUShort() {
        return 0xffff & (fileByte[position++] & 0xff | (fileByte[position++] & 0xff) << 8);
    }
}
