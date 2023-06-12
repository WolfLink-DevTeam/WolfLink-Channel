package org.vanillacommunity.springboot.VanillaGlobalChannel.mcserver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("mcServerManager")
@ConfigurationProperties(prefix = "mcserver")
@Data
public class MCServerManager{

    private Map<Integer,MCServer> serverMap;

    private List<MCServer> serverlist;

    public MCServerManager()
    {
        serverMap = new HashMap<>();
        serverlist = new ArrayList<>();
    }
    public void init()
    {
        serverlist.forEach(server -> serverMap.put(server.getId(),server));
    }
    public String getServerInfoStr()
    {
        List<String> resultList = new ArrayList<>();
        serverMap.values().forEach(server -> resultList.add(server.toString()));
        return resultList.toString();
    }
}
