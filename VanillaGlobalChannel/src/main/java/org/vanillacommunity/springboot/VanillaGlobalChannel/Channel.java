package org.vanillacommunity.springboot.VanillaGlobalChannel;

import lombok.Getter;

import java.util.*;

@Getter
public class Channel {

    private final int id;
    private String displayName;

    Map<Integer,Queue<DataPack>> queueMap = new HashMap<>();

    public Channel(int id)
    {
        this.id = id;
        this.displayName = id+"频道";
    }
    public Channel(int id,String displayName)
    {
        this.id = id;
        this.displayName = displayName;
    }
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    /**
     * 给每个服务器注册一个该频道的队列，存储在queueMap中方便获取。
     * 调用该方法时必须保证MCServerManager已经实例化完毕。
     */
    public void initQueueMap()
    {
        MCServerManager.instance.getServerList().keySet().forEach(i -> queueMap.put(i, new LinkedList<>()));
    }
}
