package com.meng.modules;

import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.adapter.BotWrapperEntity;
import com.meng.sjfmd.libs.GSON;
import java.lang.reflect.Method;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Description: 通过反射执行方法
 * @author: 司徒灵羽
 **/

public class ReflectCommand extends BaseGroupModule {

    public ReflectCommand(BotWrapperEntity bw) {
        super(bw);
    }

    @Override
    public ReflectCommand load() {
        return null;
    }

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long fromQQ = gme.getSender().getId();
        long fromGroup = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
		if (fromQQ == 2856986197L && msg.startsWith("-invoke")) {
			String[] args = msg.split(" ");
			try {
				Class target = Class.forName(args[1]);
				Object module = entity.moduleManager.getModule(target);
				if (module == null) {
					module = target.newInstance();
					entity.sjfTx.sendGroupMessage(fromGroup, "新模块:" + target.getName());
				}
				int parseInt = Integer.parseInt(args[3]);
				Class[] paramTypes = new Class[parseInt];
				Object[] param = new Object[parseInt];
				for (int i=0;i < parseInt;++i) {
					getTypeAndValue(args[4 + i], args[4 + parseInt + i], i, paramTypes, param);
				}
				Method m = target.getMethod(args[2], paramTypes);
				entity.sjfTx.sendGroupMessage(fromGroup, "运行结果:\n" + m.invoke(module, param));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				entity.sjfTx.sendGroupMessage(fromGroup, e.toString());
				return true;
			}
		}
		return false;
	}

	private void getTypeAndValue(String typeStr, String valueStr, int arrayIndex, Class[] types, Object[] values) {
		switch (typeStr) {
			case "byte" :
				types[arrayIndex] = byte.class;
				values[arrayIndex] = Byte.parseByte(valueStr);
				break;
			case "short" :
				types[arrayIndex] = short.class;
				values[arrayIndex] = Short.parseShort(valueStr);
				break;
			case "char" :
				types[arrayIndex] = char.class;
				values[arrayIndex] = valueStr.charAt(0);
				break;
			case "int" :
				types[arrayIndex] = int.class;
				values[arrayIndex] = Integer.parseInt(valueStr);
				break;
			case "long" :
				types[arrayIndex] = long.class;
				values[arrayIndex] = Long.parseLong(valueStr);
				break;
			case "float" :
				types[arrayIndex] = float.class;
				values[arrayIndex] = Float.parseFloat(valueStr);
				break;
			case "double" :
				types[arrayIndex] = double.class;
				values[arrayIndex] = Double.parseDouble(valueStr);
				break;
			case "boolean" :
				types[arrayIndex] = boolean.class;
				values[arrayIndex] = Boolean.parseBoolean(valueStr);
				break;
			default:
				try {
					types[arrayIndex] = Class.forName(typeStr);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return;
				}
				values[arrayIndex] = GSON.fromJson(valueStr, types[arrayIndex]);
				if (values[arrayIndex] == null) {
					values[arrayIndex] = valueStr;
				}
				break;
		}
	}
}
