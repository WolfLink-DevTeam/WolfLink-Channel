package org.wolflink.minecraft.plugin.channelbukkit.impl;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.wolflink.minecraft.interfaces.IPlayer;

import java.util.UUID;

public class BukkitPlayer implements IPlayer {

    private final OfflinePlayer offlinePlayer;
    public BukkitPlayer(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }
    public BukkitPlayer(Player player) {
        this.offlinePlayer = player;
    }

    @Override
    public UUID getUUID() {
        return offlinePlayer.getUniqueId();
    }

    @Override
    public String getName() {
        return offlinePlayer.getName();
    }

    @Override
    public void sendMessage(String message) {
        if(offlinePlayer.isOnline()) {
            Player player = offlinePlayer.getPlayer();
            if(player == null) return;
            player.sendMessage(message);
        }
    }

    @Override
    public boolean isOnline() {
        return offlinePlayer.isOnline();
    }
}
