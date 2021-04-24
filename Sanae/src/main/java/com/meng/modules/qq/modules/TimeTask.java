package com.meng.modules.qq.modules;

import com.meng.Functions;
import com.meng.config.ConfigManager;
import com.meng.gameData.TouHou.UserInfo;
import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.modules.AimMessage;
import com.meng.tools.SJFExecutors;
import com.meng.tools.Tools;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.At;

/**
 * @author: 司徒灵羽
 **/
public class TimeTask extends BaseModule {

    private final long groupYuTang = 617745343L;
    private final long groupDNFmoDao = 424838564L;
    private final long groupXueXi = 312342896L;
    private final long alice = 1326051907L;
    private final long YYS = 1418780411L;

    private HashSet<TaskBean> tasks = new HashSet<>();

    private String[] morning = new String[]{"早上好","早安","早","大家早上好","大家早上好啊..","Good morning!"};
    private String[] evening = new String[]{"晚安","大家晚安","晚安....","大家晚安....","大家早点休息吧"};   

    public TimeTask(SBot bw) {
        super(bw);
    }

    public void addTask(int onHour, int onMinute, Runnable r) {
        tasks.add(new TaskBean(onHour, onMinute, r));
    }

    @Override
    public TimeTask load() {
        SJFExecutors.executeAtFixedRate(new Runnable(){

                @Override
                public void run() {
                    entity.moduleManager.getModule(AimMessage.class).addTipSingleton(groupYuTang, YYS, Tools.ArrayTool.rfa(new String[]{"想吃YYS", "想食YYS", "想上YYS", new At(YYS).serializeToMiraiCode() + "老婆"}));
                    entity.moduleManager.getModule(AimMessage.class).addTipSingleton(alice, "老婆");
                    Calendar c = Calendar.getInstance();
                    if (getTipHour(c)) {
                        if (c.getActualMaximum(Calendar.DAY_OF_MONTH) == c.get(Calendar.DATE)) {
                            entity.sendGroupMessage(groupDNFmoDao, "最后一天莉，，，看看冒险团商店");
                            entity.sendGroupMessage(groupXueXi, "最后一天莉，，，看看冒险团商店");
                        }
                        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
                            entity.sendGroupMessage(groupDNFmoDao, "星期三莉，，，看看成长胶囊");
                            entity.sendGroupMessage(groupXueXi, "星期三莉，，，看看成长胶囊");
                        }
                    }
                }
            }, 0, 1, TimeUnit.HOURS);
        SJFExecutors.executeAtFixedRate(new Runnable(){

                @Override
                public void run() {
                    Calendar c = Calendar.getInstance();
                    for (TaskBean ts : tasks) {
                        if ((ts.h == -1 || ts.h == c.get(Calendar.HOUR_OF_DAY)) && (ts.min == -1 || ts.min == c.get(Calendar.MINUTE))) {
                            SJFExecutors.execute(ts.r);
                        }
                    }
                }
            }, 0, 1, TimeUnit.MINUTES);

        addTask(23, 0, new Runnable(){

                @Override
                public void run() {
                    for (Group group : entity.getGroups()) {
                        if (ConfigManager.getInstance().isFunctionEnabled(group.getId(), Functions.GroupMessageEvent)) {
                            try {
                                entity.sendGroupMessage(group.getId(), Tools.ArrayTool.rfa(evening));
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } 
                        }
                    }
                    entity.sleeping = true; 
                }
            });
        addTask(6, 0, new Runnable(){

                @Override
                public void run() {
                    entity.sleeping = false;
                    for (Group group : entity.getGroups()) {
                        if (ConfigManager.getInstance().isFunctionEnabled(group.getId(), Functions.GroupMessageEvent)) {
                            try {
                                entity.sendGroupMessage(group.getId(), Tools.ArrayTool.rfa(morning));
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } 
                        }
                    }
                }
            });

        addTask(0, 0, new Runnable(){

                @Override
                public void run() {
                    entity.moduleManager.getModule(UserInfo.class).onNewDay();
                }
            });
        return this;
    }

    private boolean getTipHour(Calendar c) {
        return (c.get(Calendar.HOUR_OF_DAY) == 12 || c.get(Calendar.HOUR_OF_DAY) == 16 || c.get(Calendar.HOUR_OF_DAY) == 22);
    }

    @Override
    public String getModuleName() {
        return "timetask";
    }

    private static class TaskBean {
        public int h;
        public int min;
        public Runnable r;

        public TaskBean(int h, int min, Runnable r) {
            this.h = h;
            this.min = min;
            this.r = r;
        }

        @Override
        public int hashCode() {
            return Objects.hash(h, min, r);
        }
    }
}
