package org.vanillacommunity.vanillaglobalchannel.nukkit;

import org.vanillacommunity.vanillaglobalchannel.common.PlatformAdapter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NukkitAdapter extends PlatformAdapter {

    @Override
    public Path getPath() { return Paths.get(Nukkit.getInstance().getDataFolder().getPath());}

}
