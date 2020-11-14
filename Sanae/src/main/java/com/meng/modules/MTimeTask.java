package com.meng.modules;

import com.meng.SJFInterfaces.*;
import com.meng.adapter.*;
import com.meng.tools.*;
import java.util.*;
import java.util.concurrent.*;
import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.message.*;

public class MTimeTask extends BaseGroupModule {

    private final long groupYuTang = 617745343L;
    private final long groupDNFmoDao = 424838564L;
    private final long groupXueXi = 312342896L;
    private final long alice = 1326051907L;
    private final long YYS = 1418780411L;

    private HashSet<TaskBean> tasks = new HashSet<>();

    public MTimeTask(BotWrapperEntity bw) {
        super(bw);
    }

    @Override
    public boolean onGroupMessage(GroupMessageEvent gme) {
        return false;
    }

    public void addTask(TaskBean tb) {
        tasks.add(tb);
    }

    @Override
    public MTimeTask load() {
        SJFExecutors.executeAtFixedRate(new Runnable(){

                @Override
                public void run() {
                    entity.moduleManager.getModule(MAimMessage.class).addTipSingleton(groupYuTang, YYS, Tools.ArrayTool.rfa(new String[]{"想吃YYS", "想食YYS", "想上YYS", entity.at(groupYuTang, YYS) + "老婆"}));
                    entity.moduleManager.getModule(MAimMessage.class).addTipSingleton(alice, "老婆");
                    Calendar c = Calendar.getInstance();
                    if (getTipHour(c)) {
                        if (c.getActualMaximum(Calendar.DAY_OF_MONTH) == c.get(Calendar.DATE)) {
                            entity.sjfTx.sendGroupMessage(groupDNFmoDao, "最后一天莉，，，看看冒险团商店");
                            entity.sjfTx.sendGroupMessage(groupXueXi, "最后一天莉，，，看看冒险团商店");
                        }
                        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
                            entity.sjfTx.sendGroupMessage(groupDNFmoDao, "星期三莉，，，看看成长胶囊");
                            entity.sjfTx.sendGroupMessage(groupXueXi, "星期三莉，，，看看成长胶囊");
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
                            ts.r.run();
                        }
                    }
                }
            }, 0, 2, TimeUnit.MINUTES);
        addTask(new TaskBean(23, 0, new Runnable(){

                        @Override
                        public void run() {
                            String[] string = new String[]{"晚安","大家晚安","晚安....","大家晚安....","大家早点休息吧"};   
                            for (Group group : entity.bot.getGroups()) {
                                if (entity.configManager.isFunctionEnbled(group.getId(), MainSwitch.class)) {
                                    if (entity.sjfTx.sendGroupMessage(group.getId(), Tools.ArrayTool.rfa(string)) < 0) {
                                        continue;
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } 
                                }
                            }
                            entity.sleeping = true; 
                        }
                    }));
        addTask(new TaskBean(6, 0, new Runnable(){

                        @Override
                        public void run() {
                            entity.sleeping = false;
                            String[] string = new String[]{"早上好","早安","早","大家早上好","大家早上好啊..","Good morning!"};
                            for (Group group : entity.bot.getGroups()) {
                                if (entity.configManager.isFunctionEnbled(group.getId(), MainSwitch.class)) {
                                    if (entity.sjfTx.sendGroupMessage(group.getId(), Tools.ArrayTool.rfa(string)) < 0) {
                                        continue;
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } 
                                }
                            }
                            entity.sleeping = true; 
                        }
                    }));           
        return this;
    }

    private boolean getTipHour(Calendar c) {
        return (c.get(Calendar.HOUR_OF_DAY) == 12 || c.get(Calendar.HOUR_OF_DAY) == 16 || c.get(Calendar.HOUR_OF_DAY) == 22);
    }

    @Override
    public String getModuleName() {
        return "timetask";
    }

    public static class TaskBean {
        public int h;
        public int min;
        public Runnable r;

        public TaskBean(int h, int min, Runnable r) {
            this.h = h;
            this.min = min;
            this.r = r;
        }
    }
}
