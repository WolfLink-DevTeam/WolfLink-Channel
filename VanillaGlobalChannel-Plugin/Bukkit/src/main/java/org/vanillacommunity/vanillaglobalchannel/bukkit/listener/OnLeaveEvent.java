package org.vanillacommunity.vanillaglobalchannel.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;

public class OnLeaveEvent implements Listener {
    @EventHandler
    public void leave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());
        playerData.setChannelID(-1);
        p.sendMessage(ConfigManager.commandLeave);
        // On player leave game -> channel gc
        
        PlayerData.dataMap.remove(p.getUniqueId());
        PlayerManager.getInstance().getPlayerMap().remove(p.getUniqueId());
    }
}
