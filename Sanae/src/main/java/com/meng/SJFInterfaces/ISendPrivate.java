package com.meng.SJFInterfaces;

import java.util.*;

/**
 * @author 司徒灵羽
 */

public interface ISendPrivate {
	public int sendPrivate(long toQQ, String msg);
	public int sendPrivate(long toQQ, List<String> msg);
}
