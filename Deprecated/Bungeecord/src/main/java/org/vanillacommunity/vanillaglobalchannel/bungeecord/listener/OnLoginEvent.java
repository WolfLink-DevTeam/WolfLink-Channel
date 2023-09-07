package org.vanillacommunity.vanillaglobalchannel.bungeecord.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.vanillacommunity.vanillaglobalchannel.bungeecord.BungeecordPlayer;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;

public class OnLoginEvent implements Listener {
    @EventHandler
    public void login(PostLoginEvent e)
    {
        ProxiedPlayer p = e.getPlayer();
        if(!PlayerData.dataMap.containsKey(p.getUniqueId()))
        {
            BungeecordPlayer bungeecordPlayer = new BungeecordPlayer(p.getUniqueId());
            new PlayerData(p.getDisplayName(),p.getUniqueId(),bungeecordPlayer);
            PlayerManager.getInstance().getPlayerMap().put(p.getUniqueId(),bungeecordPlayer);
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
