package com.meng.modules;
import com.meng.*;
import com.meng.SJFInterfaces.*;
import com.meng.sjfmd.libs.*;
import java.lang.reflect.*;
import java.util.*;
import javax.tools.*;
import com.meng.dynamicCompile.*;
import java.io.*;

/**
 * @author 司徒灵羽
 */

public class ReflectCommand implements IGroupMessage {

    private BotWrapper wrapper;
    
    public ReflectCommand(BotWrapper bw){
       wrapper = bw;
    }
    
	@Override
	public boolean onGroupMessage(long fromGroup, long fromQQ, String msg, int msgId) {
		if (fromQQ == 2856986197L && msg.startsWith("-invoke")) {
			String[] args = msg.split(" ");
			try {
				Class target = Class.forName(args[1]);
				Object module = ModuleManager.getModule(target);
				if (module == null) {
					module = target.newInstance();
					wrapper.getAutoreply().sendGroupMessage(fromGroup, "新模块:" + target.getName());
				}
				int parseInt = Integer.parseInt(args[3]);
				Class[] paramTypes = new Class[parseInt];
				Object[] param = new Object[parseInt];
				for (int i=0;i < parseInt;++i) {
					getTypeAndValue(args[4 + i], args[4 + parseInt + i], i, paramTypes, param);
				}
				Method m = target.getMethod(args[2], paramTypes);
				wrapper.getAutoreply().sendGroupMessage(fromGroup, "运行结果:\n" + m.invoke(module, param));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				wrapper.getAutoreply().sendGroupMessage(fromGroup, e.toString());
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
