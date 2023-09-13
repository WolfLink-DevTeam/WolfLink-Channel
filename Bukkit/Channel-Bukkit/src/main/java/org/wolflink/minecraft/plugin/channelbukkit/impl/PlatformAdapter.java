package org.wolflink.minecraft.plugin.channelbukkit.impl;

import org.bukkit.Bukkit;
import org.wolflink.minecraft.interfaces.IPlayer;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

public class PlatformAdapter implements org.wolflink.minecraft.interfaces.PlatformAdapter {
    @Override
    public IPlayer adaptPlayer(UUID playerUuid) {
        return new BukkitPlayer(Bukkit.getOfflinePlayer(playerUuid));
    }

    @Override
    public Collection<IPlayer> getOnlinePlayers() {
        return null;
    }

    @Override
    public File getDataFolder() {
        return null;
    }
}
