package com.meng.tools;

import java.util.Random;

public class SJFRandom extends Random {
    private static Random instance = new SJFRandom();

    public static long nextInRange(long min, long max) {
        return nextLong(max - min) + min;
    }

    public static long nextLong(long bound) {
        return instance.nextLong() % bound;
    }

    public static long nextLong() {
        return instance.nextLong();
    }

    public static double nextDouble() {
        return instance.nextDouble();
    }

    public static double nextGaussian() {
        return instance.nextGaussian();
    }

    public static float nextFloat() {
        return instance.nextFloat();
    }

    public static boolean nextBoolean() {
        return instance.nextBoolean();
    }

    public static void nextBytes(byte[] bytes) {
        instance.nextBytes(bytes);
    }

    public static int nextInt() {
        return instance.nextInt();
    }

    public static int nextInt(int bound) {
        return instance.nextInt(bound);
    }

    public static <T> T randomSelect(T[] array) {
        return array[instance.nextInt(array.length)];
    }

//    public static <T> T randomSelect(T[][] arrays) {
//        T[] array = arrays[instance.nextInt(arrays.length)];
//        return randomSelect(array);
//    }

//    public static <T> T hashSelect(long seed, T[][] arrays) { 
//        T[] t1 = arrays[hashSelectInt(seed) % arrays.length];
//        return hashSelect(seed, t1);
//    }

    public static int hashSelectInt(long seed) {
        String md5 = Hash.getMd5Instance().calculate(String.valueOf(seed + System.currentTimeMillis() / (24 * 60 * 60 * 1000)));
        return Integer.parseInt(md5.substring(26), 16);
    }

    public static int hashSelectInt(long seed, int bound) {
        return hashSelectInt(seed) % bound;
    }

    public static float hashSelectFloat(long seed) {
        String md5 = Hash.getMd5Instance().calculate(String.valueOf(seed + System.currentTimeMillis() / (24 * 60 * 60 * 1000)));
        return new Random(Integer.parseInt(md5.substring(26), 16)).nextFloat();
    }

    public static float hashSelectFloat(long seed, float scale) {
        return hashSelectFloat(seed) * scale;
    }

    public static <T> T hashSelect(long seed, T[] array) {
        return array[hashSelectInt(seed) % array.length];
    }  
}
