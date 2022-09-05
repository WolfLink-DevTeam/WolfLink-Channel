package org.vanillacommunity.vanillaglobalchannel.nukkit;

import cn.nukkit.Player;
import org.vanillacommunity.vanillaglobalchannel.common.player.IPlayer;

import java.util.UUID;

public class NukkitPlayer implements IPlayer {

    private UUID uuid;

    public NukkitPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getDisplayName() {
        Player p = Nukkit.getInstance().getServer().getPlayer(uuid).orElseGet(null);
        if(p == null)return null;
        return p.getDisplayName();
    }

    @Override
    public void sendMessage(String message) {
        Player p = Nukkit.getInstance().getServer().getPlayer(uuid).orElseGet(null);
        if(p == null)return;
        p.sendMessage(message);
    }

    @Override
    public boolean isOnline() {
        Player p = Nukkit.getInstance().getServer().getPlayer(uuid).orElseGet(null);
        if(p == null)return false;
        return p.isOnline();
    }
}
