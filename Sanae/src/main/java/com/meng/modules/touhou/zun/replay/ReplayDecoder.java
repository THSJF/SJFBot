package com.meng.modules.touhou.zun.replay;

import com.meng.modules.touhou.gameData.TH06Data;
import com.meng.modules.touhou.gameData.TH07Data;
import com.meng.modules.touhou.gameData.TH08Data;
import com.meng.modules.touhou.gameData.TH10Data;
import com.meng.modules.touhou.gameData.TH11Data;
import com.meng.modules.touhou.gameData.TH12Data;
import com.meng.modules.touhou.gameData.TH13Data;
import com.meng.modules.touhou.gameData.TH14Data;
import com.meng.modules.touhou.gameData.TH15Data;
import com.meng.modules.touhou.gameData.TH16Data;
import com.meng.modules.touhou.gameData.TH17Data;
import com.meng.modules.touhou.gameData.TH18Data;
import com.meng.tools.BitConverter;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import java.io.File;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

public class ReplayDecoder {

    private final BitConverter converter = BitConverter.getInstanceLittleEndian();

    public ReplayResult read(File replayFile) {
        ReplayBinaryFile file = new ReplayBinaryFile(FileTool.readBytes(replayFile));
        switch (FileFormat.getFileType(file.file).describe) {
            case rpy_th06_replay:
                return readTh06(file);
            case rpy_th07_replay:
                return readTh07(file);
            case rpy_th08_replay:
                return readTh08(file);
            case rpy_th09_replay:
                return readTh09(file);
            case rpy_th09_5_replay:
                return readTh095(file);
            case rpy_th10_replay:
                return readTh10(file);
            case rpy_th11_replay:
                return readTh11(file);
            case rpy_th12_replay:
                return readTh12(file);
            case rpy_th12_5_replay:
                return readTh125(file);
            case rpy_th12_8_replay:
                return readTh128(file);
            case rpy_th13_th14_replay:
                return readTh13Th14(file);
            case rpy_th14_3_replay:
                return readTh143(file);
            case rpy_th15_replay:
                return readTh15(file);
            case rpy_th16_replay:
                return readTh16(file);
            case rpy_th16_5_replay:
                return readTh165(file);
            case rpy_th17_replay:
                return readTh17(file);
            case rpy_th18_replay:
                return readTh18(file);
        }
        return null;
    }

    private ReplayResult readTh06(ReplayBinaryFile file) {
        ReplayResult replay = new ReplayResult(TH06Data.getInstance().getNameFull());
        String[] difficulties = { "Easy", "Normal", "Hard", "Lunatic", "Extra" };
        readThprac(file, replay);
        byte[] buffer = file.file;
        replay.character = TH06Data.getInstance().getPlayerType(buffer[0x06]);
        replay.difficulty = difficulties[buffer[0x07]];
        byte key = buffer[0x0e];
        for (int i = 0x0f; i < buffer.length; i++) {
            buffer[i] -= key;
            key += 7;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0x10; buffer[i] != 0x00; i++) {
            builder.append((char)buffer[i]);
        }
        replay.date = builder + "";
        builder.setLength(0);
        for (int i = 0x19; buffer[i] != 0x00; i++) {
            builder.append((char)buffer[i]);
        }
        replay.name = builder + "";
        replay.score = converter.toInt(buffer, 0x24) * 10 + "";
        int[] score_offsets = new int[7];
        int max_stage = 0;
        for (int i = 0; i < 7; i++) {
            score_offsets[i] = converter.toInt(buffer, (0x34 + 4 * i));
            if (score_offsets[i] != 0x00) {
                max_stage = i;
            }
        }
        if (max_stage == 6) {
            replay.splits = new ReplayResult.ReplaySplits[1];
            int offset = score_offsets[6];
            replay.splits[0] = new ReplayResult.ReplaySplits();
            replay.splits[0].stage = 7;
            replay.splits[0].score = converter.toInt(buffer, offset);
            replay.splits[0].power = buffer[offset + 0x8] + "";
            replay.splits[0].lives = buffer[offset + 0x9] + "";
            replay.splits[0].bombs = buffer[offset + 0xa] + "";
            replay.splits[0].additional = "Rank: " + buffer[offset + 0xb];
        } else {
            max_stage += 1;
            replay.splits = new ReplayResult.ReplaySplits[max_stage];
            for (int i = 0; i < max_stage; i++) {
                int offset = score_offsets[i];
                if (offset != 0x00) {
                    replay.splits[i] = new ReplayResult.ReplaySplits();
                    replay.splits[i].stage = i + 1;
                    replay.splits[i].score = converter.toInt(buffer, offset);
                    replay.splits[i].power = buffer[offset + 0x8] + "";
                    replay.splits[i].lives = buffer[offset + 0x9] + "";
                    replay.splits[i].bombs = buffer[offset + 0xa] + "";
                    replay.splits[i].additional = "Rank: " + buffer[offset + 0xb];
                }
            }
        }
        return replay;
    }

