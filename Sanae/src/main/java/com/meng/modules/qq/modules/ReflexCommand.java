package com.meng.modules.qq.modules;

import com.meng.modules.qq.BaseModule;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.handler.group.IGroupMessageEvent;
import com.meng.tools.JsonHelper;
import com.meng.tools.SignatureAnalyzer;
import com.meng.tools.TextLexer;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @Description: 通过反射执行方法
 * @author: 司徒灵羽
 **/
public class ReflexCommand extends BaseModule implements IGroupMessageEvent {

    public ReflexCommand(SBot bw) {
        super(bw);
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
            try {
                List<String> cmds = TextLexer.analyze(msg);
                Class<?> targetClass = Class.forName(cmds.get(1));
                Class<?>[] argType = SignatureAnalyzer.analyze(cmds.get(3));
                Object[] args = new Object[argType.length];
                Map<Class<?>,Function<String,Object>> parser = new HashMap<Class<?>,Function<String,Object>>(){
                    {
                        put(byte.class, new Function<String,Object>(){

                                @Override
                                public Object apply(String p1) {
                                    return Byte.parseByte(p1, getRadix(p1));
                                }  
                            });
                        put(short.class, new Function<String,Object>(){

                                @Override
                                public Object apply(String p1) {
                                    return Short.parseShort(p1, getRadix(p1));
                                }  
                            });
                        put(char.class, new Function<String,Object>(){

                                @Override
                                public Object apply(String p1) {
                                    if (p1.length() > 1) {
                                        throw new IllegalArgumentException("length > 1");
                                    }
                                    return p1.charAt(0);
                                }  
                            });
                        put(int.class, new Function<String,Object>(){

                                @Override
                                public Object apply(String p1) {
                                    return Integer.parseInt(p1, getRadix(p1));
                                }  
                            });
                        put(float.class, new Function<String,Object>(){

                                @Override
                                public Object apply(String p1) {
                                    return Float.parseFloat(p1);
                                }  
                            });
                        put(long.class, new Function<String,Object>(){

                                @Override
                                public Object apply(String p1) {
                                    return Long.parseLong(p1, getRadix(p1));
                                }  
                            });
                        put(double.class, new Function<String,Object>(){

                                @Override
                                public Object apply(String p1) {
                                    return Double.parseDouble(p1);
                                }  
                            });
                        put(String.class, new Function<String,Object>(){

                                @Override
                                public Object apply(String p1) {
                                    return p1;
                                }  
                            });
                    }
                };
                for (int i = 0; i < args.length; ++i) {
                    final Class<?> t = argType[i];
                    Function<String, Object> function = parser.get(argType);
                    if (function == null) {
                        function = new Function<String,Object>(){

                            @Override
                            public Object apply(String p1) {
                                return JsonHelper.fromJson(p1, t);
                            }
                        };
                    }
                    args[i] = function.apply(cmds.get(i + 4));
                }
                Object module = entity.moduleManager.getModule(targetClass);
                Method targetMethod = targetClass.getDeclaredMethod(cmds.get(2), argType);
                if (module == null) {
                    Object result = targetMethod.invoke(null, args);
                    String json = JsonHelper.toJson(result);
                    sendGroupMessage(groupId, "运行结果:\ntoString()=" + result.toString() + "\ntoJson()=" + json);
				} else {
                    Object result = targetMethod.invoke(module, args);
                    String json = JsonHelper.toJson(result);
                    sendGroupMessage(groupId, "运行结果:\ntoString()=" + result.toString() + "\ntoJson()=" + json);
				}
                return true;
			} catch (Exception e) {
				e.printStackTrace();
				sendGroupMessage(groupId, e.toString());
				return true;
			}
		}
		return false;
	}

    private int getRadix(String p1) {
        p1 = p1.toLowerCase();
        int radix = 10;
        if (p1.startsWith("0x")) {
            radix = 16;
        } else if (p1.startsWith("0b")) {
            radix = 2;
        } else if (p1.startsWith("0")) {
            radix = 8;
        }
        return radix;
    }
}
