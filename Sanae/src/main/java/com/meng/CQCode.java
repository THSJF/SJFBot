package com.meng;
import java.io.File;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.Image;

public class CQCode {
    private Bot bot;

    public CQCode(Bot b) {
        bot = b;
    }

    public String at(long qqId) {
        return String.format("[mirai:at:%d,%d]", qqId, qqId);
    }

    public String at(long groupId, long qqId) {
        return String.format("[mirai:at:%d,%d]", qqId, bot.getGroup(groupId).get(qqId).getNameCard());
    }

    public String at(long qqId, String name) {
        return String.format("[mirai:at:%d,%d]", qqId, name);
    }

    public String image(File f, long groupId) {
        return bot.getGroup(groupId).uploadImage(f).toString();
    }

    public long getAt(String msg) {
        return 0;
        return;
    }
}