    private ReplayResult readTh07(ReplayBinaryFile file) {
        ReplayResult replay = new ReplayResult(TH07Data.getInstance().getNameFull());
        readThprac(file, replay);
        String[] difficulties = new String[] { "Easy", "Normal", "Hard", "Lunatic", "Extra", "Phantasm" };
        //raw data starts at 84
        byte[] buffer = file.file;
        byte key = buffer[0x0d];
        for (int i = 16; i < buffer.length; ++i) {
            buffer[i] -= key;
            key += 7;
        }
        long length = converter.toInt(buffer, 20);
        long dlength = converter.toInt(buffer, 24);
        int[] score_offsets = new int[7];
        int max_stage = 0;
        for (int i = 0; i < 7; i++) {
            score_offsets[i] = converter.toInt(buffer, 0x1c + 4 * i);
            if (score_offsets[i] != 0x00) {
                max_stage = i;
            }
        }
        System.arraycopy(buffer, 0x54, buffer, 0, buffer.length - 0x54);
        byte[] decodeData = new byte[(int)dlength];
        decompress(buffer, decodeData, length);
        replay.character = TH07Data.getInstance().getPlayerType(decodeData[2]);
        replay.difficulty = difficulties[decodeData[3]];
        StringBuilder builder = new StringBuilder();
        for (int i = 4; i < 9; i++) {
            builder.append((char)decodeData[i]);
        }
        replay.date = builder + "";
        builder.setLength(0);
        for (int i = 10; i < 18; i++) {
            builder.append((char)decodeData[i]);
        }
        replay.name = builder + "";
        replay.score = converter.toInt(decodeData, 24) * 10 + "";
        if (max_stage == 6) {
            replay.splits = new ReplayResult.ReplaySplits[1];
            int offset = score_offsets[6];
            replay.splits[0] = new ReplayResult.ReplaySplits();
            replay.splits[0].stage = 7;
            replay.splits[0].score = converter.toInt(decodeData, offset); //  stored as end of stage score in pcb
            replay.splits[0].piv = converter.toInt(decodeData, offset + 0x8);// + "/" + converter.toInt(decodeData, offset + 0xc);
            replay.splits[0].additional = "Point Items: " + converter.toInt(decodeData, offset + 0x4) + " | CherryMAX: " + converter.toInt(decodeData, offset + 0xc);
            replay.splits[0].graze = converter.toInt(decodeData, offset + 0x14);
            replay.splits[0].power = (0xFF & decodeData[offset + 0x22]) + "";
            replay.splits[0].lives = decodeData[offset + 0x23] + "";
            replay.splits[0].bombs = decodeData[offset + 0x24] + "";
        } else {
            if (max_stage == 5) {
                replay.stage = "All Clear";
            } else {
                replay.stage = "Stage " + (max_stage + 1);
            }
            max_stage += 1;
            replay.splits = new ReplayResult.ReplaySplits[max_stage];
            for (int i = 0; i < max_stage; i++) {
                if (score_offsets[i] != 0x00) {
                    replay.splits[i] = new ReplayResult.ReplaySplits();
                    int offset = score_offsets[i] - 0x54;
                    replay.splits[i].stage = i + 1;
                    replay.splits[i].score = converter.toInt(decodeData, offset); //  stored as end of stage score in pcb
                    replay.splits[i].piv = converter.toInt(decodeData, offset + 0x8);// + "/" + converter.toInt(decodeData, offset + 0xc);
                    replay.splits[i].additional = "Point Items: " + converter.toInt(decodeData, offset + 0x4) + " | CherryMAX: " + converter.toInt(decodeData, offset + 0xc);
                    replay.splits[i].graze = converter.toInt(decodeData, offset + 0x14);
                    replay.splits[i].power = (0xFF & decodeData[offset + 0x22]) + "";
                    replay.splits[i].lives = decodeData[offset + 0x23] + "";
                    replay.splits[i].bombs = decodeData[offset + 0x24] + "";
                }
            }
        }
        return replay;
    }

