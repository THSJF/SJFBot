package com.meng.config.qq;

import com.meng.Functions;
import com.meng.config.DataPersistenter;
import com.meng.config.SanaeData;
import com.meng.config.javabeans.PersonInfo;
import com.meng.modules.qq.SBot;
import com.meng.modules.qq.modules.BaseModule;
import java.util.Collections;
import java.util.Set;
import net.mamoe.mirai.contact.Group;

/**
 * @Description: 配置管理器
 * @author: 司徒灵羽
 **/
public class ConfigManager extends BaseModule {

    @SanaeData("ncfg.json")
    private ConfigHolder configHolder = new ConfigHolder();

    public ConfigManager(SBot sb) {
        super(sb);
    }

    @Override
    public String getModuleName() {
        return "configmanager";
    }

	public ConfigHolder getConfigHolder() {
		return configHolder;
	}

    public ConfigManager load() {
		DataPersistenter.read(this);
        return this;
    }

    public boolean setFunctionEnabled(long gid, Functions m, boolean enable) {
        GroupConfig get = configHolder.groupCfgs.get(gid);
        if (get == null) {
            get = new GroupConfig();
            configHolder.groupCfgs.put(gid, get);
        }
        if (enable) {
            get.enabled.add(m);
        } else {
            get.enabled.remove(m);
        }
        save();
        return enable;
    }

	public boolean isFunctionEnabled(long gid, Functions m) {
        GroupConfig get = configHolder.groupCfgs.get(gid);
        if (get == null) {
            get = new GroupConfig();
            configHolder.groupCfgs.put(gid, get);
            save();
        }
        return get.enabled.contains(m);
    }

    public boolean isFunctionEnabled(Group group, Functions m) {
        return isFunctionEnabled(group.getId(), m);
    }

    public PersonConfig getPersonConfig(long qq) {
        PersonConfig get = configHolder.personCfg.get(qq);
        if (get == null) {
            get = new PersonConfig();
            configHolder.personCfg.put(qq, get);
        }
        save();
        return get;
    }

	public void addBlockQQ(long qq) {
        configHolder.blockOnlyQQ.add(qq);
		save();
    }

	public void removeBlockQQ(long qq) {
        configHolder.blockOnlyQQ.remove(qq);
		save();
    }

	public boolean isBlockQQ(long qq) {
        return configHolder.blockOnlyQQ.contains(qq) || configHolder.blackQQ.contains(qq);
    }

	public boolean isBlockOnlyQQ(long qq) {
        return configHolder.blockOnlyQQ.contains(qq);
    }

	public void addBlackQQ(long qq) {
        configHolder.blackQQ.add(qq);
		save();
    }

	public void removeBlackQQ(long q) {
		configHolder.blackQQ.remove(q);
		save();
	}

    public boolean isBlackQQ(long qq) {
        return configHolder.blackQQ.contains(qq);
    }

	public void addBlackGroup(long group) {
		configHolder.blackGroup.add(group);
		save();
	}

	public void removeBlackGroup(long g) {
		configHolder.blackGroup.remove(g);
		save();
	}

    public boolean isBlackGroup(long qq) {
        return configHolder.blackGroup.contains(qq);
    }

	public void addBlockWord(String str) {
		configHolder.blockWord.add(str);
		save();
	}

	public void removeBlockWord(String str) {
		configHolder.blockWord.remove(str);
		save();
	}

    public boolean isBlockWord(String word) {
        for (String nrw : configHolder.blockWord) {
            if (word.contains(nrw)) {
                return true;
            }
        }
        return false;
    }

	public void addPersonInfo(PersonInfo pi) {
		configHolder.personInfos.add(pi);
		save();
	}

	public Set<PersonInfo> getPersonInfo() {
		return Collections.unmodifiableSet(configHolder.personInfos);
	}

	public void removePersonInfo(PersonInfo pi) {
		configHolder.personInfos.remove(pi);
		save();
	}

	public PersonInfo getPersonInfoFromQQ(long qq) {
        for (PersonInfo pi : configHolder.personInfos) {
            if (pi.qq == qq) {
                return pi;
            }
        }
        return null;
    }

    public PersonInfo getPersonInfoFromName(String name) {
        for (PersonInfo pi : configHolder.personInfos) {
            if (pi.name.equals(name)) {
                return pi;
            }
        }
        return null;
    }

    public PersonInfo getPersonInfoFromBid(long bid) {
        for (PersonInfo pi : configHolder.personInfos) {
            if (pi.bid == bid) {
                return pi;
            }
        }
        return null;
    }

	public PersonInfo getPersonInfoFromLiveId(long lid) {
        for (PersonInfo pi : configHolder.personInfos) {
            if (pi.bliveRoom == lid) {
                return pi;
			}
		}
        return null;
	}

    public void setWelcome(long fromGroup, String content) {
        if (content == null) {
            configHolder.welcomeMap.remove(fromGroup);
        } else {
            configHolder.welcomeMap.put(fromGroup, content);
        }
        save();
    }

    public String getWelcome(long fromGroup) {
        return configHolder.welcomeMap.get(fromGroup);
    }

	public boolean isOwner(long fromQQ) {
        return configHolder.owner.contains(fromQQ);
    }

	public void addOwner(long qq) {
		configHolder.owner.add(qq);
		save();
	}

	public void removeOwner(long m) {
		configHolder.owner.remove(m);
		save();
	}

	public Set<Long> getOwners() {
		return Collections.unmodifiableSet(configHolder.owner);
	}

	public boolean isMaster(long fromQQ) {
        return configHolder.masters.contains(fromQQ);
    }

	public void addMaster(long qq) {
		configHolder.masters.add(qq);
		save();
	}

	public void removeMaster(long m) {
		configHolder.masters.remove(m);
		save();
	}

	public Set<Long> getMasters() {
		return Collections.unmodifiableSet(configHolder.masters);
	}

	public boolean isAdminPermission(long fromQQ) {
        return configHolder.admins.contains(fromQQ) || configHolder.masters.contains(fromQQ);
    }

	public void addAdmin(long qq) {
		configHolder.admins.add(qq);
		save();
	}

	public void removeAdmin(long a) {
		configHolder.admins.remove(a);
		save();
	}

	public Set<Long> getAdmins() {
		return Collections.unmodifiableSet(configHolder.admins);
	}

	public void setNickName(long qq, String nickname) {
		if (nickname != null) {
			configHolder.nicknameMap.put(qq, nickname);
		} else {
			configHolder.nicknameMap.remove(qq);
		}
		save();
	}

	public String getNickName(long group, long qq) {
		String nick = configHolder.nicknameMap.get(qq);
		if (nick == null) {
			PersonInfo pi = getPersonInfoFromQQ(qq);
			if (pi == null) {
                try {
                    nick = entity.getGroupMemberInfo(group, qq).getNameCard();
                } catch (Exception ignore) {
                    //mirai有时候会获取失败
                }
            } else {
				nick = pi.name;
			}
		}
		return nick == null ? String.valueOf(qq) : nick;
	}

    public void addBlack(long group, long qq) {
        configHolder.blackQQ.add(qq);
        configHolder.blackGroup.add(group);
        configHolder.groupCfgs.remove(group);
        save();
        entity.sendGroupMessage(SBot.yysGroup, "已将用户" + qq + "加入黑名单");
        entity.sendGroupMessage(SBot.yysGroup, "已将群" + group + "加入黑名单");
    }

}
