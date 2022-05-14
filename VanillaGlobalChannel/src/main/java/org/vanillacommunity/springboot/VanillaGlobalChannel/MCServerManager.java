package org.vanillacommunity.springboot.VanillaGlobalChannel;

import java.util.HashMap;
import java.util.Map;

public class MCServerManager {
    static MCServerManager instance;
    Map<Integer,MCServer> serverList = new HashMap<>();
    private MCServerManager(){}

    public static MCServerManager getInstance()
    {
        if (instance == null) instance = new MCServerManager();
        return instance;
    }

    public void initMCServers()
    {
        // TODO 记得写好配置文件后来修改这边
        serverList.put(1,new MCServer(1,"狼与香辛料亭","§6"));
        serverList.put(2,new MCServer(2,"璎珞","§d"));
    }
    public Map<Integer,MCServer> getServerList()
    {
        return serverList;
    }
}
