package com.whitemagic2014.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.File;
import java.util.List;
import com.whitemagic2014.events.GroupEvents;
import net.mamoe.mirai.message.MessageEvent;
import com.whitemagic2014.events.MessageEvents;
import com.whitemagic2014.events.RecallEvent;

/**
 * @Description: 创建bot
 * @author: magic chen
 * @date: 2020/8/20 15:46
 **/
public class SJF {

    private Bot bot;

    public SJF() {    
        bot = startBot(2528419891L, "sword_arr", "deviceInfo.json");
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    /**
     * @Name: startBot
     * @Description: 创建bot
     * @Param: account bot账号
     * @Param: pwd  bot密码
     * @Param: deviceInfo 存储设备信息文件
     * @Param: events 注册监听事件
     * @Param: netlog net日志重定向到文件夹路径
     * @Return: net.mamoe.mirai.Bot
     * @Author: magic chen
     * @Date: 2020/8/20 15:54
     **/
    private Bot startBot(Long account, String pwd, String deviceInfo) {
        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo(deviceInfo);
          final Bot bot = BotFactoryJvm.newBot(account, pwd, config);
       
          
       // for (ListenerHost event : events) {
            Events.registerEvents(bot, new GroupEvents());
        Events.registerEvents(bot, new MessageEvents());
        Events.registerEvents(bot, new RecallEvent());
        
            
            // }
          
          bot.login();
    
        
        new Thread(new Runnable(){

                @Override
                public void run() {
                    bot.join(); 
                }
            }).start();
        return bot;
    }

}
