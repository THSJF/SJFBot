package com.meng.modules;

import com.meng.modules.qq.SBot;
import com.meng.tools.FileTool;
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

    public File generateJingShenZhiZhu(File file) {
        try {
            File retFile = FileTool.createFile(new File(SBot.appDirectory + "imageFactory/jingshenzhizhu/" + System.currentTimeMillis() + ".png"));
            BufferedImage src = ImageIO.read(file);
            BufferedImage des1 = scaleImage(rotateImage(src, 346), 190);
            Image im = ImageIO.read(new File(SBot.appDirectory + "/baseImage/精神支柱.png"));
            BufferedImage b = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            b.getGraphics().drawImage(im, 0, 0, null);
            b.getGraphics().drawImage(des1, -29, 30, null);
            ImageIO.write(b, "png", retFile);
            return retFile;
        } catch (IOException e) {
            return null;
        }
    }

    public File generateShenChu(File file) {
        try {
            File retFile = FileTool.createFile(new File(SBot.appDirectory + "imageFactory/shenchu/" + System.currentTimeMillis() + ".png"));
            BufferedImage src = ImageIO.read(file);
            BufferedImage des1 = new BufferedImage(228, 228, BufferedImage.TYPE_INT_ARGB);
            des1.getGraphics().drawImage(src, 0, 0, 228, 228, null);
            Image im = ImageIO.read(new File(SBot.appDirectory + "/baseImage/神触.png"));
            BufferedImage b = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            b.getGraphics().drawImage(im, 0, 0, null);
            b.getGraphics().drawImage(des1, 216, -20, null);
            ImageIO.write(b, "png", retFile); 
            return retFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage rotateImage(Image src, int angel) {
        int srcWidth = src.getWidth(null);
        int srcHeight = src.getHeight(null);
        if (angel >= 90) {
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

    private BufferedImage scaleImage(BufferedImage img, int newSize) {
        BufferedImage img2 = new BufferedImage(newSize, newSize, BufferedImage.TYPE_INT_ARGB);
        img2.getGraphics().drawImage(img, 0, 0, newSize, newSize, null);
        return img2;
    }
}
