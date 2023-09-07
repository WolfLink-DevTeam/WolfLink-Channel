package org.vanillacommunity.vanillaglobalchannel.nukkit.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.network.DataPack;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;

public class OnChatEvent implements Listener {
    @EventHandler
    public void chat(PlayerChatEvent e) {
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
