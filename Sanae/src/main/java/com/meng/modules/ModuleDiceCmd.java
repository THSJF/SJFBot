package com.meng.modules;

import com.meng.BotWrapper;
import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.config.ConfigManager;
import java.util.Random;

/**
 * @author 司徒灵羽
 */

public class ModuleDiceCmd extends BaseGroupModule {

	private String[] cmdMsg;
	private int pos=0;

    public ModuleDiceCmd(BotWrapper bw) {
        super(bw);
    }

	@Override
	public ModuleDiceCmd load() {
		return this;
	}

	@Override
	public boolean onGroupMessage(long fromGroup, long fromQQ, String msg, int msgId) {
		if (msg.charAt(0) != '.') {
			return false;
		}
        Random random = new Random();
		cmdMsg = msg.split(" ");
		pos = 0;
		if (pos < cmdMsg.length) {
			try {
				switch (next()) {
					case ".r":
						String rs = next();
						wrapper.getAutoreply().sendGroupMessage(fromGroup, String.format("%s投掷%s:D100 = %d", ConfigManager.getNickName(fromQQ), rs == null ?"": rs, random.nextInt(100)));
						return true;
					case ".ra":
						String ras = next();
						wrapper.getAutoreply().sendGroupMessage(fromGroup, String.format("%s进行检定:D100 = %d/%s", ConfigManager.getNickName(fromQQ), random.nextInt(Integer.parseInt(ras)), ras));
						return true;
					case ".li":
						wrapper.getAutoreply().sendGroupMessage(fromGroup, String.format("%s的疯狂发作-总结症状:\n1D10=%d\n症状: 狂躁：调查员患上一个新的狂躁症，在1D10=%d小时后恢复理智。在这次疯狂发作中，调查员将完全沉浸于其新的狂躁症状。这是否会被其他人理解（apparent to other people）则取决于守秘人和此调查员。\n1D100=%d\n具体狂躁症: 臆想症（Nosomania）：妄想自己正在被某种臆想出的疾病折磨。(KP也可以自行从狂躁症状表中选择其他症状)", ConfigManager.getNickName(fromQQ), random.nextInt(11), random.nextInt(11), random.nextInt(101)));
						return true;
					case ".ti":
						wrapper.getAutoreply().sendGroupMessage(fromGroup, String.format("%s的疯狂发作-临时症状:\n1D10=%d\n症状: 逃避行为：调查员会用任何的手段试图逃离现在所处的位置，状态持续1D10=%d轮。", ConfigManager.getNickName(fromQQ), random.nextInt(11), random.nextInt(11)));
						return true;
					case ".rd":
						wrapper.getAutoreply().sendGroupMessage(fromGroup, String.format("由于%s %s骰出了: D100=%d", next(), ConfigManager.getNickName(fromGroup, fromQQ), random.nextInt(101)));
						return true;
					case ".nn":
						String name = next();
						if (name == null) {
							ConfigManager.setNickName(fromQQ, null);
							wrapper.getAutoreply().sendGroupMessage(fromGroup, "我以后会用你的QQ昵称称呼你");
							return true;
						}
						if (name.length() > 30) {
							wrapper.getAutoreply().sendGroupMessage(fromGroup, "太长了,记不住");
							return true;
						}
						ConfigManager.setNickName(fromQQ, name);
						wrapper.getAutoreply().sendGroupMessage(fromGroup, "我以后会称呼你为" + name);
						return true;
					case ".help":

						return true;
				}
			} catch (NumberFormatException ne) {
				wrapper.getAutoreply().sendGroupMessage(fromGroup, "参数错误");
			}
		}
		return false;
	}

	private String next() {
		try {
			return cmdMsg[pos++];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
}

