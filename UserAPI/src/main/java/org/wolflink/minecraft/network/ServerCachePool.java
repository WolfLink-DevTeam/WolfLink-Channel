package org.wolflink.minecraft.network;

import org.wolflink.common.ioc.IOC;
import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Client;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class ServerCachePool {
    private final Map<String,Client> clientMap = new HashMap<>();
    public Client getClient(String clientAccount) {
        if(clientMap.containsKey(clientAccount)) return clientMap.get(clientAccount);
        else {
            Client client = IOC.getBean(Network.class).queryClient(clientAccount);
            if(client == null) {
                System.out.println("无法获取到客户端 "+clientAccount+" 的信息");
                return null;
            }
            clientMap.put(clientAccount,client);
            return client;
        }
    }
}
