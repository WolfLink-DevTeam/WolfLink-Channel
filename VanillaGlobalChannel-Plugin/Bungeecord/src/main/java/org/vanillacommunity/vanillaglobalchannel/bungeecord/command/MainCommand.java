package org.vanillacommunity.vanillaglobalchannel.bungeecord.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;


public class MainCommand extends Command {

    public MainCommand()
    {
        super("vgc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        ProxiedPlayer p = (ProxiedPlayer) sender;
        PlayerData playerData = PlayerData.dataMap.get(p.getUniqueId());

        if(args.length <= 0)
        {
            for(String str : ConfigManager.commandHelp)
            {
                p.sendMessage(str);
            }
        }
        else if(args.length <= 1)
        {
            if(args[0].equalsIgnoreCase("leave"))
            {
                playerData.setChannelID(-1);
                p.sendMessage(ConfigManager.commandLeave);
            }
            else if(args[0].equalsIgnoreCase("channel"))
            {
                int channelID = ConfigManager.defaultChannelID;
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
                    p.sendMessage(str);

                } catch (NumberFormatException ignore)
                {
                    String str = ConfigManager.commandChannelIDNotfound;
                    str = str.replaceAll("%channelID%",args[1]);
                    p.sendMessage(str);
                }
            }
        }
    }
}
