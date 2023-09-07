package org.vanillacommunity.vanillaglobalchannel.velocity;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import org.vanillacommunity.vanillaglobalchannel.common.player.IPlayer;

import java.util.Optional;
import java.util.UUID;

public class VelocityPlayer implements IPlayer {

    private UUID uuid;
    private Optional<Player> optPlayer;

    public VelocityPlayer(UUID uuid)
    {
        this.uuid = uuid;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getDisplayName() {
        this.optPlayer = Velocity.getInstance().getServer().getPlayer(uuid);
        return optPlayer.map(Player::getUsername).orElse(null);
    }

    @Override
    public void sendMessage(String message) {
        this.optPlayer = Velocity.getInstance().getServer().getPlayer(uuid);
        if(optPlayer.isPresent())
        {
            Player p = optPlayer.get();
            p.sendMessage(Component.text(message));
        }
    }

    @Override
    public boolean isOnline() {
        this.optPlayer = Velocity.getInstance().getServer().getPlayer(uuid);
        return optPlayer.isPresent();
    }
}
