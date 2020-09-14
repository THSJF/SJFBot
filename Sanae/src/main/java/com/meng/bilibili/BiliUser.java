package com.meng.bilibili;

import java.util.*;

public class BiliUser {
	private int flags=0;
	private boolean lastStatus = false;
	private boolean needTip = false;
	public int roomID = -1;
	public int uid= -1;
	public long lastVideo = 0;
	public long lastArtical = 0;
	private boolean needTipArtical = false;
	private boolean needTipVideo = false;
	public ArrayList<FansInGroup> fans = new ArrayList<>();

	public void setNeedTipVideo(boolean needTipVideo) {
		if (needTipVideo) {
			flags |= (1 << 0);
		} else {
			flags &= ~(1 << 0);
		}
		this.needTipVideo = needTipVideo;
	}

	public boolean isNeedTipVideo() {
		// return flags&(1<<0)!=0;
		return needTipVideo;
	}

	public void setNeedTipArtical(boolean needTipArtical) {
		if (needTipArtical) {
			flags |= (1 << 1);
		} else {
			flags &= ~(1 << 1);
		}
		this.needTipArtical = needTipArtical;
	}

	public boolean isNeedTipArtical() {
		// return flags&(1<<1)!=0;
		return needTipArtical;
	}

	public void setNeedTip(boolean needTip) {
		if (needTip) {
			flags |= (1 << 2);
		} else {
			flags &= ~(1 << 2);
		}
		this.needTip = needTip;
	}

	public boolean isNeedTip() {
		// return flags&(1<<2)!=0;
		return needTip;
	}

	public void setLastStatus(boolean lastStatus) {
		if (lastStatus) {
			flags |= (1 << 3);
		} else {
			flags &= ~(1 << 3);
		}
		this.lastStatus = lastStatus;
	}

	public boolean lastStatus() {
		// return flags&(1<<3)!=0;
		return lastStatus;
	}

	public class FansInGroup {
		public long group;
		public long qq;
		public FansInGroup(long fromGroup, long fromQQ) {
			group = fromGroup;
			qq = fromQQ;
		}
	}

	public void addFans(long fromGroup, long fromQQ) {
		for (FansInGroup fig:fans) {
			if (fig.qq == fromQQ && fig.group == fromGroup) {
				return;
			}
		}
		fans.add(new FansInGroup(fromGroup, fromQQ));
	}

	public void removeFans(long fromGroup, long fromQQ) {
		for (int i=0;i < fans.size();++i) {
			if (fans.get(i).qq == fromQQ && fans.get(i).group == fromGroup) {
				fans.remove(i);
				return;
			}
		}
	}
}
