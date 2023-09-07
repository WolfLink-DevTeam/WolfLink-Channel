package org.vanillacommunity.vanillaglobalchannel.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
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
            if(ConfigManager.autoJoinChannel)
            {
                int channelID = ConfigManager.defaultChannelID;
                PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());
                if(playerData.setChannelID(channelID))
                {
                    String str = ConfigManager.commandChannelDefault;
                    str = str.replaceAll("%defaultChannelID%",channelID+"");
                    str = str.replaceAll("%channelName%", ChannelManager.getInstance().getChannelMap().get(channelID).getDisplayName());
                    p.sendMessage(Component.text(str));
                }
                else
                {
                    String str = ConfigManager.commandChannelDefaultNotfound;
                    str = str.replaceAll("%defaultChannelID%",channelID+"");
                    p.sendMessage(Component.text(str));
                }
            }
        }
    }
}
