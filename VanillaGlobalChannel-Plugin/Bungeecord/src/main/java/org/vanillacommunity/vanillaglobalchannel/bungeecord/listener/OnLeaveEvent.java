package org.vanillacommunity.vanillaglobalchannel.bungeecord.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;

public class OnLeaveEvent implements Listener {
    @EventHandler
    public void leave(PlayerDisconnectEvent e)
    {
        ProxiedPlayer p = e.getPlayer();
        PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());
        playerData.setChannelID(-1);
        p.sendMessage(ConfigManager.commandLeave);
        // On player leave game -> channel gc

        PlayerData.dataMap.remove(p.getUniqueId());
        PlayerManager.getInstance().getPlayerMap().remove(p.getUniqueId());
    }
}
