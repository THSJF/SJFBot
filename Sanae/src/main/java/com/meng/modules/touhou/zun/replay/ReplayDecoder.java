package com.meng.modules.touhou.zun.replay;

import com.meng.tools.BitConverter;
import com.meng.tools.FileTool;
import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

public class ReplayDecoder {
    private final ReplayBinaryFile file;

    public ReplayDecoder(File replay) {
        file = new ReplayBinaryFile(FileTool.readBytes(replay));
    }

    private boolean jumpToUser() {
        file.seek(12, ReplayBinaryFile.SeekOrigin.Begin);
        long offset = file.readUInt();
        file.seek(offset, ReplayBinaryFile.SeekOrigin.Begin); 
        return file.readInt() == 0x52455355;
    }

    /*
     * Format of the Decoding Functions for Touhou 8 - onwards
     * 
     * Offset to USER info is stored at offset 0xC, length 4
     * Jump to offset, return false if exception thrown
     * Read USER to confirm correct jump, return false if not
     * 
     * Store the USER data length, it may come in handy?
     * Terminating bytes for a String are 0x0D, 0x0A
     * Jump to and read PLAYER NAME as String
     * Jump to and read DATE as String
     * Jump to and read CHARACTER as String, if the replay was encoded by a japanese copy of the game this reading will be gibberish
     *      - Implement a lookup table for each game to convert the hex into a readable String
     * Jump to and read SCORE as long, then format it to a String with thousand separators
     * Jump to and read DIFFICULTY as String
     * 
     * */

    public boolean readTh06(ZunReplay replay) {
        //lookup table
        String[] chars = new String[] { "ReimuA", "ReimuB", "MarisaA", "MarisaB" };
        String[] difficulties = new String[] { "Easy", "Normal", "Hard", "Lunatic", "Extra" };


        int[] buf = new int[2];
        file.seek(2, ReplayBinaryFile.SeekOrigin.Current);   //skip version number
        buf[0] = file.readByte();   //shot type
        replay.character = chars[buf[0]];
        buf[1] = file.readByte();   //difficulty
        replay.difficulty = difficulties[buf[1]];
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);   //skip checksum to encryption key
        byte key = file.readByte();

        byte[] buffer = new byte[65];
        for (int i = 0; i < 65; i++) {
            buffer[i] = file.readByte();
            buffer[i] -= key;
            key += 7;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < 10; i++) {
            if (buffer[i] == 0x00) {
                i = 10;
            } else {
                builder.append((char)buffer[i]);
            }
        }
        replay.date = builder.toString();
        builder.setLength(0);
        for (int i = 10; i < 19; i++) {
            if (buffer[i] == 0x00) {
                i = 19; 
            } else {
                builder.append((char)buffer[i]);
            }
        }
        replay.name = builder.toString();

        long score = BitConverter.getInstanceLittleEndian().toUInt(buffer, 21);
        replay.score = NumberFormat.getInstance().format(score);

