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

public class Porn extends BaseModule implements IGroupMessageEvent {

    private HashSet<Long> ready = new HashSet<>();

    public Porn(SBot sb) {
        super(sb);
    }

    @Override
    public Porn load() {
        return this;
    }

    @Override
    public String getModuleName() {
        return getClass().getName();
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        if (!entity.configManager.isFunctionEnabled(event.getGroup().getId(), Functions.Porn)) {
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
        if (img != null && (msg.toLowerCase().startsWith("porn"))) {
            processImg(img, event);
            return true;
        } else if (img == null && msg.equals("porn")) {
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
            entity.sendQuote(event, "正在识别……");
            Youtu.PornResult response = Youtu.getFaceYoutu().doPornWithUrl(Image.queryUrl(img));
            ArrayList<Youtu.PornResult.Tag> items = response.tags;
            StringBuilder sb = new StringBuilder();
            for (Youtu.PornResult.Tag tag : items) {
                sb.append(switchTagName(tag.tag_name)).append(":").append(tag.tag_confidence).append("%\n");
            }
            sb.setLength(sb.length() - 1);
            entity.sendQuote(event, sb.toString());
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString().replace("java", "jvav"));
        }
    }

    private String switchTagName(String s) {
        switch (s) {
            case "normal":
                return "普通";
            case "hot":
                return "性感";
            case "porn":
                return "色情";
            case "female-genital":
                return "女性阴部";
            case "female-breast":
                return "女性胸部";
            case "male-genital":
                return "男性阴部";
            case "pubes":
                return "阴毛";
            case "anus":
                return "肛门";
            case "sex":
                return "性行为";
            case "normal_hot_porn":
                return "色情综合值";
            default:
                return null;
        }
    }
}
