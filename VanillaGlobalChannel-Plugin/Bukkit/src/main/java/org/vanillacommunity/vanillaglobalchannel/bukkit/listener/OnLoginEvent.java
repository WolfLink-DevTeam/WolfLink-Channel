package org.vanillacommunity.vanillaglobalchannel.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.vanillacommunity.vanillaglobalchannel.bukkit.BukkitPlayer;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;

public class OnLoginEvent implements Listener {
    @EventHandler
    public void login(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        if(!PlayerData.dataMap.containsKey(p.getUniqueId()))
        {
            BukkitPlayer bukkitPlayer = new BukkitPlayer(p.getUniqueId());
            new PlayerData(p.getDisplayName(),p.getUniqueId(),bukkitPlayer);
            PlayerManager.getInstance().getPlayerMap().put(p.getUniqueId(),bukkitPlayer);
        }
    }
}
