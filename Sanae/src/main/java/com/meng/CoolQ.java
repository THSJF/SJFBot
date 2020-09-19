package com.meng;

import java.io.File;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.Message;

public class CoolQ extends SimpleListenerHost {
    private Bot bot;
    public CoolQ(Bot b) {
        bot = b; 
    }
    protected String appDirectory;

    {
        File file = new File("C://sanae_data");
        if (!file.exists()) {
            file.mkdirs();
        }
        appDirectory = file.getAbsolutePath();   
    }
    /**
     * 获取应用目录
     *
     * @return 应用目录, 返回的路径末尾带"\"
     */
    public String getAppDirectory() {
        return appDirectory;
    }

    /**
     * 获取登录QQ
     *
     * @return 登录QQ
     */
    public long getLoginQQ() {
        return bot.getId();
    }

    /**
     * 获取登录昵称
     *
     * @return 登录昵称
     */
    public String getLoginNick() {
        return bot.getNick();
    }

    /**
     * 发送私聊消息
     *
     * @param qqId 目标QQ
     * @param msg  消息内容
     * @return 失败返回负值, 成功返回消息ID
     */
    public int sendPrivateMsg(long qqId, String msg) {
        try {
            bot.getFriend(qqId).sendMessage(msg);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 发送群消息
     *
     * @param groupId 目标群
     * @param msg     消息内容
     * @return 失败返回负值, 成功返回消息ID
     */
    public int sendGroupMsg(long groupId, Message msg) {
        try {
            bot.getGroup(groupId).sendMessage(msg);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int sendGroupMsg(long groupId, String msg) {
        try {
            bot.getGroup(groupId).sendMessage(msg);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 撤回消息
     *
     * @param msgId 消息ID
     * @return 状态码
     */
    public int deleteMsg(MessageChain mc) {
        bot.recall(mc);
        return 0;
    }

    public int deleteMsg(MessageSource ms) {
        bot.recall(ms);
        return 0;
    }

    /**
     * 接收消息中的语音(record)
     *
     * @param file      收到消息中的语音文件名(file)
     * @param outformat 应用所需的语音文件格式，目前支持 mp3,amr,wma,m4a,spx,ogg,wav,flac
     * @return 返回保存在 \data\record\ 目录下的文件名
     */
    public String getRecord(MessageChain mc) {

        try {

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return getRecord(file, outformat);
    }

    /**
     * 移除群员
     *
     * @param groupId 目标群
     * @param qqId    目标QQ
     * @param notBack 如果为true，则“不再接收此人加群申请”，请慎用(无效)
     * @return 状态码
     */
    public int setGroupKick(long groupId, long qqId, boolean notBack) {
        try {
            bot.getGroup(groupId).get(qqId).kick();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 禁言群员
     *
     * @param groupId 群号
     * @param qqId    目标QQ
     * @param banTime 禁言的时间，单位为秒。如果要解禁，这里填写0
     * @return 状态码
     */
    public int setGroupBan(long groupId, long qqId, int banTime) {
        try {
            bot.getGroup(groupId).get(qqId).mute(banTime);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 全群禁言
     *
     * @param groupId 目标群
     * @param isBan   true/开启 false/关闭
     * @return 状态码
     */
    public int setGroupWholeBan(long groupId, boolean isBan) {
        try {
            bot.getGroup(groupId).getSettings().setMuteAll(isBan);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 设置群成员名片
     *
     * @param groupId 目标群
     * @param qqId    目标QQ
     * @param nick    新名片(昵称)
     * @return 状态码
     */
    public int setGroupCard(long groupId, long qqId, String nick) {
        try {
            bot.getGroup(groupId).get(qqId).setNameCard(nick);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 退出QQ群,慎用,此接口需要严格授权
     *
     * @param groupId   目标群
     * @param isDisband 默认为假 true/解散本群(群主) false/退出本群(管理、群成员)(无效)
     * @return 状态码
     */
    public int setGroupLeave(long groupId, boolean isDisband) {
        return bot.getGroup(groupId).quit() ?0: -1;
    }

    /**
     * 设置群成员专属头衔,需群主权限
     *
     * @param groupId    目标群
     * @param qqId       目标QQ
     * @param title      头衔，如果要删除，这里填空
     * @param expireTime 专属头衔有效期，单位为秒。如果永久有效，这里填写-1(无效)
     * @return 状态码
     */
    public int setGroupSpecialTitle(long groupId, long qqId, String title, long expireTime) {
        try {
            bot.getGroup(groupId).get(qqId).setSpecialTitle(title);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取群成员信息
     *
     * @param groupId 目标QQ所在群
     * @param qqId    目标QQ
     * @return 如果成功，返回群成员信息，失败返回null
     */
    public Member getGroupMemberInfo(long groupId, long qqId) {
        return bot.getGroup(groupId).get(qqId);
    }


    @EventHandler
    public int setFriendAddRequest(NewFriendRequestEvent nfre, boolean accept) {
        if (accept) {
            nfre.accept();
        } else {
            nfre.reject();
        }
        return 0;
    }

    /**
     * 处理群添加请求
     *
     * @param responseFlag 请求事件收到的“responseFlag”参数
     * @param requestType  根据请求事件的子类型区分 REQUEST_GROUP_ADD(群添加) 或 REQUEST_GROUP_INVITE(群邀请)
     * @param backType     REQUEST_ADOPT(通过) 或 REQUEST_REFUSE(拒绝)
     * @param reason       操作理由，仅 REQUEST_GROUP_ADD(群添加) 且 REQUEST_REFUSE(拒绝) 时可用
     * @return 状态码
     */
    public int setGroupAddRequest(MemberJoinRequestEvent mjre, boolean accept) {
        if (accept) {
            mjre.accept();
        } else {
            mjre.reject();
        }
        return 0;
    }

    /**
     * 获取群成员列表
     *
     * @param groupId 目标群
     * @return 如果成功，返回群成员列表
     */
    public ContactList<Member> getGroupMemberList(long groupId) {
        try {
            return bot.getGroup(groupId).getMembers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取群列表
     *
     * @return 如果成功，返回群列表
     */
    public ContactList<Group> getGroupList() {
        try {
            return bot.getGroups();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    } 
}
