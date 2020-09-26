package com.meng.tip;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.javabeans.GroupConfig;
import com.meng.tools.SJFExecutors;
import com.meng.tools.Tools;
import java.io.File;
import java.util.Calendar;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @author 司徒灵羽
 */

public class MTimeTip extends BaseGroupModule implements Runnable {
    private final long groupYuTang = 617745343L;
    private final long groupDNFmoDao = 424838564L;
    private final long groupXueXi = 312342896L;
    private final long alice = 1326051907L;
    private final long YYS = 1418780411L;
    private boolean tipedYYS = true;
    private boolean tipedAlice = true;

    public MTimeTip(BotWrapperEntity bw) {
        super(bw);
    }

	@Override
	public MTimeTip load() {
		return this;
	}

    @Override
    public void run() {
        while (true) {
            Calendar c = Calendar.getInstance();
            if (c.get(Calendar.MINUTE) == 0) {
                tipedAlice = false;
                if (c.get(Calendar.HOUR_OF_DAY) == 23) {
                    SJFExecutors.execute(new Runnable() {
							@Override
							public void run() {
								for (GroupConfig groupConfig : entity.configManager.getGroupConfigs()) {
									if (groupConfig.isMainSwitchEnable()) {
										if (entity.sjfTx.sendGroupMessage(groupConfig.n, "少女休息中...") < 0) {
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
						});
                }
                if (c.get(Calendar.HOUR_OF_DAY) == 6) {
                    entity.sleeping = false;
                }
                if (c.get(Calendar.HOUR_OF_DAY) % 3 == 0) {
                    tipedYYS = false;
                }

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
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean getTipHour(Calendar c) {
        return (c.get(Calendar.HOUR_OF_DAY) == 12 || c.get(Calendar.HOUR_OF_DAY) == 16 || c.get(Calendar.HOUR_OF_DAY) == 22);
    }

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long fromQQ = gme.getSender().getId();
        long fromGroup = gme.getGroup().getId();
        if (!tipedYYS && fromGroup == groupYuTang && fromQQ == YYS) {
            String[] strings = new String[]{"想吃YYS", "想食YYS", "想上YYS",entity.at(1418780411L) + "老婆"};
            entity.sjfTx.sendGroupMessage(groupYuTang, Tools.ArrayTool.rfa(strings));
            tipedYYS = true;
            return true;
        }
        if (!tipedAlice && fromQQ == alice) {
            entity.sjfTx.sendGroupMessage(fromGroup, entity.image(new File(entity.appDirectory + "pic\\alice.jpg"), fromGroup));
            tipedAlice = true;
            return true;
        }
        return false;
    }

}