    private ReplayResult readTh08(ReplayBinaryFile file) {
        if (!jumpToUser(file)) return null;
        ReplayResult replay = new ReplayResult(TH08Data.getInstance().getNameFull());
        readThprac(file, replay);
        file.readInt();
        replay.name = file.seek(17, ReplayBinaryFile.SeekOrigin.Current).readString();
        replay.date = file.seek(11, ReplayBinaryFile.SeekOrigin.Current).readString();
        replay.character = file.seek(9, ReplayBinaryFile.SeekOrigin.Current).readString();
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(Long.parseLong(file.seek(8, ReplayBinaryFile.SeekOrigin.Current).readString()));
        replay.difficulty = file.seek(8, ReplayBinaryFile.SeekOrigin.Current).readString();
        replay.stage = file.readString();
        //check if spell practice or game replay actually do this later
        byte[] buffer = file.file;
        byte key = buffer[0x15];
        int slength = converter.toInt(buffer, 0x0c);
        for (int i = 24; i < buffer.length; ++i) {
            buffer[i] -= key;
            key += 7;
        }
        int dlength = converter.toInt(buffer, 0x1c);
        int[] score_offsets = new int[9];
        int max_stage = 0;
        for (int i = 0; i < score_offsets.length; i++) {
            score_offsets[i] = converter.toInt(buffer, 0x20 + 4 * i);
            if (score_offsets[i] != 0x00) {
                max_stage = i;
            }
        }
        System.arraycopy(buffer, 0x68, buffer, 0, buffer.length - 0x68);
        byte[] decodeData = new byte[dlength];
        decompress(buffer, decodeData, slength - 0x68);
        if (max_stage == score_offsets.length - 1) {
            replay.splits = new ReplayResult.ReplaySplits[1];
            int offset = score_offsets[score_offsets.length - 1] - 0x68;
            replay.splits[0] = new ReplayResult.ReplaySplits();
            replay.splits[0].stage = 7;
            replay.splits[0].score = converter.toInt(decodeData, offset) * 10; //  stored as end of stage score in pcb
            replay.splits[0].additional = "Point Items: " + converter.toInt(decodeData, offset + 0x4) + " | Time: " + converter.toInt(decodeData, offset + 0xc);
            replay.splits[0].graze = converter.toInt(decodeData, offset + 0x8);
            replay.splits[0].piv = converter.toInt(decodeData, offset + 0x14);// + "/" + converter.toInt(decodeData, offset + 0xc);
            replay.splits[0].power = (0xFF & decodeData[offset + 0x1c]) + "";
            replay.splits[0].lives = decodeData[offset + 0x1d] + "";
            replay.splits[0].bombs = decodeData[offset + 0x1e] + "";
        } else {
            max_stage += 1;
            replay.splits = new ReplayResult.ReplaySplits[max_stage];
            for (int i = 0; i < max_stage; i++) {
                if (score_offsets[i] != 0x00) {
                    replay.splits[i] = new ReplayResult.ReplaySplits();
                    int offset = score_offsets[i] - 0x68;
                    replay.splits[i].score = converter.toInt(decodeData, offset) * 10; //  stored as end of stage score in pcb
                    replay.splits[i].additional = "Point Items: " + converter.toInt(decodeData, offset + 0x4) + " | Time: " + converter.toInt(decodeData, offset + 0xc);
                    replay.splits[i].graze = converter.toInt(decodeData, offset + 0x8);
                    replay.splits[i].piv = converter.toInt(decodeData, offset + 0x14);// + "/" + converter.toInt(decodeData, offset + 0xc);
                    replay.splits[i].power = (0xFF & decodeData[offset + 0x1c]) + "";
                    replay.splits[i].lives = decodeData[offset + 0x1d] + "";
                    replay.splits[i].bombs = decodeData[offset + 0x1e] + "";
                    switch (i) {
                        case 3:
                            replay.splits[i].stage = 4;
                            replay.splits[i].additional += " | Stage: 4A";
                            break;
                        case 4:
                            replay.splits[i].stage = 4;
                            replay.splits[i].additional += " | Stage: 4B";
                            break;
                        case 5:
                            replay.splits[i].stage = 5;
                            break;
                        case 6:
                            replay.splits[i].stage = 6;
                            replay.splits[i].additional += " | Stage: 6A";
                            break;
                        case 7:
                            replay.splits[i].stage = 6;
                            replay.splits[i].additional += " | Stage: 6B";
                            break;
                        default:
                            replay.splits[i].stage = i + 1;
                            break;
                    }
                }
            }
        }
        return replay;
    }

    private ReplayResult readTh09(ReplayBinaryFile file) {
        if (!jumpToUser(file)) return null;
        ReplayResult replay = new ReplayResult("东方花映塚 ~ Phantasmagoria of Flower View.");
        readThprac(file, replay);
        String[] chars = { "博丽灵梦", "雾雨魔理沙", "十六夜咲夜", "魂魄妖梦", "铃仙·优昙华院·因幡", "琪露诺", "莉莉卡", "米斯蒂娅", "因幡帝", "风见幽香", "射命丸文", "梅蒂欣·梅兰可莉", "小野冢小町", "四季映姬", "梅露兰", "露娜萨" };
        file.readInt();
        replay.name = file.seek(17, ReplayBinaryFile.SeekOrigin.Current).readString();
        replay.date = file.seek(11, ReplayBinaryFile.SeekOrigin.Current).readString();
        replay.difficulty = file.seek(8, ReplayBinaryFile.SeekOrigin.Current).readString();
        replay.stage = file.seek(8, ReplayBinaryFile.SeekOrigin.Current).readString();
        byte[] buffer = file.file;
        byte key = buffer[0x15];
        int length = converter.toInt(buffer, 0x0c);
        for (int i = 24; i < buffer.length; ++i) {
            buffer[i] -= key;
            key += 7;
        }
        int dlength = converter.toInt(buffer, 0x1c);
        int[] score_offset = new int[40];
        int max_stage = 0;
        for (int i = 0; i < 10; i++) {
            score_offset[i] = converter.toInt(buffer, 0x20 + 4 * i);
            if (score_offset[i] != 0x00) {
                max_stage = i;
            }
        }
        for (int i = 10; i < 40; i++) {
            score_offset[i] = converter.toInt(buffer, 0x20 + 4 * i);
        }
        System.arraycopy(buffer, 0xc0, buffer, 0, buffer.length - 0xc0);
        byte[] decodeData = new byte[dlength];
        decompress(buffer, decodeData, length - 0xc0);
        max_stage += 1;
        if (score_offset[9] == 0x00) {
            replay.splits = new ReplayResult.ReplaySplits[max_stage];
            //  story mode
            for (int i = 0; i < max_stage; i++) {
                if (score_offset[i] != 0x00) {
                    replay.splits[i] = new ReplayResult.ReplaySplits();
                    replay.splits[i].stage = i + 1;
                    int offset = score_offset[i] - 0xc0;
                    int offset_p2 = score_offset[10 + i] - 0xc0;
                    replay.splits[i].score = converter.toInt(decodeData, offset) * 10; //  stored as end of stage score in pcb
                    replay.splits[i].lives = decodeData[offset + 0x8] + "";
                    replay.splits[i].additional = chars[decodeData[offset + 0x6]] + " vs " + chars[decodeData[offset_p2 + 0x6]];
                }
            }
        } else {
            //  vs
            replay.splits = new ReplayResult.ReplaySplits[1];
            replay.splits[0] = new ReplayResult.ReplaySplits();
            replay.splits[0].additional = chars[decodeData[score_offset[9] - 0xc0 + 0x6]] + " vs " + chars[decodeData[score_offset[19] - 0xc0 + 0x6]];
        }
        return replay;
    }

