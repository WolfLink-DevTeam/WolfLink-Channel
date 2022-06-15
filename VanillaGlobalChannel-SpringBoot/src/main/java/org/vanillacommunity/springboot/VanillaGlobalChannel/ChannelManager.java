package org.vanillacommunity.springboot.VanillaGlobalChannel;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("channelManager")
@Data
public class ChannelManager {

    Map<Integer,Channel> channelList;

    public ChannelManager()
    {
        channelList = new HashMap<>();
    }
    public void init()
    {
        // TODO 记得写好配置文件后来修改这边
        channelList.put(1,new Channel(1,"§d桃源仙境"));
        channelList.put(2,new Channel(2,"§e矿工茶馆"));
        channelList.put(3,new Channel(3,"§b清风河畔"));
        channelList.put(4,new Channel(4,"§6温泉屋"));
    }
    public String getChannelInfoStr()
    {
        List<String> resultList = new ArrayList<>();
        channelList.values().forEach(channel -> resultList.add(channel.toString()));
        return resultList.toString();
    }
}
