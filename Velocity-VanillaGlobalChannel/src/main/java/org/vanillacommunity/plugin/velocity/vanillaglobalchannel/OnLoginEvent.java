package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;

public class OnLoginEvent {
    @Subscribe
    private void login(LoginEvent e)
    {
        Player p = e.getPlayer();
        if(e.getResult().isAllowed())
        {
            if(!PlayerData.dataMap.containsKey(p.getUniqueId()))
            {
                new PlayerData(p.getUsername(),p.getUniqueId());
            }
        }
    }
}