    private ReplayResult readTh095(ReplayBinaryFile file) {
        if (!jumpToUser(file)) return null;
        ReplayResult replay = new ReplayResult("th9.5");
        readThprac(file, replay);
        file.readInt();
        file.seek(4, ReplayBinaryFile.SeekOrigin.Current);
        file.readString();
        file.readString();
        replay.name = file.seek(5, ReplayBinaryFile.SeekOrigin.Current).readString();
        replay.stage = file.readString() + " " + file.readString();
        replay.date = file.seek(5, ReplayBinaryFile.SeekOrigin.Current).readString();
        replay.score = NumberFormat.getInstance(Locale.CHINA).format(Long.parseLong(file.seek(6, ReplayBinaryFile.SeekOrigin.Current).readString()));
        return replay;
    }

    //  the replay user data format for touhou 10 through to 16 (and presumably from here on in) is identical
    private ReplayResult readTh10(ReplayBinaryFile file) {
        ReplayResult replay = new ReplayResult(TH10Data.getInstance().getNameFull());
        readBaseUserData(file, replay);
        byte[] buffer = new byte[file.file.length];
        System.arraycopy(file.file, 0, buffer, 0, buffer.length);
        int length = converter.toInt(buffer, 28);
        int dlength = converter.toInt(buffer, 32);
        byte[] decodedata = new byte[dlength];
        System.arraycopy(buffer, 36, buffer, 0, buffer.length - 36);
        decode(buffer, length, 0x400, (byte)0xaa, (byte)0xe1);
        decode(buffer, length, 0x80, (byte)0x3d, (byte)0x7a);
        decompress(buffer, decodedata, length);
        int stageoffset = 0x64, stage = decodedata[0x4c];
        if (stage > 6) {
            stage = 6;
        }
        replay.splits = new ReplayResult.ReplaySplits[stage];
        for (int i = 0; i < stage; ++i) {
            replay.splits[i] = new ReplayResult.ReplaySplits();
            replay.splits[i].stage = decodedata[stageoffset];
            replay.splits[i].score = converter.toInt(decodedata, stageoffset + 0xc) * 10;
            replay.splits[i].power = (0.05f * converter.toInt(decodedata, stageoffset + 0x10)) + "";
            replay.splits[i].piv = converter.toInt(decodedata, stageoffset + 0x14);
            int lives = decodedata[stageoffset + 0x1c];
            replay.splits[i].lives = lives + "";
            replay.splits[i].graze = 0;
            replay.splits[i].bombs = "0";
            stageoffset += converter.toInt(decodedata, stageoffset + 0x8) + 0x1c4;
        }
        return replay;
    }

    private ReplayResult readTh11(ReplayBinaryFile file) {
        ReplayResult replay = new ReplayResult(TH11Data.getInstance().getNameFull());
        readBaseUserData(file, replay);
        byte[] buffer = file.file;
        int length = converter.toInt(buffer, 28);
        int dlength = converter.toInt(buffer, 32);
        byte[] decodedata = new byte[dlength];
        System.arraycopy(buffer, 36, buffer, 0, buffer.length - 36);
        decode(buffer, length, 0x800, (byte)0xaa, (byte)0xe1);
        decode(buffer, length, 0x40, (byte)0x3d, (byte)0x7a);
        decompress(buffer, decodedata, length);
        int stageoffset = 0x70, stage = decodedata[0x58];
        if (stage > 6) {
            stage = 6;
        }
        replay.splits = new ReplayResult.ReplaySplits[stage];
        for (int i = 0; i < stage; ++i) {
            replay.splits[i] = new ReplayResult.ReplaySplits();
            replay.splits[i].stage = decodedata[stageoffset];
            replay.splits[i].score = converter.toInt(decodedata, stageoffset + 0xc) * 10;
            replay.splits[i].power = (0.05f * (float)converter.toInt(decodedata, stageoffset + 0x10)) + "";
            replay.splits[i].piv = converter.toInt(decodedata, stageoffset + 0x14);
            int lives = decodedata[stageoffset + 0x18];
            int pieces = decodedata[stageoffset + 0x1a];
            replay.splits[i].lives = lives + "" + " (" + pieces + "/5)";
            replay.splits[i].graze = converter.toInt(decodedata, stageoffset + 0x34);
            replay.splits[i].bombs = "0";
            stageoffset += converter.toInt(decodedata, stageoffset + 0x8) + 0x90;
        }
        return replay;
    }

