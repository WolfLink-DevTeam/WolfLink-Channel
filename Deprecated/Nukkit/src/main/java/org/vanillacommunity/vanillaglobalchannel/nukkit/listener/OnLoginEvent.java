package org.vanillacommunity.vanillaglobalchannel.nukkit.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;
import org.vanillacommunity.vanillaglobalchannel.nukkit.NukkitPlayer;

public class OnLoginEvent implements Listener {
    @EventHandler
    public void login(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        if(!PlayerData.dataMap.containsKey(p.getUniqueId()))
        {
            NukkitPlayer nukkitPlayer = new NukkitPlayer(p.getUniqueId());
            new PlayerData(p.getDisplayName(),p.getUniqueId(),nukkitPlayer);
            PlayerManager.getInstance().getPlayerMap().put(p.getUniqueId(),nukkitPlayer);
        }
        if(ConfigManager.autoJoinChannel)
        {
            int channelID = ConfigManager.defaultChannelID;
            PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());
            if(playerData.setChannelID(channelID))
            {
                String str = ConfigManager.commandChannelDefault;
                str = str.replaceAll("%defaultChannelID%",channelID+"");
                str = str.replaceAll("%channelName%", ChannelManager.getInstance().getChannelMap().get(channelID).getDisplayName());
                p.sendMessage(str);
            }
            else
            {
                String str = ConfigManager.commandChannelDefaultNotfound;
                str = str.replaceAll("%defaultChannelID%",channelID+"");
                p.sendMessage(str);
            }
        }
    }
}
