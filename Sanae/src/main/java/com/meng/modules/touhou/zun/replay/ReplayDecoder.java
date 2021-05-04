package com.meng.modules.touhou.zun.replay;

import com.meng.tools.BitConverter;
import com.meng.tools.FileTool;
import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

public class ReplayDecoder {
    private static BinaryFile file;

    public static boolean ReadFile(ReplayEntry replay) {
        boolean status = false;
        if (replay.replay != null) {
            return true;
        } else {
            replay.replay = replay.new ReplayInfo();
        }

        file = new BinaryFile(FileTool.readBytes(replay.FullPath));
        //read first 4 bytes
        int hexIn;
        String hex = "";

        for (int i = 0; i < 4; i++) {

            if ((hexIn = file.ReadByte()) != -1) {
                hex +=  String.format("%2x", hexIn);
            } else {
                file.Close();
                return false;
            }
        }

        switch (hex) {
            case "54365250":
                //T6RP
                replay.replay.game = 0;
                status = Read_T6RP(replay.replay);
                break;
            case "54375250":
                //T7RP
                replay.replay.game = 1;
                status = Read_T7RP(replay.replay);
                break;
            case "54385250":
                //T8RP
                replay.replay.game = 2;
                status = Read_T8RP(replay.replay);
                break;
            case "54395250":
                //T9RP
                replay.replay.game = 3;
                status = Read_T9RP(replay.replay);
                break;
            case "74393572":
                //t95r
                replay.replay.game = 4;
                status = Read_t95r(replay.replay);
                break;
            case "74313072":
                //t10r
                replay.replay.game = 5;
                status = Read_t10r(replay.replay);
                break;
            case "74313172":
                //t11r
                replay.replay.game = 6;
                status = Read_t11r(replay.replay);
                break;
            case "74313272":
                //t12r
                replay.replay.game = 7;
                status = Read_t12r(replay.replay);
                break;
            case "74313235":
                //t125
                replay.replay.game = 8;
                status = Read_t125(replay.replay);
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
                status = Read_t13r(replay.replay);
                break;
            case "74313433":
                //t143
                replay.replay.game = 12;
                status = Read_t143(replay.replay);
                break;
            case "74313572":
                //t15r
                replay.replay.game = 13;
                status = Read_t15r(replay.replay);
                break;
            case "74313672":
                //t16r
                replay.replay.game = 14;
                status = Read_t16r(replay.replay);
                break;
            case "74313536":
                //t156
                //shouldn't this be 165? gg zun
                replay.replay.game = 15;
                status = Read_t156(replay.replay);
                break;
            case "74313772":
                //im guessing the full release will be t17r
                //judging by the trial's replay format, nothing has changed which is good
                replay.replay.game = 16;
                status = Read_t17r(replay.replay);
                break;
            case "74313874":
                //  change this to actual one on full game release
                replay.replay.game = 17;
                status = Read_t18t(replay.replay);
                break;
            default:
                break;
        }

        file.Close();
        return status;
    }

    private static long ReadUInt32() {
        return file.readUInt();
    }

    private static String ReadStringANSI() {
        StringBuilder builder = new StringBuilder();
        int[] buf = new int[3];
        buf[0] = file.ReadByte();
        buf[1] = file.ReadByte();
        if (buf[0] != 13 && buf[1] != 10) {
            buf[2] = file.ReadByte();
            do
            {
                builder.append((char)buf[0]);
                buf[0] = buf[1];
                buf[1] = buf[2];
                buf[2] = file.ReadByte();
            } while (buf[0] != 13 && buf[1] != 10);
        }
        file.Seek(-1, BinaryFile.SeekOrigin.Current);
        return builder.toString();
    }

