package com.meng.config;

import java.util.*;

public class GroupConfig extends Object {
    public long n=0;
	public int s1=0;
	public int f1=0;

	@Override
	public int hashCode() {
		int i=0;
		return super.hashCode();
	}

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GroupConfig)) {
            return false;
        }
        GroupConfig p = (GroupConfig) obj;
        return this.n == p.n;
    }
}
