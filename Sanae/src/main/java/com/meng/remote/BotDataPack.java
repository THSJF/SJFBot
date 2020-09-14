package com.meng.remote;

import com.meng.tools.*;
import java.io.*;
import java.util.*;

public class BotDataPack {

	public ArrayList<Byte> data=new ArrayList<>();
	public byte[] dataArray;
	public static final short headLength=10;
	public int dataPointer=0;

	public static final byte typeByte=0;
	public static final byte typeShort=1;
	public static final byte typeInt=2;
	public static final byte typeLong=3;
	public static final byte typeFloat=4;
	public static final byte typeDouble=5;
	public static final byte typeString=6;
	public static final byte typeBoolean=7;
	public static final byte typeFile=8;

	public static final int opLoginQQ = 0;
	public static final int opLoginNick = 1;
	public static final int opPrivateMsg = 2;
	public static final int opGroupMsg = 3;
	public static final int opDiscussMsg = 4;
	public static final int opDeleteMsg = 5;
	public static final int opSendLike = 6;
	public static final int opCookies = 7;
	public static final int opCsrfToken = 8;
	public static final int opRecord = 9;
	public static final int opGroupKick = 10;
	public static final int opGroupBan = 11;
	public static final int opGroupAdmin = 12;
	public static final int opGroupWholeBan = 13;
	public static final int opGroupAnonymousBan = 14;
	public static final int opGroupAnonymous = 15;
	public static final int opGroupCard = 16;
	public static final int opGroupLeave = 17;
	public static final int opGroupSpecialTitle = 18;
	public static final int opGroupMemberInfo = 19;
	public static final int opDiscussLeave = 20;
	public static final int opFriendAddRequest = 21;
	public static final int opGroupMemberList = 22;
	public static final int opGroupList = 23;
	public static final int heardBeat = 24;

	public static final int onGroupMsg = 25;
	public static final int onPerSecMsgInfo=26;
	public static final int getConfig = 27;

	public static final int opAddQuestion = 28;
	public static final int opAllQuestion = 29;
	public static final int opSetQuestion = 30;
	public static final int opQuestionPic = 31;
	public static final int opTextNotify = 32;

	public static final int opEnableFunction=33;

	public static final int addGroup=34;
	public static final int addNotReplyUser = 35;
	public static final int addNotReplyWord = 36;
	public static final int addPersonInfo = 37;
	public static final int addMaster = 38;
	public static final int addAdmin = 39;
	public static final int addGroupAllow = 40 ;
	public static final int addBlackQQ = 41;
	public static final int addBlackGroup = 42;
	public static final int removeGroup = 43;
	public static final int removeNotReplyUser = 44;
	public static final int removeNotReplyWord = 45;
	public static final int removePersonInfo = 46;
	public static final int removeMaster = 47;
	public static final int removeAdmin = 48;
	public static final int removeGroupAllow = 49;
	public static final int removeBlackQQ = 50;
	public static final int removeBlackGroup = 51;
	public static final int setPersonInfo = 52;
	public static final int opGetApp = 53;
	public static final int opCrashLog = 54;
	public static final int opUploadApk = 55;
	public static final int sendToMaster = 56;
	public static final int getIdFromHash = 57;
	public static BotDataPack encode(int opCode) {
		return new BotDataPack(opCode);
	}

	public static BotDataPack decode(byte[] bytes) {
		return new BotDataPack(bytes);
	}

	private BotDataPack(int opCode) {
		//length(4) version(2) opCode(4)
		writeByteDataIntoArray(Tools.BitConverter.getBytes(0));
		writeByteDataIntoArray(Tools.BitConverter.getBytes((short)1));
		writeByteDataIntoArray(Tools.BitConverter.getBytes(opCode));
	}   

	private BotDataPack(byte[] pack) {
		dataArray = pack;
		dataPointer = headLength;
	} 

	public byte[] getData() {
		byte[] retData=new byte[data.size()];
		for (int i=0;i < data.size();++i) {
			retData[i] = data.get(i);
		}
		byte[] len=Tools.BitConverter.getBytes(retData.length);
		retData[0] = len[0];
		retData[1] = len[1];
		retData[2] = len[2];
		retData[3] = len[3];
		dataArray = retData;
		return retData;
	}

