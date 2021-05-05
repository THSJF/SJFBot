package com.meng.modules.touhou.zun.replay;

import com.meng.tools.BitConverter;
import com.meng.tools.FileTool;
import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

public class ReplayDecoder {
    private BinaryFile file;

    public boolean readFile(ReplayEntry replay) {
        boolean status = false;
        if (replay.replay != null) {
            return true;
        } else {
            replay.replay = replay.new ReplayInfo();
        }
        file = new BinaryFile(FileTool.readBytes(replay.fullPath));
        //read first 4 bytes
        int hexIn;
        String hex = "";
        for (int i = 0; i < 4; i++) {
            if ((hexIn = file.readByte()) != -1) {
                hex +=  String.format("%2x", hexIn);
            } else {
                return false;
            }
        }
        switch (hex) {
            case "54365250":
                //T6RP
                replay.replay.game = 0;
                status = read_T6RP(replay.replay);
                break;
            case "54375250":
                //T7RP
                replay.replay.game = 1;
                status = read_T7RP(replay.replay);
                break;
            case "54385250":
                //T8RP
                replay.replay.game = 2;
                status = read_T8RP(replay.replay);
                break;
            case "54395250":
                //T9RP
                replay.replay.game = 3;
                status = read_T9RP(replay.replay);
                break;
            case "74393572":
                //t95r
                replay.replay.game = 4;
                status = read_T95r(replay.replay);
                break;
            case "74313072":
                //t10r
                replay.replay.game = 5;
                status = read_T10r(replay.replay);
                break;
            case "74313172":
                //t11r
                replay.replay.game = 6;
                status = read_T11r(replay.replay);
                break;
            case "74313272":
                //t12r
                replay.replay.game = 7;
                status = read_T12r(replay.replay);
                break;
            case "74313235":
                //t125
                replay.replay.game = 8;
                status = read_T125(replay.replay);
                break;
            case "31323872":
                //128r
                replay.replay.game = 9;
                status = Read_128r(replay.replay);
                break;
            case "74313372":
                //t13r
                //has both td and ddc for some fucking reason
                //since im reading the user data at the end though it doesnt matter
                status = read_T13r(replay.replay);
                break;
            case "74313433":
                //t143
                replay.replay.game = 12;
                status = read_T143(replay.replay);
                break;
            case "74313572":
                //t15r
                replay.replay.game = 13;
                status = read_T15r(replay.replay);
                break;
            case "74313672":
                //t16r
                replay.replay.game = 14;
                status = read_T16r(replay.replay);
                break;
            case "74313536":
                //t156
                //shouldn't this be 165? gg zun
                replay.replay.game = 15;
                status = read_T156(replay.replay);
                break;
            case "74313772":
                //im guessing the full release will be t17r
                //judging by the trial's replay format, nothing has changed which is good
                replay.replay.game = 16;
                status = read_T17r(replay.replay);
                break;
            case "74313874":
                //  change this to actual one on full game release
                replay.replay.game = 17;
                status = read_T18t(replay.replay);
                break;
            default:
                break;
        }
        return status;
    }

    private long readUInt32() {
        return file.readUInt();
    }

    private String readStringANSI() {
        StringBuilder builder = new StringBuilder();
        int[] buf = new int[3];
        buf[0] = file.readByte();
        buf[1] = file.readByte();
        if (buf[0] != 13 && buf[1] != 10) {
            buf[2] = file.readByte();
            do {
                builder.append((char)buf[0]);
                buf[0] = buf[1];
                buf[1] = buf[2];
                buf[2] = file.readByte();
            } while (buf[0] != 13 && buf[1] != 10);
        }
        file.seek(-1, BinaryFile.SeekOrigin.Current);
        return builder.toString();
    }

