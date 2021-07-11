package com.meng.modules.qq.modules;

import com.meng.bot.Functions;
import com.meng.config.CommandDescribe;
import com.meng.config.ConfigManager;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.ExceptionCatcher;
import com.meng.tools.FileTool;
import com.meng.tools.Network;
import com.meng.tools.SJFExecutors;
import com.meng.tools.SJFPathTool;
import com.meng.tools.SJFRandom;
import com.meng.tools.SeijaImageFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.EmptyMessageChain;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

/**
 * @Description: 复读机
 * @author: 司徒灵羽
 **/
public class RepeaterManager extends BaseModule implements IGroupMessageEvent {

	private HashMap<Long, BaseRepeater> repeaters = new HashMap<>();

    public RepeaterManager(SBot bw) {
        super(bw);
    }

	@Override
    @CommandDescribe(cmd = "-", note = "复读机")
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long groupId = gme.getGroup().getId();
        BaseRepeater rp = repeaters.get(groupId);
        if (!ConfigManager.getInstance().isFunctionEnabled(gme.getGroup(), Functions.Repeater)) {
            return false;
        }
		if (rp == null) {
			repeaters.put(groupId, rp = new ReverseRepeater());
		}
        long qqId = gme.getSender().getId();
        return rp.check(groupId, qqId, gme.getMessage());
    }

	private abstract class BaseRepeater {
		private MessageChain lastMsgRecieved = EmptyMessageChain.INSTANCE;
		private boolean lastStatus = false;
       	private boolean check(long groupId, long qqId, MessageChain msg) {
            boolean b = false; 
            if (!lastStatus && SBot.messageEquals(lastMsgRecieved, msg)) {
                b = repeatStart(groupId, qqId, msg);
            } else if (lastStatus && SBot.messageEquals(lastMsgRecieved, msg)) {
                b = repeatRunning(groupId, qqId, msg);
            } else if (lastStatus && !SBot.messageEquals(lastMsgRecieved, msg)) {
                b = repeatEnd(groupId, qqId, msg);
            }
			lastStatus = SBot.messageEquals(lastMsgRecieved, msg);
            lastMsgRecieved = msg;
			return b;
		}

		protected abstract boolean repeatEnd(long groupId, long qqId, MessageChain msg);
		protected abstract boolean repeatRunning(long groupId, long qqId, MessageChain msg);
		protected abstract boolean repeatStart(long groupId, long qqId, MessageChain msg);
	}

    private boolean isText(MessageChain mc) {
        for (Message msg : mc) {
            if (!(msg instanceof PlainText)) {
                return false;
            }
        }
        return true;
    }

    private class SimpleRepeater extends BaseRepeater {

        @Override
        protected boolean repeatEnd(long groupId, long qqId, MessageChain msg) {
            return false;
        }

        @Override
        protected boolean repeatRunning(long groupId, long qqId, MessageChain msg) {
            return false;
        }

        @Override
        protected boolean repeatStart(final long groupId, long qqId, final MessageChain msg) {
            SJFExecutors.execute(new Runnable(){

                    @Override
                    public void run() {
//                        String text;
//                        if (times % 4 == 0 && isText(msg)) {
//                            text = new StringBuilder(msg.contentToString()).reverse().toString();
//                            if (text.equals(msg.contentToString())) {
//                                text += " ";
//                            }
//                            sendGroupMessage(groupId, text);
//                            return;
//                        }
                        sendGroupMessage(groupId, msg);
                    }
                });
            return true;
        }
    }

    private class ReverseRepeater extends BaseRepeater {

        private int times = 0;

        @Override
        protected boolean repeatEnd(long groupId, long qqId, MessageChain msg) {
            return false;
        }

        @Override
        protected boolean repeatRunning(long groupId, long qqId, MessageChain msg) {
            return false;
        }

        @Override
        protected boolean repeatStart(final long groupId, long qqId, final MessageChain msg) {
            SJFExecutors.execute(new Runnable(){

                    @Override
                    public void run() {
                        if (times++ % 4 == 0 && isText(msg)) {
                            String text = new StringBuilder(msg.contentToString()).reverse().toString();
                            if (text.equals(msg.contentToString())) {
                                text += " ";
                            }
                            sendGroupMessage(groupId, text);
                            return;
                        } else if (msg.get(Image.Key) != null) {
                            if (SJFRandom.randomInt(5) == 1) {
                                Image miraiImg = msg.get(Image.Key);
                                File imageFile = SJFPathTool.getImagePath("gen/" + SJFRandom.randomInt() + ".png");
                                FileTool.saveFile(imageFile, Network.httpGetRaw(entity.getUrl(miraiImg)));
                                try {
                                    sendGroupMessage(groupId, entity.toImage(SeijaImageFactory.reverseGIF(imageFile, 2), SBot.instance.getGroup(groupId)));
                                } catch (FileNotFoundException e) {
                                    ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
                                }
                            }
                            return;
                        }
                        sendGroupMessage(groupId, msg);
                    }
                });
            return true;
        }
    }
}

