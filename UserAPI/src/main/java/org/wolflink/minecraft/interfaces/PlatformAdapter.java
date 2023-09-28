package org.wolflink.minecraft.interfaces;

import org.wolflink.minecraft.PlatformType;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

public interface PlatformAdapter {

    /**
     * 一个 UUID 对应一个 IPlayer
     */
    IPlayer adaptPlayer(UUID playerUuid);
    Collection<IPlayer> getOnlinePlayers();
    File getDataFolder();
    PlatformType getPlatformType();
}
