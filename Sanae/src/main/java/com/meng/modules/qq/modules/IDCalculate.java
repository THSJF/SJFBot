package com.meng.modules.qq.modules;
import com.meng.modules.qq.handler.friend.IFriendMessageEvent;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.BaseModule;
import com.meng.tools.TextLexer;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;

public class IDCalculate extends BaseModule implements IFriendMessageEvent,IGroupMessageEvent {

    private int[] magics = {7, 9,10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2}; //魔法师的味道

    public IDCalculate(SBot b) {
        super(b);
    }

    @Override
    public boolean onFriendMessage(FriendMessageEvent event) {
        return calculate(event);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent event) {
        return calculate(event);
    }

    private boolean calculate(MessageEvent event) {
        String msg = event.getMessage().contentToString();
        if (msg.length() != 17) {
            return false;
        }
        char[] chars = msg.toCharArray();
        if (!TextLexer.isNumber(chars)) {
            return false; 
        }
        StringBuilder builder = new StringBuilder(msg);
        int sum = 0;
        for (int i = 0;i < chars.length;++i) {
            sum += (chars[i] - '0') * magics[i];
        }
        int tmp = sum % 11;
        int finalResult = (12 - tmp) % 11;
        builder.append(tmp == 10 ? "X": finalResult);
        event.getSender().sendMessage(builder.toString());
        return true;
    }
}
