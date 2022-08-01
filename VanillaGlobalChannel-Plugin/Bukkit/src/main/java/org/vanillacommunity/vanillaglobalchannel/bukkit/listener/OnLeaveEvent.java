package org.vanillacommunity.vanillaglobalchannel.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;

public class OnLeaveEvent implements Listener {
    @EventHandler
    public void leave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        PlayerData.dataMap.remove(p.getUniqueId());
        PlayerManager.getInstance().getPlayerMap().remove(p.getUniqueId());
    }
}