    private ReplayResult readTh12(ReplayBinaryFile file) {
        ReplayResult replay = new ReplayResult(TH12Data.getInstance().getNameFull());
        readBaseUserData(file, replay);
        byte[] buffer = file.file;
        int length = converter.toInt(buffer, 28);
        int dlength = converter.toInt(buffer, 32);
        byte[] decodedata = new byte[dlength];
        System.arraycopy(buffer, 36, buffer, 0, buffer.length - 36);
        decode(buffer, length, 0x800, (byte)0x5e, (byte)0xe1);
        decode(buffer, length, 0x40, (byte)0x7d, (byte)0x3a);
        decompress(buffer, decodedata, length);
        int stageoffset = 0x70;
        int stage = decodedata[0x58];
        if (stage > 6) {
            stage = 6;
        }
        replay.splits = new ReplayResult.ReplaySplits[stage];
        for (int i = 0; i < stage; ++i) {
            replay.splits[i] = new ReplayResult.ReplaySplits();
            replay.splits[i].stage = decodedata[stageoffset];
            replay.splits[i].score = converter.toInt(decodedata, stageoffset + 0xc) * 10;
            replay.splits[i].power = ((float)converter.toInt(decodedata, stageoffset + 0x10) / 100f) + "";
            replay.splits[i].piv = (converter.toInt(decodedata, stageoffset + 0x14) / 1000) * 10;
            int lives = decodedata[stageoffset + 0x18];
            int lpieces = decodedata[stageoffset + 0x1a];
            if (lpieces > 0) {
                lpieces -= 1;
            }
            int bombs = decodedata[stageoffset + 0x1c];
            int bpieces = decodedata[stageoffset + 0x1e];
            replay.splits[i].additional = "UFOs: ";
            for (int j = 0; j < 3; ++j) {
                switch (decodedata[stageoffset + 0x20 + j * 4]) {
                    case 0:
                        replay.splits[i].additional += "None ";
                        break;
                    case 1:
                        replay.splits[i].additional += "Red ";
                        break;
                    case 2:
                        replay.splits[i].additional += "Blue ";
                        break;
                    case 3:
                        replay.splits[i].additional += "Green ";
                        break;
                }
            }
            replay.splits[i].lives = lives + "" + " (" + lpieces + "/4)";
            replay.splits[i].bombs = bombs + "" + " (" + bpieces + "/3)";
            replay.splits[i].graze = converter.toInt(decodedata, stageoffset + 0x44);
            stageoffset += converter.toInt(decodedata, stageoffset + 0x8) + 0xa0;
        }
        return replay;
    }

    private ReplayResult readTh125(ReplayBinaryFile file) {
        if (!jumpToUser(file)) return null;
        ReplayResult replay = new ReplayResult("th12.5");
        readThprac(file, replay);
        file.readInt();
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
        return replay;
    }

    private ReplayResult readTh128(ReplayBinaryFile file) {
        if (!jumpToUser(file)) return null;
        ReplayResult replay = new ReplayResult("th12.8");
        readThprac(file, replay);
        file.readInt();
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
        byte[] buffer = file.file;
        int length = converter.toInt(buffer, 28);
        int dlength = converter.toInt(buffer, 32);
        byte[] decodedata = new byte[dlength];
        System.arraycopy(buffer, 36, buffer, 0, buffer.length - 36);
        decode(buffer, length, 0x800, (byte)0x5e, (byte)0xe7);
        decode(buffer, length, 0x80, (byte)0x7d, (byte)0x36);
        decompress(buffer, decodedata, length);
        int stageoffset = 0x70;
        int stage = decodedata[0x58];
        replay.splits = new ReplayResult.ReplaySplits[stage];
        for (int i = 0; i < stage; ++i) {
            replay.splits[i] = new ReplayResult.ReplaySplits();
            //replay.splits[i].stage = decodedata[stageoffset];
            replay.splits[i].score = converter.toInt(decodedata, stageoffset + 0xc) * 10;
            replay.splits[i].power = (converter.toInt(decodedata, stageoffset + 0x10) + 1) + "";
            replay.splits[i].lives = (converter.toInt(decodedata, stageoffset + 0x80) / 100) + "" + "%";
            replay.splits[i].bombs = (converter.toInt(decodedata, stageoffset + 0x84) / 100) + "" + "%";
            replay.splits[i].additional = "Freeze Area: " + (converter.toInt(decodedata, stageoffset + 0x88)) + "" + "%";
            stageoffset += converter.toInt(decodedata, stageoffset + 0x8) + 0x90;
        }
        return replay;
    }

    private ReplayResult readTh13Th14(ReplayBinaryFile file) {
        ReplayResult replay = new ReplayResult(TH13Data.getInstance().getNameFull());
        readBaseUserData(file, replay);
        ReplayResult readTh13 = readTh13(file, replay);
        if (readTh13.splits.length < 1) {
            replay.game = TH14Data.getInstance().getNameFull();
            return readTh14(file, replay);
        }
        return readTh13;  
    }

