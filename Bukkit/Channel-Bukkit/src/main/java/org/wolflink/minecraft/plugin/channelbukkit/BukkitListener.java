package org.wolflink.minecraft.plugin.channelbukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.wolflink.common.ioc.IOC;
import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.file.Configuration;
import org.wolflink.minecraft.interfaces.IPlayer;
import org.wolflink.minecraft.network.Network;
import org.wolflink.minecraft.plugin.channelbukkit.impl.BukkitPlayer;

@Singleton
public class BukkitListener implements Listener {
    @Inject
    private Configuration configuration;

    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    void on(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        IPlayer iPlayer = new BukkitPlayer(player);
        if(iPlayer.isInChannel() || configuration.isForceJoinChannel()) {
            event.setCancelled(true);
            String msg = event.getMessage();
            IOC.getBean(Network.class).sendChat(iPlayer,msg);
        }
    }
}
