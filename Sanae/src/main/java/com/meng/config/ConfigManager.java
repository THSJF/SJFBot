package com.meng.config;

import com.meng.SJFInterfaces.IPersistentData;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.javabeans.ConfigHolder;
import com.meng.config.javabeans.GroupConfig;
import com.meng.config.javabeans.PersonInfo;
import com.meng.tools.SJFExecutors;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @Description: 配置文件管理器
 * @author: 司徒灵羽
 **/

public class ConfigManager implements IPersistentData {

    private ConfigHolder configHolder = new ConfigHolder();
	private static final GroupConfig emptyConfig = new GroupConfig();
    public BotWrapperEntity entity;

    public boolean isBotOff(long fromGroup) {
        return !getGroupConfig(fromGroup).isBotOn();
    }

    public void setBotWrapperEntity(BotWrapperEntity bwe) {
        entity = bwe;
    }

	public ConfigHolder getConfigHolder() {
		return configHolder;
	}

    public void init() {
		DataPersistenter.read(this);
    }

	public void setFunctionEnabled(long fromGroup, int functionID, boolean enable) {
		if (enable) {
			getGroupConfig(fromGroup).f1 |= (1 << functionID);
		} else {
			getGroupConfig(fromGroup).f1 &= ~(1 << functionID);
		}
		save();
	}

	public void addGroupConfig(GroupConfig gc) {
		configHolder.groupConfigs.add(gc);
		save();
	}

	public void removeGroupConfig(long gcn) {
		for (GroupConfig gc : configHolder.groupConfigs) {
			if (gc.n == gcn) {
				configHolder.groupConfigs.remove(gc);
				break;
			}
		}
		save();
	}

	public void removeGroupConfig(GroupConfig gc) {
		configHolder.groupConfigs.remove(gc);
		save();
	}

	public void removeGroupConfig(Collection<GroupConfig> gc) {
		configHolder.groupConfigs.removeAll(gc);
		save();
	}

    public GroupConfig getGroupConfig(long fromGroup) {
        for (GroupConfig gc : configHolder.groupConfigs) {
            if (fromGroup == gc.n) {
                return gc;
            }
        }
        return emptyConfig;
    }

	public Set<GroupConfig> getGroupConfigs() {
		return Collections.unmodifiableSet(configHolder.groupConfigs);
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
		String nick=null;
		nick = configHolder.nicknameMap.get(qq);
		if (nick == null) {
			PersonInfo pi = getPersonInfoFromQQ(qq);
			if (pi == null) {
				nick = entity.getGroupMemberInfo(group, qq).getNameCard();
			} else {
				nick = pi.name;
			}
		}
		return nick;
	}

    public void addBlack(long group, final long qq) {
        configHolder.blackQQ.add(qq);
        configHolder.blackGroup.add(group);
        for (GroupConfig groupConfig : configHolder.groupConfigs) {
            if (groupConfig.n == group) {
                configHolder.groupConfigs.remove(groupConfig);
                break;
            }
        }
        save();
        SJFExecutors.execute(new Runnable() {
				@Override
				public void run() {
					//    HashSet<Group> groups = Tools.CQ.findQQInAllGroup(qq);
					//   for (Group g : groups) {
                    // if (Tools.CQ.ban(g.getId(), qq, 300)) {
                    //    sendMessage(g.getId(), 0, "不要问为什么你会进黑名单，你干了什么自己知道");
                    //   }
					//    }
				}
			});
        entity.sjfTx.sendGroupMessage(BotWrapperEntity.mainGroup, "已将用户" + qq + "加入黑名单");
        entity.sjfTx.sendGroupMessage(BotWrapperEntity.mainGroup, "已将群" + group + "加入黑名单");
    }

	public long getOgg() {
		return -1;
    }

	@Override
	public String getPersistentName() {
		return "ncfg.json";
	}

	@Override
	public Type getDataType() {
		return ConfigHolder.class;
	}

	@Override
	public ConfigHolder getDataBean() {
		return configHolder;
	}

    @Override
    public BotWrapperEntity getWrapper() {
        return entity;
    }

	@Override
	public void setDataBean(Object o) {
		configHolder = (ConfigHolder) o;
	}

    public void save() {
		DataPersistenter.save(this);
    }
}
