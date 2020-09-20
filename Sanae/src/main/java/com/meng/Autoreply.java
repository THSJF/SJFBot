package com.meng;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.meng.config.ConfigManager;
import com.meng.modules.ModuleManager;
import com.meng.tools.CleanRunnable;
import com.meng.tools.Tools;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import com.meng.tools.SJFExecutors;

public class Autoreply {

    public boolean sleeping = true;
   	public static final String userAgent="Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0";
	public static final long mainGroup=807242547L;
    public ModuleManager moduleManager;
    
    public static Gson gson;
    public CoolQ CQ;
    public CQCode CC;
    public Bot bot;
    public BotWrapper wrapper;

    static{
        GsonBuilder gb = new GsonBuilder();
        gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gson = gb.create();
    }

    public Autoreply(Bot b) {
        bot = b;
        CQ = new CoolQ(b);
        CC = new CQCode(bot);
        wrapper = new BotWrapper(bot, this,CQ ,CC);
        moduleManager = new ModuleManager(wrapper);
        startup();
    }

    public int startup() {
        // 获取应用数据目录(无需储存数据时，请将此行注释)

        // 返回如：D:\CoolQ\app\com.sobte.cqp.jcq\app\com.example.demo\
        System.out.println("开始加载");

		System.out.println("load success");
		new CleanRunnable();
		sleeping = false;
		return 0;
    }

    public int exit() {
		SJFExecutors.shutdownNow(this);
		System.exit(0);
        return 0;
    }

    public int privateMsg(int subType, final int msgId, final long fromQQ, final String msg, int font) {
        // 这里处理消息
        // if (fromQQ != 2856986197L) {
        // return 0;
        // }
        return 0;
    }

    public int groupMsg(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg, int font) {
		//	if (fromGroup != 807242547L){
		//		return 0;
		//}
        moduleManager.onGroupMessage(fromGroup,fromQQ,msg,msgId);
        return 0;
    }

    public int discussMsg(int subtype, int msgId, long fromDiscuss, long fromQQ, String msg, int font) {
        // 这里处理消息

        return 0;
    }

    public int groupUpload(int subType, int sendTime, long fromGroup, long fromQQ, String file) {
		return 0;
    }

    public int groupAdmin(int subtype, int sendTime, long fromGroup, long beingOperateQQ) {
        // 这里处理消息
		/*    if (subtype == 1) {
		 sendMessage(fromGroup, 0, CC.at(beingOperateQQ) + "你绿帽子没莉");
		 } else if (subtype == 2) {
		 sendMessage(fromGroup, 0, CC.at(beingOperateQQ) + "群主给了你个绿帽子");
		 }*/
        return 0;
    }

    /**
     * 好友事件-好友已添加 (Type=201)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype  子类型，目前固定为1
     * @param sendTime 发送时间(时间戳)
     * @param fromQQ   来源QQ
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int friendAdd(int subtype, int sendTime, long fromQQ) {
        // 这里处理消息

        return 0;
    }

    /**
     * 请求-好友添加 (Type=301)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype      子类型，目前固定为1
     * @param sendTime     发送时间(时间戳)
     * @param fromQQ       来源QQ
     * @param msg          附言
     * @param responseFlag 反馈标识(处理请求用)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int requestAddFriend(int subtype, int sendTime, long fromQQ, String msg, String responseFlag) {
        // 这里处理消息
        if (ConfigManager.instence.isNotReplyQQ(fromQQ)) {
            CQ.setFriendAddRequest(responseFlag, REQUEST_REFUSE, "");
			sendMessage(0, 2856986197L, "拒绝了" + fromQQ + "加为好友");
            return 0;
        }
        /*
         * REQUEST_ADOPT 通过 REQUEST_REFUSE 拒绝
         */
        sendMessage(0, 2856986197L, fromQQ + "把我加为好友");
        // 同意好友添加请求
        return 0;
    }

	/**
     * 群事件-群禁言 (Type={@link IType#EVENT_System_GroupBan 104})<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subType        子类型，1/被解禁 2/被禁言
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param fromQQ         操作者QQ
     * @param beingOperateQQ 被操作QQ(若为全群禁言/解禁，则本参数为 0)
     * @param duration       禁言时长(单位 秒，仅子类型为2时可用)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
	public int groupBan(int subType, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ, long duration) {
		System.out.println(fromGroup + "中" + fromQQ + "禁言" + beingOperateQQ + duration);
		return 0;
	}

    /**
     * 请求-群添加 (Type=302)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype      子类型，1/他人申请入群 2/自己(即登录号)受邀入群
     * @param sendTime     发送时间(时间戳)
     * @param fromGroup    来源群号
     * @param fromQQ       来源QQ
     * @param msg          附言
     * @param responseFlag 反馈标识(处理请求用)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int requestAddGroup(int subtype, int sendTime, final long fromGroup, final long fromQQ, String msg, final String responseFlag) {
        // 这里处理消息

        /*
         * REQUEST_ADOPT 通过 REQUEST_REFUSE 拒绝 REQUEST_GROUP_ADD 群添加
         * REQUEST_GROUP_INVITE 群邀请
         */
        if (subtype == 1) {
            if (ConfigManager.instence.isBlackQQ(fromQQ)) {
                CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_ADD, REQUEST_REFUSE, "黑名单用户");
				sendMessage(fromGroup, 0, "拒绝了黑名单用户" + fromQQ + "的加群申请");
				return 0;
            }
			sendMessage(fromGroup, 0, "有人申请加群，管理员快看看吧");
        } else if (subtype == 2) {
            if (ConfigManager.instence.isBlackQQ(fromQQ) || ConfigManager.instence.isBlackGroup(fromGroup)) {
                CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_ADD, REQUEST_REFUSE, "");
				sendMessage(0, 2856986197L, "拒绝了" + fromQQ + "邀请我加入群" + fromGroup);
                return 0;
            }
			CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_INVITE, REQUEST_ADOPT, null);
			sendMessage(0, 2856986197L, fromQQ + "邀请我加入群" + fromGroup);
		}
        return 0;
    }

    public int sendGroupMessage(long fromGroup, Message msg) {
        if (sleeping || ConfigManager.instence.SanaeConfig.botOff.contains(fromGroup)) {
            return -1;
        }
        ++RemoteWebSocket.botInfoBean.msgSendPerSec;
        int value=-1;
        value = CQ.sendGroupMsg(fromGroup, msg);
        ModuleManager.instence.getModule(ModuleGroupCounter.class).onMsg(fromGroup, 0, "", 0);
        remoteWebSocket.sendMsg(1, fromGroup, CQ.getLoginQQ(), msg.contentToString(), value);
        return value;
    }

    public int sendGroupMessage(long fromGroup, String msg) {
        return sendGroupMessage(fromGroup, new PlainText(msg));
    }

	public  int sendGroupMessage(long fromGroup, String[] msg) {
		return sendGroupMessage(fromGroup, (String)Tools.ArrayTool.rfa(msg));
    }

	public int sendGroupMessage(long fromGroup, ArrayList<String> msg) {
		return sendGroupMessage(fromGroup, msg.get(random.nextInt(msg.size())));
    }
}
