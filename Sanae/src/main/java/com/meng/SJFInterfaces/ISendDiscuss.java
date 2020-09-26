package com.meng.SJFInterfaces;

import java.util.List;

/**
 * @author 司徒灵羽
 */

public interface ISendDiscuss {
	public int sendDiscuss(long toDiscuss, String msg);
	public int sendDiscuss(long toDiscuss, List<String> msg);

}
