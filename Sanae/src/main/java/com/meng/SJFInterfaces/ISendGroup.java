package com.meng.SJFInterfaces;

import java.util.*;

/**
 * @author 司徒灵羽
 */

public interface ISendGroup {
	public int sendGroup(long toGroup, String msg);
	public int sendGroup(long toGroup, List<String> msg);
}
