package org.wolflink.minecraft.interfaces;

import java.util.UUID;

/**
 * 一个通用的，适用于任何 MC 服务端的玩家接口(不一定在线)
 */
public interface IPlayer {

    UUID getUUID();

    String getName();

    void sendMessage(String message);

    boolean isOnline();

}
