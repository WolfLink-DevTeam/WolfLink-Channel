package org.vanillacommunity.vanillaglobalchannel.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;
import org.vanillacommunity.vanillaglobalchannel.velocity.VelocityPlayer;

public class OnLoginEvent {
    @Subscribe
    private void login(LoginEvent e)
    {
        Player p = e.getPlayer();
        if(e.getResult().isAllowed())
        {
            if(!PlayerData.dataMap.containsKey(p.getUniqueId()))
            {
                VelocityPlayer velocityPlayer = new VelocityPlayer(p.getUniqueId());
                new PlayerData(p.getUsername(),p.getUniqueId(),velocityPlayer);
                PlayerManager.getInstance().getPlayerMap().put(p.getUniqueId(),velocityPlayer);
            }
        }
    }
}
