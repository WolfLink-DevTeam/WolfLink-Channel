package org.vanillacommunity.vanillaglobalchannel.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.network.DataPack;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;

public class OnChatEvent {

    @Subscribe
    private void chat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());
        //判断是否启用全局聊天
        if(playerData.getChannelID() != -1)
        {
            DataPack dataPack = new DataPack(playerData.getChannelID(), ConfigManager.serverID,p.getUsername(),e.getMessage());
            ChannelManager.getInstance().offerPack(dataPack);
            e.setResult(PlayerChatEvent.ChatResult.denied());
        }
    }

}