    private ReplayResult readTh13(ReplayBinaryFile file, ReplayResult replay) {
        byte[] buffer = new byte[file.file.length];
        System.arraycopy(file.file, 0, buffer, 0, buffer.length);//为了后面读th14正常，需要保持数组不变
        int length = converter.toInt(buffer, 28);
        int dlength = converter.toInt(buffer, 32);
        byte[] decodedata = new byte[dlength];
        System.arraycopy(buffer, 36, buffer, 0, buffer.length - 36);
        decode(buffer, length, 0x400, (byte)0x5c, (byte)0xe1);
        decode(buffer, length, 0x100, (byte)0x7d, (byte)0x3a);
        decompress(buffer, decodedata, length);
        int stageoffset = 0x74;
        int stage = decodedata[0x58];
        if (stage > 6) {
            stage = 6;
        }
        replay.splits = new ReplayResult.ReplaySplits[stage];
        for (int i = 0; i < stage; ++i) {
            replay.splits[i] = new ReplayResult.ReplaySplits();
            replay.splits[i].stage = decodedata[stageoffset];
            replay.splits[i].score = converter.toInt(decodedata, stageoffset + 0x1c) * 10;
            replay.splits[i].power = ((float)converter.toInt(decodedata, stageoffset + 0x44) / 100f) + "";
            replay.splits[i].piv = (converter.toInt(decodedata, stageoffset + 0x38) / 1000) * 10;
            int lives = decodedata[stageoffset + 0x50];
            int lpieces = decodedata[stageoffset + 0x54];
            int bombs = decodedata[stageoffset + 0x5c];
            int bpieces = decodedata[stageoffset + 0x60];
            replay.splits[i].additional = "Trance: " + converter.toInt(decodedata, stageoffset + 0x64) + "/600";
            replay.splits[i].lives = lives + " (" + lpieces + ")";   //  i dont have the piece table rn
            replay.splits[i].graze = converter.toInt(decodedata, stageoffset + 0x2c);
            replay.splits[i].bombs = bombs + " (" + bpieces + "/8)";
            stageoffset += converter.toInt(decodedata, stageoffset + 0x8) + 0xc4;
        }
        return replay;
    }

    private ReplayResult readTh14(ReplayBinaryFile file, ReplayResult replay) {
        byte[] buffer = file.file;
        int length = converter.toInt(buffer, 28);
        int dlength = converter.toInt(buffer, 32);
        byte[] decodedata = new byte[dlength];
        System.arraycopy(buffer, 36, buffer, 0, buffer.length - 36);
        decode(buffer, length, 0x400, (byte)0x5c, (byte)0xe1);
        decode(buffer, length, 0x100, (byte)0x7d, (byte)0x3a);
        decompress(buffer, decodedata, length);
        int stageoffset = 0x94;
        int stage = decodedata[0x78];
        if (stage > 6) {
            stage = 6;
        }
        replay.splits = new ReplayResult.ReplaySplits[stage];
        for (int i = 0; i < stage; ++i) {
            replay.splits[i] = new ReplayResult.ReplaySplits();
            replay.splits[i].stage = decodedata[stageoffset];
            replay.splits[i].score = converter.toInt(decodedata, stageoffset + 0x1c) * 10;
            replay.splits[i].power = ((float)converter.toInt(decodedata, stageoffset + 0x44) / 100f) + "";
            replay.splits[i].piv = (converter.toInt(decodedata, stageoffset + 0x38) / 1000) * 10;
            int lives = decodedata[stageoffset + 0x50];
            int lpieces = decodedata[stageoffset + 0x54];
            int bombs = decodedata[stageoffset + 0x5c];
            int bpieces = decodedata[stageoffset + 0x60];
            replay.splits[i].additional = "Lives gained: " + decodedata[stageoffset + 0x58];
            replay.splits[i].lives = lives + " (" + lpieces + "/3)";   //  i dont have the piece table rn
            replay.splits[i].graze = converter.toInt(decodedata, stageoffset + 0x2c);
            replay.splits[i].bombs = bombs + " (" + bpieces + "/8)";
            stageoffset += converter.toInt(decodedata, stageoffset + 0x8) + 0xdc;
        }
        return replay;
    }

    private ReplayResult readTh143(ReplayBinaryFile file) {
        if (!jumpToUser(file)) return null;
        ReplayResult replay = new ReplayResult("th14.3");
        readThprac(file, replay);
        file.readInt();
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
        return replay;
    }

    private ReplayResult readTh15(ReplayBinaryFile file) {
        ReplayResult replay = new ReplayResult(TH15Data.getInstance().getNameFull());
        readBaseUserData(file, replay);
        byte[] buffer = file.file;
        int length = converter.toInt(buffer, 28);
        int dlength = converter.toInt(buffer, 32);
        byte[] decodedata = new byte[dlength];
        System.arraycopy(buffer, 36, buffer, 0, buffer.length - 36);
        decode(buffer, length, 0x400, (byte)0x5c, (byte)0xe1);
        decode(buffer, length, 0x100, (byte)0x7d, (byte)0x3a);
        decompress(buffer, decodedata, length);
        int stageoffset = 0xa4;
        int stage = decodedata[0x88];
        if (stage > 6) {
            stage = 6;
        }
        replay.splits = new ReplayResult.ReplaySplits[stage];
        for (int i = 0; i < stage; ++i) {
            replay.splits[i] = new ReplayResult.ReplaySplits();
            replay.splits[i].stage = decodedata[stageoffset];
            replay.splits[i].score = converter.toInt(decodedata, stageoffset + 0x30) * 10;
            replay.splits[i].power = ((float)converter.toInt(decodedata, stageoffset + 0x64) / 100f) + "";
            replay.splits[i].piv = (converter.toInt(decodedata, stageoffset + 0x58) / 1000) * 10;
            int lives = decodedata[stageoffset + 0x74];
            int lpieces = decodedata[stageoffset + 0x78];
            int bombs = decodedata[stageoffset + 0x80];
            int bpieces = decodedata[stageoffset + 0x84];
            replay.splits[i].additional = "(none)";
            replay.splits[i].lives = lives + " (" + lpieces + "/3)";   //  i dont have the piece table rn
            replay.splits[i].graze = converter.toInt(decodedata, stageoffset + 0x40);
            replay.splits[i].bombs = bombs + " (" + bpieces + "/8)";
            stageoffset += converter.toInt(decodedata, stageoffset + 0x8) + 0x238;
        }
        return replay;
    }

