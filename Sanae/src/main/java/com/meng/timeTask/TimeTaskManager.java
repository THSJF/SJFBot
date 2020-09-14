package com.meng.timeTask;
import com.meng.*;
import com.meng.bilibili.*;
import com.meng.config.*;
import com.meng.groupMsgProcess.*;
import com.sobte.cqp.jcq.entity.*;
import java.util.*;

public class TimeTaskManager implements Runnable {

	private interface Task {
		public int getTime();
		public void onTime();
	}

	private ArrayList<Task> taskList=new ArrayList<>();

	private String[] goodMorning = new String[]{
		"早上好",
		"早安",
		"早",
		"大家早上好",
		"大家早上好啊.."
	};
	private String[] goodEvening = new String[]{
		"晚安",
		"大家晚安",
		"晚安....",
		"大家晚安....",
		"大家早点休息吧"
	};

	public TimeTaskManager() {
		taskList.add(new Task(){

				@Override
				public int getTime() {
					return 11_00;
				}

				@Override
				public void onTime() {
					for (long l : ConfigManager.instence.RanConfig.adminList) {
						Autoreply.CQ.sendLikeV2(l, 10);
					}
					for (long l : ConfigManager.instence.SanaeConfig.zanSet) {
						Autoreply.CQ.sendLikeV2(l, 10);
					}
					for (BiliUser bm:ConfigManager.instence.SanaeConfig.biliMaster.values()) {
						for (BiliUser.FansInGroup fans:bm.fans) {
							ModuleFaith mf=ModuleManager.instence.getModule(ModuleFaith.class);
							if (mf.getFaith(fans.qq) > 0) {
								mf.subFaith(fans.qq, 1);
							}
						}
					}
				}
			});
		taskList.add(new Task(){

				@Override
				public int getTime() {
					return 22_00;
				}

				@Override
				public void onTime() {
					Autoreply.ins.threadPool.execute(new Runnable() {
							@Override
							public void run() {
								Autoreply.sendMessage(793236161L, 0, "莉莉晚");
								List<Group> groupList=Autoreply.CQ.getGroupList();
								for (Group g:groupList) {
									if (Autoreply.sendMessage(g.getId(), 0, goodEvening) < 0) {
										continue;
									}
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								Autoreply.sleeping = true;
							}
						});
				}
			});
		taskList.add(new Task(){

				@Override
				public int getTime() {
					return 6_00;
				}

				@Override
				public void onTime() {
					Autoreply.ins.threadPool.execute(new Runnable() {
							@Override
							public void run() {
								Autoreply.sleeping = false;
								List<Group> groupList=Autoreply.CQ.getGroupList();
								for (Group g:groupList) {
									if (g.getId() == 793236161L) {
										Autoreply.sendMessage(g.getId(), 0, "莉莉早");
									}
									if (Autoreply.sendMessage(g.getId(), 0, goodMorning) < 0) {
										continue;
									}
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								ModuleManager.instence.getModule(ModuleMorning.class).reset();
							}
						});
				}
			});
	}

    @Override
    public void run() {
        while (true) {
            Calendar c = Calendar.getInstance();
			for (int i=0;i < taskList.size();++i) {
				Task t=taskList.get(i);
				if (c.get(Calendar.HOUR_OF_DAY) == t.getTime() / 100 && c.get(Calendar.MINUTE) == t.getTime() % 100) {
					t.onTime();
				}
			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
}
