package org.vanillacommunity.vanillaglobalchannel.common.player;

import java.util.UUID;

public interface IPlayer {

    UUID getUUID();

    String getDisplayName();

    void sendMessage(String message);

    boolean isOnline();

}
