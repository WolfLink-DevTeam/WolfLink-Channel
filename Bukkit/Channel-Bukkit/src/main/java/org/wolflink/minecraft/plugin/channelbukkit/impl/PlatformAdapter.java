package org.wolflink.minecraft.plugin.channelbukkit.impl;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.wolflink.minecraft.interfaces.IPlayer;

import java.io.File;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class PlatformAdapter implements org.wolflink.minecraft.interfaces.PlatformAdapter {

    private final JavaPlugin plugin;

    @Override
    public IPlayer adaptPlayer(UUID playerUuid) {
        return new BukkitPlayer(Bukkit.getOfflinePlayer(playerUuid));
    }

    @Override
    public Collection<IPlayer> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().stream().map(BukkitPlayer::new).collect(Collectors.toSet());
    }

    @Override
    public File getDataFolder() {
        return plugin.getDataFolder();
    }
}
