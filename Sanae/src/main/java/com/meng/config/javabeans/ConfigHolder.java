package com.meng.config.javabeans;

import java.util.*;

public class ConfigHolder {
    public HashSet<GroupConfig> groupConfigs = new HashSet<>();
    public HashSet<Long> blockOnlyQQ = new HashSet<>();
    public HashSet<Long> blackQQ = new HashSet<>();
    public HashSet<Long> blackGroup = new HashSet<>();
    public HashSet<String> blockWord = new HashSet<>();
    public HashSet<PersonInfo> personInfos = new HashSet<>();
    public HashSet<Long> owner = new HashSet<>();
	public HashSet<Long> masters = new HashSet<>();
    public HashSet<Long> admins = new HashSet<>();
    public HashMap<Long,String> nicknameMap = new HashMap<>();
	
	public HashMap<Long,String> welcomeMap = new HashMap<>();
	public HashMap<Long,PersonConfig> personCfg=new HashMap<>();
	public HashSet<Long> zanSet = new HashSet<>();
	
}
