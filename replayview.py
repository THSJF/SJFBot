from threp import THReplay

tr=THReplay('th14_03.rpy')

print(tr.getBaseInfo())

print(tr.getBaseInfoDic())

print(tr.getStageScore())

# 获取rep的屏幕移动，list，包含一些字符串
# etc.
# 其中一个字符串：[0     ]→→→→→→→→→→→→→→→→↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↖↖↖↖↖↖↖↖↖↑↑○○○○○○○○○○○○○○○○○○
# 开头括号里的数字表示这是在该stage的第几帧，箭头表示方向，圆圈表示不动
print(tr.getScreenAction())

# 获取rep的按键记录，list，包含一些子list，每个子list包含60个字符串，代表一秒
# etc.
# 其中一个子list：['→', '→', '→', '→', '→', '→', '→', '→', '→', '→', '→', '→', '→', '→', '→', '→', '↑', '↑', '↑', '↑', '↑', '↑', '↑', '↑', '↑', '↑', '↑', '↑', '↑', '↑', '↑', '↑←', '↑←', '↑←', '↑←', '↑←', '↑←', '↑←', '↑←', '↑←', '↑', '↑', '○', '○', '○', '○', '○', '○', '○', '○', '○', '○', '○', '○', '○', '○', '○', '○', '○', '○']
# 每个字符串记录了这帧按下的方向键，箭头表示方向，圆圈表示没按
print(tr.getKeyboardAction())

# 获取rep的机签，字符串
# etc. WASUP
print(tr.getPlayer())

# 获取rep的处理落，浮点数
# etc. 0.03
print(tr.getSlowRate())

# 获取rep的时间，字符串
# etc. 2015/02/17 22:23
print(tr.getDate())

# 获取解析错误信息，list，包含一些字典
# etc. 共有三种错误
# 1.length so short error，单面长度过短错误
# 2.frame read error，单面帧数读取错误
# 3.length read error，单面长度读取错误
print(tr.getError())

# 获取rep的总帧数，整数
# etc. 84565
print(tr.getFrameCount())

# 获取rep中按下Z键的帧数的list，帧数从1开始数
# etc. [63, 98, 136]
print(tr.getZ())

# 获取rep中按下X键的帧数的list，帧数从1开始数
# etc. [193, 480, 766]
print(tr.getX())

# 获取rep中按下C键的帧数的list，帧数从1开始数，这个按键从TH128开始记录，TH125及以前无记录
# etc. [1046, 1260]
print(tr.getC())

# 获取rep中按下Shift键的帧数的list，帧数从1开始数
# etc. [1495, 1532, 1568]
print(tr.getShift())

#载入一个新的replay文件，参数为路径
tr.reload_replay("th15_02.rpy")
