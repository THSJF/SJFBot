package com.meng.modules.qq.modules;

import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import java.lang.reflect.Method;
import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @Description: 通过反射执行方法
 * @author: 司徒灵羽
 **/
public class ReflexCommand extends BaseModule implements IGroupMessageEvent {

    public ReflexCommand(SBot bw) {
        super(bw);
    }

    @Override
    public ReflexCommand load() {
        return this;
    }
    // not done
    //-invoke ClassName MethodName Signature args
    //    0       1         2           3     4
	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long qqId = gme.getSender().getId();
        long groupId = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
		if (qqId == 2856986197L && msg.startsWith("-invoke")) {
			String[] args = msg.split(" ");
			try {
				Class<?> target = Class.forName(args[1]);
				Object module = entity.moduleManager.getModule(target);
				if (module == null) {
					module = target.newInstance();
					sendGroupMessage(groupId, "新模块:" + target.getName());
				}
				int parseInt = Integer.parseInt(args[3]);
				Class<?>[] paramTypes = new Class<?>[parseInt];
				Object[] param = new Object[parseInt];
				for (int i=0;i < parseInt;++i) {
					getTypeAndValue(args[4 + i], args[4 + parseInt + i], i, paramTypes, param);
				}
				Method m = target.getMethod(args[2], paramTypes);
				sendGroupMessage(groupId, "运行结果:\n" + m.invoke(module, param));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				sendGroupMessage(groupId, e.toString());
				return true;
			}
		}
		return false;
	}

	private void getTypeAndValue(String typeStr, String valueStr, int arrayIndex, Class<?>[] types, Object[] values) {
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
					
					return;
				}
				values[arrayIndex] = valueStr;
				if (values[arrayIndex] == null) {
					values[arrayIndex] = valueStr;
				}
				break;
		}
	}
}