    private ReplayResult readTh16(ReplayBinaryFile file) {
        ReplayResult replay = new ReplayResult(TH16Data.getInstance().getNameFull());
        readBaseUserData(file, replay);
        byte[] buffer = file.file;
        int length = converter.toInt(buffer, 28);
        int dlength = converter.toInt(buffer, 32);
        byte[] decodedata = new byte[dlength];
        System.arraycopy(buffer, 36, buffer, 0, buffer.length - 36);
        decode(buffer, length, 0x400, (byte)0x5c, (byte)0xe1);
        decode(buffer, length, 0x100, (byte)0x7d, (byte)0x3a);
        decompress(buffer, decodedata, length);
        int stageoffset = 0xa0;
        int stage = decodedata[0x80];
        if (stage > 6) {
            stage = 6;
        }
        replay.splits = new ReplayResult.ReplaySplits[stage];
        for (int i = 0; i < stage; ++i) {
            replay.splits[i] = new ReplayResult.ReplaySplits();
            replay.splits[i].stage = decodedata[stageoffset];
            replay.splits[i].score = converter.toInt(decodedata, stageoffset + 0x34) * 10;
            replay.splits[i].power = ((float)converter.toInt(decodedata, stageoffset + 0x68) / 100f) + "";
            replay.splits[i].piv = (converter.toInt(decodedata, stageoffset + 0x5c) / 1000) * 10;
            int lives = decodedata[stageoffset + 0x78];
            int lpieces = decodedata[stageoffset + 0x7c];
            int bombs = decodedata[stageoffset + 0x84];
            int bpieces = decodedata[stageoffset + 0x88];
            replay.splits[i].additional = "Season: " + converter.toInt(decodedata, stageoffset + 0x8c) + "/" + converter.toInt(decodedata, stageoffset + 0x90);
            replay.splits[i].lives = lives + " (" + lpieces + "/3)"; //  i dont have the piece table rn
            replay.splits[i].graze = converter.toInt(decodedata, stageoffset + 0x44);
            replay.splits[i].bombs = bombs + " (" + bpieces + "/5)";
            stageoffset += converter.toInt(decodedata, stageoffset + 0x8) + 0x294;
        }
        return replay;
    }

    private ReplayResult readTh165(ReplayBinaryFile file) {
        if (!jumpToUser(file)) return null;
        ReplayResult replay = new ReplayResult("th16.5");
        readThprac(file, replay);
        file.readInt();
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
        return replay;
    }

    private ReplayResult readTh17(ReplayBinaryFile file) {
        ReplayResult replay = new ReplayResult(TH17Data.getInstance().getNameFull());
        readBaseUserData(file, replay);
        byte[] buffer = file.file;
        int length = converter.toInt(buffer, 28);
        int dlength = converter.toInt(buffer, 32);
        byte[] decodedata = new byte[dlength];
        System.arraycopy(buffer, 36, buffer, 0, buffer.length - 36);
        decode(buffer, length, 0x400, (byte)0x5c, (byte)0xe1);
        decode(buffer, length, 0x100, (byte)0x7d, (byte)0x3a);
        decompress(buffer, decodedata, length);
        int stageoffset = 0xa0;
        int stage = decodedata[0x84];
        if (stage > 6) {
            stage = 6;
        }
        replay.splits = new ReplayResult.ReplaySplits[stage];
        for (int i = 0; i < stage; ++i) {
            replay.splits[i] = new ReplayResult.ReplaySplits();
            replay.splits[i].stage = decodedata[stageoffset];
            replay.splits[i].score = converter.toInt(decodedata, stageoffset + 0x34) * 10;
            replay.splits[i].power = ((float)converter.toInt(decodedata, stageoffset + 0x68) / 100f) + "";
            replay.splits[i].piv = (converter.toInt(decodedata, stageoffset + 0x5c) / 1000) * 10;
            int lives = decodedata[stageoffset + 0x78];
            int lpieces = decodedata[stageoffset + 0x7c];
            int bombs = decodedata[stageoffset + 0x84];
            int bpieces = decodedata[stageoffset + 0x88];
            //  token stuff starts at 0x9c
            //  hyper might be c8 or dc
            replay.splits[i].additional = "";
            replay.splits[i].lives = lives + "" + " (" + lpieces + "/3)";   //  i dont have the piece table rn
            replay.splits[i].graze = converter.toInt(decodedata, stageoffset + 0x44);
            replay.splits[i].bombs = bombs + "" + " (" + bpieces + "/3)";
            stageoffset += converter.toInt(decodedata, stageoffset + 0x8) + 0x158;
        }
        return replay;
    }

