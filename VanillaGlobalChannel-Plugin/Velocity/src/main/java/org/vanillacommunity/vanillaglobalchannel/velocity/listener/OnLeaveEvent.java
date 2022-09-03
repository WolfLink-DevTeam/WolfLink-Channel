package org.vanillacommunity.vanillaglobalchannel.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;

public class OnLeaveEvent {
    @Subscribe
    private void leave(DisconnectEvent e)
    {
        Player p = e.getPlayer();
        PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());
        playerData.setChannelID(-1);
        p.sendMessage(Component.text(ConfigManager.commandLeave));
        // On player leave game -> channel gc

        PlayerData.dataMap.remove(p.getUniqueId());
        PlayerManager.getInstance().getPlayerMap().remove(p.getUniqueId());
    }
}
