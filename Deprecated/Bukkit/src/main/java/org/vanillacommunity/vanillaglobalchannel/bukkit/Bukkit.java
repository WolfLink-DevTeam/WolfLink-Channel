package org.vanillacommunity.vanillaglobalchannel.bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import org.vanillacommunity.vanillaglobalchannel.bukkit.command.MainCommand;
import org.vanillacommunity.vanillaglobalchannel.bukkit.listener.OnChatEvent;
import org.vanillacommunity.vanillaglobalchannel.bukkit.listener.OnLeaveEvent;
import org.vanillacommunity.vanillaglobalchannel.bukkit.listener.OnLoginEvent;
import org.vanillacommunity.vanillaglobalchannel.common.Main;
import org.vanillacommunity.vanillaglobalchannel.common.PlatformAdapter;
import org.vanillacommunity.vanillaglobalchannel.common.PlatformSign;

import java.util.Objects;

public final class Bukkit extends JavaPlugin {

    private static JavaPlugin instance;

    public static JavaPlugin getInstance()
    {
        return instance;
    }
    @Override
    public void onEnable() {
        PlatformAdapter.platformSign = PlatformSign.BUKKIT;
        instance = this;
        // Plugin startup logic
        Main.getInstance().onInit();

        Main.getInstance().onStart();

        Objects.requireNonNull(this.getServer().getPluginCommand("vgc")).setExecutor(new MainCommand());

        this.getServer().getPluginManager().registerEvents(new OnChatEvent(),this);
        this.getServer().getPluginManager().registerEvents(new OnLeaveEvent(),this);
        this.getServer().getPluginManager().registerEvents(new OnLoginEvent(),this);
    }

    @Override
    public void onDisable() {

        Main.getInstance().onShutdown();
        // Plugin shutdown logic
    }
}
