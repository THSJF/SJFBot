package com.meng.config.qq;

import com.meng.bot.Functions;
import java.util.EnumSet;

/**
 * @author: 司徒灵羽
 **/
public class GroupConfig {
    public EnumSet<Functions> enabled = EnumSet.allOf(Functions.class);
}
