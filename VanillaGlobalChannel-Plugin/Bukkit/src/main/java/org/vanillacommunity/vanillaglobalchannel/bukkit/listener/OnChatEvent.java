package org.vanillacommunity.vanillaglobalchannel.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.network.DataPack;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;

public class OnChatEvent implements Listener {
    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());
        //判断是否启用全局聊天
        if(playerData.getChannelID() != -1)
        {
            DataPack dataPack = new DataPack(playerData.getChannelID(), ConfigManager.serverID,p.getDisplayName(),e.getMessage());
            ChannelManager.getInstance().offerPack(dataPack);
            e.setCancelled(true);
        }
    }
}