        return true;
    }

    public boolean readTh07(ZunReplay replay) {
        String[] chars = new String[] { "ReimuA", "ReimuB", "MarisaA", "MarisaB", "SakuyaA", "SakuyaB" };
        String[] difficulties = new String[] { "Easy", "Normal", "Hard", "Lunatic", "Extra", "Phantasm" };
        //raw data starts at 84
        byte[] buffer = new byte[file.Length];

        file.seek(13, ReplayBinaryFile.SeekOrigin.Begin);
        byte key = file.readByte();
        file.seek(0, ReplayBinaryFile.SeekOrigin.Begin);
        for (int i = 0; i < 16; i++) {
            buffer[i] = file.readByte();
        }
        for (int i = 16; i < file.Length; ++i) {
            buffer[i] = file.readByte();
            buffer[i] -= key;
            key += 7;
        }
        BitConverter converter = BitConverter.getInstanceLittleEndian();
        long length = converter.toUInt(buffer, 20);
        long dlength = converter.toUInt(buffer, 24);
        byte[] rawData = new byte[file.Length];
        System.arraycopy(buffer, 0x54, rawData, 0, buffer.length - 0x54);
        byte[] decodeData = new byte[(int)dlength];

        long rlength = decompress(rawData, decodeData, length);

        replay.character = chars[decodeData[2]];
        replay.difficulty = difficulties[decodeData[3]];
        StringBuilder builder = new StringBuilder();
        for (int i = 4; i < 9; i++) {
            builder.append((char)decodeData[i]);
        }
        replay.date = builder.toString();
        replay.date = new String(decodeData, 4, 5);
        builder.setLength(0);
        for (int i = 10; i < 18; i++) {
            builder.append((char)decodeData[i]);
        }
        replay.name = builder.toString();
        long score = 0;
        score = BitConverter.getInstanceLittleEndian().toUInt(decodeData, 24);
        score *= 10;
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(score);
        file.seek(0xcc - 0x54, ReplayBinaryFile.SeekOrigin.Begin);

        replay.slow = BitConverter.getInstanceLittleEndian().toFloat(decodeData, 0x74) + "";
        return true;
    }

    private int getBit(byte[] buffer, int[] pointer, byte[] filter, byte length) {
        //function rewritten in Java from https://github.com/Fluorohydride/threp/blob/master/common.cpp
        int result = 0;
        byte current = buffer[pointer[0]];
        for (byte i = 0; i < length; ++i) {
            result <<= 1;
            if ((current & filter[0]) != 0x00) {
                result |= 0x01;
            }
            filter[0] = (byte)((0xFF & filter[0]) >>> 1);
            if (filter[0] == 0) {
                current = buffer[++pointer[0]];
                filter[0] = (byte) 0x80;
            }
        }
        return result;
    }

    private long decompress(byte[] buffer, byte[] decode, long length) {
        //function rewritten in Java from https://github.com/Fluorohydride/threp/blob/master/common.cpp
        int[] pointer = {0};
        int dest = 0;
        int index;
        int bits;
        byte[] filter = {(byte) 0x80};
        byte[] dict = new byte[8208];   //0x2010
        while (pointer[0] < length) {
            bits = getBit(buffer, pointer, filter, (byte)1);
            if (pointer[0] >= length) return dest;
            if (bits != 0) {
                bits = getBit(buffer, pointer, filter, (byte)8);
                if (pointer[0] >= length) return dest;
                decode[dest] = (byte)bits;
                dict[dest & 0x1fff] = (byte)bits;
                dest++;
            } else {
                bits = getBit(buffer, pointer, filter, (byte)13);
                if (pointer[0] >= length) return dest;
                index = bits - 1;
                bits = getBit(buffer, pointer, filter, (byte)4);
                if (pointer[0] >= length) return dest;
                bits += 3;
                for (int i = 0; i < bits; i++) {
                    dict[dest & 0x1fff] = dict[index + i];
                    decode[dest] = dict[index + i];
                    dest++;
                }
            }
        }
        return dest;
    }

    public boolean readTh08(ZunReplay replay) {
        if (!jumpToUser()) return false;
        long length = file.readUInt();
        file.seek(17, ReplayBinaryFile.SeekOrigin.Current);
        replay.name = file.readString();
        file.seek(11, ReplayBinaryFile.SeekOrigin.Current);
        replay.date = file.readString();
        file.seek(9, ReplayBinaryFile.SeekOrigin.Current);
        replay.character = file.readString();
        file.seek(8, ReplayBinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(file.readString());
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        file.seek(8, ReplayBinaryFile.SeekOrigin.Current);
        replay.difficulty = file.readString();
        replay.stage = file.readString();
        //check if spell practice or game replay actually do this later
        return true;
    }

    public boolean readTh09(ZunReplay replay) {
        if (!jumpToUser()) return false;
        long length = file.readUInt();
        file.seek(17, ReplayBinaryFile.SeekOrigin.Current);
        replay.name = file.readString();
        file.seek(11, ReplayBinaryFile.SeekOrigin.Current);
        replay.date = file.readString();
        file.seek(8, ReplayBinaryFile.SeekOrigin.Current);
        replay.difficulty = file.readString();
        file.seek(8, ReplayBinaryFile.SeekOrigin.Current);
        replay.stage = file.readString();
        return true;
    }

    public boolean readTh095(ZunReplay replay) {
        if (!jumpToUser()) return false;
        long length = file.readUInt();
        file.seek(4, ReplayBinaryFile.SeekOrigin.Current);
        file.readString();
        file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.name = file.readString();
        replay.stage = file.readString() + " " + file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.date = file.readString();
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(file.readString());
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);

        return true;
    }

    //  the replay user data format for touhou 10 through to 16 (and presumably from here on in) is identical
    public boolean readTh10(ZunReplay replay) {
        if (!jumpToUser()) return false;
        long length = file.readUInt();
        file.seek(4, ReplayBinaryFile.SeekOrigin.Current);
        file.readString();   //SJIS, 東方XYZ リプレイファイル情報, Touhou XYZ replay file info
        file.readString();   //Skip over game version info
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.name = file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.date = file.readString();
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        replay.character = file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.difficulty = file.readString();
        // file.Seek(6, BinaryFile.SeekOrigin.Current);
        replay.stage = file.readString();   //stage
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);

        long scoreConv = Long.parseLong(file.readString()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    public boolean readTh11(ZunReplay replay) {
        return readTh10(replay);
    }

    public boolean readTh12(ZunReplay replay) {
        return readTh10(replay);
    }

    public boolean readTh125(ZunReplay replay) {
        if (!jumpToUser()) return false;
        long length = file.readUInt();
        file.seek(4, ReplayBinaryFile.SeekOrigin.Current);
        file.readString();
        file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.name = file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.date = file.readString();
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        replay.character = file.readString();
        replay.stage = file.readString();
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(file.readString());
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    public boolean readTh128(ZunReplay replay) {
        if (!jumpToUser()) return false;
        long length = file.readUInt();
        file.seek(4, ReplayBinaryFile.SeekOrigin.Current);
        file.readString();
        file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.name = file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.date = file.readString();
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        replay.stage = file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.difficulty = file.readString();
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        file.readString();   //stage
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(file.readString()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    public boolean readTh13(ZunReplay replay) {
        if (!jumpToUser()) return false;
        long length = file.readUInt();
        file.seek(4, ReplayBinaryFile.SeekOrigin.Current);
        file.seek(4, ReplayBinaryFile.SeekOrigin.Current);   //which game
        byte ver = file.readByte();
        if (ver == 144) {
            replay.game = 10;
        } else {
            replay.game = 11;
        }
        file.readString();   //SJIS, 東方XYZ リプレイファイル情報, Touhou XYZ replay file info
        file.readString();   //Skip over game version info
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.name = file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.date = file.readString();
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        replay.character = file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.difficulty = file.readString();
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        replay.stage = file.readString();   //stage
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(file.readString()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        file.seek(10, ReplayBinaryFile.SeekOrigin.Current);
        replay.slow = file.readString();    
        return true;
    }

    public boolean readTh14(ZunReplay replay) {
        return readTh10(replay);
    }

    public boolean readTh143(ZunReplay replay) {
        if (!jumpToUser()) return false;
        long length = file.readUInt();
        file.seek(4, ReplayBinaryFile.SeekOrigin.Current);
        file.readString();
        file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.name = file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.date = file.readString();
        replay.stage = file.readString() + " " + file.readString();
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(file.readString()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    public boolean readTh15(ZunReplay replay) {
        return readTh10(replay);
    }

    public boolean readTh165(ZunReplay replay) {
        return readTh143(replay);
    }

    public boolean readTh16(ZunReplay replay) {
        return readTh10(replay);
    }

    public boolean readTh17(ZunReplay replay) {
        return readTh10(replay);
    }

    public boolean readTh18(ZunReplay replay) {
        return readTh10(replay);
    }

    public static class ReplayBinaryFile {

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

        public void seek(long l, SeekOrigin origin) {
            if (origin == SeekOrigin.Current) {
                position += l;
            } else if (origin == SeekOrigin.Begin) {
                position = (int)l;
            }
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
}
