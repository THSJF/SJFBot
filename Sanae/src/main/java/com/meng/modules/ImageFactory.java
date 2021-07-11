package com.meng.modules;

import com.meng.modules.qq.SBot;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageFactory {

    private static ImageFactory instance;

    public static ImageFactory getInstance() {
        if (instance == null) {
            instance = new ImageFactory();
        }
        return instance;
    }

    public BufferedImage generateGray(BufferedImage img) {
        for (int i = 0;i < img.getWidth();i++) {
            for (int j =0;j < img.getHeight();j++) {
                int col = img.getRGB(i, j);
                int alpha = col & 0xFF000000;
                int R = (col & 0x00FF0000) >> 16;
                int G = (col & 0x0000FF00) >> 8;
                int B = (col & 0x000000FF);
                //  int Y = (int)(R * 0.299 + G * 0.587 + B * 0.114);
                int Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
                int newColor = alpha | (Y << 16) | (Y << 8) | Y;
                img.setRGB(i, j, newColor);
            }
        }
        return img;
    }

    public float[] RGBToYUV(int R, int G, int B) {
        return new float[]{
            0.299f * R + 0.587f * G + 0.114f * B,
            -0.147f * R - 0.289f * G + 0.436f * B,
            0.615f * R - 0.515f * G - 0.100f * B
        };
    }

    public int[] YUVToRGB(float Y, float U, float V) {
        return new int[]{
            ((int)(Y + 1.14f * V)),
            ((int)(Y - 0.39f * U - 0.58f * V)),
            ((int)(Y + 2.03f * U))
        };
    }

    public BufferedImage generateJingShenZhiZhu(BufferedImage src) {
        try {
            BufferedImage des1 = scaleImage(generateRotateImage(src, 346), 190);
            Image im = ImageIO.read(new File(SBot.appDirectory + "/baseImage/精神支柱.png"));
            BufferedImage b = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            b.getGraphics().drawImage(im, 0, 0, null);
            b.getGraphics().drawImage(des1, -29, 30, null);
            return b;
        } catch (IOException e) {
            return null;
        }
    }

    public BufferedImage generateShenChu(BufferedImage src) {
        try {
            BufferedImage des1 = new BufferedImage(228, 228, BufferedImage.TYPE_INT_ARGB);
            des1.getGraphics().drawImage(src, 0, 0, 228, 228, null);
            Image im = ImageIO.read(new File(SBot.appDirectory + "/baseImage/神触.png"));
            BufferedImage b = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            b.getGraphics().drawImage(im, 0, 0, null);
            b.getGraphics().drawImage(des1, 216, -20, null);
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage generateMirror(BufferedImage srcImage, int flag) {
        Image im = srcImage;
        int w = im.getWidth(null);
        int h = im.getHeight(null);
        int size = w * h;
        BufferedImage b = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        b.getGraphics().drawImage(im.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        int[] rgb1 = b.getRGB(0, 0, w, h, new int[size], 0, w);
        int[] rgb2 = new int[size];
        switch (flag % 3) {
            case 0:
                for (int y = 0; y < h; ++y) {
                    int yw = y * w;
                    for (int x = 0; x < w; ++x) {
                        rgb2[(w - 1 - x) + yw] = rgb1[x + yw]; // 镜之国
                    }
                }
                break;
            case 1:
                for (int y = 0; y < h; y++) {
                    // 天地
                    if (w >= 0) {
                        System.arraycopy(rgb1, y * w, rgb2, (h - 1 - y) * w, w);
                    }
                }
                break;
            case 2:
                int halfH = h / 2;
                for (int y = 0; y < h; y++) {
                    // 天壤梦弓
                    if (w >= 0) {
                        System.arraycopy(rgb1, y * w, rgb2, (y < halfH ? y + halfH : y - halfH) * w, w);
                    }
                }
                break;
        }
        b.setRGB(0, 0, w, h, rgb2, 0, w);
        return b;
    } 

    public BufferedImage generateRotateImage(Image src, int angel) {
        int srcWidth = src.getWidth(null);
        int srcHeight = src.getHeight(null);
        if (angel > 90) {
            if (angel / 90 % 2 == 1) {
                srcHeight = srcHeight ^ srcWidth;
                srcWidth = srcHeight ^ srcWidth;
                srcHeight = srcHeight ^ srcWidth;
            }
        }
        double r = Math.sqrt(srcHeight * srcHeight + srcWidth * srcWidth) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel % 90) / 2) * r;
        double angelAlpha = (Math.PI - Math.toRadians(angel % 90)) / 2;
        double angelDaltaWidth = Math.atan((double) srcHeight / srcWidth);
        double angelDaltaHeight = Math.atan((double) srcWidth / srcHeight);
        int lenDaltaWidth = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaWidth));
        int lenDaltaHeight = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaHeight));
        int desWidth = srcWidth + lenDaltaWidth * 2;
        int desHeight = srcHeight + lenDaltaHeight * 2;
        BufferedImage res = new BufferedImage(desWidth, desHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = res.createGraphics();
        g2.translate((desWidth - srcWidth) / 2, (desHeight - srcHeight) / 2);
        g2.rotate(Math.toRadians(angel), srcWidth / 2, srcHeight / 2);
        g2.drawImage(src, null, null);
        return res;
    }

    public BufferedImage scaleImage(BufferedImage img, int newSize) {
        BufferedImage img2 = new BufferedImage(newSize, newSize, BufferedImage.TYPE_INT_ARGB);
        img2.getGraphics().drawImage(img, 0, 0, newSize, newSize, null);
        return img2;
    }
}
