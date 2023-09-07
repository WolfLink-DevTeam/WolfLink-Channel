package org.vanillacommunity.vanillaglobalchannel.bungeecord;

import org.vanillacommunity.vanillaglobalchannel.common.PlatformAdapter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BungeecordAdapter extends PlatformAdapter {

    @Override
    public Path getPath() { return Paths.get(Bungeecord.getInstance().getDataFolder().getPath());}
}
