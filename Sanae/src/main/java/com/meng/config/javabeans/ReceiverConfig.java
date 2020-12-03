package com.meng.config.javabeans;
import java.util.EnumSet;
import com.meng.EventReceivers;

public class ReceiverConfig {
    public EnumSet<EventReceivers> enabled = EnumSet.allOf(EventReceivers.class);
}
