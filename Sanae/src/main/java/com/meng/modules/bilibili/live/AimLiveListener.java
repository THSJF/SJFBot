package com.meng.modules.bilibili.live;

import com.meng.config.ConfigManager;
import com.meng.config.Person;
import com.meng.modules.bilibili.user.UserApi;
import com.meng.modules.bilibili.user.javabean.RoomInfoByUid;
import java.util.HashSet;
import java.util.Set;
import com.meng.modules.qq.SBot;
import java.util.List;
import java.util.ArrayList;

public class AimLiveListener implements Runnable {

    private Set<Person> persons = new HashSet<>();
    private static AimLiveListener instance;
    public static AimLiveListener getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new AimLiveListener();
    }

    private AimLiveListener() {
        List<Person> toAdd = new ArrayList<>(16);
        toAdd.add(new Person("FJS", 1526596255, 392470565));
        toAdd.add(new Person("WZQ", 1275651410, 517185438));
        ConfigManager cManager = ConfigManager.getInstance();
        Set<Person> ps = cManager.getConfigHolder().person;
        for (Person p : toAdd) {
            if (!ps.contains(p)) {
                ps.add(p);
                persons.add(p);
            } else {
                for (Person pInCfg : ps) {
                    if (!pInCfg.equals(p)) {
                        continue;  
                    }
                    persons.add(pInCfg);
                }
            }
        }
        persons.add(cManager.getPersonFromBid(21895119));
        persons.add(cManager.getPersonFromBid(64483321));
    }

    @Override
    public void run() {
        try {
            for (Person livePerson : persons) {
                if (livePerson.bLiveRoom == 0 && livePerson.bid != 0) {
                    RoomInfoByUid sjb = UserApi.getUidToRoom(livePerson.bid);
                    if (sjb.data.roomid == 0) {
                        livePerson.bLiveRoom = -1;
                        ConfigManager.getInstance().save();
                        continue;
                    }
                    livePerson.bLiveRoom = sjb.data.roomid;
                    ConfigManager.getInstance().save();
                    System.out.println("检测到用户" + livePerson.name + "(" + livePerson.bid + ")的直播间" + livePerson.bLiveRoom);
                }
                RoomInfoByUid sjb = UserApi.getUidToRoom(livePerson.bid);
                boolean living = sjb.data.liveStatus == 1;
                livePerson.liveUrl = sjb.data.url;
                livePerson.bLiveRoom = sjb.data.roomid;
                if (livePerson.needTip) {
                    if (!livePerson.lastStatus && living) {
                        onStart(livePerson);
                    } else if (livePerson.lastStatus && !living) {
                        onStop(livePerson);
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
        SBot.instance.sendGroupMessage(719324487, String.format("%s开始了直播:%s\n%s", personInfo.name, personInfo.bLiveRoom, LiveApi.getRoomInfo(personInfo.bLiveRoom).toString()));
    }

    private void onStop(Person personInfo) {
        SBot.instance.sendGroupMessage(719324487, String.format("%s直播结束", personInfo.name));
    }
}
