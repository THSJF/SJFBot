package com.meng.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SJFPathTool {
    public static String getAppDirectory() {
        return "C://Program Files/sanae_data/";
    }

    public static String getImagePath() {
        return getAppDirectory() + "/image/";
    }

    public static File getImagePath(String fileName) {
        return new File(getImagePath() + fileName);
    }

    public static String getR15Path() {
        return getAppDirectory() + "/image/r15/";
    }

    public static File getR15Path(String fileName) {
        return new File(getR15Path() + fileName);
    }

    public static String getFantasyPath() {
        return getAppDirectory() + "/image/fantasy/";
    }

    public static File getFantasyPath(String fileName) {
        return new File(getFantasyPath() + fileName);
    }


    public static String getJsPath() {
        return getAppDirectory() + "js/";
    }

    public static File getJsPath(String fileName) {
        return new File(getJsPath() + fileName);
    }

    public static String getMusicCutPath() {
        return getAppDirectory() + "touhou/musicCut/";
    }

    public static File getMusicCutPath(String fileName) {
        return new File(getMusicCutPath() + fileName);
    }

    public static String getTTSPath() {
        return getAppDirectory() + "tts/";
    }

    public static File getTTSPath(String fileName) {
        return new File(getTTSPath() + fileName);
    }

    public static File getCrashLog() {
        return new File(getAppDirectory() + "/crash/crash-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + "-" + System.currentTimeMillis() + ".log");
    }

    public static String getRecallPath() {
        return getAppDirectory() + "/image/recalled/";
    }

    public static File getRecallPath(String fileName) {
        return new File(getRecallPath() + fileName);
    }

    public static String getFlashImagePath() {
        return getAppDirectory() + "/image/flashImage/";
    }

    public static File getFlashImagePath(String fileName) {
        return new File(getFlashImagePath() + fileName);
    }

    public static String getUFOPath() {
        return getAppDirectory() + "/image/ufo/";
    }

    public static File getUFOPath(String fileName) {
        return new File(getUFOPath() + fileName);
    }

    public static String getQaImagePath() {
        return getAppDirectory() + "/image/qaImages/";
    }

    public static File getQaImagePath(String fileName) {
        return new File(getQaImagePath() + fileName);
    }

    public static String getBaseImagePath() {
        return getAppDirectory() + "/image/baseImage/";
    }

    public static File getBaseImagePath(String fileName) {
        return new File(getBaseImagePath() + fileName);
    }

    public static String getDeepDanbooruPath() {
        return getAppDirectory() + "/image/deepdanbooru/";
    }

    public static File getDeepDanbooruPath(String fileName) {
        return new File(getDeepDanbooruPath() + fileName);
    }

    public static String getReplayPath() {
        return getAppDirectory() + "/touhou/replay/";
    }

    public static File getReplayPath(String fileName) {
        return new File(getReplayPath() + fileName);
    }
}
