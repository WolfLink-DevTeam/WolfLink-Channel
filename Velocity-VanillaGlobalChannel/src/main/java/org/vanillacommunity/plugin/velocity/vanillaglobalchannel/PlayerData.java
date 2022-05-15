package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
    public static Map<UUID,PlayerData> dataMap = new HashMap<>();
    private String name;
    private UUID uuid;
    private boolean globalChatStatus;
    private int channelID;
    public PlayerData(String name,UUID uuid)
    {
        this.name = name;
        this.uuid = uuid;
        globalChatStatus = false;
        channelID = -1;
        dataMap.put(uuid,this);
    }

    public int getChannelID() throws ObjectMappingException {
        if(channelID == -1)channelID = ConfigManager.getInstance().getDefaultChannelID();
        return channelID;
    }

    public boolean getGlobalChatStatus() {
        return globalChatStatus;
    }
}
