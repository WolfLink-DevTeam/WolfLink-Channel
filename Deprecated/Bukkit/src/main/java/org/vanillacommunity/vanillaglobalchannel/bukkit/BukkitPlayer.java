package org.vanillacommunity.vanillaglobalchannel.bukkit;

import org.bukkit.entity.Player;
import org.vanillacommunity.vanillaglobalchannel.common.player.IPlayer;

import java.util.UUID;

public class BukkitPlayer implements IPlayer {

    private UUID uuid;

    public BukkitPlayer(UUID uuid) { this.uuid = uuid; }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getDisplayName() {
        Player p = Bukkit.getInstance().getServer().getPlayer(uuid);
        if(p == null)return null;
        return p.getDisplayName();
    }

    @Override
    public void sendMessage(String message) {
        Player p = Bukkit.getInstance().getServer().getPlayer(uuid);
        if(p == null)return;
        p.sendMessage(message);
    }

    @Override
    public boolean isOnline() {
        Player p = Bukkit.getInstance().getServer().getPlayer(uuid);
        if(p == null)return false;
        return p.isOnline();
    }
}
