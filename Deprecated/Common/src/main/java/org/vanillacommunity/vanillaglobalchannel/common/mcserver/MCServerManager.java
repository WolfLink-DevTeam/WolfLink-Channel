package org.vanillacommunity.vanillaglobalchannel.common.mcserver;

import lombok.Data;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.network.NetTransManager;
import org.vanillacommunity.vanillaglobalchannel.common.Tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MCServerManager{

    private static MCServerManager instance;
    Map<Integer, MCServer> serverList;

    private MCServerManager()
    {
        serverList = new HashMap<>();
    }

    public static MCServerManager getInstance() {
        if(instance == null)instance = new MCServerManager();
        return instance;
    }
    public void init()
    {
        System.out.println(ConfigManager.serverInfo1);
        NetTransManager.getInstance().getWebSocket().send("GetServerInfo");
    }
    // 其实应该是解析服务器数据，把字符串解析为 MCServer 对象之后存储到对应的映射表中
    public void bindServerInfo(String str)
    {
        List<String> serverInfo = Tools.stringToList(str);

        for(String serverStr : serverInfo)
        {
            String[] args = serverStr.split("/");

            MCServer server = new MCServer(Integer.parseInt(args[0]),args[1]);

            instance.getServerList().put(server.getId(),server);
        }
        System.out.println(ConfigManager.serverInfoFinish);
    }
}
