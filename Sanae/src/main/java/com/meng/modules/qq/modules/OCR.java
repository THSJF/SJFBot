package com.meng.modules.qq.modules;

import com.meng.Functions;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.Youtu;
import java.util.ArrayList;
import java.util.HashSet;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;

public class OCR extends BaseModule implements IGroupMessageEvent {

    private HashSet<Long> ready = new HashSet<>();

    public OCR(SBot sb) {
        super(sb);
    }

    @Override
    public OCR load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return getClass().getName();
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        if (!entity.configManager.isFunctionEnabled(event.getGroup().getId(), Functions.OCR)) {
            return false;
        }
        Image img = event.getMessage().get(Image.Key);
        if (img == null) {
            FlashImage fi = event.getMessage().get(FlashImage.Key);
            if (fi != null) {
                img = fi.getImage();
            }
        }
        String msg = event.getMessage().contentToString();
        long qqId = event.getSender().getId();
        if (img != null && (msg.toLowerCase().startsWith("ocr"))) {
            processImg(img, event);
            return true;
        } else if (img == null && msg.equals("ocr")) {
            ready.add(qqId);
            entity.sendQuote(event, "发送一张图片吧");
            return true;
        } else if (img != null && ready.contains(qqId)) {
            processImg(img, event);
            ready.remove(qqId);
            return true;
        }
        return false;
    }

    private void processImg(Image img, GroupMessageEvent event) {
        try {
            Youtu.OcrResult response = Youtu.getFaceYoutu().doOcrWithUrl(entity.getUrl(img));
            StringBuilder sb = new StringBuilder();
            ArrayList<Youtu.OcrResult.Items> items = response.items;
            sb.append("结果:");
            for (Youtu.OcrResult.Items s : items) {
                sb.append("\n").append(s.itemstring);
            }
            entity.sendMessage(event.getGroup(), sb.toString());
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString());
        }
    }
}