    private static boolean JumpToUser(int loc) {
        file.Seek(loc, BinaryFile.SeekOrigin.Begin);
        long offset = ReadUInt32();
        file.Seek(offset, BinaryFile.SeekOrigin.Begin); 
        StringBuilder val = new StringBuilder();
        byte buf;
        for (int i = 0; i < 4; i++) {
            buf = file.ReadByte();
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

    private static boolean Read_T6RP(ReplayEntry.ReplayInfo replay) {
        //lookup table
        String[] chars = new String[] { "ReimuA", "ReimuB", "MarisaA", "MarisaB" };
        String[] difficulties = new String[] { "Easy", "Normal", "Hard", "Lunatic", "Extra" };


        int[] buf = new int[2];
        file.Seek(2, BinaryFile.SeekOrigin.Current);   //skip version number
        buf[0] = file.ReadByte();   //shot type
        replay.character = chars[buf[0]];
        buf[1] = file.ReadByte();   //difficulty
        replay.difficulty = difficulties[buf[1]];
        file.Seek(6, BinaryFile.SeekOrigin.Current);   //skip checksum to encryption key
        byte key = file.ReadByte();

        byte[] buffer = new byte[65];
        for (int i = 0; i < 65; i++) {
            buffer[i] = file.ReadByte();
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

    private static boolean Read_T7RP(ReplayEntry.ReplayInfo replay) {
        String[] chars = new String[] { "ReimuA", "ReimuB", "MarisaA", "MarisaB", "SakuyaA", "SakuyaB" };
        String[] difficulties = new String[] { "Easy", "Normal", "Hard", "Lunatic", "Extra", "Phantasm" };
        //raw data starts at 84
        byte[] buffer = new byte[file.Length];

        file.Seek(13, BinaryFile.SeekOrigin.Begin);
        byte key = file.ReadByte();
        file.Seek(0, BinaryFile.SeekOrigin.Begin);
        for (int i = 0; i < 16; i++) {
            buffer[i] = file.ReadByte();
        }
        for (int i = 16; i < file.Length; ++i) {
            buffer[i] = file.ReadByte();
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
        print(decodeData);
        System.out.println();
        file.Seek(0xcc - 0x54, BinaryFile.SeekOrigin.Begin);
        System.out.println(Integer.toHexString(0xcc - 0x54));

        replay.slow = BitConverter.getInstanceLittleEndian().toFloat(decodeData, 0x74) + "";
        System.out.println(new String(decodeData, 0, 1000));
        FileTool.saveFile(new File("/storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv/th7_03.rpyp"), decodeData);
        return true;
    }

    private static void print(byte[] bs) {
        int i = 0;
        for (byte b:bs) {
            System.out.print(Integer.toHexString(0xff & b));
            System.out.print(" ");
            if (i++ == 60) {
                System.out.println();
            }
            if (i > 1000) {
                break;
            }
        }
    }

    private static int get_bit(byte[] buffer, int[] pointer, byte[] filter, byte length) {
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

    private static long decompress(byte[] buffer, byte[] decode, long length) {
        //function rewritten in Java from https://github.com/Fluorohydride/threp/blob/master/common.cpp
        int[] pointer = {0};
        int dest = 0;
        int index;
        int bits;
        byte[] filter = {(byte) 0x80};
        byte[] dict = new byte[8208];   //0x2010
        while (pointer[0] < length) {
            bits = get_bit(buffer, pointer, filter, (byte)1);
            if (pointer[0] >= length) return dest;
            if (bits != 0) {
                bits = get_bit(buffer, pointer, filter, (byte)8);
                if (pointer[0] >= length) return dest;
                decode[dest] = (byte)bits;
                dict[dest & 0x1fff] = (byte)bits;
                dest++;
            } else {
                bits = get_bit(buffer, pointer, filter, (byte)13);
                if (pointer[0] >= length) return dest;
                index = bits - 1;
                bits = get_bit(buffer, pointer, filter, (byte)4);
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

    private static boolean Read_T8RP(ReplayEntry.ReplayInfo replay) {
        if (!JumpToUser(12)) return false;
        long length = ReadUInt32();
        file.Seek(17, BinaryFile.SeekOrigin.Current);
        replay.name = ReadStringANSI();
        file.Seek(11, BinaryFile.SeekOrigin.Current);
        replay.date = ReadStringANSI();
        file.Seek(9, BinaryFile.SeekOrigin.Current);
        replay.character = ReadStringANSI();
        file.Seek(8, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(ReadStringANSI());
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        file.Seek(8, BinaryFile.SeekOrigin.Current);
        replay.difficulty = ReadStringANSI();
        replay.stage = ReadStringANSI();
        //check if spell practice or game replay actually do this later
        return true;
    }

    private static boolean Read_T9RP(ReplayEntry.ReplayInfo replay) {
        if (!JumpToUser(12)) return false;
        long length = ReadUInt32();
        file.Seek(17, BinaryFile.SeekOrigin.Current);
        replay.name = ReadStringANSI();
        file.Seek(11, BinaryFile.SeekOrigin.Current);
        replay.date = ReadStringANSI();
        file.Seek(8, BinaryFile.SeekOrigin.Current);
        replay.difficulty = ReadStringANSI();
        file.Seek(8, BinaryFile.SeekOrigin.Current);
        replay.stage = ReadStringANSI();
        return true;
    }

    private static boolean Read_t95r(ReplayEntry.ReplayInfo replay) {
        if (!JumpToUser(12)) return false;
        long length = ReadUInt32();
        file.Seek(4, BinaryFile.SeekOrigin.Current);
        ReadStringANSI();
        ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = ReadStringANSI();
        replay.stage = ReadStringANSI() + " " + ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = ReadStringANSI();
        file.Seek(6, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(ReadStringANSI());
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);

        return true;
    }

    //  the replay user data format for touhou 10 through to 16 (and presumably from here on in) is identical
    private static boolean Read_t10r(ReplayEntry.ReplayInfo replay) {
        if (!JumpToUser(12)) return false;
        long length = ReadUInt32();
        file.Seek(4, BinaryFile.SeekOrigin.Current);
        ReadStringANSI();   //SJIS, 東方XYZ リプレイファイル情報, Touhou XYZ replay file info
        ReadStringANSI();   //Skip over game version info
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = ReadStringANSI();
        file.Seek(6, BinaryFile.SeekOrigin.Current);
        replay.character = ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.difficulty = ReadStringANSI();
        // file.Seek(6, BinaryFile.SeekOrigin.Current);
        replay.stage = ReadStringANSI();   //stage
        file.Seek(6, BinaryFile.SeekOrigin.Current);

        long scoreConv = Long.parseLong(ReadStringANSI()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    private static boolean Read_t11r(ReplayEntry.ReplayInfo replay) {
        return Read_t10r(replay);
    }

    private static boolean Read_t12r(ReplayEntry.ReplayInfo replay) {
        return Read_t10r(replay);
    }

    private static boolean Read_t125(ReplayEntry.ReplayInfo replay) {
        if (!JumpToUser(12)) return false;
        long length = ReadUInt32();
        file.Seek(4, BinaryFile.SeekOrigin.Current);
        ReadStringANSI();
        ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = ReadStringANSI();
        file.Seek(6, BinaryFile.SeekOrigin.Current);
        replay.character = ReadStringANSI();
        replay.stage = ReadStringANSI();
        file.Seek(6, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(ReadStringANSI());
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    private static boolean Read_128r(ReplayEntry.ReplayInfo replay) {
        if (!JumpToUser(12)) return false;
        long length = ReadUInt32();
        file.Seek(4, BinaryFile.SeekOrigin.Current);
        ReadStringANSI();
        ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = ReadStringANSI();
        file.Seek(6, BinaryFile.SeekOrigin.Current);
        replay.stage = ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.difficulty = ReadStringANSI();
        file.Seek(6, BinaryFile.SeekOrigin.Current);
        ReadStringANSI();   //stage
        file.Seek(6, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(ReadStringANSI()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    private static boolean Read_t13r(ReplayEntry.ReplayInfo replay) {
        if (!JumpToUser(12)) return false;
        long length = ReadUInt32();
        file.Seek(4, BinaryFile.SeekOrigin.Current);
        file.Seek(4, BinaryFile.SeekOrigin.Current);   //which game
        byte ver = (byte)file.ReadByte();
        if (ver == 144) {
            replay.game = 10;
        } else {
            replay.game = 11;
        }
        ReadStringANSI();   //SJIS, 東方XYZ リプレイファイル情報, Touhou XYZ replay file info
        ReadStringANSI();   //Skip over game version info
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = ReadStringANSI();
        file.Seek(6, BinaryFile.SeekOrigin.Current);
        replay.character = ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.difficulty = ReadStringANSI();
        file.Seek(6, BinaryFile.SeekOrigin.Current);
        replay.stage = ReadStringANSI();   //stage
        file.Seek(6, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(ReadStringANSI()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        file.Seek(10, BinaryFile.SeekOrigin.Current);
        replay.slow = ReadStringANSI();    
        return true;
    }

    private static boolean Read_t14r(ReplayEntry.ReplayInfo replay) {
        return Read_t10r(replay);
    }

    private static boolean Read_t143(ReplayEntry.ReplayInfo replay) {
        if (!JumpToUser(12)) return false;
        long length = ReadUInt32();
        file.Seek(4, BinaryFile.SeekOrigin.Current);
        ReadStringANSI();
        ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.name = ReadStringANSI();
        file.Seek(5, BinaryFile.SeekOrigin.Current);
        replay.date = ReadStringANSI();
        replay.stage = ReadStringANSI() + " " + ReadStringANSI();
        file.Seek(6, BinaryFile.SeekOrigin.Current);
        long scoreConv = Long.parseLong(ReadStringANSI()) * 10;  //replay stores the value without the 0
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(scoreConv);
        return true;
    }

    private static boolean Read_t15r(ReplayEntry.ReplayInfo replay) {
        return Read_t10r(replay);
    }

    private static boolean Read_t156(ReplayEntry.ReplayInfo replay) {
        return Read_t143(replay);
    }

    private static boolean Read_t16r(ReplayEntry.ReplayInfo replay) {
        return Read_t10r(replay);
    }

    private static boolean Read_t17r(ReplayEntry.ReplayInfo replay) {
        return Read_t10r(replay);
    }

    private static boolean Read_t18t(ReplayEntry.ReplayInfo replay) {
        return Read_t10r(replay);
    }

    public static class ReplayEntry {
        public String FullPath;
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
