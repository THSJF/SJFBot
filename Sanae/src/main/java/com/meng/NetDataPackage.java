package com.meng;

import com.meng.tools.BitConverter;
import com.meng.tools.ExceptionCatcher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NetDataPackage {

    private static BitConverter bitConverter = BitConverter.getInstanceBigEndian();

    private ByteArrayOutputStream bos;
    private DataOutputStream dos;

    private ByteArrayInputStream bis;
    private DataInputStream dis;

    private Header header;

    public static NetDataPackage SUCCESS = new NetDataPackage(NetOperatType.OPERATE_SUCCESS.value());
    public static NetDataPackage FAILED = new NetDataPackage(NetOperatType.OPERATE_FAILED.value());
    
    
    public static NetDataPackage encode(int opCode) {
        return new NetDataPackage(opCode);
    }

    public static NetDataPackage decode(byte[] bs) {
        return new NetDataPackage(bs);
    }

    public static NetDataPackage decode(NetDataPackage ndp) {
        return decode(ndp.getData());
    }

    private NetDataPackage(byte[] bs) {
        bis = new ByteArrayInputStream(bs);
        dis = new DataInputStream(bis);
        header = new Header();
        try {
            header.length = dis.readInt();
            header.headerLength = dis.readShort();
            header.version = dis.readShort();
            header.time = dis.readLong();
            header.flag = dis.readLong();
            header.opCode = dis.readInt();
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
    }

    private NetDataPackage(int opCode) {
        bos = new ByteArrayOutputStream();
        dos = new DataOutputStream(bos);
        header = new Header();
        header.opCode = opCode;
        try {
            dos.writeInt(0);
            dos.writeShort(28);
            dos.writeShort(1);
            dos.writeLong(System.currentTimeMillis());
            dos.writeLong(9961);
            dos.writeInt(opCode);
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        //length(4) headLength(2) version(2) time(8) target/from(8) opCode(4)
    }

    private class Header {
        public int length;
        public short headerLength = 28;
        public short version = 1;
        public long time = System.currentTimeMillis();
        public long flag = 9961;
        public int opCode;
    }

    public byte[] getData() {
        byte[] bs = bos.toByteArray();
        byte[] len = bitConverter.getBytes(bs.length);
        bs[0] = len[0];
        bs[1] = len[1];
        bs[2] = len[2];
        bs[3] = len[3]; 
        return bs;
    }

    public NetDataPackage writeByte(int v) {
        try {
            dos.writeByte(v);
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        return this;
    }

    public byte readByte() {
        try {
            return dis.readByte();
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return -1;
        }
    }

    public NetDataPackage writeShort(int v) {
        try {
            dos.writeShort(v);
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        return this;
    }

    public short readShort() {
        try {
            return dis.readShort();
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return -1;
        }
    } 

    public NetDataPackage writeInt(int v) {
        try {
            dos.writeInt(v);
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        return this;
    }

    public int readInt() {
        try {
            return dis.readInt();
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return -1;
        }
    }

    public NetDataPackage writeLong(long v) {
        try {
            dos.writeLong(v);
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        return this;
    }

    public long readLong() {
        try {
            return dis.readLong();
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return -1;
        }
    } 

    public NetDataPackage writeFloat(float v) {
        try {
            dos.writeFloat(v);
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        return this;
    }

    public float readFloat() {
        try {
            return dis.readFloat();
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return -1;
        }
    }

    public NetDataPackage writeDouble(double v) {
        try {
            dos.writeDouble(v);
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        return this;
    }

    public double readDouble() {
        try {
            return dis.readDouble();
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return -1;
        }
    } 

    public NetDataPackage writeBoolean(boolean v) {
        try {
            dos.writeBoolean(v);
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        return this;
    }

    public boolean readBoolean() {
        try {
            return dis.readBoolean();
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return false;
        }
    }

    public NetDataPackage writeString(String v) {
        try {
            dos.writeUTF(v);
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        return this;
    }

    public String readString() {
        try {
            return dis.readUTF();
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            return "";
        }
    }

    public int getLength() {
        return header.length;
    }

    public short getHeadLength() {
        return header.headerLength;
    }

    public short getVersion() {
        return header.version;
    }

    public long getTimeStamp() {
        return header.time;
    }

    public long getFlag() {
        return header.flag;
    }

    public int getOpCode() {
        return header.opCode;
    }
}
