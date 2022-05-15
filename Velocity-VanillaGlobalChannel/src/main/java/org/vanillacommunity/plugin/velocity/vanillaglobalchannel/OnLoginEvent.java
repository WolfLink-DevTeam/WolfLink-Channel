package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.PlayerAvailableCommandsEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;

public class OnLoginEvent {
    @Subscribe
    private void login(LoginEvent e)
    {
        if(e.getResult().isAllowed())
        {
            Player p = e.getPlayer();
            if(!PlayerData.dataMap.containsKey(p.getUniqueId()))
            {
                new PlayerData(p.getUsername(),p.getUniqueId());
            }
        }
    }
}
