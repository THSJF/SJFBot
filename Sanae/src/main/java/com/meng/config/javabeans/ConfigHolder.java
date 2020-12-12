package com.meng.config.javabeans;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author: 司徒灵羽
 **/
public class ConfigHolder {
    public HashMap<Long,GroupConfig> groupCfgs = new HashMap<>();
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
