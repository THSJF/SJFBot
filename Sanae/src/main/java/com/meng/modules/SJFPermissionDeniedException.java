package com.meng.modules;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import com.meng.help.Permission;

public class SJFPermissionDeniedException extends RuntimeException {
    public GroupMessageEvent event;

    public SJFPermissionDeniedException(GroupMessageEvent event) {
        this.event = event;
    }
    
}
