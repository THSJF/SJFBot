package com.meng;
import com.meng.game.TouHou.*;
import com.meng.groupMsgProcess.*;
import java.util.*;

public class MyRandom extends Random {

	private HashSet<Integer> hashSet = new HashSet<Integer>();
	private int num = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 36887219237309276L;

	@Override
	public int nextInt() {
		return  (int)(System.currentTimeMillis() % (super.nextInt() + 1));
	}

	@Override
	public int nextInt(int n) {
		return (int)(System.currentTimeMillis() % (super.nextInt(n) + 1));
	}

	public int nextQA() {
		int siz=ModuleManager.instence.getModule(ModuleQA.class).qaList.size();
		if (siz - hashSet.size() < 10) {
			hashSet.clear();
		}
		while (true) {
			num = super.nextInt(siz);
            if (!hashSet.contains(num)) {
                hashSet.add(num);
				break;
			}  
		}
		return num; 
	}
}
