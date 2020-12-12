package com.meng.config.javabeans;

import com.meng.Functions;
import java.util.EnumSet;

/**
 * @author: 司徒灵羽
 **/
public class GroupConfig {
    public EnumSet<Functions> enabled = EnumSet.allOf(Functions.class);
}
