package com.meng.modules.qq.modules;

import com.meng.Functions;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.SJFMain;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileFormat;
import com.meng.tools.FileTool;
import com.meng.tools.Hash;
import com.meng.tools.Youtu;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class EuropeDogs extends BaseModule implements IGroupMessageEvent {
    public EuropeDogs(SBot s) {
        super(s);
    }
    @Override
    public EuropeDogs load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return "欧洲狗识别";
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        if (!entity.configManager.isFunctionEnabled(event.getGroup().getId(), Functions.EuropeDogs)) {
            return false;
        }
        Image img = event.getMessage().get(Image.Key);
        if (img == null) {
            FlashImage fi = event.getMessage().get(FlashImage.Key);
            if (fi != null) {
                img = fi.getImage();
            }
        }
        if (img != null) {
            try {
                String url = Image.queryUrl(img);
                byte[] fileBytes = Jsoup.connect(url).ignoreContentType(true).method(Connection.Method.GET).execute().bodyAsBytes();
                File folder = new File(SBot.appDirectory + "/image/Europe/");
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                File file = new File(folder.getAbsolutePath() + "/" + Hash.getMd5Instance().calculate(fileBytes) + "." + FileFormat.getFileType(fileBytes));
                FileTool.saveFile(file, fileBytes);
                SJFMain.lastImageFile = file;
                BufferedImage bfi = ImageIO.read(SJFMain.lastImageFile);
                float h = bfi.getHeight(null);
                float w = bfi.getWidth(null);
                float s = w / h;
                if (s > 1.7f) {
                    BufferedImage nbf = bfi.getSubimage(0, 0, (int)(w / 2), (int)h);
                    processImg(nbf, event);
                }
                file.delete();
            } catch (Exception e) {
                //     ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            }
        }
        return false;
    }

    private void processImg(BufferedImage img, GroupMessageEvent event) {
        File tmp = new File(SBot.appDirectory + "/image/Europe/" + System.currentTimeMillis() + ".jpg");
        try {  
            ImageIO.write(img, "jpg", tmp);
            StringBuilder sb = new StringBuilder();
            Youtu.OcrResult response = Youtu.getFaceYoutu().doOcrWithFile(tmp.getAbsolutePath());
            ArrayList<Youtu.OcrResult.Items> items = response.items;
            sb.append("Europe结果:");
            for (Youtu.OcrResult.Items s : items) {
                sb.append("\n").append(s.itemstring);
            }
            System.out.println(sb.toString());
            if (items.size() == 3 && items.get(0).itemstring.equals("极品") && (items.get(2).itemstring.equals("角色卡") || items.get(2).itemstring.equals("圣痕") || items.get(2).itemstring.equals("武器"))) {
                entity.sendMessage(event.getGroup(), items.get(1).itemstring + ",去你大爷的欧洲狗");
            }
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString());
        } finally {
            tmp.delete();   
        }
    }
}
