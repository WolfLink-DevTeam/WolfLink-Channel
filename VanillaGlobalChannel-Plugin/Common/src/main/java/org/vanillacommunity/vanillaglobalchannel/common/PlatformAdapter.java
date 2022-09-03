package org.vanillacommunity.vanillaglobalchannel.common;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

public class PlatformAdapter{

    public static PlatformSign platformSign;

    private static PlatformAdapter instance;

    public static PlatformAdapter getInstance() {
        if(instance == null)
        {
            try
            {
                switch (platformSign) {
                    case VELOCITY -> instance = (PlatformAdapter) Class.forName("org.vanillacommunity.vanillaglobalchannel.velocity.VelocityAdapter").getConstructor().newInstance();
                    case BUNGEECORD -> instance = (PlatformAdapter) Class.forName("org.vanillacommunity.vanillaglobalchannel.bungeecord.BungeecordAdapter").getConstructor().newInstance();
                    case BUKKIT -> instance = (PlatformAdapter) Class.forName("org.vanillacommunity.vanillaglobalchannel.bukkit.BukkitAdapter").getConstructor().newInstance();
                    case NUKKIT -> instance = (PlatformAdapter) Class.forName("org.vanillacommunity.vanillaglobalchannel.nukkit.NukkitAdapter").getConstructor().newInstance();
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e)
            { e.printStackTrace();}
        }
        return instance;
    }
    public Path getPath() {
        return null;
    }
}
