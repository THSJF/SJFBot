package com.meng.modules;

import com.meng.Functions;
import com.meng.SBot;
import com.meng.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.Youtu;
import java.util.ArrayList;
import java.util.HashSet;
import net.mamoe.mirai.event.events.MemberNudgedEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;

public class ImageTag extends BaseModule implements IGroupMessageEvent {

    private HashSet<Long> ready = new HashSet<>();

    public ImageTag(SBot sb) {
        super(sb);
    }

    @Override
    public ImageTag load() {
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
        Image img = event.getMessage().firstOrNull(Image.Key);
        if (img == null) {
            FlashImage fi = event.getMessage().firstOrNull(FlashImage.Key);
            if (fi != null) {
                img = fi.getImage();
            }
        }
        String msg = event.getMessage().contentToString();
        long qqId = event.getSender().getId();
        if (img != null && (msg.toLowerCase().startsWith("tag"))) {
            processImg(img, event);
            return true;
        } else if (img == null && msg.equals("tag")) {
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
            Youtu.TagResult response = Youtu.getFaceYoutu().doTagWithUrl(entity.queryImageUrl(img));
            ArrayList<Youtu.TagResult.Tag> items = response.tags;
            StringBuilder sb = new StringBuilder("结果:");
            for (Youtu.TagResult.Tag tag : items) {
                sb.append("\n").append(tag.tag_name);
            }
            entity.sendMessage(event.getGroup(), sb.toString());
        } catch (Exception e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            entity.sendQuote(event, e.toString());
        }
    }

    @Override
    public boolean onGroupMemberNudge(MemberNudgedEvent event) {
        return false;
    }

    @Override
    public boolean onGroupMessageRecall(MessageRecallEvent.GroupRecall event) {
        return false;
    }
}
