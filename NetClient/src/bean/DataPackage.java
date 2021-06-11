package bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import tools.BitConverter;

public class DataPackage {

    public ByteArrayOutputStream data = new ByteArrayOutputStream();
    public byte[] dataArray;
    public static final short headLength = 28;
    public int dataPointer = 0;
    private BitConverter convert = BitConverter.getInstanceLittleEndian();

    public static final byte typeByte = 0;
    public static final byte typeShort = 1;
    public static final byte typeInt = 2;
    public static final byte typeLong = 3;
    public static final byte typeFloat = 4;
    public static final byte typeDouble = 5;
    public static final byte typeString = 6;
    public static final byte typeBoolean = 7;
    public static final byte typeFile = 8;
    public static final byte typeSencence = 9;

    public static DataPackage encode(int opCode) {
        return new DataPackage(opCode, System.currentTimeMillis());
    }

    public static DataPackage encode(DataPackage dataPack) {
        return new DataPackage(dataPack);
    }

    public static DataPackage encode(int opCode, DataPackage dataPack) {
        return new DataPackage(opCode, dataPack);
    }

    public static DataPackage decode(byte[] bytes) {
        return new DataPackage(bytes);
    }

    private DataPackage(int opCode, long timeStamp) {
        //length(4) headLength(2) version(2) time(8) target/from(8) opCode(4)
        writeByteDataIntoArray(convert.getBytes(0));
        writeByteDataIntoArray(convert.getBytes(headLength));
        writeByteDataIntoArray(convert.getBytes((short)1));
        writeByteDataIntoArray(convert.getBytes(timeStamp));
        writeByteDataIntoArray(convert.getBytes(0L));
        writeByteDataIntoArray(convert.getBytes(opCode));
    }   

    private DataPackage(DataPackage dataPack) {
        //length(4) headLength(2) version(2) time(8) target/from(8) opCode(4)
        writeByteDataIntoArray(convert.getBytes(0));
        writeByteDataIntoArray(convert.getBytes(headLength));
        writeByteDataIntoArray(convert.getBytes(dataPack.getVersion()));
        writeByteDataIntoArray(convert.getBytes(dataPack.getTimeStamp()));
        writeByteDataIntoArray(convert.getBytes(dataPack.getTarget()));
        writeByteDataIntoArray(convert.getBytes(dataPack.getOpCode()));
    }

    private DataPackage(int opCode, DataPackage dataPack) {
        //length(4) headLength(2) version(2) time(8) target/from(8) opCode(4)
        writeByteDataIntoArray(convert.getBytes(0));
        writeByteDataIntoArray(convert.getBytes(headLength));
        writeByteDataIntoArray(convert.getBytes(dataPack.getVersion()));
        writeByteDataIntoArray(convert.getBytes(dataPack.getTimeStamp()));
        writeByteDataIntoArray(convert.getBytes(dataPack.getTarget()));
        writeByteDataIntoArray(convert.getBytes(opCode));
    }

    private DataPackage(byte[] pack) {
        dataArray = pack;
        dataPointer = headLength;
    } 

    public byte[] getData() {
        byte[] retData = data.toByteArray();
        byte[] len = convert.getBytes(retData.length);
        retData[0] = len[0];
        retData[1] = len[1];
        retData[2] = len[2];
        retData[3] = len[3];
        dataArray = retData;
        return retData;
    }

    public int getLength() {
        return convert.toInt(dataArray, 0);
    }  

    public short getHeadLength() {
        return convert.toShort(dataArray, 4);
    }

    public short getVersion() {
        return convert.toShort(dataArray, 6);
    }

    public long getTimeStamp() {
        return convert.toLong(dataArray, 8);
    }

    public long getTarget() {
        return convert.toLong(dataArray, 16);
    }

    public int getOpCode() {
        return convert.toInt(dataArray, 24);
    }

    private DataPackage writeByteDataIntoArray(byte... bs) {
        for (byte b:bs) {
            data.write(b);
            ++dataPointer;
        }
        return this;
    }

