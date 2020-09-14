package com.meng.bilibili;

import com.google.gson.*;
import com.meng.*;
import com.meng.config.*;
import com.meng.groupMsgProcess.*;
import com.meng.tools.*;

public class LiveListener implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				for (BiliUser personInfo : ConfigManager.instence.SanaeConfig.biliMaster.values()) {
					if (personInfo.roomID == 0 || personInfo.roomID == -1) {
						continue;
					}
					SpaceToLiveJavaBean sjb = new Gson().fromJson(Tools.Network.getSourceCode("https://api.live.bilibili.com/room/v1/Room/getRoomInfoOld?mid=" + personInfo.uid), SpaceToLiveJavaBean.class);
					boolean living = sjb.data.liveStatus == 1;
					if (personInfo.isNeedTip()) {
						if (!personInfo.lastStatus() && living) {
							onStart(personInfo);
						} else if (personInfo.lastStatus() && !living) {
							onStop(personInfo);
						}
					}
					personInfo.setLastStatus(living);
					personInfo.setNeedTip(true);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				System.out.println("直播监视出了问题：");
				e.printStackTrace();
			}
		}
	}

    private void onStart(BiliUser livePerson) {
        tipStart(livePerson);
	}

    private void onStop(BiliUser livePerson) {

	}

    private void tipStart(BiliUser p) {
        String userName = new JsonParser().parse(Tools.Network.getSourceCode("https://api.live.bilibili.com/live_user/v1/UserInfo/get_anchor_in_room?roomid=" + p.roomID)).getAsJsonObject().get("data").getAsJsonObject().get("info").getAsJsonObject().get("uname").getAsString();
        String html = Tools.Network.getSourceCode("https://live.bilibili.com/" + p.roomID);
        String jsonInHtml = html.substring(html.indexOf("{\"roomInitRes\":"), html.lastIndexOf("}") + 1);
        JsonObject data = new JsonParser().parse(jsonInHtml).getAsJsonObject().get("baseInfoRes").getAsJsonObject().get("data").getAsJsonObject();
		for (int i = 0, groupListSize = p.fans.size(); i < groupListSize; i++) {
            BiliUser.FansInGroup fans = p.fans.get(i);
			if (ModuleManager.instence.getModule(ModuleFaith.class).getFaith(fans.qq) > 0) {
				Autoreply.sendMessage(fans.group, 0, String.format("%s你关注的主播「%s」开播啦\n房间号:%d\n房间标题:%s\n分区:%s", Autoreply.CC.at(fans.qq), userName, p.roomID, data.get("title").getAsString(), data.get("parent_area_name").getAsString()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
			}
		}
	}
}
