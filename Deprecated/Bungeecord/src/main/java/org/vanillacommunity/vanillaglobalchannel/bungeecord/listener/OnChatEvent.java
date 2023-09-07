package org.vanillacommunity.vanillaglobalchannel.bungeecord.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.network.DataPack;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;

public class OnChatEvent implements Listener {
    @EventHandler
    public void chat(ChatEvent e) {
        if(!(e.getSender() instanceof ProxiedPlayer))return;
        if(e.getMessage().startsWith("/"))return;
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
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
