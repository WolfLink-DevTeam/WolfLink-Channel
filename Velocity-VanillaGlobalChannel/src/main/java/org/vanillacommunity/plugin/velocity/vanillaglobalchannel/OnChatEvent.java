package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class OnChatEvent {

    @Subscribe
    private void chat(PlayerChatEvent e) throws ObjectMappingException {
        Player p = e.getPlayer();
        PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());
        //判断是否启用全局聊天
        if(playerData.getGlobalChatStatus())
        {
            ClientDataPack clientDataPack = new ClientDataPack(playerData.getChannelID(),ConfigManager.getInstance().getServerID(),p.getUsername(),e.getMessage());
            ChannelManager.getInstance().offerPack(clientDataPack);
            e.setResult(PlayerChatEvent.ChatResult.denied());
        }
    }

}
