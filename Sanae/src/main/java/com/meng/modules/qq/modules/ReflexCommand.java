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
import java.util.Objects;
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
    //-invoke ClassName Signature args...
    //    0       1         2       3...  
	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
        long qqId = gme.getSender().getId();
        long groupId = gme.getGroup().getId();
        String msg = gme.getMessage().contentToString();
		if (qqId == 2856986197L && msg.startsWith("-invoke")) {
            try {
                List<String> cmds = TextLexer.analyze(msg);
                if (cmds.size() != 5) {
                    sendQuote(gme, "参数错误");
                }
                cmds.remove(0);
                Class<?> targetClass = Class.forName(cmds.get(1));
                SignatureAnalyzer.JVMMethodSignature signature =  SignatureAnalyzer.analyze(cmds.get(2));
                Object[] args = new Object[signature.argTypes.length];
                Map<Class<?>,Function<String,Object>> parser = new HashMap<Class<?>,Function<String,Object>>(){
                    {
                        put(byte.class, new Function<String,Object>(){

                                @Override
                                public Object apply(String p1) {
                                    int radix = getRadix(p1);
                                    return Byte.parseByte(prepareNumber(p1, radix), radix);
                                }  
                            });
                        put(short.class, new Function<String,Object>(){

                                @Override
                                public Object apply(String p1) {
                                    int radix = getRadix(p1);
                                    return Short.parseShort(prepareNumber(p1, radix), radix);
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
                                    int radix = getRadix(p1);
                                    return Integer.parseInt(prepareNumber(p1, radix), radix);
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
                                    int radix = getRadix(p1);
                                    return Long.parseLong(prepareNumber(p1, radix), radix);
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
                    final Class<?> type = signature.argTypes[i];
                    Function<String, Object> function = parser.get(type);
                    if (function == null) {
                        function = new Function<String,Object>(){

                            @Override
                            public Object apply(String p1) {
                                return JsonHelper.fromJson(p1, type);
                            }
                        };
                    }
                    args[i] = function.apply(cmds.get(i + 3));
                }
                Object module = entity.moduleManager.getModule(targetClass);
                Method targetMethod = targetClass.getDeclaredMethod(signature.name, signature.argTypes);
                Object result = targetMethod.invoke(module, args);
                String to1 = result.toString();
                sendGroupMessage(groupId, "运行结果:\n" + (to1.equals(Objects.toString(result)) ? JsonHelper.toJson(result) : to1));
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

    private String prepareNumber(String number, int radix) {
        if (radix == 2 || radix == 16) {
            return number.substring(2);
        }
        if (radix == 8) {
            return number.substring(1);
        }
        return number;
    }
}
