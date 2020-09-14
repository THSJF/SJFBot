package com.meng;

import com.google.gson.*;
import com.meng.bilibili.*;
import com.meng.config.*;
import com.meng.game.TouHou.*;
import com.meng.groupMsgProcess.*;
import com.meng.remote.*;
import com.meng.timeTask.*;
import com.meng.tip.*;
import com.meng.tools.*;
import com.sobte.cqp.jcq.entity.*;
import com.sobte.cqp.jcq.event.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Autoreply {

    public static Autoreply ins;
	public MyRandom random = new MyRandom();
	public ModuleCQCode CQcodeManager = new ModuleCQCode();
    public GroupMemberChangerListener groupMemberChangerListener;
    public static String appDirectory = "";
    public ExecutorService threadPool = Executors.newCachedThreadPool();

    public static boolean sleeping = true;

	public static final String userAgent="Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0";
    public HashSet<Long> SeijiaInThis = new HashSet<>();
	public BirthdayTip birthdayTip;

	public RemoteWebSocket remoteWebSocket;

	public static final long mainGroup=807242547L;

    public Gson gson;
	
    public static void main(String[] args) {
        
        Autoreply demo = new Autoreply();
        demo.startup();
    }

    public int startup() {
        // 获取应用数据目录(无需储存数据时，请将此行注释)
        ins = this;
        
		GsonBuilder gb = new GsonBuilder();
		gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		gson = gb.create();
        // 返回如：D:\CoolQ\app\com.sobte.cqp.jcq\app\com.example.demo\
        System.out.println("开始加载");
		ConfigManager.instence = new ConfigManager();
		ModuleManager.instence = new ModuleManager();
		ModuleManager.instence.load();
		ConfigManager.instence.load();
		groupMemberChangerListener = new GroupMemberChangerListener();
		birthdayTip = new BirthdayTip();
		threadPool.execute(new TimeTaskManager());
		threadPool.execute(new UpdateListener());
		threadPool.execute(new LiveListener());
		remoteWebSocket = new RemoteWebSocket();
		remoteWebSocket.start();
		new SoftUpgradeServer(new InetSocketAddress(9234)).start();
		threadPool.execute(new Runnable(){

				@Override
				public void run() {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {}
					List<Group> groupList=Autoreply.CQ.getGroupList();
					for (Group g:groupList) {
						List<Member> mlist=Autoreply.CQ.getGroupMemberList(g.getId());
						for (Member m:mlist) {
							if (m.getQqId() == 2089693971L) {
								Autoreply.ins.SeijiaInThis.add(g.getId());
								break;
							}
						}
					}
				}
			});
		try {
			//new QuestionServer(9961).start();
		} catch (Exception e) {}
		enable();
		System.out.println("load success");
		threadPool.execute(new CleanRunnable());
		Autoreply.sleeping = false;
		return 0;
    }

    @Override
    public int exit() {
		threadPool.shutdownNow();
		System.exit(0);
        return 0;
    }

    @Override
    public int enable() {
        enable = true;
        return 0;
    }

    @Override
    public int disable() {
        enable = false;
        return 0;
    }

    @Override
    public int privateMsg(int subType, final int msgId, final long fromQQ, final String msg, int font) {
        // 这里处理消息
        // if (fromQQ != 2856986197L) {
        // return MSG_IGNORE;
        // }
        if (ConfigManager.instence.isNotReplyQQ(fromQQ) || ConfigManager.instence.isNotReplyWord(msg)) {
            return MSG_IGNORE;
        }
        if (ConfigManager.instence.isMaster(fromQQ)) {
			String[] strings = msg.split("\\.", 3);
			if (strings[0].equals("send")) {
				sendMessage(Long.parseLong(strings[1]), 0, strings[2]);
			}
		}
		ModuleFaith fm=ModuleManager.instence.getModule(ModuleFaith.class);
		if (msg.startsWith("-qa a ")) {
			if (fm.getFaith(fromQQ) <= 0) {
				sendMessage(0, fromQQ, "你的信仰值不足以完成此操作");
				return MSG_IGNORE;
			}
			QA qa=ModuleManager.instence.getModule(ModuleQA.class).qaList.get(Integer.parseInt(msg.substring("-qa a ".length())));
			HashSet<Integer> trueAnswers=qa.getTrueAns();
			StringBuilder sb=new StringBuilder();
			sb.append("题目:\n");
			sb.append(qa.q);
			sb.append("\n答案:");
			for (int i:trueAnswers) {
				sb.append("\n").append(qa.a.get(i));
			}
			sb.append("\n消耗1信仰查看了答案,剩余:");
			sb.append(fm.getFaith(fromQQ));
			fm.subFaith(fromQQ, 1);
			sendMessage(0, fromQQ, sb.toString());
		}
        return MSG_IGNORE;
    }

    @Override
    public int groupMsg(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg, int font) {
		//	if (fromGroup != 807242547L){
		//		return MSG_IGNORE;
		//}
		++RemoteWebSocket.botInfoBean.msgPerSec;
		remoteWebSocket.sendMsg(1, fromGroup, fromQQ, msg, msgId);
		if (!Autoreply.ins.SeijiaInThis.contains(fromGroup)) {
			ConfigManager.instence.send(SanaeDataPack.encode(SanaeDataPack.opIncSpeak).write(fromGroup).write(fromQQ));
		}
        threadPool.execute(new MsgRunnable(fromGroup, fromQQ, msg, msgId));
        return MSG_IGNORE;
    }

    @Override
    public int discussMsg(int subtype, int msgId, long fromDiscuss, long fromQQ, String msg, int font) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    @Override
    public int groupUpload(int subType, int sendTime, long fromGroup, long fromQQ, String file) {
		return MSG_IGNORE;
    }

    @Override
    public int groupAdmin(int subtype, int sendTime, long fromGroup, long beingOperateQQ) {
        // 这里处理消息
		/*    if (subtype == 1) {
		 sendMessage(fromGroup, 0, CC.at(beingOperateQQ) + "你绿帽子没莉");
		 } else if (subtype == 2) {
		 sendMessage(fromGroup, 0, CC.at(beingOperateQQ) + "群主给了你个绿帽子");
		 }*/
        return MSG_IGNORE;
    }

    /**
     * 群事件-群成员减少 (Type=102)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype        子类型，1/群员离开 2/群员被踢
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param fromQQ         操作者QQ(仅子类型为2时存在)
     * @param beingOperateQQ 被操作QQ
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    @Override
    public int groupMemberDecrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
        // 这里处理消息
        groupMemberChangerListener.checkDecrease(subtype, sendTime, fromGroup, fromQQ, beingOperateQQ);
        return MSG_IGNORE;
    }

    /**
     * 群事件-群成员增加 (Type=103)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype        子类型，1/管理员已同意 2/管理员邀请
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param fromQQ         操作者QQ(即管理员QQ)
     * @param beingOperateQQ 被操作QQ(即加群的QQ)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    @Override
    public int groupMemberIncrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
        // 这里处理消息
        if (beingOperateQQ == CQ.getLoginQQ()) {
            return MSG_IGNORE;
        }
        groupMemberChangerListener.checkIncrease(subtype, sendTime, fromGroup, fromQQ, beingOperateQQ);
        return MSG_IGNORE;
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
    @Override
    public int friendAdd(int subtype, int sendTime, long fromQQ) {
        // 这里处理消息

        return MSG_IGNORE;
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
    @Override
    public int requestAddFriend(int subtype, int sendTime, long fromQQ, String msg, String responseFlag) {
        // 这里处理消息
        if (ConfigManager.instence.isNotReplyQQ(fromQQ)) {
            CQ.setFriendAddRequest(responseFlag, REQUEST_REFUSE, "");
			sendMessage(0, 2856986197L, "拒绝了" + fromQQ + "加为好友");
            return MSG_IGNORE;
        }
        /*
         * REQUEST_ADOPT 通过 REQUEST_REFUSE 拒绝
         */
        sendMessage(0, 2856986197L, fromQQ + "把我加为好友");
        // 同意好友添加请求
        return MSG_IGNORE;
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
		return MSG_IGNORE;
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
    @Override
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
				return MSG_IGNORE;
            }
			sendMessage(fromGroup, 0, "有人申请加群，管理员快看看吧");
        } else if (subtype == 2) {
            if (ConfigManager.instence.isBlackQQ(fromQQ) || ConfigManager.instence.isBlackGroup(fromGroup)) {
                CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_ADD, REQUEST_REFUSE, "");
				sendMessage(0, 2856986197L, "拒绝了" + fromQQ + "邀请我加入群" + fromGroup);
                return MSG_IGNORE;
            }
			CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_INVITE, REQUEST_ADOPT, null);
			sendMessage(0, 2856986197L, fromQQ + "邀请我加入群" + fromGroup);
		}
        return MSG_IGNORE;
    }

    public static int sendMessage(long fromGroup, long fromQQ, String msg) {
		if (sleeping || ConfigManager.instence.SanaeConfig.botOff.contains(fromGroup)) {
            return -1;
        }
		++RemoteWebSocket.botInfoBean.msgSendPerSec;
		int value=-1;
        if (fromGroup == 0 || fromGroup == -1) {
            value = CQ.sendPrivateMsg(fromQQ, msg);
        } else {
			value = CQ.sendGroupMsg(fromGroup, msg);
			ModuleManager.instence.getModule(ModuleGroupCounter.class).onMsg(fromGroup, 0, "", 0);
			ins.remoteWebSocket.sendMsg(1, fromGroup, CQ.getLoginQQ(), msg, value);
        }
		return value;
    }

	public static int sendMessage(long fromGroup, long fromQQ, String[] msg) {
		return sendMessage(fromGroup, fromQQ, (String)Tools.ArrayTool.rfa(msg));
    }

	public static int sendMessage(long fromGroup, long fromQQ, ArrayList<String> msg) {
		return sendMessage(fromGroup, fromQQ, msg.get(ins.random.nextInt(msg.size())));
    }
}
