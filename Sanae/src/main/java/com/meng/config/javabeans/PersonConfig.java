package com.meng.config.javabeans;

/**
 * @author: 司徒灵羽
 **/
public class PersonConfig {

	public static final int qa = 1 << 0;
    public static final int botOn = 1 << 1;
    private int flag = -1;

    public boolean isQaAllowOther() {
        return (flag & (1 << 0)) != 0;
    }

	public void setQaAllowOther(boolean b) {
		if (b) {
			flag |= (1 << 0);
		} else {
			if (isQaAllowOther()) {
				flag -= (1 << 0);
			}
		}
	}

    public boolean isBotOn() {
        return (flag & (1 << 1)) != 0;
    }

    public void setBotOn(boolean b) {
        if (b) {
            flag |= (1 << 1);
        } else {
            if (isQaAllowOther()) {
                flag -= (1 << 1);
            }
        }
	}
}

