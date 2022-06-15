package org.vanillacommunity.springboot.VanillaGlobalChannel;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("channelManager")
@ConfigurationProperties(prefix = "channel")
@Data
public class ChannelManager {

    Map<Integer,Channel> channelMap = new HashMap<>();
    List<Channel> channelList = new ArrayList<>();

    public ChannelManager()
    {}
    public void init()
    {
        channelList.forEach(channel -> channelMap.put(channel.getId(),channel));
    }
    public String getChannelInfoStr()
    {
        List<String> resultList = new ArrayList<>();
        channelMap.values().forEach(channel -> resultList.add(channel.toString()));
        return resultList.toString();
    }
}
