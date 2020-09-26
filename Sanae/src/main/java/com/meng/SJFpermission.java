package com.meng;

/**
 * @author: 司徒灵羽
 **/

public enum SJFpermission {
	Owner(6),
	SJFMaster(5),
	GroupMaster(4),
	SJFAdmin(3),
	GroupAdmin(2),
	Normal(1);

	private SJFpermission(int v){
		value = v;
	}

	private int value=0;
}
