package org.vanillacommunity.vanillaglobalchannel.nukkit.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;

public class OnLeaveEvent implements Listener {
    @EventHandler
    public void leave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        PlayerData.dataMap.remove(p.getUniqueId());
        PlayerManager.getInstance().getPlayerMap().remove(p.getUniqueId());
        PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());
        playerData.setChannelID(-1);
    }
}
