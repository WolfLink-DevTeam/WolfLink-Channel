package org.vanillacommunity.vanillaglobalchannel.common.player;

import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 玩家数据，保存了关于玩家的一些详细信息
 * 还负责玩家加入频道和离开频道的一些方法
 */
public class PlayerData {

    IPlayer iPlayer;
    public static Map<UUID,PlayerData> dataMap = new HashMap<>();

    private String displayName;
    private final UUID uuid;
    private int channelID;
    public PlayerData(String name,UUID uuid,IPlayer iPlayer)
    {
        this.iPlayer = iPlayer;
        this.displayName = name;
        this.uuid = uuid;
        channelID = -1;
        dataMap.put(uuid,this);
        if(!ChannelManager.getInstance().getLeftUserList().contains(uuid)) ChannelManager.getInstance().getLeftUserList().add(uuid);
    }

    public int getChannelID() {
        return channelID;
    }
    public boolean setChannelID(int id)
    {
        if(id == -1)
        {
            ChannelManager.getInstance().changeChannel(uuid,channelID,-1);
            channelID = -1;
            return true;
        }
        if(ChannelManager.getInstance().getChannelMap().containsKey(id))
        {
            ChannelManager.getInstance().getChannelMap().get(id).showAnnouncement(iPlayer);
            ChannelManager.getInstance().changeChannel(uuid,channelID,id);
            channelID = id;
            return true;
        }
        return false;
    }
}
