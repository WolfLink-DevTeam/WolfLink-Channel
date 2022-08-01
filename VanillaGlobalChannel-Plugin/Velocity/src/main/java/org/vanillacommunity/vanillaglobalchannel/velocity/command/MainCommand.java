package org.vanillacommunity.vanillaglobalchannel.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;


public class MainCommand implements SimpleCommand{
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();

        String[] args = invocation.arguments();

        Player p = (Player) source;

        PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());

        if(args.length <= 0)
        {
            for(String str : ConfigManager.commandHelp)
            {
                p.sendMessage(Component.text(str));
            }
        }
        else if(args.length <= 1)
        {
            if(args[0].equalsIgnoreCase("leave"))
            {
                playerData.setChannelID(-1);
                p.sendMessage(Component.text(ConfigManager.commandLeave));
            }
            else if(args[0].equalsIgnoreCase("channel"))
            {
                int channelID = ConfigManager.defaultChannelID;
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
        else if(args.length <= 2)
        {
            if(args[0].equalsIgnoreCase("channel"))
            {
                try
                {
                    int channelID = Integer.parseInt(args[1]);
                    if(!ChannelManager.getInstance().getChannelMap().containsKey(channelID))throw new NumberFormatException("ignore");
                    playerData.setChannelID(channelID);
                    String str = ConfigManager.commandChannelID;
                    str = str.replaceAll("%channelID%",args[1]);
                    str = str.replaceAll("%channelName%",ChannelManager.getInstance().getChannelMap().get(channelID).getDisplayName());
                    p.sendMessage(Component.text(str));

                } catch (NumberFormatException ignore)
                {
                    String str = ConfigManager.commandChannelIDNotfound;
                    str = str.replaceAll("%channelID%",args[1]);
                    p.sendMessage(Component.text(str));
                }
            }
        }
    }

//    @Override
//    public boolean hasPermission(Invocation invocation) {
//        return invocation.source().hasPermission("vgc.use");
//    }
}
