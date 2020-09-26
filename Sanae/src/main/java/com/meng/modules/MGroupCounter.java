package com.meng.modules;

import com.google.gson.reflect.TypeToken;
import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.SJFInterfaces.IPersistentData;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.DataPersistenter;
import com.meng.tools.SJFExecutors;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Description:群发言统计 
 * @author: 司徒灵羽
 **/

public class MGroupCounter extends BaseGroupModule implements IPersistentData {

    private HashMap<String, GroupInfo> countMap = new HashMap<>();

    public MGroupCounter(BotWrapperEntity bw) {
        super(bw);
    }

    public class GroupInfo {
        public int speak = 0;
        public int pic = 0;
        public int biliLink = 0;
        public int repeat = 0;
        public int repeatBreak = 0;
        public int pohai = 0;
        public int sp = 0;
        public int mengEr = 0;
        public int time = 0;
        public int grass = 0;
    }

	@Override
	public MGroupCounter load() {
		DataPersistenter.read(this);
		SJFExecutors.executeAtFixedRate(new Runnable() {
				@Override
				public void run() {
					saveData();
				}
			}, 1, 1, TimeUnit.MINUTES);
		return this;
    }

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long fromGroup = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
		if (!entity.configManager.getGroupConfig(fromGroup).isGroupCountEnable()) {
			return false;
		}
		GroupInfo groupInfo = getBean(fromGroup);
        ++groupInfo.speak;
		if (msg.contains("艹") || msg.contains("草")) {
			incGrass(fromGroup);
        }
		if (msg.equals("查看群统计")) {
			entity.sjfTx.sendGroupMessage(fromGroup, getMyCount(fromGroup));
            return true;
		}
        if (msg.equals("查看群排行")) {
			entity.sjfTx.sendGroupMessage(fromGroup, getTheFirst());
            return true;
		}
		return false;
	}

    public void incSpeak(long qq) {
		GroupInfo groupInfo = getBean(qq);
        ++groupInfo.speak;	
    }

    public void incPic(long qq) {
        GroupInfo groupInfo = getBean(qq);
        ++groupInfo.pic;
    }

    public void incPohaitu(long qq) {
        GroupInfo groupInfo = getBean(qq);
        ++groupInfo.pohai;
    }

    public void incFudu(long qq) {
        GroupInfo groupInfo = getBean(qq);
        ++groupInfo.repeat;
    }

    public void incRepeatBreaker(long qq) {
        GroupInfo groupInfo = getBean(qq);
        ++groupInfo.repeatBreak;
    }

    public void incSearchPicture(long qq) {
        GroupInfo groupInfo = getBean(qq);
        ++groupInfo.sp;
    }

    public void incBilibiliLink(long qq) {
        GroupInfo groupInfo = getBean(qq);
        ++groupInfo.biliLink;
    }

    public void incMengEr(long qq) {
        GroupInfo groupInfo = getBean(qq);
        ++groupInfo.mengEr;
    }

    public void decLife(long qq) {
        GroupInfo groupInfo = getBean(qq);
        --groupInfo.time;
    }

    public void incGrass(long qq) {
        GroupInfo groupInfo = getBean(qq);
        ++groupInfo.grass;
    }

    private GroupInfo getBean(long qq) {
        GroupInfo groupInfo = countMap.get(String.valueOf(qq));
        if (groupInfo == null) {
            groupInfo = new GroupInfo();
            countMap.put(String.valueOf(qq), groupInfo);
        }
        return groupInfo;
    }

    public String getMyCount(long qq) {
        GroupInfo groupInfo = countMap.get(String.valueOf(qq));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("你群共");
        if (groupInfo.speak != 0) {
            stringBuilder.append("水群").append(groupInfo.speak).append("句");
        }
        if (groupInfo.pic != 0) {
            stringBuilder.append("\n").append("发图").append(groupInfo.pic).append("次");
        }
        if (groupInfo.repeat != 0) {
            stringBuilder.append("\n").append("复读").append(groupInfo.repeat).append("次");
        }
        if (groupInfo.pohai != 0) {
            stringBuilder.append("\n").append("迫害").append(groupInfo.pohai).append("次");
        }
        if (groupInfo.repeatBreak != 0) {
            stringBuilder.append("\n").append("打断复读").append(groupInfo.repeatBreak).append("次");
        }
        if (groupInfo.sp != 0) {
            stringBuilder.append("\n").append("搜图").append(groupInfo.sp).append("次");
        }
        if (groupInfo.biliLink != 0) {
            stringBuilder.append("\n").append("bilibili链接").append(groupInfo.biliLink).append("次");
        }
        if (groupInfo.mengEr != 0) {
            stringBuilder.append("\n").append("无悔发言").append(groupInfo.mengEr).append("次");
        }
        if (groupInfo.time != 0) {
            stringBuilder.append("\n").append("时间").append(groupInfo.time).append("秒");
        }
        if (groupInfo.grass != 0) {
            stringBuilder.append("\n").append("为绿化贡献").append(groupInfo.grass).append("棵草");
        }
        return stringBuilder.toString();

    }

    public String getTheFirst() {
        int pic = 0;
        int pohai = 0;
        int repeat = 0;
        int repeatBreaker = 0;
        int biliLink = 0;
        int sp = 0;
        int speak = 0;
        int mengEr = 0;
        int time = 0;
        int grass = 0;
        String picq = null;
        String pohaiq = null;
        String repeatq = null;
        String repeatBreakerq = null;
        String biliLinkq = null;
        String spq = null;
        String speakq = null;
        String mengErq = null;
        String timeq = null;
        String grassq = null;

        for (Entry<String, GroupInfo> entry : countMap.entrySet()) {
            GroupInfo groupInfo = entry.getValue();
            if (groupInfo.speak > speak) {
                speak = groupInfo.speak;
                speakq = entry.getKey();
            }
            if (groupInfo.pic > pic) {
                pic = groupInfo.pic;
                picq = entry.getKey();
            }
            if (groupInfo.pohai > pohai) {
                pohai = groupInfo.pohai;
                pohaiq = entry.getKey();
            }
            if (groupInfo.repeat > repeat) {
                repeat = groupInfo.repeat;
                repeatq = entry.getKey();
            }
            if (groupInfo.repeatBreak > repeatBreaker) {
                repeatBreaker = groupInfo.repeatBreak;
                repeatBreakerq = entry.getKey();
            }
            if (groupInfo.biliLink > biliLink) {
                biliLink = groupInfo.biliLink;
                biliLinkq = entry.getKey();
            }
            if (groupInfo.sp > sp) {
                sp = groupInfo.sp;
                spq = entry.getKey();
            }
            if (groupInfo.mengEr > mengEr) {
                mengEr = groupInfo.mengEr;
                mengErq = entry.getKey();
            }
            if (groupInfo.time < time) {
                time = groupInfo.time;
                timeq = entry.getKey();
            }
            if (groupInfo.grass > grass) {
                grass = groupInfo.grass;
                grassq = entry.getKey();
            }
        }
        StringBuilder sb = new StringBuilder();
        if (speakq != null) {
            sb.append(speakq).append("水群").append(speak).append("句");
        }
        if (picq != null) {
            sb.append("\n").append(picq).append("发图").append(pic).append("张");
        }
        if (pohaiq != null) {
            sb.append("\n").append(pohaiq).append("迫害").append(pohai).append("次");
        }
        if (repeatq != null) {
            sb.append("\n").append(repeatq).append("复读").append(repeat).append("次");
        }
        if (repeatBreakerq != null) {
            sb.append("\n").append(repeatBreakerq).append("打断复读").append(repeatBreaker).append("次");
        }
        if (biliLinkq != null) {
            sb.append("\n").append(biliLinkq).append("bilibili链接").append(biliLink).append("次");
        }
        if (spq != null) {
            sb.append("\n").append(spq).append("搜图").append(sp).append("次");
        }
        if (mengErq != null) {
            sb.append("\n").append(mengErq).append("无悔发言").append(mengEr).append("次");
        }
        if (timeq != null) {
            sb.append("\n").append(timeq).append("时间").append(time).append("秒");
        }
        if (grassq != null) {
            sb.append("\n").append(grassq).append("为绿化贡献").append(grass).append("棵草");
        }
        return sb.toString();
    }

    private void saveData() {
        DataPersistenter.save(this);
    }

	@Override
	public String getPersistentName() {
		return "GroupCount.json";
	}

	@Override
	public Type getDataType() {
		return new TypeToken<HashMap<String, GroupInfo>>() {}.getType();
	}

	@Override
	public Object getDataBean() {
		return countMap;
	}

    @Override
    public BotWrapperEntity getWrapper() {
        return entity;
    }

	@Override
	public void setDataBean(Object o) {
		countMap = (HashMap) o;
	}
}

