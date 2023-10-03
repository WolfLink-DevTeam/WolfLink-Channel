package org.wolflink.minecraft.plugin.channelbukkit;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.wolflink.common.ioc.IOC;
import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Result;
import org.wolflink.minecraft.actions.ChannelAction;
import org.wolflink.minecraft.file.Language;
import org.wolflink.minecraft.plugin.channelbukkit.impl.BukkitPlayer;

@Singleton
public class CommandManager implements CommandExecutor {
    @Inject
    private Language lang;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(args.length < 1) {
            for (String str : IOC.getBean(Language.class).getCmdHelp()) {
                player.sendMessage(str);
            }
            return false;
        }
        Result result = null;
        if(args[0].equalsIgnoreCase("join")) {
            result = IOC.getBean(ChannelAction.class).playerJoin(new BukkitPlayer(player));
        }
        else if(args[0].equalsIgnoreCase("leave")) {
            result = IOC.getBean(ChannelAction.class).playerLeave(new BukkitPlayer(player));
        }
        if(result == null) return false;
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME,1f,1f);
        player.sendMessage(result.getMsg());
        return result.isResult();
    }
}