	public int getLength() {
		return Tools.BitConverter.toInt(dataArray, 0);
	}  

	public short getVersion() {
		return Tools.BitConverter.toShort(dataArray, 4);
	}

	public int getOpCode() {
		return Tools.BitConverter.toShort(dataArray, 6);
	}

	private BotDataPack writeByteDataIntoArray(byte... bs) {
		for (byte b:bs) {
			data.add(b);
			++dataPointer;
		}
		return this;
	}

	public BotDataPack write(byte b) {
		writeByteDataIntoArray(typeByte);
		writeByteDataIntoArray(b);
		return this;
	}

	public BotDataPack write(short s) {
		writeByteDataIntoArray(typeShort);
		writeByteDataIntoArray(Tools.BitConverter.getBytes(s));
		return this;
	}

	public BotDataPack write(int i) {
		writeByteDataIntoArray(typeInt);
		writeByteDataIntoArray(Tools.BitConverter.getBytes(i));
		return this;
	}

	public BotDataPack write(long l) {
		writeByteDataIntoArray(typeLong);
		writeByteDataIntoArray(Tools.BitConverter.getBytes(l));
		return this;
	}

	public BotDataPack write(float f) {
		writeByteDataIntoArray(typeFloat);
		writeByteDataIntoArray(Tools.BitConverter.getBytes(f));
		return this;
	}

	public BotDataPack write(double d) {
		writeByteDataIntoArray(typeDouble);
		writeByteDataIntoArray(Tools.BitConverter.getBytes(d));
		return this;
	}

	public BotDataPack write(String s) {
		writeByteDataIntoArray(typeString);
		byte[] stringBytes = Tools.BitConverter.getBytes(s);
		write(stringBytes.length);
		writeByteDataIntoArray(stringBytes);
		return this;
	}

	public BotDataPack write(boolean b) {
		writeByteDataIntoArray(typeBoolean);
		writeByteDataIntoArray(b ?(byte)1: (byte)0);
		return this;
	}

	public BotDataPack write(File file) {
		FileInputStream fin=null;
		try {
			fin = new FileInputStream(file);
			byte[] bs=new byte[(int)file.length()];
			fin.read(bs, 0, bs.length);
			writeByteDataIntoArray(typeFile);
			write((int)file.length());
			writeByteDataIntoArray(bs);
			fin.close();
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
		return this;
	}

	public File readFile(File file) {
		if (dataArray[dataPointer++] == typeFile) {
			int fileLen=readInt();
			FileOutputStream fos=null;
			try {
				fos = new FileOutputStream(file);
				fos.write(dataArray, dataPointer, fileLen);
				fos.close();
			} catch (Exception e) {
				file.delete();
				file = null;
				e.printStackTrace();
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
			short s = Tools.BitConverter.toShort(dataArray, dataPointer);
			dataPointer += 2;
			return s;
		}
		throw new RuntimeException("not a short number");
	}

	public int readInt() {
		if (dataArray[dataPointer++] == typeInt) {
			int i= Tools.BitConverter.toInt(dataArray, dataPointer);
			dataPointer += 4;
			return i;
		}
		throw new RuntimeException("not a int number");
	}

	public long readLong() {
		if (dataArray[dataPointer++] == typeLong) {
			long l= Tools.BitConverter.toLong(dataArray, dataPointer);
			dataPointer += 8;
			return l;
		}
		throw new RuntimeException("not a long number");
	}

	public float readFloat() {
		if (dataArray[dataPointer++] == typeFloat) {
			float f = Tools.BitConverter.toFloat(dataArray, dataPointer);
			dataPointer += 4;
			return f;
		}
		throw new RuntimeException("not a float number");
	}

	public double readDouble() {
		if (dataArray[dataPointer++] == typeDouble) {
			double d = Tools.BitConverter.toDouble(dataArray, dataPointer);
			dataPointer += 8;
			return d;
		}
		throw new RuntimeException("not a double number");
	}

	public String readString() {
		try {
			if (dataArray[dataPointer++] == typeString) {
				int len = readInt();
				String s = Tools.BitConverter.toString(dataArray, dataPointer, len);
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
