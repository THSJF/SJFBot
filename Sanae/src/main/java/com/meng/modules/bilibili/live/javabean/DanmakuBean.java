package com.meng.modules.bilibili.live.javabean;

import com.meng.tools.JsonHelper;

public class DanmakuBean {
	/*	1.29800, 为弹幕播放起始时间 （在视频中出现的时间，单位是秒）

	 第二个参数是弹幕的模式1..3 滚动弹幕 4底端弹幕 5顶端弹幕 6.逆向弹幕 7精准定位 8高级弹幕 

	 第三个参数是字号， 12非常小,16特小,18小,25中,36大,45很大,64特别大 
	 第四个参数是字体的颜色以HTML颜色的十进制为准 
	 第五个参数是Unix格式的时间戳。基准时间为 1970-1-1 08:00:00 
	 第六个参数是弹幕池 0普通池 1字幕池 2特殊池【目前特殊池为高级弹幕专用】 
	 第七个参数是发送者的ID，用于“屏蔽此弹幕的发送者”功能 
	 第八个参数是弹幕在弹幕数据库中rowID 用于“历史弹幕”功能。
	 */
	public long uid = -1;
	public float time;
	public int mode;
	public int fontSize;
	public int color;
	public long timeStamp;
	public int danmakuPool;
	public long userHash;
	public long databaseId;
	public String msg;
	
	@Override
	public String toString() {
		return JsonHelper.toJson(this);
	}
}
