package org.vanillacommunity.vanillaglobalchannel.bungeecord.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.vanillacommunity.vanillaglobalchannel.bungeecord.BungeecordPlayer;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;

public class OnLoginEvent implements Listener {
    @EventHandler
    public void login(PostLoginEvent e)
    {
        ProxiedPlayer p = e.getPlayer();
        if(!PlayerData.dataMap.containsKey(p.getUniqueId()))
        {
            BungeecordPlayer bungeecordPlayer = new BungeecordPlayer(p.getUniqueId());
            new PlayerData(p.getDisplayName(),p.getUniqueId(),bungeecordPlayer);
            PlayerManager.getInstance().getPlayerMap().put(p.getUniqueId(),bungeecordPlayer);
        }
    }
}
