package com.meng.modules.imageFactory;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import com.meng.modules.qq.SBot;
import java.awt.Image;

public class ImageFactory {

    private static ImageFactory instance;

    public static ImageFactory getInstance() {
        if(instance == null){
            instance = new ImageFactory();
        }
        return instance;
    }
    
    public File generateJingShenZhiZhu(File file) {
        try {
            File retFile = new File(SBot.appDirectory + "jingshenzhizhu/" + System.currentTimeMillis() + ".jpg");
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
            File retFile = new File(SBot.appDirectory + "shenchu/" + System.currentTimeMillis() + ".jpg");
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
