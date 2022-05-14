package org.vanillacommunity.springboot.VanillaGlobalChannel;

import java.util.HashMap;
import java.util.Map;

public class ChannelManager {

    private static ChannelManager instance;

    Map<Integer,Channel> channelMap = new HashMap<>();

    private ChannelManager(){}

    public static ChannelManager getInstance()
    {
        if(instance == null) {instance = new ChannelManager();}
        return instance;
    }

    /**
     * 初始化count个频道。
     * @param count 需要初始化的频道的数量
     */
    public void initChannels(int count)
    {
        for(int i = 1 ; i <= count ; i++)
        {
            Channel channel = new Channel(i);
            channelMap.put(i,channel);
            channel.initQueueMap();
        }
    }

    /**
     * 根据数据包内的频道号，分发数据包到对应频道。
     * @param dataPack 信息数据包
     */
    public boolean sendDataPack(DataPack dataPack)
    {
        int channelID = dataPack.getChannelID();
        Channel channel = channelMap.get(channelID);
        if(channel == null)return false;
        channel.queueMap.values().forEach(queue -> queue.offer(dataPack));
        return true;
    }

}
