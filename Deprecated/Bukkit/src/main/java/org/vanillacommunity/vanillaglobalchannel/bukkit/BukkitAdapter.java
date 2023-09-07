package org.vanillacommunity.vanillaglobalchannel.bukkit;

import org.vanillacommunity.vanillaglobalchannel.common.PlatformAdapter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BukkitAdapter extends PlatformAdapter {

    @Override
    public Path getPath() { return Paths.get(Bukkit.getInstance().getDataFolder().getPath());}
}
