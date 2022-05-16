package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
    public static Map<UUID,PlayerData> dataMap = new HashMap<>();
    private String name;
    private UUID uuid;
    private int channelID;
    public PlayerData(String name,UUID uuid)
    {
        this.name = name;
        this.uuid = uuid;
        channelID = -1;
        dataMap.put(uuid,this);
    }

    public int getChannelID() {
        return channelID;
    }
    public boolean setChannelID(int id)
    {
        if(id == -1)
        {
            channelID = -1;
            return true;
        }
        if(ChannelManager.getInstance().getChannelInfoMap().containsKey(id))
        {
            ChannelManager.getInstance().changeChannel(uuid,channelID,id);
            channelID = id;
            return true;
        }
        return false;
    }

}
