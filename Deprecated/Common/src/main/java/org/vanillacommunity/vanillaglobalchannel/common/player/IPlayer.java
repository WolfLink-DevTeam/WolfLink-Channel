package org.vanillacommunity.vanillaglobalchannel.common.player;

import java.util.UUID;

/**
 * 一个通用的，适用于任何 MC 服务端的玩家接口
 */
public interface IPlayer {

    UUID getUUID();

    String getDisplayName();

    void sendMessage(String message);

    boolean isOnline();

}