    public DataPackage write(byte b) {
        writeByteDataIntoArray(typeByte);
        writeByteDataIntoArray(b);
        return this;
    }

    public DataPackage write(short s) {
        writeByteDataIntoArray(typeShort);
        writeByteDataIntoArray(convert.getBytes(s));
        return this;
    }

    public DataPackage write(int i) {
        writeByteDataIntoArray(typeInt);
        writeByteDataIntoArray(convert.getBytes(i));
        return this;
    }

    public DataPackage write(long l) {
        writeByteDataIntoArray(typeLong);
        writeByteDataIntoArray(convert.getBytes(l));
        return this;
    }

    public DataPackage write(float f) {
        writeByteDataIntoArray(typeFloat);
        writeByteDataIntoArray(convert.getBytes(f));
        return this;
    }

    public DataPackage write(double d) {
        writeByteDataIntoArray(typeDouble);
        writeByteDataIntoArray(convert.getBytes(d));
        return this;
    }

    public DataPackage write(String s) {
        writeByteDataIntoArray(typeString);
        byte[] stringBytes = convert.getBytes(s);
        write(stringBytes.length);
        writeByteDataIntoArray(stringBytes);
        return this;
    }

    public DataPackage write(boolean b) {
        writeByteDataIntoArray(typeBoolean);
        writeByteDataIntoArray((byte)(b ? 1: 0));
        return this;
    }

    public DataPackage write(File file) {
        try {
            FileInputStream fin = new FileInputStream(file);
            byte[] bs = new byte[(int)file.length()];
            fin.read(bs, 0, bs.length);
            writeByteDataIntoArray(typeFile);
            write(file.length());
            writeByteDataIntoArray(bs);
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
        return this;
    }

    public File readFile(File file) {
        if (dataArray[dataPointer++] == typeFile) {
            long fileLen = readLong();
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(dataArray, dataPointer, (int)fileLen);
            } catch (Exception e) {
                file.delete();
                file = null;
            }
            dataPointer += fileLen;
            return file;
        }
        throw new RuntimeException("not a file");
    }

    public byte readByte() {
        if (dataArray[dataPointer++] == typeByte) {
            return dataArray[dataPointer++];
        }
        throw new RuntimeException("not a byte number");
    }

    public short readShort() {
        if (dataArray[dataPointer++] == typeShort) {
            short s = convert.toShort(dataArray, dataPointer);
            dataPointer += 2;
            return s;
        }
        throw new RuntimeException("not a short number");
    }

    public int readInt() {
        if (dataArray[dataPointer++] == typeInt) {
            int i= convert.toInt(dataArray, dataPointer);
            dataPointer += 4;
            return i;
        }
        throw new RuntimeException("not a int number");
    }

    public long readLong() {
        if (dataArray[dataPointer++] == typeLong) {
            long l= convert.toLong(dataArray, dataPointer);
            dataPointer += 8;
            return l;
        }
        throw new RuntimeException("not a long number");
    }

    public float readFloat() {
        if (dataArray[dataPointer++] == typeFloat) {
            float f = convert.toFloat(dataArray, dataPointer);
            dataPointer += 4;
            return f;
        }
        throw new RuntimeException("not a float number");
    }

    public double readDouble() {
        if (dataArray[dataPointer++] == typeDouble) {
            double d = convert.toDouble(dataArray, dataPointer);
            dataPointer += 8;
            return d;
        }
        throw new RuntimeException("not a double number");
    }

    public String readString() {
        try {
            if (dataArray[dataPointer++] == typeString) {
                int len = readInt();
                String s = convert.toString(dataArray, dataPointer, len);
                dataPointer += len;
                return s;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return null;
    }

    public boolean readBoolean() {
        if (dataArray[dataPointer++] == typeBoolean) {
            return dataArray[dataPointer++] == 1;
        }
        throw new RuntimeException("not a boolean value");
    }

    public boolean hasNext() {
        return dataPointer != dataArray.length;
    }
}
