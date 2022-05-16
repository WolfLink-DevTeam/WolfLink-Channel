package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;

public class OnChatEvent {

    @Subscribe
    private void chat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());
        //判断是否启用全局聊天
        if(playerData.getChannelID() != -1)
        {
            ClientDataPack clientDataPack = new ClientDataPack(playerData.getChannelID(),ConfigManager.serverID,p.getUsername(),e.getMessage());
            ChannelManager.getInstance().offerPack(clientDataPack);
            e.setResult(PlayerChatEvent.ChatResult.denied());
        }
    }

}
