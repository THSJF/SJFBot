package com.meng.config;

/**
 * @Description: 配置管理器
 * @author: 司徒灵羽
 **/
import com.meng.annotation.BotData;
import com.meng.bot.Functions;
import com.meng.config.qq.GroupConfig;
import com.meng.help.Permission;
import com.meng.modules.qq.SBot;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import net.mamoe.mirai.contact.Group;

public class ConfigManager {

    @BotData("ncfg.json")
    private ConfigHolder configHolder = new ConfigHolder();

    private static ConfigManager instance;

    public static ConfigManager getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new ConfigManager();
    }

    public ConfigHolder getConfigHolder() {
        return configHolder;
    }

    public void init() {
        DataPersistenter.read(instance);
        for(Person p : configHolder.person){
            if(configHolder.admins.contains(p.qq)){
                p.permission = Permission.Admin;
            }
            if(configHolder.masters.contains(p.qq)){
                p.permission = Permission.Master;
            }
            if(configHolder.owner.contains(p.qq)){
                p.permission = Permission.Owner;
            }
        }
        save();
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

    public void addPerson(Person pi) {
        configHolder.person.add(pi);
        save();
    }

    public Set<Person> getPerson() {
        return Collections.unmodifiableSet(configHolder.person);
    }

    public void removePerson(Person pi) {
        configHolder.person.remove(pi);
        save();
    }

    public Person.Config getPersonConfig(long qq) {
        Person.Config get = configHolder.personConfig.get(qq);
        if (get == null) {
            get = new Person.Config();
            configHolder.personConfig.put(qq, get);
        }
        save();
        return get;
    }

    public Person getPersonFromQQ(long qq) {
        for (Person pi : configHolder.person) {
            if (pi.qq == qq) {
                return pi;
            }
        }
        return null;
    }

    public Person getPersonFromName(String name) {
        for (Person pi : configHolder.person) {
            if (pi.name.equals(name)) {
                return pi;
            }
        }
        return null;
    }

    public Person getPersonFromBid(long bid) {
        for (Person pi : configHolder.person) {
            if (pi.bid == bid) {
                return pi;
            }
        }
        return null;
    }

    public Person getPersonFromLiveId(long lid) {
        for (Person pi : configHolder.person) {
            if (pi.bLiveRoom == lid) {
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

    public boolean isOwner(long qq) {
        return getPersonFromQQ(qq).permission == Permission.Owner;
    }

    public void addOwner(long qq) {
        getPersonFromQQ(qq).permission = Permission.Owner;
        save();
    }

    public void removeOwner(long qq) {
        setNormalPermission(qq);
    }

    public Set<Person> getPersonByPermission(Permission pms){
        Set<Person> persons = new HashSet<Person>();
        for (Person p : configHolder.person) {
            if (p.permission == pms) {
                persons.add(p);
            }
        }
        return persons;
    }
    
    public Set<Person> getOwners() {
        return getPersonByPermission(Permission.Owner);
    }

    public boolean isMaster(long qq) {
        return getPersonFromQQ(qq).permission == Permission.Master;
    }

    public void addMaster(long qq) {
        getPersonFromQQ(qq).permission = Permission.Master;
        save();
    }

    public void removeMaster(long qq) {
        setNormalPermission(qq);
    }

    public Set<Person> getMasters() {
        return getPersonByPermission(Permission.Master);
    }

    public boolean isAdminPermission(long qq) {
        return getPersonFromQQ(qq).permission == Permission.Admin || isMaster(qq);
    }
    
    public void addAdmin(long qq) {
        getPersonFromQQ(qq).permission = Permission.Admin;
        save();
    }

    public void removeAdmin(long qq) {
        setNormalPermission(qq);
    }

    public Set<Person> getAdmins() {
        return getPersonByPermission(Permission.Admin);
    }

    public void setNormalPermission(long qq){
        getPersonFromQQ(qq).permission = Permission.Normal;
        save();
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
            Person pi = getPersonFromQQ(qq);
            if (pi == null) {
                return SBot.instance.getGroupMemberInfo(group, qq).getNick();
            } else {
                return pi.name;
            }
        }
        return nick == null ? String.valueOf(qq) : nick;
    }

    public boolean addBlack(long group, long qq) {
        boolean result = true;
        result &= configHolder.blackQQ.add(qq);
        result &= configHolder.blackGroup.add(group);
        result &= configHolder.groupCfgs.remove(group) == null ? false : true;
        save();
        return result;
    }

    public boolean save() {
        return DataPersistenter.save(this);
    }

    public static class ConfigHolder {

        public HashMap<Long,GroupConfig> groupCfgs = new HashMap<>();
        public HashSet<Long> blockOnlyQQ = new HashSet<>();
        public HashSet<Long> blackQQ = new HashSet<>();
        public HashSet<Long> blackGroup = new HashSet<>();
        public HashSet<String> blockWord = new HashSet<>();
        public HashSet<Person> person = new HashSet<>();
        public HashMap<Long,Person.Config> personConfig = new HashMap<>();
        public HashSet<Long> owner = new HashSet<>();
        public HashSet<Long> masters = new HashSet<>();
        public HashSet<Long> admins = new HashSet<>();
        public HashMap<Long,String> nicknameMap = new HashMap<>();

        public HashMap<Long,String> welcomeMap = new HashMap<>();
        public HashSet<Long> zanSet = new HashSet<>();

    }
}
