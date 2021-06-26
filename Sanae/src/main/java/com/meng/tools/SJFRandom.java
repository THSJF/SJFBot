package com.meng.tools;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Collections;
import java.util.ArrayList;

public class SJFRandom extends Random {
    private static Random instance = new SJFRandom();

    public static long nextInRange(long min, long max) {
        return randomLong(max - min) + min;
    }

    public static int nextInRange(int min, int max) {
        return randomInt(max - min) + min;
    }
    
    public static long randomLong(long bound) {
        return instance.nextLong() % bound;
    }

    public static long randomLong() {
        return instance.nextLong();
    }

    public static double randomDouble() {
        return instance.nextDouble();
    }

    public static double randomGaussian() {
        return instance.nextGaussian();
    }

    public static float randomFloat() {
        return instance.nextFloat();
    }

    public static boolean randomBoolean() {
        return instance.nextBoolean();
    }

    public static void randomBytes(byte[] bytes) {
        instance.nextBytes(bytes);
    }

    public static int randomInt() {
        return instance.nextInt();
    }

    public static int randomInt(int bound) {
        return instance.nextInt(bound);
    }

    public static <T> T randomSelect(T[] array) {
        return array[instance.nextInt(array.length)];
    }

    public static <T> T randomSelect(List<T> list) {
        return list.get(instance.nextInt(list.size()));
    }
    
    public static <T> T randomSelect(Set<T> set) {
        return randomSelect(new ArrayList<T>(set));
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
    
    public static <T> T hashSelect(long seed, List<T> list) {
        return list.get(hashSelectInt(seed) % list.size());
    }
}