    private boolean jumpToUser(int loc) {
        file.seek(loc, BinaryFile.SeekOrigin.Begin);
        long offset = readUInt32();
        file.seek(offset, BinaryFile.SeekOrigin.Begin); 
        StringBuilder val = new StringBuilder();
        byte buf;
        for (int i = 0; i < 4; i++) {
            buf = file.readByte();
            val.append(String.format("%2x", buf));
        }
        return val.toString().equals("55534552") ? true : false;
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

    private boolean read_T6RP(ReplayEntry.ReplayInfo replay) {
        //lookup table
        String[] chars = new String[] { "ReimuA", "ReimuB", "MarisaA", "MarisaB" };
        String[] difficulties = new String[] { "Easy", "Normal", "Hard", "Lunatic", "Extra" };


        int[] buf = new int[2];
        file.seek(2, BinaryFile.SeekOrigin.Current);   //skip version number
        buf[0] = file.readByte();   //shot type
        replay.character = chars[buf[0]];
        buf[1] = file.readByte();   //difficulty
        replay.difficulty = difficulties[buf[1]];
        file.seek(6, BinaryFile.SeekOrigin.Current);   //skip checksum to encryption key
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

    private boolean read_T7RP(ReplayEntry.ReplayInfo replay) {
        String[] chars = new String[] { "ReimuA", "ReimuB", "MarisaA", "MarisaB", "SakuyaA", "SakuyaB" };
        String[] difficulties = new String[] { "Easy", "Normal", "Hard", "Lunatic", "Extra", "Phantasm" };
        //raw data starts at 84
        byte[] buffer = new byte[file.Length];

        file.seek(13, BinaryFile.SeekOrigin.Begin);
        byte key = file.readByte();
        file.seek(0, BinaryFile.SeekOrigin.Begin);
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
        file.seek(0xcc - 0x54, BinaryFile.SeekOrigin.Begin);

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

    private boolean read_T8RP(ReplayEntry.ReplayInfo replay) {
        if (!jumpToUser(12)) return false;
        long length = readUInt32();
        file.seek(17, BinaryFile.SeekOrigin.Current);
        replay.name = readStringANSI();
        file.seek(11, BinaryFile.SeekOrigin.Current);
        replay.date = readStringANSI();
        file.seek(9, BinaryFile.SeekOrigin.Current);
        replay.character = readStringANSI();
        file.seek(8, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(readStringANSI());
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        file.seek(8, BinaryFile.SeekOrigin.Current);
        replay.difficulty = readStringANSI();
        replay.stage = readStringANSI();
        //check if spell practice or game replay actually do this later
        return true;
    }

    private boolean read_T9RP(ReplayEntry.ReplayInfo replay) {
        if (!jumpToUser(12)) return false;
        long length = readUInt32();
        file.seek(17, BinaryFile.SeekOrigin.Current);
        replay.name = readStringANSI();
        file.seek(11, BinaryFile.SeekOrigin.Current);
        replay.date = readStringANSI();
        file.seek(8, BinaryFile.SeekOrigin.Current);
        replay.difficulty = readStringANSI();
        file.seek(8, BinaryFile.SeekOrigin.Current);
        replay.stage = readStringANSI();
        return true;
    }

    private boolean read_T95r(ReplayEntry.ReplayInfo replay) {
        if (!jumpToUser(12)) return false;
        long length = readUInt32();
        file.seek(4, BinaryFile.SeekOrigin.Current);
        readStringANSI();
        readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = readStringANSI();
        replay.stage = readStringANSI() + " " + readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = readStringANSI();
        file.seek(6, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(readStringANSI());
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);

        return true;
    }

    //  the replay user data format for touhou 10 through to 16 (and presumably from here on in) is identical
    private boolean read_T10r(ReplayEntry.ReplayInfo replay) {
        if (!jumpToUser(12)) return false;
        long length = readUInt32();
        file.seek(4, BinaryFile.SeekOrigin.Current);
        readStringANSI();   //SJIS, 東方XYZ リプレイファイル情報, Touhou XYZ replay file info
        readStringANSI();   //Skip over game version info
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = readStringANSI();
        file.seek(6, BinaryFile.SeekOrigin.Current);
        replay.character = readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.difficulty = readStringANSI();
        // file.Seek(6, BinaryFile.SeekOrigin.Current);
        replay.stage = readStringANSI();   //stage
        file.seek(6, BinaryFile.SeekOrigin.Current);

        long scoreConv = Long.parseLong(readStringANSI()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    private boolean read_T11r(ReplayEntry.ReplayInfo replay) {
        return read_T10r(replay);
    }

    private boolean read_T12r(ReplayEntry.ReplayInfo replay) {
        return read_T10r(replay);
    }

    private boolean read_T125(ReplayEntry.ReplayInfo replay) {
        if (!jumpToUser(12)) return false;
        long length = readUInt32();
        file.seek(4, BinaryFile.SeekOrigin.Current);
        readStringANSI();
        readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = readStringANSI();
        file.seek(6, BinaryFile.SeekOrigin.Current);
        replay.character = readStringANSI();
        replay.stage = readStringANSI();
        file.seek(6, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(readStringANSI());
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    private boolean Read_128r(ReplayEntry.ReplayInfo replay) {
        if (!jumpToUser(12)) return false;
        long length = readUInt32();
        file.seek(4, BinaryFile.SeekOrigin.Current);
        readStringANSI();
        readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = readStringANSI();
        file.seek(6, BinaryFile.SeekOrigin.Current);
        replay.stage = readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.difficulty = readStringANSI();
        file.seek(6, BinaryFile.SeekOrigin.Current);
        readStringANSI();   //stage
        file.seek(6, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(readStringANSI()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    private boolean read_T13r(ReplayEntry.ReplayInfo replay) {
        if (!jumpToUser(12)) return false;
        long length = readUInt32();
        file.seek(4, BinaryFile.SeekOrigin.Current);
        file.seek(4, BinaryFile.SeekOrigin.Current);   //which game
        byte ver = (byte)file.readByte();
        if (ver == 144) {
            replay.game = 10;
        } else {
            replay.game = 11;
        }
        readStringANSI();   //SJIS, 東方XYZ リプレイファイル情報, Touhou XYZ replay file info
        readStringANSI();   //Skip over game version info
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = readStringANSI();
        file.seek(6, BinaryFile.SeekOrigin.Current);
        replay.character = readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.difficulty = readStringANSI();
        file.seek(6, BinaryFile.SeekOrigin.Current);
        replay.stage = readStringANSI();   //stage
        file.seek(6, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(readStringANSI()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        file.seek(10, BinaryFile.SeekOrigin.Current);
        replay.slow = readStringANSI();    
        return true;
    }

    private boolean read_T14r(ReplayEntry.ReplayInfo replay) {
        return read_T10r(replay);
    }

    private boolean read_T143(ReplayEntry.ReplayInfo replay) {
        if (!jumpToUser(12)) return false;
        long length = readUInt32();
        file.seek(4, BinaryFile.SeekOrigin.Current);
        readStringANSI();
        readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = readStringANSI();
        file.seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = readStringANSI();
        replay.stage = readStringANSI() + " " + readStringANSI();
        file.seek(6, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(readStringANSI()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    private boolean read_T15r(ReplayEntry.ReplayInfo replay) {
        return read_T10r(replay);
    }

    private boolean read_T156(ReplayEntry.ReplayInfo replay) {
        return read_T143(replay);
    }

    private boolean read_T16r(ReplayEntry.ReplayInfo replay) {
        return read_T10r(replay);
    }

    private boolean read_T17r(ReplayEntry.ReplayInfo replay) {
        return read_T10r(replay);
    }

    private boolean read_T18t(ReplayEntry.ReplayInfo replay) {
        return read_T10r(replay);
    }

    public static class ReplayEntry {
        public String fullPath;
        public ReplayInfo replay;

        public class ReplayInfo {
            public int game;
            public String name;
            public String date;
            public String character;
            public String difficulty;
            public String stage;
            public String score;
            public String slow;

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(name).append(" ")
                    .append(date).append(" ")
                    .append(character).append(" ")
                    .append(difficulty).append(" ")
                    .append(stage).append(" ")
                    .append(score).append(" ")
                    .append(slow);
                return sb.toString();
            }
        }
    }
}
