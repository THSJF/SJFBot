package com.meng.modules.qq.richMessage;

import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.Message;

public class Weather {

    private static final String card = "[mirai:app:{\"app\"\\:\"com.tencent.weather\"\\,\"desc\"\\:\"天气\"\\,\"view\"\\:\"RichInfoView\"\\,\"ver\"\\:\"0.0.0.1\"\\,\"prompt\"\\:\"\\[应用\\]天气\"\\,\"meta\"\\:{\"richinfo\"\\:{\"adcode\"\\:\"\"\\,\"air\"\\:\"%s\"\\,\"city\"\\:\"%s\"\\,\"date\"\\:\"%s\"\\,\"max\"\\:\"%s\"\\,\"min\"\\:\"%s\"\\,\"ts\"\\:\"%s\"\\,\"type\"\\:\"201\"\\,\"wind\"\\:\"12\"}}}]";

    private String result;
    public Weather(String air, String city, String date, String maxDegree, String minDegree, String timestamp) {
        result = String.format(card, air, city, date, maxDegree, minDegree, timestamp);
    }

    public Message toMiraiMessage() {
        return MiraiCode.deserializeMiraiCode(result);
    }
}
