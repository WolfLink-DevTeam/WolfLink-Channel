package org.wolflink.minecraft.interfaces;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

public interface PlatformAdapter {

    IPlayer adaptPlayer(UUID playerUuid);
    Collection<IPlayer> getOnlinePlayers();
    File getDataFolder();
}