    private ReplayResult readTh18(ReplayBinaryFile file) {
        ReplayResult replay = new ReplayResult(TH18Data.getInstance().getNameFull());
        readBaseUserData(file, replay);
        byte[] buffer = file.file;
        int length = converter.toInt(buffer, 28);
        int dlength = converter.toInt(buffer, 32);
        byte[] decodedata = new byte[dlength];
        System.arraycopy(buffer, 36, buffer, 0, buffer.length - 36);
        decode(buffer, length, 0x400, (byte)0x5c, (byte)0xe1);
        decode(buffer, length, 0x100, (byte)0x7d, (byte)0x3a);
        decompress(buffer, decodedata, length);
        int stageoffset = 0xc8;
        int stage = decodedata[0xa8];
        if (stage > 6) {
            stage = 6;
        }
        replay.splits = new ReplayResult.ReplaySplits[stage];
        for (int i = 0; i < stage; ++i) {
            replay.splits[i] = new ReplayResult.ReplaySplits();
            replay.splits[i].stage = decodedata[stageoffset];
            replay.splits[i].score = converter.toInt(decodedata, stageoffset + 0x88) * 10;
            replay.splits[i].power = ((float)converter.toInt(decodedata, stageoffset + 0xc4) / 100f) + "";
            replay.splits[i].piv = converter.toInt(decodedata, stageoffset + 0xbc);
            int lives = decodedata[stageoffset + 0xd4];
            int lpieces = decodedata[stageoffset + 0xd8];
            int bombs = decodedata[stageoffset + 0xe4];
            int bpieces = decodedata[stageoffset + 0xe8];
            replay.splits[i].additional = "";
            replay.splits[i].lives = lives + "" + " (" + lpieces + "/3)";   //  i dont have the piece table rn
            replay.splits[i].graze = 0;
            replay.splits[i].bombs = bombs + "" + " (" + bpieces + "/3)";
            stageoffset += converter.toInt(decodedata, stageoffset + 0x8) + 0x126c;
        }
        return replay;
    }

    private int getBit(byte[] buffer, int[] pointer, byte[] filter, byte length) {
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

    private void readBaseUserData(ReplayBinaryFile file, ReplayResult replay) {
        if (!jumpToUser(file)) {
            return;
        }
        readThprac(file, replay);
        file.readInt();
        file.seek(4, ReplayBinaryFile.SeekOrigin.Current);
        file.readString();
        file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.name = file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.date = file.readString();
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        replay.character = file.readString();
        file.seek(5, ReplayBinaryFile.SeekOrigin.Current);
        replay.difficulty = file.readString();
        // file.Seek(6, SeekOrigin.Current);
        replay.stage = file.readString();   //stage
        file.seek(6, ReplayBinaryFile.SeekOrigin.Current);
        replay.score = Long.parseLong(file.readString()) * 10 + "";
    }

    private long decompress(byte[] buffer, byte[] decode, long length) {
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
                if (pointer[0] >= length) {
                    return dest;
                }
                index = bits - 1;
                bits = getBit(buffer, pointer, filter, (byte)4);
                if (pointer[0] >= length) {
                    return dest;
                }
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

    private void decode(byte[] buffer, int length, int block_size, byte _base, byte add) {
        byte[] tbuf = new byte[length];
        tbuf = Arrays.copyOf(buffer, length);
        int i, p = 0, tp1, tp2, hf, left = length;
        if ((left % block_size) < (block_size / 4)) {
            left -= left % block_size;
        }
        left -= length & 1;
        while (left != 0) {
            if (left < block_size) {
                block_size = left;
            }
            tp1 = p + block_size - 1;
            tp2 = p + block_size - 2;
            hf = (block_size + (block_size & 0x1)) / 2;
            for (i = 0; i < hf; ++i, ++p) {
                buffer[tp1] = (byte)(tbuf[p] ^ _base);
                _base += add;
                tp1 -= 2;
            }
            hf = block_size / 2;
            for (i = 0; i < hf; ++i, ++p) {
                buffer[tp2] = (byte)(tbuf[p] ^ _base);
                _base += add;
                tp2 -= 2;
            }
            left -= block_size;
        }
    }

    private boolean jumpToUser(ReplayBinaryFile file) {
        file.seek(12, ReplayBinaryFile.SeekOrigin.Begin);
        long offset = file.readInt();
        file.seek(offset, ReplayBinaryFile.SeekOrigin.Begin);
        return file.readInt() == 0x52455355;
    }

    private void readThprac(ReplayBinaryFile file, ReplayResult replay) {
        byte[] buffer = file.file;
        int length = buffer.length;
        if (converter.toInt(buffer, length - 4) == 0x43415250) {//thprac in game before th10
            int jsonLength = converter.toInt(buffer, length - 8);
            replay.thprac = new String(buffer, length - 8 - jsonLength, jsonLength).trim();
        } else {
            int index = converter.toInt(buffer, 12);
            int leng = converter.toInt(buffer, index + 4);
            while (index + leng < buffer.length) {
                index = index + leng;
                leng = converter.toInt(buffer, index + 4);
            }
            if (converter.toInt(buffer, index + 8) != 0x43415250) {
                return;
            }
            replay.thprac = new String(buffer, index + 12, converter.toInt(buffer, index + 4) - 12).trim();
        }
    }
}
