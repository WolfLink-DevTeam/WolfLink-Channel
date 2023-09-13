package org.wolflink.minecraft.plugin.channelbukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.wolflink.common.ioc.IOC;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.interfaces.IPlayer;
import org.wolflink.minecraft.network.Network;
import org.wolflink.minecraft.plugin.channelbukkit.impl.BukkitPlayer;

@Singleton
public class ChatListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    void on(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        IPlayer iPlayer = new BukkitPlayer(player);
        if(iPlayer.isInChannel()) {
            event.setCancelled(true);
            String msg = event.getMessage();
            IOC.getBean(Network.class).sendChat(iPlayer,msg);
        }
    }
}
