package com.meng.config.javabeans;

import java.util.*;

public class GroupConfig extends Object {
    public long n=0;
	public int f1=0;

	public boolean isMainSwitchEnable() {
		return (f1 & (1 << 0)) != 0;
	}

	public void setMainSwitchEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 0);
		} else {
			f1 &= ~(1 << 0);
		}
	}

	public boolean isRepeaterEnable() {
		return (f1 & (1 << 1)) != 0;
	}

	public void setRepeaterEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 1);
		} else {
			f1 &= ~(1 << 1);
		}
	}

	public boolean isMoShenFuSongEnable() {
		return (f1 & (1 << 2)) != 0;
	}

	public void setMoShenFuSongEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 2);
		} else {
			f1 &= ~(1 << 2);
		}
	}

	public boolean isBilibiliEnable() {
		return (f1 & (1 << 3)) != 0;
	}

	public void setBilibiliEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 3);
		} else {
			f1 &= ~(1 << 3);
		}
	}

	public boolean isDiceEnable() {
		return (f1 & (1 << 4)) != 0;
	}

	public void setDiceEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 4);
		} else {
			f1 &= ~(1 << 4);
		}
	}

	public boolean isSpellCollectEnable() {
		return (f1 & (1 << 5)) != 0;
	}

	public void setSpellCollectEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 5);
		} else {
			f1 &= ~(1 << 5);
		}
	}

	public boolean isOCREnable() {
		return (f1 & (1 << 6)) != 0;
	}

	public void setOCREnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 6);
		} else {
			f1 &= ~(1 << 6);
		}
	}

	public boolean isBarcodeEnable() {
		return (f1 & (1 << 7)) != 0;
	}

	public void setBarcodeEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 7);
		} else {
			f1 &= ~(1 << 7);
		}
	}

	public boolean isBannerEnable() {
		return (f1 & (1 << 8)) != 0;
	}

	public void setBannerEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 8);
		} else {
			f1 &= ~(1 << 8);
		}
	}

	public boolean isCQCodeEnable() {
		return (f1 & (1 << 9)) != 0;
	}

	public void setCQCodeEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 9);
		} else {
			f1 &= ~(1 << 9);
		}
	}

	public boolean isMusicEnable() {
		return (f1 & (1 << 10)) != 0;
	}

	public void setMusicEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 10);
		} else {
			f1 &= ~(1 << 10);
		}
	}

	public boolean isPicSearchEnable() {
		return (f1 & (1 << 11)) != 0;
	}

	public void setPicSearchEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 11);
		} else {
			f1 &= ~(1 << 11);
		}
	}

	public boolean isBilibiliLinkEnable() {
		return (f1 & (1 << 12)) != 0;
	}

	public void setBilibiliLinkEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 12);
		} else {
			f1 &= ~(1 << 12);
		}
	}

	public boolean isR15Enable() {
		return (f1 & (1 << 13)) != 0;
	}

	public void setR15Enable(boolean enable) {
		if (enable) {
			f1 |= (1 << 13);
		} else {
			f1 &= ~(1 << 13);
		}
	}

	public boolean isPoHaiEnable() {
		return (f1 & (1 << 14)) != 0;
	}

	public void setPoHaiEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 14);
		} else {
			f1 &= ~(1 << 14);
		}
	}

	public boolean isNvZhuangEnable() {
		return (f1 & (1 << 15)) != 0;
	}

	public void setNvZhuangEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 15);
		} else {
			f1 &= ~(1 << 15);
		}
	}

	public boolean is2019NoVEnable() {
		return (f1 & (1 << 16)) != 0;
	}

	public void set2019NoVEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 16);
		} else {
			f1 &= ~(1 << 16);
		}
	}

	public boolean isSeqEnable() {
		return (f1 & (1 << 17)) != 0;
	}

	public void setSeqEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 17);
		} else {
			f1 &= ~(1 << 17);
		}
	}

	public boolean isDictionaryEnable() {
		return (f1 & (1 << 18)) != 0;
	}

	public void setDictionaryEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 18);
		} else {
			f1 &= ~(1 << 18);
		}
	}

	public boolean isCheHuiMoTuEnable() {
		return (f1 & (1 << 19)) != 0;
	}

	public void setCheHuiMoTuEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 19);
		} else {
			f1 &= ~(1 << 19);
		}
	}

	public boolean isPicEditEnable() {
		return (f1 & (1 << 20)) != 0;
	}

	public void setPicEditEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 20);
		} else {
			f1 &= ~(1 << 20);
		}
	}

	public boolean isUserCountEnable() {
		return (f1 & (1 << 21)) != 0;
	}

	public void setUserCounterEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 21);
		} else {
			f1 &= ~(1 << 21);
		}
	}

	public boolean isGroupCountEnable() {
		return (f1 & (1 << 22)) != 0;
	}

	public void setGroupCounterEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 22);
		} else {
			f1 &= ~(1 << 22);
		}
	}

	public boolean isGroupCountChartEnable() {
		return (f1 & (1 << 23)) != 0;
	}

	public void setGroupCountChartEnable(boolean enable) {
		if (enable) {
			f1 |= (1 << 23);
		} else {
			f1 &= ~(1 << 23);
		}
	}

	@Override
	public int hashCode() {
		return (int)n;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        GroupConfig p = (GroupConfig) obj;
        return this.n == p.n;
    }
}
