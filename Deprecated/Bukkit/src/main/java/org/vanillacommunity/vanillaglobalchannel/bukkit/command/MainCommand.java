package org.vanillacommunity.vanillaglobalchannel.bukkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.vanillacommunity.vanillaglobalchannel.common.ConfigManager;
import org.vanillacommunity.vanillaglobalchannel.common.channel.ChannelManager;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerData;


public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player))return true;
        Player p = (Player) sender;
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
        return true;
    }
}
