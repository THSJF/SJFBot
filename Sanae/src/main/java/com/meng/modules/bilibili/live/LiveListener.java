package com.meng.modules.bilibili.live;

import com.meng.config.ConfigManager;
import com.meng.config.Person;
import com.meng.modules.bilibili.user.UserApi;
import com.meng.modules.bilibili.user.javabean.RoomInfoByUid;
import java.util.HashSet;
import java.util.Set;

public class LiveListener implements Runnable {

    private Set<OnStatusChangeListener> listeners = new HashSet<>();
    private static LiveListener instance;
    public static LiveListener getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new LiveListener();
    }

    private LiveListener() {}

    public void addListener(OnStatusChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void run() {
        try {
            for (Person personInfo : ConfigManager.getInstance().getPerson()) {
                if (personInfo.bLiveRoom == -1) {
                    continue;
                }
                if (personInfo.bLiveRoom == 0 && personInfo.bid != 0) {
                    RoomInfoByUid sjb = UserApi.getUidToRoom(personInfo.bid);
                    if (sjb.data.roomid == 0) {
                        personInfo.bLiveRoom = -1;
                        ConfigManager.getInstance().save();
                        continue;
                    }
                    personInfo.bLiveRoom = sjb.data.roomid;
                    ConfigManager.getInstance().save();
                    System.out.println("检测到用户" + personInfo.name + "(" + personInfo.bid + ")的直播间" + personInfo.bLiveRoom);
                }
                RoomInfoByUid sjb = UserApi.getUidToRoom(personInfo.bid);
                boolean living = sjb.data.liveStatus == 1;
//                    if (living) {
//                        if (Autoreply.instance.danmakuListenerManager.getListener(personInfo.bliveRoom) == null) {
//                            DanmakuListener dl=new DanmakuListener(new URI("wss://broadcastlv.chat.bilibili.com:2245/sub"), personInfo);
//                            dl.connect();
//                            Autoreply.instance.danmakuListenerManager.listener.add(dl);
//                        }
//                    } else {
//                        DanmakuListener dl=Autoreply.instance.danmakuListenerManager.getListener(personInfo.bliveRoom);
//                        if (dl != null) {
//                            dl.close();
//                        } 
//                    }
                Person livePerson = ConfigManager.getInstance().getPersonFromBid(personInfo.bid);
                livePerson.liveUrl = sjb.data.url;
                livePerson.bLiveRoom = sjb.data.roomid;
                if (livePerson.needTip) {
                    if (!livePerson.lastStatus && living) {
                        onStart(personInfo);
                    } else if (livePerson.lastStatus && !living) {
                        onStop(personInfo);
                    }
                }
                livePerson.lastStatus = living;
                livePerson.needTip = true;
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("直播监视出了问题：");
            e.printStackTrace();
        }
    }

    private void onStart(Person personInfo) {
        for (OnStatusChangeListener listener : listeners) {
            listener.onStart(personInfo);
        }
    }

    private void onStop(Person personInfo) {
        for (OnStatusChangeListener listener : listeners) {
            listener.onStop(personInfo);
        }
    }

    public interface OnStatusChangeListener {
        public void onStart(Person person);
        public void onStop(Person person);
    }
}
