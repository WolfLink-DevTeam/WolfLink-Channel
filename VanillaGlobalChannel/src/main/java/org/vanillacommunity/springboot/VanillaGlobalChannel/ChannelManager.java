package org.vanillacommunity.springboot.VanillaGlobalChannel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

@Configuration("channelManager")
@Order(2)
public class ChannelManager extends Manager{

    public static ChannelManager instance;

    @Getter
    @Setter
    Map<Integer,Channel> channelMap;

    public ChannelManager()
    {
        channelMap = new HashMap<>();
        ChannelManager.instance = this;
    }

    /**
     * 初始化频道。
     */
    @Override
    public void init()
    {

        for(int i = 1 ; i <= 10 ; i++)
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
        channel.queueMap.values().forEach(queue -> {
            queue.offer(dataPack);
            if(queue.size() > 10)queue.poll();
        });
        return true;
    }

}
