package org.vanillacommunity.vanillaglobalchannel.bungeecord;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.vanillacommunity.vanillaglobalchannel.common.player.IPlayer;

import java.util.UUID;

public class BungeecordPlayer implements IPlayer {

    private UUID uuid;

    public BungeecordPlayer(UUID uuid)
    {
        this.uuid = uuid;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getDisplayName() {
        ProxiedPlayer player = Bungeecord.getInstance().getProxy().getPlayer(uuid);
        return player.getDisplayName();
    }

    @Override
    public void sendMessage(String message) {
        ProxiedPlayer player = Bungeecord.getInstance().getProxy().getPlayer(uuid);
        player.sendMessage(message);
    }

    @Override
    public boolean isOnline() {
        ProxiedPlayer player = Bungeecord.getInstance().getProxy().getPlayer(uuid);
        return player.isConnected();
    }
}
