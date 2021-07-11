package com.meng.tools;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SeijaImageFactory {
    public static File reverseGIF(File gifFile, int reverseFlag) throws FileNotFoundException {
        GifDecoder gifDecoder = new GifDecoder();
        FileInputStream fis = new FileInputStream(gifFile);
        gifDecoder.read(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
        BufferedImage cacheImage = gifDecoder.getFrame(0);
//         int tw = cacheImage.getWidth(null) - 1;
//         int th = cacheImage.getHeight(null) - 1;
//         if (cacheImage.getRGB(0, 0) == 0 &&
//         cacheImage.getRGB(tw, 0) == 0 &&
//         cacheImage.getRGB(0, th) == 0 &&
//         cacheImage.getRGB(tw, th) == 0) {
//         localAnimatedGifEncoder.setTransparent(new Color(0, 0, 0, 0));
//         }
        localAnimatedGifEncoder.start(baos);// start
        localAnimatedGifEncoder.setRepeat(0);// 设置生成gif的开始播放时间。0为立即开始播放
        float fa = (float) cacheImage.getHeight() / (gifDecoder.getFrameCount());
        switch (reverseFlag % 4) {
            case 0:// 镜之国
                cacheImage = spell1(gifDecoder.getFrame(0));
                for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
                    localAnimatedGifEncoder.setDelay(gifDecoder.getDelay(i));
                    localAnimatedGifEncoder.addFrame(spell1(gifDecoder.getFrame(i), cacheImage));
                }
                break;
            case 1:// 天地
                cacheImage = spell2(gifDecoder.getFrame(0));
                for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
                    localAnimatedGifEncoder.setDelay(gifDecoder.getDelay(i));
                    localAnimatedGifEncoder.addFrame(spell2(gifDecoder.getFrame(i), cacheImage));
                }
                break;
            case 2:// 天壤梦弓
                for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
                    localAnimatedGifEncoder.setDelay(gifDecoder.getDelay(i));
                    localAnimatedGifEncoder.addFrame(spell3(gifDecoder.getFrame(i), (int) (fa * (gifDecoder.getFrameCount() - i)), cacheImage));
                }
                break;
            case 3:// Reverse Hierarchy
                cacheImage = spell4(gifDecoder.getFrame(0));
                for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
                    localAnimatedGifEncoder.setDelay(gifDecoder.getDelay(i));
                    localAnimatedGifEncoder.addFrame(spell4(gifDecoder.getFrame(i), cacheImage));
                }
                break;
        }
        localAnimatedGifEncoder.finish();
        File imagePath = null;
        try {
            imagePath = SJFPathTool.getImagePath("gen/" + SJFRandom.randomInt() + ".gif");
            FileOutputStream fos = new FileOutputStream(imagePath);
            baos.writeTo(fos);
            baos.flush();
            fos.flush();
            baos.close();
            fos.close();
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
        return imagePath;
    }

    private static BufferedImage spell1(BufferedImage current, BufferedImage cache) {
        int w = current.getWidth(null);
        int h = current.getHeight(null);
        BufferedImage bmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        bmp.getGraphics().drawImage(cache.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = current.getRGB(x, y);
                bmp.setRGB(w - 1 - x, y, i == 0 ? 1 : i);// 镜之国
            }
        }
        cache.getGraphics().drawImage(bmp.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        return bmp;
    }

    private static BufferedImage spell1(BufferedImage current) {
        int w = current.getWidth(null);
        int h = current.getHeight(null);
        BufferedImage bmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = current.getRGB(x, y);
                bmp.setRGB(w - 1 - x, y, i == 0 ? 1 : i);// 镜之国
            }
        }
        return bmp;
    }

    private static BufferedImage spell2(BufferedImage current, BufferedImage cache) {
        int w = current.getWidth(null);
        int h = current.getHeight(null);
        BufferedImage bmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        bmp.getGraphics().drawImage(cache.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = current.getRGB(x, y);
                bmp.setRGB(x, h - 1 - y, i == 0 ? 1 : i);// 天地
            }
        }
        cache.getGraphics().drawImage(bmp.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        return bmp;
    }

    private static BufferedImage spell2(BufferedImage current) {
        int w = current.getWidth(null);
        int h = current.getHeight(null);
        BufferedImage bmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = current.getRGB(x, y);
                bmp.setRGB(x, h - 1 - y, i == 0 ? 1 : i);// 天地
            }
        }
        return bmp;
    }

    private static BufferedImage spell3(BufferedImage current, int px, BufferedImage cache) {
        int w = current.getWidth(null);
        int h = current.getHeight(null);
        BufferedImage bmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        bmp.getGraphics().drawImage(spell3_at1(cache, px).getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        spell3Process(current, px, w, h, bmp);
        cache.getGraphics().drawImage(spell3_at1(bmp, -px).getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        return bmp;
    }

    private static BufferedImage spell3_at1(BufferedImage cache, int px) {
        int w = cache.getWidth(null);
        int h = cache.getHeight(null);
        BufferedImage bmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        int j;
        if (px > 0) {
            spell3Process(cache, px, w, h, bmp);
        } else {
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    j = y + px;
                    int i = cache.getRGB(x, y);
                    if (j >= 0) {
                        bmp.setRGB(x, j, i == 0 ? 1 : i);
                    } else {
                        bmp.setRGB(x, j + h, i == 0 ? 1 : i);
                    }
                }
            }
        }
        return bmp;
    }

    private static void spell3Process(BufferedImage cache, int px, int w, int h, BufferedImage bmp) {
        int j;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                j = y + px;
                int i = cache.getRGB(x, y);
                if (j < h) {
                    bmp.setRGB(x, j, i == 0 ? 1 : i);
                } else {
                    bmp.setRGB(x, j - h, i == 0 ? 1 : i);
                }
            }
        }
    }

    private static BufferedImage spell4(BufferedImage current, BufferedImage cache) {
        int w = current.getWidth(null);
        int h = current.getHeight(null);
        BufferedImage bmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        bmp.getGraphics().drawImage(cache.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        spell4Process(current, w, h, bmp);
        cache.getGraphics().drawImage(bmp.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        return bmp;
    }

    private static BufferedImage spell4(BufferedImage current) {
        int w = current.getWidth(null);
        int h = current.getHeight(null);
        BufferedImage bmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        spell4Process(current, w, h, bmp);
        return bmp;
    }

    private static void spell4Process(BufferedImage current, int w, int h, BufferedImage bmp) {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = current.getRGB(x, y);
                bmp.setRGB(w - 1 - x, h - 1 - y, i == 0 ? 1 : i);// Reverse_Hierarchy
            }
        }
    }
}
