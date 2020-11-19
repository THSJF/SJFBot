package com.meng.config.javabeans;

import com.meng.modules.Modules;
import java.util.EnumSet;

/**
 * @author: 司徒灵羽
 **/
public class GroupConfig {
    public EnumSet<Modules> enabled = EnumSet.allOf(Modules.class);
}
